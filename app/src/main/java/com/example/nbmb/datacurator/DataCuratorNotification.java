package com.example.nbmb.datacurator;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class DataCuratorNotification {
    private final static String CHANNEL_ID = "com.example.nbmb.datacurator,channel_id";

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

    public static NotificationCompat.Builder createNotificationBuilder(Context context, String title, String text) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_test_notification)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        return notificationBuilder;
    }

    public static void showNotification(NotificationManagerCompat notificationManager, String notificationText, int notificationId,
                                        NotificationCompat.Builder notificationBuilder) {
        notificationBuilder.setContentText(notificationText);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    public static void setTapAction(PendingIntent intent, NotificationCompat.Builder notificationBuilder) {
        notificationBuilder.setContentIntent(intent);
    }
}
