package io.jasonatwood.permissionmanagersample;

import android.Manifest;
import android.support.v4.app.Fragment;

import io.jasonatwood.permissionmanager.PermissionManager;

public class RequestContactsPermissionFragment extends RequestPermissionFragment {

    public static Fragment newInstance() {
        return new RequestContactsPermissionFragment();
    }

    @Override
    public void onStart() {
        super.onStart();

        PermissionManager.askForPermission(getActivity(),
                getPermission(),
                "Fragment needs Contacts permission to look up contacts. This app won't work without it.",
                permissionGranted -> {
                    if (permissionGranted) {
                        mStatusTextView.setText("Granted");
                    } else {
                        mStatusTextView.setText("Denied");
                    }
                });
    }

    @Override
    protected String getPermission() {
        return Manifest.permission.GET_ACCOUNTS;
    }
}
