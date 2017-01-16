package io.jasonatwood.permissionmanagersample;

import android.Manifest;

import io.jasonatwood.permissionmanager.PermissionManager;

public class RequestReadContactsPermissionFragment extends RequestPermissionFragment {

    public static RequestReadContactsPermissionFragment newInstance() {
        return new RequestReadContactsPermissionFragment();
    }

    @Override
    public void onStart() {
        super.onStart();

        PermissionManager.askForPermission(getActivity(),
                getPermission(),
                "We need permission to read contacts",
                permissionGranted -> updateStatus(permissionGranted));
    }

    @Override
    protected String getPermission() {
        return Manifest.permission.READ_CONTACTS;
    }
}
