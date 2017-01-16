package io.jasonatwood.permissionmanagersample;

import android.Manifest;

import io.jasonatwood.permissionmanager.PermissionManager;

public class RequestWriteContactsPermissionFragment extends RequestPermissionFragment {

    public static RequestWriteContactsPermissionFragment newInstance() {
        return new RequestWriteContactsPermissionFragment();
    }

    @Override
    public void onStart() {
        super.onStart();

        PermissionManager.askForPermission(getActivity(),
                getPermission(),
                "We need permission to write contacts",
                permissionGranted -> updateStatus(permissionGranted));
    }

    @Override
    protected String getPermission() {
        return Manifest.permission.WRITE_CONTACTS;
    }
}
