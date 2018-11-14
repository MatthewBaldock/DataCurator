package com.example.nbmb.datacurator.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
public class WifiDisableService {
    ScheduledThreadPoolExecutor executor;
    DisableTask disableTask;
    public WifiDisableService()
    {
        executor = new ScheduledThreadPoolExecutor(1);
    }
    public void onHandleWork(Context context, Intent intent) {

        disableTask = new DisableTask(context);
        int duration = intent.getIntExtra("duration",0);
        Log.d("DURATION","Duration: "+duration);
        String onOff = intent.getStringExtra("onoff");
        String period = intent.getStringExtra("period");
        Log.d("DURATION","VALUE: "+onOff);
        executor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
        if (onOff.equals("ON"))
        {
            switch(period)
            {
                case "Seconds":
                    executor.schedule(disableTask,duration,TimeUnit.SECONDS);
                    break;
                case "Minutes":
                    executor.schedule(disableTask,duration,TimeUnit.MINUTES);
                    break;
                case "Hours":
                    executor.schedule(disableTask,duration,TimeUnit.HOURS);
                    break;
                case "Days":
                    executor.schedule(disableTask,duration,TimeUnit.DAYS);
                    break;
                default:
                    executor.execute(disableTask);
                    break;
            }

        }
        else if (onOff.equals("OFF"))
        {
            disableTask.run();
        }

    }

}
