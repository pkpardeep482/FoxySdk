package com.pardeep.foxy_native_module.foxynativemodules.utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.pardeep.foxy_dynamic.BuildConfig;
import com.pardeep.foxy_dynamic.R;


public class FoxyNotificationManager {
    final public static int ID_CART_REMINDER = 10001;
    final public static int ID_STORY_NOTIFICATION = 10002;
    final public static int ID_DAILY_DEALS_NOTIFICATION = 10003;
    final public static int ID_REVIEW_REMINDER_NOTIFICATION = 10004;

    private NotificationCompat.Builder builder = null;
    private NotificationManager notificationManager = null;

    public FoxyNotificationManager createNotification(Context context, String title, String description, boolean isPersistent) {
        builder = new NotificationCompat.Builder(context,
                "FoxyReminderService");

        builder.setContentTitle(title)
                .setContentText(description)

//                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
//                        R.drawable.ic_la))
                .setOngoing(isPersistent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL);


        notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel("ReminderService",
                    " Reminders",
                    NotificationManager.IMPORTANCE_HIGH);
            // Configure the notification channel.
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(mChannel);
            }
        }
        return this;
    }

    public FoxyNotificationManager createNotification(Context context, String title, String description, boolean isPersistent, int priority) {
        notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel("NotificationsService",
                    " Notifications",
                    priority);
            // Configure the notification channel.
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(mChannel);
            }
        }

        builder = new NotificationCompat.Builder(context,
                "FoxyNotificationsService");
        builder.setContentTitle(title)
                .setContentText(description)
                .setOngoing(isPersistent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL);

        return this;
    }

    public FoxyNotificationManager addActionToNotification(String actionName,
                                                           PendingIntent actionIntent) {
        if (builder == null || notificationManager == null) {
            return null;
        }
//        this.builder.addAction(R.mipmap.ic_launcher, actionName, actionIntent);
        return this;
    }

    public FoxyNotificationManager addContentIntent(PendingIntent contentIntent) {
        if (builder == null || notificationManager == null) {
            return null;
        }
        this.builder.setContentIntent(contentIntent);
        return this;
    }

    public void notify(int id) {
        notificationManager.notify(id, builder.build());
    }

    public FoxyNotificationManager addBigText(String body) {
        if (builder == null || notificationManager == null) {
            return null;
        }
        this.builder.setStyle(new NotificationCompat.BigTextStyle().bigText(body));
        return this;
    }

    public FoxyNotificationManager addCustomCollapsedView(RemoteViews remoteViews) {
        if (builder == null || notificationManager == null) {
            return null;
        }
        this.builder.setCustomContentView(remoteViews);
        return this;
    }


    public FoxyNotificationManager addCustomExpandedView(RemoteViews remoteViews) {
        if (builder == null || notificationManager == null) {
            return null;
        }
        this.builder.setCustomBigContentView(remoteViews);
        return this;
    }

    public FoxyNotificationManager addCustomHeadsUpView(RemoteViews remoteViews) {
        if (builder == null || notificationManager == null) {
            return null;
        }
        this.builder.setCustomHeadsUpContentView(remoteViews);
        return this;
    }


    public FoxyNotificationManager setDecoratedCustomViewStyle() {
        if (builder == null || notificationManager == null) {
            return null;
        }
        this.builder.setStyle(new NotificationCompat.DecoratedCustomViewStyle());
        return this;
    }

    public static void cancel(Context context, int notificationId) {
        NotificationManagerCompat.from(context).cancel(notificationId);
    }
}
