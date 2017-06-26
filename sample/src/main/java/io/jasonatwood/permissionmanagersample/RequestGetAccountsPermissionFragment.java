package io.jasonatwood.permissionmanagersample;

import android.Manifest;
import android.support.v4.app.Fragment;

import io.jasonatwood.permissionmanager.PermissionManager;

public class RequestGetAccountsPermissionFragment extends RequestPermissionFragment {

    public static Fragment newInstance() {
        return new RequestGetAccountsPermissionFragment();
    }

    @Override
    public void onStart() {
        super.onStart();

        PermissionManager.askForPermission(getActivity(),
                getPermission(),
                "Fragment needs Get Accounts permission to look up contacts. This app won't work without it.",
                permissionGranted -> {
                    updateStatus(permissionGranted);
                });
    }

    @Override
    protected String getPermission() {
        return Manifest.permission.GET_ACCOUNTS;
    }
}
