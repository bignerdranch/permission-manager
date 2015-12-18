package com.bignerdranch.permissionmanagersample;

import android.Manifest;
import android.support.v4.app.Fragment;

import com.bignerdranch.permissionmanager.PermissionListener;
import com.bignerdranch.permissionmanager.PermissionManager;

public class RequestContactsPermissionFragment extends RequestPermissionFragment {

    private PermissionListener mListener;

    public static Fragment newInstance() {
        return new RequestContactsPermissionFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        mListener = new PermissionListener() {
            @Override
            public void onResult(boolean permissionGranted) {
                if (permissionGranted) {
                    mStatusTextView.setText("Granted");
                } else {
                    mStatusTextView.setText("Denied");
                }
            }
        };

        PermissionManager.askForPermission(getActivity(),
                getPermission(),
                mListener,
                "Fragment needs Contacts permission to blah blah. This app won't work without them.");
    }

    @Override
    protected String getPermission() {
        return Manifest.permission.GET_ACCOUNTS;
    }
}
