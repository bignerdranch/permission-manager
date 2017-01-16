package io.jasonatwood.permissionmanager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public final class PermissionRationaleDialogFragment extends DialogFragment {

    private static final String ARG_RATIONALE_MSG = "PermissionRationaleDialogFragment.ARG_RATIONALE_MSG";
    private static final String ARG_PERMISSION = "PermissionRationaleDialogFragment.ARG_PERMISSION";
    private String mPermission;

    /**
     * Package Private
     *
     * @param permission   Permission being requested
     * @param rationaleMsg Message to display in rationale dialog
     * @return A new instance of this Fragment
     */
    static PermissionRationaleDialogFragment newInstance(String permission, String rationaleMsg) {

        Bundle args = new Bundle();
        args.putString(ARG_RATIONALE_MSG, rationaleMsg);
        args.putString(ARG_PERMISSION, permission);
        PermissionRationaleDialogFragment fragment = new PermissionRationaleDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String rationaleMsg = getArguments().getString(ARG_RATIONALE_MSG);
        mPermission = getArguments().getString(ARG_PERMISSION);

        return new AlertDialog.Builder(getActivity())
                .setMessage(rationaleMsg)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (getActivity() != null
                && getActivity() instanceof DialogDismissedListener) {
            ((DialogDismissedListener) getActivity()).onPermissionRationaleDialogDismissed(mPermission);
        }
    }

    /**
     * Interface to be notified when this dialog is dismissed
     */
    public interface DialogDismissedListener {
        /**
         *  Callback notifying that a dialog has been dismissed
         *
         * @param permission Permission being requested
         */
        void onPermissionRationaleDialogDismissed(String permission);
    }
}
