package com.example.nbmb.datacurator.service;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.nbmb.datacurator.DataCuratorNotification;
import com.example.nbmb.datacurator.data_alerts.DataUsage;

import java.util.concurrent.TimeUnit;

public class NetworkStatusTask implements Runnable{
    public static final int NOTIFICATION_ID = 35;

    DataUsage du;
    NotificationCompat.Builder notificationBuilder;
    NotificationManagerCompat notificationManager;

    public NetworkStatusTask(DataUsage du, NotificationCompat.Builder notificationBuilder,
                         NotificationManagerCompat notificationManager) {
        this.du = du;
        this.notificationBuilder = notificationBuilder;
        this.notificationManager = notificationManager;
    }

    @Override
    public void run() {
        long dataUsage = du.getDataUsage() / 1000;
        String text = "Data usage: " + dataUsage + "KB in the last minute - Tap to disable Data";
        DataCuratorNotification.showNotification(notificationManager, text, NOTIFICATION_ID, notificationBuilder);
    }
}