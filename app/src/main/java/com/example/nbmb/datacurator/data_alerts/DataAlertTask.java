package com.example.nbmb.datacurator.data_alerts;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.nbmb.datacurator.DataCuratorNotification;

import java.util.concurrent.TimeUnit;

public class DataAlertTask implements Runnable{
    public static final int NOTIFICATION_ID = 34;

    long dataLimit, timePeriod, dataUnit;
    TimeUnit timeUnit;
    String dataUnitText, timeUnitText;
    DataUsage du;
    NotificationCompat.Builder notificationBuilder;
    NotificationManagerCompat notificationManager;

    public DataAlertTask(long dataLimit, long timePeriod, long dataUnit, TimeUnit timeUnit,
                         String dataUnitText, String timeUnitText, DataUsage du,
                         NotificationCompat.Builder notificationBuilder,
                         NotificationManagerCompat notificationManager) {
        this.dataLimit = dataLimit;
        this.timePeriod = timePeriod;
        this.dataUnit = dataUnit;
        this.timeUnit = timeUnit;
        this.dataUnitText = dataUnitText;
        this.timeUnitText = timeUnitText;
        this.du = du;
        this.notificationBuilder = notificationBuilder;
        this.notificationManager = notificationManager;
    }

    @Override
    public void run() {
        long dataUsage = du.getDataUsage() / dataUnit;
        if(dataUsage >= dataLimit) {
            String text = "Data usage: " + dataUsage + dataUnitText + " in the last " + timePeriod +
                    " " + timeUnitText;
            DataCuratorNotification.showNotification(notificationManager, text, NOTIFICATION_ID, notificationBuilder);
        }
    }
}
