package com.example.nbmb.datacurator;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.nbmb.datacurator.data_alerts.DataAlertTask;
import com.example.nbmb.datacurator.data_alerts.DataUsage;
import com.example.nbmb.datacurator.database.DisableDataHelper;
import com.example.nbmb.datacurator.service.NetworkStatusTask;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private final static String CHANNEL_ID = "com.example.nbmb.datacurator,network_status_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataCuratorNotification.createNotificationChannel(this.getApplicationContext(), CHANNEL_ID,
                "Network Status", "Constant notificaiton to update user about network status.",
                NotificationManager.IMPORTANCE_LOW);
        setAlert(10, "KB", 1, "minute");
    }

    public void disableView(View view)
    {
        Intent intent = new Intent(this, WifiDisable.class);
        startActivity(intent);
    }

    public void dataUsageClicked(View view) {
        Intent intent = new Intent(this, DataAlertActivity.class);
        startActivity(intent);
    }

    private void setAlert(long dataLimit, String dataUnitText, long timeLimit, String timeUnitText) {
        String title = "Network Status.";
        String text = "Data usage: " + dataLimit + dataUnitText + " in the last " + timeLimit + " " + timeUnitText;

        NotificationCompat.Builder notificationBuilder= DataCuratorNotification.createNotificationBuilder(this.getApplicationContext(),
                title, text);
        notificationBuilder.setShowWhen(false);
        notificationBuilder.setOnlyAlertOnce(true);

        DataCuratorNotification.setTapAction(PendingIntent.getActivity(this, 0,
                new Intent(Settings.ACTION_WIRELESS_SETTINGS), PendingIntent.FLAG_UPDATE_CURRENT),
                notificationBuilder);

        NetworkStatusTask task = new NetworkStatusTask(new DataUsage(this.getApplicationContext()),
                notificationBuilder, NotificationManagerCompat.from(this.getApplicationContext()));

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.scheduleAtFixedRate(task, timeLimit, timeLimit, TimeUnit.MINUTES);
    }
}
