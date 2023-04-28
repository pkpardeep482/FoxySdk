package com.pardeep.foxy_native_module.foxynativemodules.UploadManager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import androidx.core.app.NotificationCompat;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import javax.annotation.Nonnull;

public class UploadManagerModule extends ReactContextBaseJavaModule {

    public static ReactContext reactContext;
    private NotificationManager notificationManager = null;
    private NotificationCompat.Builder builder = null;
    private Handler handler = null;
    private Runnable runnableCode = null;
    private long previousTime = 0;
    private int previousProgress = 0;
    private String contentText = "Calculating time remaining...";
    private Intent intentRetry;
    private PendingIntent pendingIntentRetry;

    private final int REQUEST_CODE_RETRY = 5;
    private final int REQUEST_CODE_CANCEL = 15;
    private final int REQUEST_CODE_OPEN = 10;


    public UploadManagerModule(@Nonnull ReactApplicationContext reactContext) {
        super(reactContext);
//        this.reactContext = reactContext;
//        createNotificationBuilder();
//        intentRetry = new Intent(this.reactContext, MainActivity.class);
//        intentRetry.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        intentRetry.putExtra("notification_type", "video_upload");
//        intentRetry.putExtra("action", "retry_video_upload");
//        pendingIntentRetry = PendingIntent.getActivity(this.reactContext, REQUEST_CODE_RETRY, intentRetry, PendingIntent.FLAG_MUTABLE);
    }

    @Nonnull
    @Override
    public String getName() {
        return "UploadManager";
    }

    @ReactMethod()
    public void startUploading() {
//        try {
//            this.builder.mActions.clear();
//        } catch (Exception e) {
//
//        }
//        MainActivity.mainActivity.startService(new Intent(MainActivity.mainActivity, PersistentService.class));
    }

    @ReactMethod()
    public void setCurrentProgress(int max, int currentProgress) {
        if (this.builder == null || this.notificationManager == null) {
            return;
        }
        this.builder.setProgress(max, currentProgress, false);
        this.builder.addAction(0, null, null);
        this.notificationManager.notify(PersistentService.NOTIFICATION_ID, this.builder.build());
    }

    @ReactMethod()
    public void setNotificationText(String title, String description) {
        if (this.builder == null || this.notificationManager == null) {
            return;
        }
        this.builder.setContentTitle(title);
        this.builder.setContentText(description);
        // this.builder.addAction(0,null,null);
        this.notificationManager.notify(PersistentService.NOTIFICATION_ID, this.builder.build());

    }

    @ReactMethod()
    public void removeProgressBar() {
        if (this.builder == null || this.notificationManager == null) {
            return;
        }
        this.builder.setProgress(0, 0, false);
        this.notificationManager.notify(PersistentService.NOTIFICATION_ID, this.builder.build());
    }

    @ReactMethod()
    public void cancelNotification() {

//        if (this.notificationManager != null) {
//            this.notificationManager.cancelAll();
//            MainActivity.mainActivity.stopService(new Intent(MainActivity.mainActivity, PersistentService.class));
//        }
    }

    @ReactMethod()
    public void retryUpload(String payload) {
//        if (this.builder == null || this.notificationManager == null) {
//            return;
//        }
//
//        Intent intent = new Intent(this.reactContext, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        intent.putExtra("notification_type", "upload_notification");
//        PendingIntent pendingIntent = PendingIntent.getActivity(this.reactContext, REQUEST_CODE_OPEN, intent,  PendingIntent.FLAG_MUTABLE);
//        String title = "", description = "";
//        try {
//            JSONObject data = new JSONObject(payload);
//            title = data.getString("title");
//            description = data.getString("description");
//            this.builder.mActions.clear();
//        } catch (Exception e) {
//        }
//        this.builder.addAction(R.mipmap.ic_stat, "Retry Upload", pendingIntent);
//        this.builder.setContentTitle(title);
//        this.builder.setContentText(description);
//        this.builder.setAutoCancel(true);
//        this.builder.setOngoing(false);
//        this.builder.setProgress(0, 0, false);
//        this.notificationManager.notify(PersistentService.NOTIFICATION_ID, this.builder.build());
    }

    private void createNotificationBuilder() {

//        this.builder = new NotificationCompat.Builder(this.reactContext).setSmallIcon(R.mipmap.ic_stat)
//                .setOnlyAlertOnce(true).setAutoCancel(false).setOngoing(true);
//
//        // Add as notification
//        notificationManager = (NotificationManager) this.reactContext.getSystemService(Context.NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            builder.setChannelId(BuildConfig.PACKAGE_NAME_ANDROID + ".selfie_upload_notification");
//            NotificationChannel channel = new NotificationChannel(BuildConfig.PACKAGE_NAME_ANDROID + ".selfie_upload_notification",
//                    "Notification for selfie upload", NotificationManager.IMPORTANCE_HIGH);
//            channel.setLightColor(Color.RED);
//            channel.enableLights(true);
//            channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
//            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
//            notificationManager.createNotificationChannel(channel);
//
//        }

    }

    @ReactMethod
    private void showSkipSelfieNotification() {

    }

    @ReactMethod
    private void cancelSkipSelfieNotification() {

    }

    @ReactMethod
    private void showPersistentNotification(String identifier, String ctaTextOne, String ctaTextTwo) {
//        MainActivity.mainActivity.startService(new Intent(MainActivity.mainActivity, PersistentNotifications.class));
    }

    @ReactMethod
    private void cancelPersistentNotification(String identifier) {
//        MainActivity.mainActivity.stopService(new Intent(MainActivity.mainActivity, PersistentNotifications.class));
    }

    @ReactMethod
    private void startHeadlessForOnboardingNotification() {
        this.handler = new Handler();
        this.runnableCode = new Runnable() {
            @Override
            public void run() {
                try {
//                    Intent myIntent = new Intent(MainActivity.mainActivity, HeartbeatEventService.class);
//                    MainActivity.mainActivity.startService(myIntent);
//                    HeadlessJsTaskService.acquireWakeLockNow(MainActivity.mainActivity);
//                    handler.postDelayed(this, 2000);
                } catch (Exception e) {

                }
            }
        };

        this.handler.post(runnableCode);

    }

    @ReactMethod
    private void stopHeadlessForOnboarding() {
        try {
            if (this.handler != null) {
                this.handler.removeCallbacks(this.runnableCode);
//                MainActivity.mainActivity
//                        .stopService(new Intent(MainActivity.mainActivity, HeartbeatEventService.class));
            }
        } catch (Exception e) {
        }
    }

    @ReactMethod
    private void startVideoUploadingService() {
        if(this.builder == null) return;
//        Intent intentCancel = new Intent(this.reactContext, MainActivity.class);
//        intentCancel.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this.reactContext, REQUEST_CODE_CANCEL, intentCancel,  PendingIntent.FLAG_MUTABLE);
//        this.builder.mActions.clear();
//        this.builder.setContentText("");
//        this.builder.setContentIntent(pendingIntent);
//
//        MainActivity.mainActivity.startService(new Intent(MainActivity.mainActivity, VideoUploadManagerService.class));
    }

    @ReactMethod
    private void stopVideoUploadingService() {
        stopHeadlessForOnboarding();
//        MainActivity.mainActivity.stopService(new Intent(MainActivity.mainActivity, VideoUploadManagerService.class));
        this.notificationManager.cancel(VideoUploadManagerService.ID_UPLOAD_NOTIFICATION);
    }

    @ReactMethod
    private void setProgressToVideoUploadNotification(int max, int currentProgress) {
        if (this.builder == null || this.notificationManager == null) {
            return;
        }
        long currentTime = System.nanoTime();
        long timeDiff = currentTime - previousTime;
        int progress = (int) ((float) currentProgress/max * 100);
        long speed = 0;
        if(timeDiff>0) {
            speed = (progress - previousProgress) * 1000000000 / timeDiff;
        }
        long timeRemaining = 0;
        if(speed > 0) {
            timeRemaining = (100 - progress) / speed;
           if(timeRemaining < 10) {
               contentText = "About to finish";
           } else if (timeRemaining < 60){
                contentText = "About a minute remaining";
           } else if (timeRemaining < 180) {
                contentText = "About 2 minutes remaining";
           } else if (timeRemaining < 600) {
                contentText = "About "+(int)timeRemaining/60+ " minutes remaining";
           } else {
                contentText = "About "+(int)timeRemaining/60+ " minutes remaining";
           }
        }
        this.builder.setContentTitle("Uploading video").setProgress(max, currentProgress, false);
        this.builder.setContentText(contentText);
        this.notificationManager.notify(VideoUploadManagerService.ID_UPLOAD_NOTIFICATION, this.builder.build());
        previousTime = currentTime;
        previousProgress = progress;
    }

    @ReactMethod
    private void removeVideoUploadNotificationProgress() {
        if (this.builder == null || this.notificationManager == null) {
            return;
        }
        this.builder.setProgress(0, 0, false);
        this.notificationManager.notify(VideoUploadManagerService.ID_UPLOAD_NOTIFICATION, this.builder.build());
    }

    @ReactMethod()
    public void showNetworkFailureNotification() {
//        if (this.builder == null || this.notificationManager == null) {
//            return;
//        }
//        this.builder.mActions.clear();
//
//        Intent intentCancel = new Intent(this.reactContext, MainActivity.class);
//        intentCancel.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        intentCancel.putExtra("notification_type", "video_upload");
//        intentCancel.putExtra("action", "cancel");
//        PendingIntent pendingIntentCancel = PendingIntent.getActivity(this.reactContext, REQUEST_CODE_CANCEL, intentCancel, PendingIntent.FLAG_MUTABLE);
//
//        this.builder.setProgress(0, 0, false);
//        this.builder.setContentTitle("Uploading failed");
//        this.builder.setContentText("Please try again with good network");
//        this.builder.addAction(R.mipmap.ic_stat, "Retry Upload", pendingIntentRetry);
//        this.builder.addAction(R.mipmap.ic_stat, "Cancel", pendingIntentCancel);
//        this.builder.setAutoCancel(true);
//        this.builder.setOngoing(false);
//        this.notificationManager.notify(VideoUploadManagerService.ID_UPLOAD_NOTIFICATION, this.builder.build());
    }

    int runnableIterationCount = 0;

    @ReactMethod()
    public void checkInternetPeriodically(){
        if (this.builder == null || this.notificationManager == null) {
            return;
        }
        this.builder.mActions.clear();
        this.builder.setProgress(0, 0, true);
        this.builder.setContentTitle("Paused");
        this.builder.setContentText("Waiting for better connection");
        this.builder.setAutoCancel(true);
        this.builder.setOngoing(false);
        this.notificationManager.notify(VideoUploadManagerService.ID_UPLOAD_NOTIFICATION, this.builder.build());
        handler = new Handler();
        Runnable networkStatusChecker = new Runnable() {
            @Override
            public void run() {
                runnableIterationCount++;
//                if(MainActivity.mainActivity == null) {
//                    handler.removeCallbacks(this);
//                    return;
//                }
//                if(runnableIterationCount > 60) {
//                    Intent intentFailure = new Intent(reactContext, MainActivity.class);
//                    intentFailure.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                    intentFailure.putExtra("notification_type", "video_upload");
//                    intentFailure.putExtra("action", "mark_failed");
//                    notificationManager.cancel(VideoUploadManagerService.ID_UPLOAD_NOTIFICATION);
//                    MainActivity.mainActivity.onNewIntent(intentFailure);
//                    showNetworkFailureNotification();
//                    handler.removeCallbacks(this);
//                    return;
//                }
//
//                if(NetworkManager.isOnline(reactContext) && NetworkManager.getUploadSpeed(reactContext)>50){
//                    MainActivity.mainActivity.onNewIntent(intentRetry);
//                    return;
//                }

//                handler.postDelayed(this, 2000);
            }
        };

        handler.post(networkStatusChecker);
    }
}
