package com.pardeep.foxy_native_module.foxynativemodules.AnalyticsManager;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
//
//import com.google.firebase.analytics.FirebaseAnalytics;

public class AnalyticsManager {

    public static void LogEvent(Context context, String eventName, Bundle bundle) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
//                FirebaseAnalytics.getInstance(context).logEvent(eventName, bundle);
            }
        });
    }

}
