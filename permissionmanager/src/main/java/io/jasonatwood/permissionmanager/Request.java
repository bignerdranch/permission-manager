package io.jasonatwood.permissionmanager;

import android.app.PendingIntent;

import java.util.ArrayList;
import java.util.List;

public class Request {

    private String permission;
    private PendingIntent systemRequest;
    private List<PermissionListener> permissionListeners;


    public Request(String permission, PendingIntent systemRequest) {
        this.permission = permission;
        this.systemRequest = systemRequest;
        permissionListeners = new ArrayList<>();
    }

    public void addListener(PermissionListener listener) {
        permissionListeners.add(listener);
    }

    public String getPermission() {
        return permission;
    }

    public PendingIntent getSystemRequest() {
        return systemRequest;
    }

    public List<PermissionListener> getPermissionListeners() {
        return permissionListeners;
    }
}
