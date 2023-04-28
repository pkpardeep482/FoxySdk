package com.pardeep.foxy_native_module.foxynativemodules.deviceinfo;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.telecom.Call;
import android.telephony.TelephonyManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.pardeep.baseapp.BuildConfig;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.os.Build;

public class DeviceInfoModule extends ReactContextBaseJavaModule {
  ReactApplicationContext reactContext;

  // constructor
  public DeviceInfoModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  // Mandatory function getName that specifies the module name
  @Override
  public String getName() {
    return "DeviceDetails";
  }

  // Custom function that we are going to export to JS
  @ReactMethod
  public void getDeviceModel(Callback cb) {
    try {
      cb.invoke(null, android.os.Build.MODEL);
    } catch (Exception e) {
      cb.invoke(e.toString(), null);
    }
  }

  @ReactMethod
  public void getNotificationStatus(Callback cb){

  }

  @ReactMethod
  public void getLaunchSource(Callback cb){

  }

  @ReactMethod
  public void getDeviceBrand(Callback cb) {
    try {
      cb.invoke(null, android.os.Build.BRAND);
    } catch (Exception e) {
      cb.invoke(e.toString(), null);
    }
  }

  @ReactMethod
  public void getDeviceName(Callback cb) {
    try {
      cb.invoke(null, android.os.Build.DEVICE);
    } catch (Exception e) {
      cb.invoke(e.toString(), null);
    }
  }

  @ReactMethod
  public void getDeviceDisplayID(Callback cb) {
    try {
      cb.invoke(null, android.os.Build.DISPLAY);
    } catch (Exception e) {
      cb.invoke(e.toString(), null);
    }
  }

  @ReactMethod
  public void getDeviceHardware(Callback cb) {
    try {
      cb.invoke(null, android.os.Build.HARDWARE);
    } catch (Exception e) {
      cb.invoke(e.toString(), null);
    }
  }

  @ReactMethod
  public void getDeviceChangeListNumber(Callback cb) {
    try {
      cb.invoke(null, android.os.Build.ID);
    } catch (Exception e) {
      cb.invoke(e.toString(), null);
    }
  }

  @ReactMethod
  public void getDeviceManufacturer(Callback cb) {
    try {
      cb.invoke(null, android.os.Build.MANUFACTURER);
    } catch (Exception e) {
      cb.invoke(e.toString(), null);
    }
  }

  @ReactMethod
  public void getOverallProductName(Callback cb) {
    try {
      cb.invoke(null, android.os.Build.PRODUCT);
    } catch (Exception e) {
      cb.invoke(e.toString(), null);
    }
  }

  @ReactMethod
  public void getUser(Callback cb) {
    try {
      cb.invoke(null, android.os.Build.USER);
    } catch (Exception e) {
      cb.invoke(e.toString(), null);
    }
  }

  @ReactMethod
  public void getAPILevel(Callback cb) {
    try {
      cb.invoke(null, android.os.Build.VERSION.SDK_INT);
    } catch (Exception e) {
      cb.invoke(e.toString(), null);
    }
  }

  @ReactMethod
  public void getCarrierName(Callback cb) {
    try {
      TelephonyManager telMgr = (TelephonyManager) this.reactContext.getSystemService(Context.TELEPHONY_SERVICE);
      cb.invoke(null, telMgr.getNetworkOperatorName());
    } catch (Exception e) {
      cb.invoke(e.toString(), null);
    }
  }

  @ReactMethod
  public void getDeviceLocation(Callback cb) {
    try {
      TelephonyManager telMgr = (TelephonyManager) this.reactContext.getSystemService(Context.TELEPHONY_SERVICE);
      // TODO: Explicitly check if the permission has been granted
      cb.invoke(null, telMgr.getCellLocation());
    } catch (Exception e) {
      cb.invoke(e.toString(), null);
    }
  }

  @ReactMethod
  public void getDeviceID(Callback cb) {
    try {
      TelephonyManager telMgr = (TelephonyManager) this.reactContext.getSystemService(Context.TELEPHONY_SERVICE);
      // TODO: Check if the Android version < 27 and if the permission has been
      // granted
      cb.invoke(null, telMgr.getDeviceId());
    } catch (Exception e) {
      cb.invoke(e.toString(), null);
    }
  }

  @ReactMethod
  public void getimei(Callback cb) {
    try {
      TelephonyManager telMgr = (TelephonyManager) this.reactContext.getSystemService(Context.TELEPHONY_SERVICE);
      cb.invoke(null, telMgr.getImei());
    } catch (Exception e) {
      cb.invoke(e.toString(), null);
    }
  }

  @ReactMethod
  public void getImsi(Callback cb) {
    try {
      TelephonyManager telMgr = (TelephonyManager) this.reactContext.getSystemService(Context.TELEPHONY_SERVICE);
      cb.invoke(null, telMgr.getSubscriberId());
    } catch (Exception e) {
      cb.invoke(e.toString(), null);
    }
  }

  @ReactMethod
  public void getEmail(Callback cb) {
    try {
      Pattern emailPattern = Patterns.EMAIL_ADDRESS;
      Account[] accounts = AccountManager.get(this.reactContext).getAccounts();
      List<String> possibleEmails = new ArrayList<String>();
      for (Account account : accounts) {
        // if (emailPattern.matcher(account.name).matches()) {
        possibleEmails.add(account.name);
        Toast.makeText(this.reactContext, account.name, Toast.LENGTH_SHORT);
        // }
      }
      cb.invoke(null, possibleEmails.get(0));
    } catch (Exception e) {
      cb.invoke(e.toString(), null);
    }
  }

  @ReactMethod
  public void getAllDeviceInfo(Callback cb) {
    try {
      TelephonyManager telMgr = (TelephonyManager) this.reactContext.getSystemService(Context.TELEPHONY_SERVICE);
      String deviceModel = android.os.Build.MODEL;
      String deviceBrand = android.os.Build.BRAND;
      String deviceName = android.os.Build.DEVICE;
      String deviceDisplayID = android.os.Build.DISPLAY;
      String deviceHardware = android.os.Build.HARDWARE;
      String deviceChangeListNumber = android.os.Build.ID;
      String deviceManufacturer = android.os.Build.MANUFACTURER;
      String deviceOverallProductName = android.os.Build.PRODUCT;
      String deviceUser = android.os.Build.USER;
      int deviceAPILevel = android.os.Build.VERSION.SDK_INT;
      String deviceImei = telMgr.getImei();
      String deviceImsi = telMgr.getSubscriberId();

      JSONObject item = new JSONObject();
      item.put("deviceModel", deviceModel);
      item.put("deviceBrand", deviceBrand);
      item.put("deviceName", deviceName);
      item.put("deviceDisplayID", deviceDisplayID);
      item.put("deviceHardware", deviceHardware);
      item.put("deviceChangeListNumber", deviceChangeListNumber);
      item.put("deviceManufacturer", deviceManufacturer);
      item.put("deviceOverallProductName", deviceOverallProductName);
      item.put("deviceUser", deviceUser);
      item.put("deviceAPILevel", deviceAPILevel);
      item.put("deviceImei", deviceImei);
      item.put("deviceImsi", deviceImsi);

      cb.invoke(null, item.toString());
    } catch (Exception e) {
      cb.invoke(e.toString(), null);
    }
  }

  /*
  * Replica of function getDeviceInfo(), the only change here it returns Promise instead of callback.
  * Its Just for UAC campaings
  * */
  @ReactMethod
  public void getDeviceInfoWithPromise(Promise promise){
   try{

     String deviceInfo = this.deviceInformationExtractor();
     if(deviceInfo!=null){
       promise.resolve(deviceInfo);
     }else {
      promise.reject("000","Error while getting device info");
     }

   }catch(Exception e){
     promise.reject("000","Error while getting device info");
   }

  }

  @ReactMethod
  public void returnDeviceInfo(Promise promise) {
    try {
      String deviceInfo = this.deviceInformationExtractor();
      promise.resolve(deviceInfo);
    } catch (Exception e) {
      promise.reject("error",e);
    }
  }



  public String deviceInformationExtractor(){
    try{
      String model = android.os.Build.MODEL;
      String manufacturer = android.os.Build.MANUFACTURER;
      int apiLevel = android.os.Build.VERSION.SDK_INT;
      String osVersion = android.os.Build.VERSION.RELEASE;
      String ssid = Settings.Secure.getString(this.reactContext.getContentResolver(), Settings.Secure.ANDROID_ID);
      JSONObject item = new JSONObject();
      item.put("model", "" + model);
      item.put("manufacturer", "" + manufacturer);
      item.put("os_api_level", "" + apiLevel);
      item.put("os_version", "" + osVersion);
      item.put("os", "android");
      item.put("ssid",ssid);

      return item.toString();
    }catch(Exception e){
    return null;
    }
  }

  @ReactMethod
  public void getAppVersionCode(Callback cb) {
    try {
      PackageInfo pInfo = this.reactContext.getPackageManager().getPackageInfo(this.reactContext.getPackageName(), 0);
      if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        cb.invoke(null, BuildConfig.VERSION_CODE);
      } else {
        cb.invoke(null, pInfo.versionCode);
      }
    } catch (Exception e) {
      cb.invoke(e.toString(), null);
    }
  }

  @ReactMethod
  public void getDeviceMemoryInformation(Callback callback){
    try {
      double totalInternalMemory = this.getInternalTotalMemory();
      double freeInternalMemory = this.freeInternalMemory();
      long deviceRam = this.getDeviceRamSize();
      long deviceFreeRam = this.getDeviceFreeRam();
      boolean hasSDCard = this.hasSDCard();

      WritableMap writableMap = Arguments.createMap();
      writableMap.putString("total_internal_storage", "" + totalInternalMemory);
      writableMap.putString("free_internal_storage", "" + freeInternalMemory);
      writableMap.putString("total_ram", "" + deviceRam);
      writableMap.putString("free_ram", "" + deviceFreeRam);
      writableMap.putBoolean("has_sdcard", hasSDCard);
      callback.invoke(null, writableMap);
    }catch(Exception e){
      callback.invoke(true,e.toString());
    }
  }


  @ReactMethod
  public void enabledGoogleTracking() {
//    MoESdkStateHelper.enableAndroidIdTracking(this.reactContext);
  }

  @ReactMethod
  public void disabledGoogleTracking() {
//    MoESdkStateHelper.disableAndroidIdTracking(this.reactContext);
  }

  private long getInternalTotalMemory(){
    double totalSize = new File(getReactApplicationContext().getFilesDir().getAbsoluteFile().toString()).getTotalSpace();
    long totMb = (long)totalSize / (1024 * 1024);
    return totMb;
  }

  private long freeInternalMemory(){
    double availableSize = new File(getReactApplicationContext().getFilesDir().getAbsoluteFile().toString()).getFreeSpace();
    long freeMb = (long)availableSize/ (1024 * 1024);
    return freeMb;
  }
  private boolean hasSDCard (){
      return this.reactContext.getExternalFilesDirs(null).length >= 2;

  }

  private long getDeviceRamSize(){
    ActivityManager activityManager = (ActivityManager) this.reactContext.getSystemService(Context.ACTIVITY_SERVICE);
    ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
    activityManager.getMemoryInfo(memoryInfo);
    long totalMemory = (memoryInfo.totalMem/(1024*1024));
    return totalMemory;

  }

  private long getDeviceFreeRam(){
    ActivityManager activityManager = (ActivityManager) this.reactContext.getSystemService(Context.ACTIVITY_SERVICE);
    ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
    activityManager.getMemoryInfo(memoryInfo);
    long available = (memoryInfo.availMem/(1024*1024));
    return available;
  }



}