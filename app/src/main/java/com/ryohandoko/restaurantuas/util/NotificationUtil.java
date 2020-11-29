package com.ryohandoko.restaurantuas.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.ryohandoko.restaurantuas.R;

public class NotificationUtil {
    public static String CHANNEL_ID = "Channel 1";
    private static NotificationUtil instance = null;

    private NotificationUtil() {}

    public static NotificationUtil getInstance() {
        if(instance == null)
            instance = new NotificationUtil();
        return instance;
    }

    public void createNotification(Context context) {
        CharSequence name = CHANNEL_ID;
        String desc = "This is Channel 1";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(desc);

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    public void addNotification(Context context, String title, String msg, Class<?> clazz) {

        createNotification(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Channel 1")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        Intent notificationIntent = new Intent(context, clazz);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
