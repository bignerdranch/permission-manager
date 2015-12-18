package com.bignerdranch.permissionmanagersample;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bignerdranch.permissionmanager.PermissionListener;
import com.bignerdranch.permissionmanager.PermissionManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionManager.getInstance().askForPermission(this, Manifest.permission.GET_ACCOUNTS, new PermissionListener() {
            @Override
            public void onResult(boolean permissionGranted) {
                // TODO
            }
        }, "some message");

        Toast.makeText(this, "something", Toast.LENGTH_SHORT).show();
    }
}
