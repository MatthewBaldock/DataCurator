package com.example.nbmb.datacurator.service;

import android.app.ActivityManager;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v4.app.JobIntentService;
import android.util.Log;

import com.example.nbmb.datacurator.WifiDisable;
import com.example.nbmb.datacurator.database.DisableDataHelper;

public class DisableTask implements Runnable {
    WifiManager wifi ;
    DisableDataHelper ddhlp;
    public DisableTask(Context context){
        wifi =(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        ddhlp = new DisableDataHelper(context);
        if(wifi.isWifiEnabled())
        {
            wifi.disconnect();
            wifi.setWifiEnabled(false);
        }
    }
    @Override
    public void run()
    {
        Log.d("TASK","Check Wifi");
            wifi.setWifiEnabled(true);
            ddhlp.toggleOff();
            Log.d("TASK","Wifi Enabled again");
    }
}
