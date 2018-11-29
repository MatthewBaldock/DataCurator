package com.example.nbmb.datacurator;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.nbmb.datacurator.data_alerts.DataAlertTask;
import com.example.nbmb.datacurator.data_alerts.DataUsage;
import com.example.nbmb.datacurator.helpers.UnitHelper;

import java.util.concurrent.ScheduledThreadPoolExecutor;

import static java.util.Objects.isNull;

public class DataCuratorService extends JobIntentService {
    private static final String WAKE_LOCK_TAG = "datacurator:background";

    private PowerManager.WakeLock mWakeLock;

    // Service unique ID
    static final int SERVICE_JOB_ID = 50;

    // Enqueuing work in to this service.
    public static void enqueueWork(Context context, Intent work) {
        Log.d("startupService", "enqueueWork()");
        enqueueWork(context, DataCuratorService.class, SERVICE_JOB_ID, work);
    }

    /**
     * This is where we initialize. We call this when onStart/onStartCommand is
     * called by the system. We won't do anything with the intent here, and you
     * probably won't, either.
     */
    @Override
    protected void onHandleWork(Intent intent) {
        Log.d("startupService", "handleIntent()");

        // obtain the wake lock
        PowerManager pm = (PowerManager)
                getSystemService(POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, WAKE_LOCK_TAG);
        mWakeLock.acquire();
        // do the actual work, in a separate thread
        new PollTask().execute();
    }

    private class PollTask extends AsyncTask<Void, Void, Void> {
        /** * This is where YOU do YOUR work. There's nothing for me to write here
         * you have to fill this in. Make your HTTP request(s) or whatever it is
         * you have to do to get your updates in here, because this is run in a
         * separate thread
         */
        @Override
        protected Void doInBackground(Void... params) {
            Log.d("startupService", "background task");

            setAlert(0, "KB", 10, "Seconds");
            return null;
        }

        /**
         * In here you should interpret whatever you fetched in doInBackground
         * and push any notifications you need to the status bar, using the
         * NotificationManager. I will not cover this here, go check the docs on
         * NotificationManager.
         *
         * What you HAVE to do is call stopSelf() after you've pushed your
         * notification(s). This will:
         * 1) Kill the service so it doesn't waste precious resources
         * 2) Call onDestroy() which will release the wake lock, so the device
         * can go to sleep again and save precious battery.
         */
        @Override
        protected void onPostExecute(Void result) {
            stopSelf();
        }
    }

    private void setAlert(long dataLimit, String dataUnitText, long timeLimit, String timeUnitText) {
        Log.d("startupService", "setAlert()");

        String title = "Data alert!";
        String text = "Data usage: " + dataLimit + dataUnitText + " in the last " + timeLimit + " " + timeUnitText;

        NotificationCompat.Builder notificationBuilder= DataCuratorNotification.createNotificationBuilder(this.getApplicationContext(),
                title, text);

        DataCuratorNotification.setTapAction(PendingIntent.getActivity(this, 0,
                new Intent(Settings.ACTION_WIRELESS_SETTINGS), PendingIntent.FLAG_UPDATE_CURRENT),
                notificationBuilder);

        DataAlertTask task = new DataAlertTask(dataLimit, timeLimit,
                UnitHelper.getDataUnitFromString(dataUnitText), UnitHelper.getTimeUnitFromString(timeUnitText),
                dataUnitText, timeUnitText, new DataUsage(this.getApplicationContext()),
                notificationBuilder, NotificationManagerCompat.from(this.getApplicationContext()));

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.scheduleAtFixedRate(task, timeLimit, timeLimit, UnitHelper.getTimeUnitFromString(timeUnitText));
    }

    /**
     * This is called on 2.0+ (API level 5 or higher). Returning
     * START_NOT_STICKY tells the system to not restart the service if it is
     * killed because of poor resource (memory/cpu) conditions.
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onHandleWork(intent);
        return START_STICKY;
    }

    /**
     * In onDestroy() we release our wake lock. This ensures that whenever the
     * Service stops (killed for resources, stopSelf() called, etc.), the wake
     * lock will be released.
     */
    public void onDestroy() {
        super.onDestroy();
        if(!isNull(mWakeLock))
            mWakeLock.release();
    }
}
