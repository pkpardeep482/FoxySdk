package com.pardeep.foxy_native_module.foxynativemodules.cache;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import javax.annotation.Nonnull;

import com.pardeep.foxy_native_module.foxynativemodules.UserPreferences.UserPreferences;
// import com.pardeep.foxy_dynamic.foxynativemodules.utilities.AlarmCreator;

public class CacheManagerModule extends ReactContextBaseJavaModule {

    ReactApplicationContext reactApplicationContext;
    public CacheManagerModule(@Nonnull ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactApplicationContext = reactContext;
    }

    @Nonnull
    @Override
    public String getName() {
        return "CacheManager";
    }

    @ReactMethod
    public void saveMaxCacheLimit(int limit) {
        Log.d("CACHE_MANAGER", "set limit of: "+limit);
        UserPreferences.initPref(reactApplicationContext);
        UserPreferences.saveMaxCacheLimit(limit);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @ReactMethod
    public void saveStoryUrls(String urls) {
        Log.d("CACHE_MANAGER", "updating urls: "+urls);
        if(urls.equals("") || urls == null) return;
        UserPreferences.initPref(reactApplicationContext);
        long lastCallTime = Long.parseLong(UserPreferences.getStringPref(UserPreferences.TIME_LAST_CACHE_API_CALL));
        long currentTime = System.currentTimeMillis();
        if (currentTime < lastCallTime + 8*60*60*1000) return;

        UserPreferences.setCachingTime(currentTime);
        urls = urls.substring(0,urls.length()-1);
        UserPreferences.setCacheUrls(urls);
        // AlarmCreator.createAlarm(reactApplicationContext,AlarmCreator.DOWNLOAD_CACHE,2, Calendar.MINUTE);
    }
}