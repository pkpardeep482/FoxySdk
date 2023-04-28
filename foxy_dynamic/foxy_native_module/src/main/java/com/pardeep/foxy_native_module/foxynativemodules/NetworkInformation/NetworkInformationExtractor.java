package com.pardeep.foxy_native_module.foxynativemodules.NetworkInformation;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.CountDownTimer;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import org.json.JSONObject;
import java.util.ArrayList;

import javax.annotation.Nullable;

public class NetworkInformationExtractor {
    private TelephonyManager telephonyManager;
    private ConnectivityManager connectivityManager;
    private Context ctx;
    private ReactContext reactContext;

    private String network_name = ""; // wifi, 4g,3g etc
    private String download_speed = ""; // Download speed of active network
    private String upload_speed = ""; // Upload speed of active network
    private String carrier_name = ""; // Mobile Carrier name
    private String wifi_signal_strength = "";
    private String carrier_signal_strength = "";
    private String carrier_snr = "";

    public NetworkInformationExtractor(ReactContext context) {
        ctx = context;
        reactContext = context;
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        network_name = getNetworkName();
        ArrayList<String> networkSpeed = getNetworkSpeed(ctx);
        download_speed = networkSpeed.get(0);
        upload_speed = networkSpeed.get(1);
        carrier_name = telephonyManager.getNetworkOperatorName();
        telephonyManager.listen(new CustomPhoneStateListener(), PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);

    }

    public String getNetworkName() {

        String network_name = "unknown";
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info == null || !info.isConnected())
            return "-"; // not connected
        if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            network_name = "wifi";
            WifiManager wifiManager = (WifiManager) this.ctx.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            wifi_signal_strength = "" + wifiInfo.getRssi();

        }
        if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int networkType = info.getSubtype();
            switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN: // api< 8: replace by 11
            case TelephonyManager.NETWORK_TYPE_GSM: // api<25: replace by 16
                network_name = "2g";
                break;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B: // api< 9: replace by 12
            case TelephonyManager.NETWORK_TYPE_EHRPD: // api<11: replace by 14
            case TelephonyManager.NETWORK_TYPE_HSPAP: // api<13: replace by 15
            case TelephonyManager.NETWORK_TYPE_TD_SCDMA: // api<25: replace by 17
                network_name = "3g";
                break;
            case TelephonyManager.NETWORK_TYPE_LTE: // api<11: replace by 13
            case TelephonyManager.NETWORK_TYPE_IWLAN: // api<25: replace by 18
            case 19: // LTE_CA
                network_name = "4g";
                break;
            default:
                network_name = "unknown";
                break;

            }
        }

        return network_name;
    }

    public ArrayList<String> getNetworkSpeed(Context context) {

        Network netInfo = connectivityManager.getActiveNetwork();
        // should check null because in airplane mode it will be null
        NetworkCapabilities nc = connectivityManager.getNetworkCapabilities(netInfo);
        int downloadSpeed = 0;
        int uploadSpeed = 0;
        ArrayList<String> networkSpeed = new ArrayList<String>();
        if (nc != null) {
            downloadSpeed = nc.getLinkDownstreamBandwidthKbps();
            uploadSpeed = nc.getLinkUpstreamBandwidthKbps();
        }
        networkSpeed.add("" + downloadSpeed);
        networkSpeed.add("" + uploadSpeed);
        return networkSpeed;

    }

    private void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }

    class CustomPhoneStateListener extends PhoneStateListener {
        Callback cb;

        public CustomPhoneStateListener() {

        }

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            carrier_signal_strength = "" + signalStrength.getGsmSignalStrength();

            try {
                try {
                    carrier_snr = "" + SignalStrength.class.getMethod("getLteRssnr").invoke(signalStrength);
                }catch (Exception e){
                    
                }
                telephonyManager.listen(CustomPhoneStateListener.this, PhoneStateListener.LISTEN_NONE);

                WritableMap params = Arguments.createMap();
                params.putBoolean("success", true);
                params.putString("network_type", network_name);
                params.putString("download_speed", download_speed);
                params.putString("upload_speed", upload_speed);
                params.putString("carrier_name", carrier_name);
                params.putString("wifi_signal_strength", "" + wifi_signal_strength);
                params.putString("carrier_signal_strength", carrier_signal_strength);
                params.putString("snr", carrier_snr);
                sendEvent(reactContext, "onNetworkInformation", params);
            } catch (Exception e) {
                WritableMap params = Arguments.createMap();
                params.putBoolean("success", false);
                sendEvent(reactContext, "onNetworkInformation", params);
            }

        }
    }
}
