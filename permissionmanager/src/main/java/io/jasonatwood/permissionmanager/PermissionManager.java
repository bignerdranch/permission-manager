package io.jasonatwood.permissionmanager;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class PermissionManager {

    private static PermissionManager sPermissionManager;
    private static Map<String, Collection<WeakReference<PermissionListener>>> sPendingPermissionRequests;

    /**
     * Ask for a certain permission. Request is asynchronous. Result is delivered to PermissionListener
     *
     * @param activity    Current activity
     * @param permission  The permission you are requesting
     * @param listener    Listener that will receive response from the request. Be sure to hold onto instance. This method will not maintain strong reference.
     * @param rationalMsg Rationale message for why this permission is being requested. Used by the PermissionManager if a Rationale Dialog needs to be displayed
     */
    public static void askForPermission(Activity activity, String permission, PermissionListener listener, String rationalMsg) {

        PermissionManager instance = getInstance();

        instance.cleanupPendingRequestList();

        if (sPendingPermissionRequests.containsKey(permission)) {  // permission request is already in flight
            sPendingPermissionRequests.get(permission).add(new WeakReference<>(listener));
            return;
        }

        sPendingPermissionRequests.put(permission, new ArrayList<WeakReference<PermissionListener>>());
        sPendingPermissionRequests.get(permission).add(new WeakReference<>(listener));

        int permissionCheck = ContextCompat.checkSelfPermission(activity, permission);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            instance.notifyListeners(permission, permissionCheck);
            return;
        }

        Intent intent = PermissionRequestDelegateActivity.newIntent(activity, permission, rationalMsg);
        activity.startActivity(intent);
    }

    /**
     * Package private. This is called by the PermissionRequestDelegateActivity when it has finished asking system for permission
     *
     * @param permission Permission being requested
     * @param result     Was permission granted or denied
     */
    static void onPermissionResponse(String permission, int result) {
        PermissionManager instance = getInstance();
        instance.notifyListeners(permission, result);
    }

    private static PermissionManager getInstance() {
        // only self can instantiate; singleton pattern
        if (sPermissionManager == null) {
            sPermissionManager = new PermissionManager();
        }

        return sPermissionManager;
    }

    private PermissionManager() {
        // only self can instantiate; singleton pattern
        sPendingPermissionRequests = new HashMap<>();
    }

    private void notifyListeners(String permission, int result) {
        // get all listeners for a given permission
        Collection<WeakReference<PermissionListener>> permissionListeners = sPendingPermissionRequests.get(permission);

        // notify each listener
        for (WeakReference<PermissionListener> permissionListenerRef : permissionListeners) {
            PermissionListener permissionListener = permissionListenerRef.get();
            if (permissionListener != null) {
                permissionListener.onResult(result == PackageManager.PERMISSION_GRANTED);
            }
        }

        // remove all listeners for a given permission
        sPendingPermissionRequests.remove(permission);
    }

    private void cleanupPendingRequestList() {
        // It's possible that the weak reference values have been released but that the entry is still in the Map.
        for (Collection<WeakReference<PermissionListener>> listeners : sPendingPermissionRequests.values()) {
            for (WeakReference<PermissionListener> listener : listeners) {
                if (listener.get() == null) {
                    listeners.remove(listener);
                }
            }
        }
    }
}
