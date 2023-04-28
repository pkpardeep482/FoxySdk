package com.pardeep.foxy_native_module.foxynativemodules.navbarcolor;

import android.content.Context;
import android.os.Build;
import android.util.Patterns;
import android.app.Activity;
import android.graphics.Color;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class NavBarColorModule extends ReactContextBaseJavaModule {
  ReactApplicationContext reactContext;

  // constructor
  public NavBarColorModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  // Mandatory function getName that specifies the module name
  @Override
  public String getName() {
    return "NavBar";
  }

  // Custom function that we are going to export to JS
  @ReactMethod
  public void setNavBarColorAsWhite() {
    final Activity activity = getCurrentActivity();
    activity.getWindow().setNavigationBarColor(Color.rgb(255, 255, 255));
  }

  @ReactMethod
  public void setNavBarColorAsTranslucent() {
    final Activity activity = getCurrentActivity();
    activity.getWindow().setNavigationBarColor(Color.argb(0.3f, 0.0f, 0.0f, 0.0f));
  }

  @ReactMethod
  public void setNavBarColorAsTransparent() {
    final Activity activity = getCurrentActivity();
    activity.getWindow().setNavigationBarColor(Color.argb(0.0f, 0.0f, 0.0f, 0.0f));
  }

  @ReactMethod
  public void setNavBarColorAsBlack() {
    final Activity activity = getCurrentActivity();
    activity.getWindow().setNavigationBarColor(Color.rgb(0, 0, 0));
  }

  @ReactMethod
  public void setNavBarColorAsGrey() {
    final Activity activity = getCurrentActivity();
    activity.getWindow().setNavigationBarColor(Color.rgb(15, 15, 15));
  }
}