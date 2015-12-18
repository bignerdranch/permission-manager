package com.bignerdranch.permissionmanagersample;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class TwoComponentsDifferentPermissionActivity extends TwoPaneActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, TwoComponentsDifferentPermissionActivity.class);
    }

    @Override
    protected Fragment getTopFragment() {
        return RequestContactsPermissionFragment.newInstance();
    }

    @Override
    protected Fragment getBottomFragment() {
        return RequestStoragePermissionFragment.newInstance();
    }
}
