//package com.pardeep.foxy_dynamic.foxynativemodules.Truecaller;
//
//import android.content.Context;
//
//import com.facebook.react.ReactPackage;
//import com.facebook.react.bridge.NativeModule;
//import com.facebook.react.bridge.ReactApplicationContext;
//import com.facebook.react.uimanager.ViewManager;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import javax.annotation.Nonnull;
//
//import com.pardeep.foxy_dynamic.foxynativemodules.deviceinfo.DeviceInfoModule;
//
//public class TruecallerPackage implements ReactPackage {
//
//
//
//    @Override
//    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
//        List<NativeModule> modules = new ArrayList<>();
//        // We import the module file here
//        modules.add(new TruecallerModule(reactContext));
//        return modules;
//    }
//
//    @Nonnull
//    @Override
//    public List<ViewManager> createViewManagers(@Nonnull ReactApplicationContext reactContext) {
//        return Collections.emptyList();
//    }
//}
