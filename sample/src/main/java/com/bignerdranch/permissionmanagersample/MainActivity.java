package com.bignerdranch.permissionmanagersample;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.bignerdranch.permissionmanager.PermissionListener;
import com.bignerdranch.permissionmanager.PermissionManager;

public class MainActivity extends AppCompatActivity {

    private PermissionListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.findFragmentById(R.id.fragment_container) == null) {
            Fragment fragment = MainFragment.newInstance();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        mListener = new PermissionListener() {
            @Override
            public void onResult(boolean permissionGranted) {
                if (permissionGranted) {
                    Toast.makeText(MainActivity.this, "Activity received permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Activity received permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        };

        PermissionManager.getInstance().askForPermission(this,
                Manifest.permission.GET_ACCOUNTS,
                mListener,
                "Activity needs Contacts permission to access your accounts. This app won't work without them.");
    }
}
