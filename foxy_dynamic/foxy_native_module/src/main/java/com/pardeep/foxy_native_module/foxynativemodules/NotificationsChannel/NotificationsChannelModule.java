package com.pardeep.foxy_native_module.foxynativemodules.NotificationsChannel;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

import javax.annotation.Nonnull;



public class NotificationsChannelModule extends ReactContextBaseJavaModule {

    private ReactApplicationContext reactApplicationContext;
    private NotificationManager notificationManager;

    public NotificationsChannelModule(@Nonnull ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactApplicationContext = reactContext;
        notificationManager = (NotificationManager) reactApplicationContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Nonnull
    @Override
    public String getName() {
        return "NotificationsChannelModule";
    }

    @ReactMethod
    public void createNotificationChannel(ReadableArray notificationChannels) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }
        for(int index =0; index < notificationChannels.size(); index ++) {
            ReadableMap map = notificationChannels.getMap(index);
            String channelId = map.getString("channelId");
            String channelName = map.getString("channelName");
            String importance = map.getString("channelImportance");
            boolean enableBadge = map.getBoolean("enableBadge");
            boolean visibleOnLockScreen = map.getBoolean("visibleOnLockScreen");
            boolean enableVibration = map.getBoolean("enableVibration");
            boolean enableSound = map.getBoolean("enableSound");
            String customNotificationSound = map.getString("customNotificationSound");

            int notificationImportance = getNotificationImportance(importance);
            NotificationChannel channel = new NotificationChannel(channelId, channelName, notificationImportance);
            channel.setImportance(notificationImportance);
            channel.enableVibration(enableVibration);
            channel.setShowBadge(enableBadge);
            channel.setLockscreenVisibility(visibleOnLockScreen ? Notification.VISIBILITY_PUBLIC : Notification.VISIBILITY_PRIVATE);
            long[] pattern = {500, 500, 500, 500, 500, 500, 500, 500, 500};
            channel.setVibrationPattern(pattern);
            if(enableSound) {
                AudioAttributes att = new AudioAttributes.Builder()
                                          .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                                          .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
                channel.setSound(getNotificationRingtoneUri(customNotificationSound), att);
            }
            notificationManager.createNotificationChannel(channel);
        }
    }


    @ReactMethod
    public void getNotificationChannelStatus (ReadableArray notificationChannels, Callback callback) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            callback.invoke(false, null);
            return;
        }
        callback.invoke(notificationEnabledForApp(), null);
    }

    private int getNotificationImportance (String strImportance) {
        switch (strImportance) {
            case "high":
                return NotificationManager.IMPORTANCE_HIGH;
            case "max":
               return NotificationManager.IMPORTANCE_MAX;
            case "low":
                return NotificationManager.IMPORTANCE_LOW;
            default:
                return NotificationManager.IMPORTANCE_DEFAULT;
        }
    }

    private boolean hasCustomNotificationSound (String customNotificationSound) {
        return !customNotificationSound.isEmpty() && !customNotificationSound.equals("0");
    }

    private Uri getPhoneDefaultNotificationSound() {
        return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    }


    private Uri getNotificationRingtoneUri (String customNotificationSound) {
            return getPhoneDefaultNotificationSound();
    }


    //Checking if overall notifications enabled for app
    private boolean notificationEnabledForApp () {
        return NotificationManagerCompat.from(this.reactApplicationContext).areNotificationsEnabled();
    }

    // checking if channel specific notifications are enabled
    private boolean isNotificationChannelEnabled(String channelId){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = notificationManager.getNotificationChannel(channelId);
                if (channel == null)
                    return true; //channel is not yet created so return boolean
                // by only checking whether notifications enabled or not
                return channel.getImportance() != NotificationManager.IMPORTANCE_NONE;
            }
            return true;
        }

    @ReactMethod
    public void openNotificationChannelSettings (String channelId) {
        Log.e("Current Notification CHannel , ","=>"+channelId);
        if(channelId==null) {
           this.openNotificationSettings();
           return;
        }
        Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, this.reactApplicationContext.getPackageName());
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.reactApplicationContext.startActivity(intent);
    }


    private void openNotificationSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, this.reactApplicationContext.getPackageName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.reactApplicationContext.startActivity(intent);
        } else {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + this.reactApplicationContext.getPackageName()));
            this.reactApplicationContext.startActivity(intent);
        }
    }
}
