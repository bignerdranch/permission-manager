package io.jasonatwood.permissionmanagersample;

import android.Manifest;

import io.jasonatwood.permissionmanager.PermissionListener;
import io.jasonatwood.permissionmanager.PermissionManager;

public class RequestReadContactsPermissionFragment extends RequestPermissionFragment {

    public static RequestReadContactsPermissionFragment newInstance() {
        return new RequestReadContactsPermissionFragment();
    }

    PermissionListener mPermissionListener;

    @Override
    public void onStart() {
        super.onStart();

        PermissionManager.askForPermission(getActivity(),
                getPermission(),
                "We need permission to read contacts",
                mPermissionListener = permissionGranted -> {
                    RequestReadContactsPermissionFragment.this.updateStatus(permissionGranted);
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PermissionManager.unregister(mPermissionListener);
    }

    @Override
    protected String getPermission() {
        return Manifest.permission.READ_CONTACTS;
    }
}
