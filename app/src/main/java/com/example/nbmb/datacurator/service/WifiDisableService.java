package com.example.nbmb.datacurator.service;


import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.support.v4.app.JobIntentService;
import android.util.Log;
import android.widget.Toast;


import java.util.Timer;
import java.util.TimerTask;

public class WifiDisableService extends JobIntentService {
    public WifiDisableService()
    {
        super();
    }
    public static void enqueueWork(Context context,Intent work)
    {
        enqueueWork(context, WifiDisableService.class, 1000, work);
    }
    @Override
    protected void onHandleWork(Intent intent) {
        int duration = intent.getIntExtra("duration", 0);
        String onOff = intent.getStringExtra("onoff");
        Log.d("TOGGLE","HERE");
        Log.d("EQUALS_TOGGLE","Equals "+onOff.equals("ON"));
        if (onOff.equals("ON"))
        {
            // We have received work to do.  The system or framework is already
            // holding a wake lock for us at this point, so we can just go.

            WifiManager wifi =(WifiManager)getSystemService(Context.WIFI_SERVICE);

            WifiManager.WifiLock lock =  wifi.createWifiLock(WifiManager.WIFI_MODE_SCAN_ONLY,"WIFI_MODE_SCAN_ONLY");


                try {
                    Log.d("TRY_TOGGLE", "Wifi lock ");
                    wifi.disconnect();
                    lock.acquire();
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
                finally {
                    lock.release();
                }


        }


    }


}
