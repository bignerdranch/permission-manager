package io.jasonatwood.permissionmanager;

import android.app.PendingIntent;

import java.util.ArrayList;
import java.util.List;

/**
 * package-private
 */
class Request {

    private String permission;
    private PendingIntent systemRequest;
    private List<PermissionListener> permissionListeners;

    Request(String permission, PendingIntent systemRequest) {
        this.permission = permission;
        this.systemRequest = systemRequest;
        permissionListeners = new ArrayList<>();
    }

    void addListener(PermissionListener listener) {
        permissionListeners.add(listener);
    }

    String getPermission() {
        return permission;
    }

    PendingIntent getSystemRequest() {
        return systemRequest;
    }

    List<PermissionListener> getPermissionListeners() {
        return permissionListeners;
    }
}
