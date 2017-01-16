package io.jasonatwood.permissionmanagersample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button singlePermissionButton = (Button) findViewById(R.id.activity_main_single_permission_button);
        singlePermissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SinglePermissionRequestActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });

        Button twoComponentsSamePermissionButton = (Button) findViewById(R.id.activity_main_two_components_same_permission_button);
        twoComponentsSamePermissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = TwoComponentsSamePermissionActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });

        Button twoComponentsDifferentPermissionButton = (Button) findViewById(R.id.activity_main_two_components_different_permission_button);
        twoComponentsDifferentPermissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = TwoComponentsDifferentPermissionActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }
}
