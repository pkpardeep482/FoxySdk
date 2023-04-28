package com.pardeep.foxy_native_module.foxynativemodules.PersistentNotifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
//import android.support.annotation.Nullable;
//import android.support.v4.app.NotificationCompat;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class PersistentNotifications extends Service {
    NotificationCompat.Builder builder = null;
    NotificationManager notificationManager = null;
    public static int NOTIFICATION_ID = 2;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
