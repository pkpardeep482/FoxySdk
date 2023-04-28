package com.pardeep.foxy_native_module.foxynativemodules.InstallSource;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import javax.annotation.Nonnull;



public class InstallSourceModule extends ReactContextBaseJavaModule {

    public InstallSourceModule(@Nonnull ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Nonnull
    @Override
    public String getName() {
        return "InstallSource";
    }


    @ReactMethod
    public void appInstalledFromFbDeferredLink(Callback callback){
//        try{
//            callback.invoke(null,MainApplication.installedFromDefferedLink);
//
//        }catch (Exception e){
//            callback.invoke(e.toString(),false);
//        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @ReactMethod
    public void getAppLaunchTimeStamp(Callback callback){
//        try {
//            callback.invoke(null, String.valueOf(calander.getTime()));
//        }catch(Exception e){
//            callback.invoke(true,e.toString());
//        }
    }
}
