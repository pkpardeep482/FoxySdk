//package com.pardeep.foxy_dynamic.foxynativemodules.payment;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//
//import com.facebook.react.bridge.ActivityEventListener;
//import com.facebook.react.bridge.Arguments;
//import com.facebook.react.bridge.Callback;
//import com.facebook.react.bridge.ReactContext;
//import com.facebook.react.bridge.ReactContextBaseJavaModule;
//import com.facebook.react.bridge.ReactMethod;
//import com.facebook.react.bridge.ReadableMap;
//
//public class UpiIntentLauncher extends ReactContextBaseJavaModule {
//
//    private ReactContext reactContext;
//    private final int ACTIVITY_LAUNCH_UPI_INTENT = 1;
//
//    public UpiIntentLauncher(ReactContext reactContext){
//        this.reactContext = reactContext;
//    }
//
//    @NonNull
//    @Override
//    public String getName() {
//        return "UpiIntentLauncher";
//    }
//
//
//    @ReactMethod
//    public void launchIntent(ReadableMap readableMap) {
//         String link = readableMap.getString("upiLink");
//         String packageName  = readableMap.getString("packageName");
//         if(link == null) return;
//         Intent upiIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
//         upiIntent.setPackage(packageName);
//         try {
//             this.reactContext.startActivityForResult(upiIntent, ACTIVITY_LAUNCH_UPI_INTENT, null);
//         }catch (Exception e){
//             // TODO: add log for exception
//         }
//    }
//}
