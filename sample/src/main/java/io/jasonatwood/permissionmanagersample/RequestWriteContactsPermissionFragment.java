package io.jasonatwood.permissionmanagersample;

import android.Manifest;

import io.jasonatwood.permissionmanager.PermissionListener;
import io.jasonatwood.permissionmanager.PermissionManager;

public class RequestWriteContactsPermissionFragment extends RequestPermissionFragment {

    public static RequestWriteContactsPermissionFragment newInstance() {
        return new RequestWriteContactsPermissionFragment();
    }

    PermissionListener mPermissionListener;

    @Override
    public void onStart() {
        super.onStart();

        PermissionManager.askForPermission(getActivity(),
                getPermission(),
                "We need permission to write contacts",
                mPermissionListener = permissionGranted -> {
                    RequestWriteContactsPermissionFragment.this.updateStatus(permissionGranted);
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PermissionManager.unregister(mPermissionListener);
    }

    @Override
    protected String getPermission() {
        return Manifest.permission.WRITE_CONTACTS;
    }
}
