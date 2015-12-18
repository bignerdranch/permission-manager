package com.bignerdranch.permissionmanagersample;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.bignerdranch.permissionmanager.PermissionListener;
import com.bignerdranch.permissionmanager.PermissionManager;

public class MainFragment extends Fragment {

    private PermissionListener mListener;

    public static Fragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mListener = new PermissionListener() {
            @Override
            public void onResult(boolean permissionGranted) {
                if (permissionGranted) {
                    Toast.makeText(getContext(), "Fragment received storage granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Fragment received storage denied", Toast.LENGTH_SHORT).show();
                }
            }
        };
        PermissionManager.askForPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                mListener,
                "Fragment needs Storage permission to save your info. This app won't work without them.2");
    }

}
