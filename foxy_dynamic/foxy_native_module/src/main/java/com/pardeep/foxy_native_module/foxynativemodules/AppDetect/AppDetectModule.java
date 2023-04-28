package com.pardeep.foxy_native_module.foxynativemodules.AppDetect;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.content.pm.ResolveInfo;

public class AppDetectModule extends ReactContextBaseJavaModule {

    private ReactContext reactContext = null;

    public AppDetectModule(@Nonnull ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Nonnull
    @Override
    public String getName() {
        return "AppDetect";
    }


    /*
     * Replica of function getApps(), the only change here it returns Promise instead of sendEvent.
     * Its Just for UAC campaings
     * */
    @ReactMethod
    public void getAppsPromise(Promise promise){
        new AsyncGetApp(promise).execute();
    }


    @ReactMethod
    public void getApps() {
        new AsyncGetApp(null).execute();
    }

    @ReactMethod
    public void isPackageInstalled(String packageName, Callback callback) {
        final PackageManager packageManager = this.reactContext.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent == null) {
            callback.invoke(false);
            return;
        }
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        callback.invoke(list.size() > 0);
    }

    private void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableArray writableArray) {
        this.reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName,
                writableArray);
    }

    public Boolean isAppInstalled(String packageName) {
        try {
            PackageManager pm = this.reactContext.getPackageManager();
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {}
        return false;
    }

    public Boolean isAppUpiReady(String packageName) {
        Boolean appUpiReady = false;
        Intent upiIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("upi://pay"));
        PackageManager pm = this.reactContext.getPackageManager();
        List<ResolveInfo> upiActivities = pm.queryIntentActivities(upiIntent, 0);
        for (ResolveInfo upiActivity: upiActivities){
            if (upiActivity.activityInfo.packageName.equals(packageName)) {
                appUpiReady = true;
            }
        }
        return appUpiReady;
    }

    class AsyncGetApp extends AsyncTask<Void, Void, Void> {

        WritableArray appList = new WritableNativeArray();
        private Promise promise;
        public AsyncGetApp(Promise promise){
            this.promise = promise;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                PackageManager pm = reactContext.getPackageManager();
                List<PackageInfo> pList = pm.getInstalledPackages(0);

                for (int i = 0; i < pList.size(); i++) {
                    PackageInfo packageInfo = pList.get(i);
                    Boolean isAppInstalledAndUserReady = isAppInstalled(packageInfo.packageName) && isAppUpiReady(packageInfo.packageName);
                     if (isAppInstalledAndUserReady && ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) || packageInfo.packageName.equalsIgnoreCase("com.google.android.apps.nbu.paisa.user")) { // Bypassing GPay because in some devices its a system application
                         WritableMap info = new WritableNativeMap();
                         info.putString("packageName", packageInfo.packageName);
                         appList.pushMap(info);
                     }
                }
            } catch (Exception ex) {
                Log.e("Working as error ", "" + ex);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(this.promise==null) {
                sendEvent(reactContext, "installedApps", appList);
            }else{
                this.promise.resolve(appList);
            }
        }
    }
}
