package com.example.nbmb.datacurator;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class DataCuratorNotification {
    private static String channelId = "com.example.nbmb.datacurator,data_alert_channel";

    public static void createNotificationChannel(Context context, String channelId
            , CharSequence name, String description, int importance) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            DataCuratorNotification.channelId = channelId;
        }
    }

    public static NotificationCompat.Builder createNotificationBuilder(Context context, String title, String text) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelId)
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

    public static void hideNotification(NotificationManagerCompat notificationManager,
                                        int notificationId) {
        notificationManager.cancel(notificationId);
    }

    public static void setTapAction(PendingIntent intent, NotificationCompat.Builder notificationBuilder) {
        notificationBuilder.setContentIntent(intent);
    }
}
