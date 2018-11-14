package com.example.nbmb.datacurator;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class WifiNotification {
    private final static String CHANNEL_ID = "com.example.nbmb.datacurator,channel_id";
    private static NotificationCompat.Builder wifiNotificationBuilder;
    private static NotificationCompat.Builder dataNotificationBuilder;

    public static void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "test_channel";
            String description = "this is a test channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void createNotification(Context context) {
        wifiNotificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_test_notification)
                .setContentTitle("Test")
                .setContentText("This is a test notification")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    public static void showNotification(Context context, String notificationText) {
        int notificationId = 300;
        wifiNotificationBuilder.setContentText(notificationText);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, wifiNotificationBuilder.build());
    }

    public static void setTapAction(PendingIntent intent) {
        wifiNotificationBuilder.setContentIntent(intent);
    }
}
