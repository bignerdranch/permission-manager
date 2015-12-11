package com.bignerdranch.permissionmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

/**
 * The permission request workflow requires most work be done from inside an Activity:
 * - make request via PackageManager
 * - display rationale dialog
 * - display request dialog
 * <p>
 * This activity exists so that Permission Manager can delegate UI components to ONLY ONE Activity
 */
public final class PermissionRequestDelegateActivity extends AppCompatActivity

        implements PermissionRationaleDialogFragment.DialogDismissedListener {

    private static final int REQUEST_PERMISSION_DIALOG = 1;
    private static final String TAG_PERMISSION_RATIONALE_DIALOG = "BaseReceiptActivity.TAG_PERMISSION_RATIONALE_DIALOG";
    private static final String EXTRA_PERMISSION = "PermissionRequestDelegateActivity.EXTRA_PERMISSION";
    private static final String EXTRA_RATIONALE_MSG = "PermissionRequestDelegateActivity.EXTRA_RATIONALE_MSG";
    private String mRationaleMsg;
    private String mPermission;

    public static Intent newIntent(Context context, String permission, String rationaleMsg) {
        Intent intent = new Intent(context, PermissionRequestDelegateActivity.class);
        intent.putExtra(EXTRA_PERMISSION, permission);
        intent.putExtra(EXTRA_RATIONALE_MSG, rationaleMsg);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPermission = getIntent().getStringExtra(EXTRA_PERMISSION);
        mRationaleMsg = getIntent().getStringExtra(EXTRA_RATIONALE_MSG);
        tryToGetPermission();
    }

    private void tryToGetPermission() {

        boolean shouldShowRationale = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            shouldShowRationale = shouldShowRequestPermissionRationale(mPermission);
        }

        if (shouldShowRationale && !TextUtils.isEmpty(mRationaleMsg)) {
            displayPermissionRationaleDialog();
        } else {
            askSystemForPermission();
        }
    }

    @Override
    public void onPermissionRationaleDialogDismissed(String permission) {
        if (permission.equals(mPermission)) { // if the dialog was for the permission that we care about
            askSystemForPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_DIALOG:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int result = grantResults[i];
                    PermissionManager.getInstance().onPermissionResponse(permission, result);
                }
                finish();
                overridePendingTransition(0, 0); // disable exit animation in case user hit back button
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0); // disable exit animation in case user hit back button
    }

    private void displayPermissionRationaleDialog() {
        PermissionRationaleDialogFragment permissionRationaleDialogFragment = PermissionRationaleDialogFragment.newInstance(mPermission, mRationaleMsg);
        permissionRationaleDialogFragment.show(getSupportFragmentManager(), TAG_PERMISSION_RATIONALE_DIALOG);
    }

    private void askSystemForPermission() {
        ActivityCompat.requestPermissions(this, new String[]{mPermission}, REQUEST_PERMISSION_DIALOG);
    }
}
