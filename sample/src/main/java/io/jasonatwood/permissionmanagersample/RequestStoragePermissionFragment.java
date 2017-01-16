package io.jasonatwood.permissionmanagersample;

import android.Manifest;
import android.support.v4.app.Fragment;

import io.jasonatwood.permissionmanager.PermissionListener;
import io.jasonatwood.permissionmanager.PermissionManager;

public class RequestStoragePermissionFragment extends RequestPermissionFragment {
    private PermissionListener mListener;

    public static Fragment newInstance() {
        return new RequestStoragePermissionFragment();
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
                "Fragment needs Storage permission to blah blah. This app won't work without them.");
    }

    @Override
    protected String getPermission() {
        return Manifest.permission.WRITE_EXTERNAL_STORAGE;
    }
}
