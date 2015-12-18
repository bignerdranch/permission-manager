package com.bignerdranch.permissionmanagersample;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Sample Activity that hosts two components that are both *requesting the same permission*
 */
public class TwoComponentsSamePermissionActivity extends TwoPaneActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, TwoComponentsSamePermissionActivity.class);
    }

    @Override
    protected Fragment getTopFragment() {
        return RequestContactsPermissionFragment.newInstance();
    }

    @Override
    protected Fragment getBottomFragment() {
        return RequestContactsPermissionFragment.newInstance();
    }
}
