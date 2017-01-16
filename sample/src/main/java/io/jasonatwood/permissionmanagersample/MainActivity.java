package io.jasonatwood.permissionmanagersample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button singlePermissionButton = (Button) findViewById(R.id.activity_main_single_permission_button);
        singlePermissionButton.setOnClickListener(v -> {
            Intent intent = SinglePermissionRequestActivity.newIntent(MainActivity.this);
            startActivity(intent);
        });

        Button twoComponentsSamePermissionButton = (Button) findViewById(R.id.activity_main_two_components_same_permission_button);
        twoComponentsSamePermissionButton.setOnClickListener(v -> {
            Intent intent = TwoComponentsSamePermissionActivity.newIntent(MainActivity.this);
            startActivity(intent);
        });

        Button twoComponentsDifferentPermissionButton = (Button) findViewById(R.id.activity_main_two_components_different_permission_button);
        twoComponentsDifferentPermissionButton.setOnClickListener(v -> {
            Intent intent = TwoComponentsDifferentPermissionActivity.newIntent(MainActivity.this);
            startActivity(intent);
        });

        Button twoComponententsDiffPermissionSameGroupButton = (Button) findViewById(R.id.activity_main_two_components_different_permission_same_group_button);
        twoComponententsDiffPermissionSameGroupButton.setOnClickListener(v -> {
            Intent intent = TwoComponentsDifferentPermissionSameGroupActivity.newIntent(MainActivity.this);
            startActivity(intent);
        });
    }
}
