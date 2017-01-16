package io.jasonatwood.permissionmanager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public final class PermissionManager {

    @SuppressLint("StaticFieldLeak") // who cares since we're holding application context
    private static PermissionManager instance;
    private static boolean requestInFlight = false;

    private List<Request> requests;
    private Context applicationContext;

    /**
     * Initialize PermissionManager
     *
     * @param context any context within your application
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

        // we've already been granted permission, return true and bail out
        boolean granted = instance.checkSelfPermission(permission);
        if (granted) {
            listener.onResult(true);
            return;
        }

        // permission request already in list
        if (instance.listContainsPermission(permission)) {
            instance.addListener(permission, listener);
            return;
        }

        Intent intent = PermissionRequestDelegateActivity.newIntent(activity, permission, rationalMsg);
        int requestCode = permission.hashCode(); // We can't create multiple P.I. with the same intent and the same request code. So cook up a almost certainly unique request code to ensure the system will give us a new P.I.
        PendingIntent pendingIntent = PendingIntent.getActivity(activity, requestCode, intent, 0);
        instance.requests.add(new Request(permission, pendingIntent));
        instance.addListener(permission, listener);

        instance.handleNextRequest();
    }

    private void handleNextRequest() {
        if (requestInFlight) {
            return;
        }

        if (requests.size() > 0) {
            Request request = requests.get(0);
            PendingIntent systemRequest = request.getSystemRequest();
            try {
                systemRequest.send();
                requestInFlight = true;
            } catch (PendingIntent.CanceledException e) {
                requests.remove(0);
            }
        }
    }

    /**
     * Package private. This is called by {@link PermissionRequestDelegateActivity} when it has finished asking the system for permission
     *
     * @param permission Permission being requested
     * @param result     Was permission granted or denied
     */
    static void onPermissionResponse(String permission, int result) {
        instance.handlePermissionResponse(permission, result);
    }

    private void handlePermissionResponse(String permission, int result) {
        boolean granted = result == PackageManager.PERMISSION_GRANTED;
        notifyListeners(permission, granted);

        removePermission(permission);

        // Now that we've heard back about this one permission,
        // there may be other listeners that have a different permission, but are in the same permission GROUP.
        // Since permissions are granted on a GROUP basis, we may have been granted access to some of those other permissions.
        // So let's double check all self permissions before handling the next Request.
        recheckAllSelfPermissions();

        requestInFlight = false;
        handleNextRequest();
    }

    private void recheckAllSelfPermissions() {
        List<Request> requestsToRemove = new ArrayList<>();

        for (Request request : requests) {
            String someOtherPermission = request.getPermission();
            boolean granted = ContextCompat.checkSelfPermission(applicationContext, someOtherPermission) == PackageManager.PERMISSION_GRANTED;
            if (granted) {
                notifyListeners(someOtherPermission, true);
                requestsToRemove.add(request);
            }
        }

        requests.removeAll(requestsToRemove);
    }

    private PermissionManager(Context applicationContext) {
        this.applicationContext = applicationContext;

        // only self can instantiate; singleton pattern
        requests = new ArrayList<>();
    }

    private boolean checkSelfPermission(String permission) {
        int permissionCheck = ContextCompat.checkSelfPermission(applicationContext, permission);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    private void notifyListeners(String permission, boolean granted) {
        // get all listeners for a given permission
        List<PermissionListener> permissionListeners = getListenersForPermission(permission);

        if (permissionListeners == null) {
            return;
        }

        // notify each listener
        for (PermissionListener permissionListener : permissionListeners) {
            if (permissionListener != null) {
                permissionListener.onResult(granted);
            }
        }
    }

    private boolean listContainsPermission(String permission) {
        for (Request request : requests) {
            if (request.getPermission().equals(permission)) {
                return true;
            }
        }
        return false;
    }

    private void addListener(String permission, PermissionListener listener) {
        for (Request request : requests) {
            if (request.getPermission().equals(permission)) {
                request.addListener(listener);
            }
        }
    }

    private List<PermissionListener> getListenersForPermission(String permission) {
        for (Request request : requests) {
            if (request.getPermission().equals(permission)) {
                return request.getPermissionListeners();
            }
        }
        return null;
    }

    private void removePermission(String permission) {
        Request requestToRemove = null;
        for (Request request : requests) {
            if (request.getPermission().equals(permission)) {
                requestToRemove = request;
                break;
            }
        }

        if (requestToRemove != null) {
            requests.remove(requestToRemove);
        }
    }
}
