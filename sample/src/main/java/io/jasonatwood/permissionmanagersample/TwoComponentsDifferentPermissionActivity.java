package io.jasonatwood.permissionmanagersample;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Sample Activity that hosts two components that are *requesting different permissions*
 */
public class TwoComponentsDifferentPermissionActivity extends TwoPaneActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, TwoComponentsDifferentPermissionActivity.class);
    }

    @Override
    protected Fragment getTopFragment() {
        return RequestGetAccountsPermissionFragment.newInstance();
    }

    @Override
    protected Fragment getBottomFragment() {
        return RequestStoragePermissionFragment.newInstance();
    }
}
