package io.jasonatwood.permissionmanagersample;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class TwoComponentsDifferentPermissionSameGroupActivity extends TwoPaneActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, TwoComponentsDifferentPermissionSameGroupActivity.class);
    }

    @Override
    protected Fragment getTopFragment() {
        return RequestWriteContactsPermissionFragment.newInstance();
    }

    @Override
    protected Fragment getBottomFragment() {
        return RequestReadContactsPermissionFragment.newInstance();
    }
}
