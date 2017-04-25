package io.jasonatwood.permissionmanagersample;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import io.jasonatwood.permissionmanager.PermissionListener;
import io.jasonatwood.permissionmanager.PermissionManager;

public class SinglePermissionRequestActivity extends AppCompatActivity {

    private PermissionListener mPermissionListener;

    public static Intent newIntent(Context context) {
        return new Intent(context, SinglePermissionRequestActivity.class);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // ask for permission
        PermissionManager.askForPermission(this,
                Manifest.permission.GET_ACCOUNTS,
                "We need Get Accounts permission to access contacts.",
                mPermissionListener = (permissionGranted) -> showToast(permissionGranted)
        );
    }

    private void showToast(boolean permissionGranted) {
        Toast.makeText(SinglePermissionRequestActivity.this, "permission granted? " + permissionGranted, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PermissionManager.unregister(mPermissionListener);
    }
}