package com.example.nbmb.datacurator.service;


import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.support.v4.app.JobIntentService;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class WifiDisableService extends JobIntentService {
    public WifiDisableService()
    {
        super();
    }
    private static Timer timer = new Timer();
    @Override
    protected void onHandleWork(Intent intent) {
        int duration = intent.getIntExtra("duration", 0);
        String onOff = intent.getStringExtra("onoff");
        if (onOff == "ON")
        {
            // We have received work to do.  The system or framework is already
            // holding a wake lock for us at this point, so we can just go.
            Log.i("SimpleJobIntentService", "Executing work: " + intent);
            String label = intent.getStringExtra("label");
            if (label == null) {
                label = intent.toString();
            }
            for (int i = 0; i < 5; i++) {
                Log.i("SimpleJobIntentService", "Running service " + (i + 1)
                        + "/5 @ " + SystemClock.elapsedRealtime());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
            Log.i("SimpleJobIntentService", "Completed service @ " + SystemClock.elapsedRealtime());


        }


    }


}
