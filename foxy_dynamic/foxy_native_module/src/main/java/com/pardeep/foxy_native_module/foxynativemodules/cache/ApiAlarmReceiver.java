package com.pardeep.foxy_native_module.foxynativemodules.cache;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
//
//import androidx.work.BackoffPolicy;
//import androidx.work.Constraints;
//import androidx.work.ExistingWorkPolicy;
//import androidx.work.NetworkType;
//import androidx.work.OneTimeWorkRequest;
//import androidx.work.PeriodicWorkRequest;
//import androidx.work.WorkManager;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

// import com.pardeep.foxy_dynamic.foxynativemodules.utilities.AlarmCreator;

public class ApiAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BROADCAST_RECEIVED", "onReceive: ");
        // AlarmCreator.createAlarm(context,AlarmCreator.CALL_STORY_API,12, Calendar.HOUR_OF_DAY);

//        Constraints constraints = new Constraints.Builder()
//                .setRequiredNetworkType(NetworkType.CONNECTED)
//                .setRequiresStorageNotLow(true)
//                .build();
//
//        OneTimeWorkRequest apiWorkRequest = new OneTimeWorkRequest.Builder(StoryApiWorker.class)
//                .setConstraints(constraints)
//                .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
//                .build();
//        WorkManager.getInstance(context).enqueueUniqueWork("call-story-api", ExistingWorkPolicy.REPLACE, apiWorkRequest);
    }
}