package io.jasonatwood.permissionmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class PermissionManager {

    private static PermissionManager instance;

    private Map<String, Collection<PermissionListener>> pendingPermissionRequests;
    private Context applicationContext;

    /**
     * TODO
     *
     * @param context
     */
    public static void initialize(Context context) {
        instance = new PermissionManager(context.getApplicationContext());
    }

    /**
     * Ask for a certain permission. Request is asynchronous. Result is delivered to {@link PermissionListener}
     *
     * @param activity    Current activity
     * @param permission  The permission you are requesting
     * @param rationalMsg Rationale message for why this permission is being requested.
     * @param listener    Listener that will receive response from the request.
     */
    public static void askForPermission(Activity activity, String permission, String rationalMsg, PermissionListener listener) {

        if (instance.pendingPermissionRequests.containsKey(permission)) {  // permission request is already in flight
            instance.pendingPermissionRequests.get(permission).add(listener);
            return;
        }

        instance.pendingPermissionRequests.put(permission, new ArrayList<PermissionListener>());
        instance.pendingPermissionRequests.get(permission).add(listener);

        boolean granted = instance.checkSelfPermission(permission);
        if (granted) {
            return;
        }

        Intent intent = PermissionRequestDelegateActivity.newIntent(activity, permission, rationalMsg);
        activity.startActivity(intent);
    }

    /**
     * Package private. This is called by {@link PermissionRequestDelegateActivity} when it has finished asking the system for permission
     *
     * @param permission Permission being requested
     * @param result     Was permission granted or denied
     */
    static void onPermissionResponse(String permission, int result) {
        instance.notifyListeners(permission, result);

        // Now that we've heard back about this one permission,
        // there may be other listeners that are in the same permission GROUP.
        // Since permissions are granted on a GROUP basis, we may be able to notify those other listeners.
        instance.checkAllSelfPermissions();
    }

    private PermissionManager(Context applicationContext) {
        this.applicationContext = applicationContext;

        // only self can instantiate; singleton pattern
        pendingPermissionRequests = new HashMap<>();
    }

    private void checkAllSelfPermissions() {
        for (String permission : pendingPermissionRequests.keySet()) {
            checkSelfPermission(permission);
        }
    }

    /**
     * Determine whether you have been previously granted a particular permission.
     * If so update all listeners.
     *
     * @param permission
     * @return true if granted, false if not granted
     */
    private boolean checkSelfPermission(String permission) {
        int permissionCheck = ContextCompat.checkSelfPermission(applicationContext, permission);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            notifyListeners(permission, permissionCheck);
            return true;
        }
        return false;
    }

    private void notifyListeners(String permission, int result) {
        // get all listeners for a given permission
        Collection<PermissionListener> permissionListeners = pendingPermissionRequests.get(permission);

        if (permissionListeners == null) {
            return;
        }

        // notify each listener
        for (PermissionListener permissionListener : permissionListeners) {
            if (permissionListener != null) {
                permissionListener.onResult(result == PackageManager.PERMISSION_GRANTED);
            }
        }

        // remove all listeners for a given permission
        pendingPermissionRequests.remove(permission);
    }
}
