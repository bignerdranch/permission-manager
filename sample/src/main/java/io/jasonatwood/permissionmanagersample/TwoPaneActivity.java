package io.jasonatwood.permissionmanagersample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Sample Activity that will host two fragments side-by-side in two panes
 */
public abstract class TwoPaneActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.split_pane);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.findFragmentById(R.id.split_pane_top_pane) == null) {
            Fragment topFragment = getTopFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.split_pane_top_pane, topFragment)
                    .commit();
        }

        if (fragmentManager.findFragmentById(R.id.split_pane_bottom_pane) == null) {
            Fragment bottomFragment = getBottomFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.split_pane_bottom_pane, bottomFragment)
                    .commit();
        }
    }

    protected abstract Fragment getTopFragment();
    protected abstract Fragment getBottomFragment();
}
