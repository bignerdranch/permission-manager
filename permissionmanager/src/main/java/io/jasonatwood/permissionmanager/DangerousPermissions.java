package io.jasonatwood.permissionmanager;


import android.Manifest.permission;

import java.util.ArrayList;
import java.util.List;

public class DangerousPermissions {

    public static List<String> getDangerousPermissions() {
        List<String> dangerousPermissions = new ArrayList<>();
        dangerousPermissions.add(permission.ACCESS_COARSE_LOCATION);
        dangerousPermissions.add(permission.ACCESS_FINE_LOCATION);
        dangerousPermissions.add(permission.ADD_VOICEMAIL);
        dangerousPermissions.add(permission.BODY_SENSORS);
        dangerousPermissions.add(permission.CALL_PHONE);
        dangerousPermissions.add(permission.CAMERA);
        dangerousPermissions.add(permission.GET_ACCOUNTS);
        dangerousPermissions.add(permission.PROCESS_OUTGOING_CALLS);
        dangerousPermissions.add(permission.READ_CALENDAR);
        dangerousPermissions.add(permission.READ_CALL_LOG);
        dangerousPermissions.add(permission.READ_CONTACTS);
        dangerousPermissions.add(permission.READ_EXTERNAL_STORAGE);
        dangerousPermissions.add(permission.READ_PHONE_STATE);
        dangerousPermissions.add(permission.READ_SMS);
        dangerousPermissions.add(permission.RECEIVE_MMS);
        dangerousPermissions.add(permission.READ_SMS);
        dangerousPermissions.add(permission.RECEIVE_WAP_PUSH);
        dangerousPermissions.add(permission.RECORD_AUDIO);
        dangerousPermissions.add(permission.SEND_SMS);
        dangerousPermissions.add(permission.USE_SIP);
        dangerousPermissions.add(permission.WRITE_CALENDAR);
        dangerousPermissions.add(permission.WRITE_CALL_LOG);
        dangerousPermissions.add(permission.WRITE_CONTACTS);
        dangerousPermissions.add(permission.WRITE_EXTERNAL_STORAGE);

        // TODO handle permissions that were added after api 15

        return dangerousPermissions;
    }
}
