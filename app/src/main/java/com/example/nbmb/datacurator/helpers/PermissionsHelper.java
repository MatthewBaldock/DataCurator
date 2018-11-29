package com.example.nbmb.datacurator.helpers;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class PermissionsHelper {
    public static void checkPermissions(String permission, Activity context)
    {
        if (ContextCompat.checkSelfPermission(context,
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                    permission)) {
            } else {
                ActivityCompat.requestPermissions(context,
                        new String[]{permission},
                        100);

            }
        } else {
            Log.d("RESULTS", "ALREADY GRANTED "+permission);
        }
    }
}
