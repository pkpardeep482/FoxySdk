package com.pardeep.foxy_native_module.foxynativemodules.UploadManager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;


import com.facebook.react.HeadlessJsTaskService;
import com.pardeep.foxy_dynamic.BuildConfig;


public class PersistentService extends Service
{

    NotificationCompat.Builder builder=null;
    NotificationManager notificationManager=null;
    public static int NOTIFICATION_ID = 1;

    private Handler handler = new Handler();
    private Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            try {
                Context context = getApplicationContext();
                Intent myIntent = new Intent(context, HeartbeatEventService.class);
                context.startService(myIntent);
                HeadlessJsTaskService.acquireWakeLockNow(context);
                handler.postDelayed(this, 2000);
            }catch(Exception e){

            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.handler.post(this.runnableCode);
        builder = new NotificationCompat.Builder(this)
                .setOnlyAlertOnce(true)
                .setAutoCancel(false)
                .setOngoing(true);

        // Add as notification
        notificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(".selfie_upload_notification");
            NotificationChannel channel = new NotificationChannel(
                    ".selfie_upload_notification",
                    "Notification for selfie upload",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setLightColor(Color.RED);
            channel.enableLights(true);
            channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
        startForeground(NOTIFICATION_ID,builder.build());
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.handler.removeCallbacks(this.runnableCode);
    }


}
