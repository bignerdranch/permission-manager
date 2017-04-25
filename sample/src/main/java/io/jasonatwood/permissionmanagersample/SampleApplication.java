package io.jasonatwood.permissionmanagersample;

import android.app.Application;

import io.jasonatwood.permissionmanager.PermissionManager;


public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        PermissionManager.initialize(this);
    }
}
