package io.jasonatwood.permissionmanagersample;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import io.jasonatwood.permissionmanager.PermissionListener;
import io.jasonatwood.permissionmanager.PermissionManager;

public class SinglePermissionRequestActivity extends AppCompatActivity {

    private PermissionListener mListener;

    public static Intent newIntent(Context context) {
        return new Intent(context, SinglePermissionRequestActivity.class);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // create a listener
        mListener = new PermissionListener() {
            @Override
            public void onResult(boolean permissionGranted) {
                // handle boolean
            }
        };

        // ask for permission
        PermissionManager.askForPermission(this,
                Manifest.permission.GET_ACCOUNTS,
                mListener,
                "We need Contacts permission to blah blah..");
    }
}
