package com.pardeep.foxy_native_module.foxynativemodules.NetworkInformation;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import javax.annotation.Nonnull;

public class NativeNetworkInforModule extends ReactContextBaseJavaModule {

    ReactApplicationContext reactApplicationContext;

    public NativeNetworkInforModule(@Nonnull ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactApplicationContext = reactContext;
    }

    @Nonnull
    @Override
    public String getName() {
        return "NetworkInfo";
    }

    @ReactMethod
    public void getNetworkInformation() {
        NetworkInformationExtractor networkInformationExtractor = new NetworkInformationExtractor(
                this.reactApplicationContext);
    }
}
