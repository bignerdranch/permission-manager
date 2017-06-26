package io.jasonatwood.permissionmanagersample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A Fragment to request a permission
 */
public abstract class RequestPermissionFragment extends Fragment {

    protected TextView mStatusTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_permission_request, container, false);

        TextView permissionTextView = (TextView) root.findViewById(R.id.fragment_permission_request_permission_name_textview);
        permissionTextView.setText(getPermission());

        mStatusTextView = (TextView) root.findViewById(R.id.fragment_permission_request_permission_status_textview);

        return root;
    }

    protected abstract String getPermission();

    protected void updateStatus(boolean permissionGranted) {
        if (permissionGranted) {
            mStatusTextView.setText("Granted");
        } else {
            mStatusTextView.setText("Denied");
        }
    }
}
