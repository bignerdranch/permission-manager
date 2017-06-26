package io.jasonatwood.permissionmanagersample;

import android.Manifest;
import android.support.v4.app.Fragment;

import io.jasonatwood.permissionmanager.PermissionListener;
import io.jasonatwood.permissionmanager.PermissionManager;

public class RequestStoragePermissionFragment extends RequestPermissionFragment {

    public static Fragment newInstance() {
        return new RequestStoragePermissionFragment();
    }

    PermissionListener mPermissionListener;

    @Override
    public void onStart() {
        super.onStart();

        PermissionManager.askForPermission(getActivity(),
                getPermission(),
                "Fragment needs Storage permission to store stuff. This app won't work without it.",
                mPermissionListener = permissionGranted -> {
                    RequestStoragePermissionFragment.this.updateStatus(permissionGranted);
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PermissionManager.unregister(mPermissionListener);
    }

    @Override
    protected String getPermission() {
        return Manifest.permission.WRITE_EXTERNAL_STORAGE;
    }
}
