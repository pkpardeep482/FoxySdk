//package com.pardeep.foxy_dynamic.foxynativemodules.Truecaller;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//import android.widget.Toast;
//import androidx.annotation.NonNull;
//
//import com.facebook.react.ReactActivity;
//import com.facebook.react.bridge.ActivityEventListener;
//import com.facebook.react.bridge.Arguments;
//import com.facebook.react.bridge.Callback;
//import com.facebook.react.bridge.ReactApplicationContext;
//import com.facebook.react.bridge.ReactContext;
//import com.facebook.react.bridge.ReactContextBaseJavaModule;
//import com.facebook.react.bridge.ReactMethod;
//import com.facebook.react.bridge.WritableMap;
//import com.facebook.react.modules.core.DeviceEventManagerModule;
//import com.truecaller.android.sdk.ITrueCallback;
//import com.truecaller.android.sdk.TrueError;
//import com.truecaller.android.sdk.TrueProfile;
//import com.truecaller.android.sdk.TruecallerSDK;
//import com.truecaller.android.sdk.TruecallerSdkScope;
//
//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
//
//import in.foxy.MainActivity;
//
//public class TruecallerModule extends ReactContextBaseJavaModule implements ActivityEventListener, ITrueCallback {
//
//    ReactContext context = null;
//    private TruecallerSDK truecallerSDK = null;
//    private boolean isTruecallerSdkUsable = false;
//
//    public TruecallerModule(@Nonnull ReactApplicationContext reactContext) {
//        super(reactContext);
//        context = reactContext;
//        TruecallerSdkScope trueScope = new TruecallerSdkScope.Builder(context, this)
//                .consentMode(TruecallerSdkScope.CONSENT_MODE_BOTTOMSHEET)
//                .consentTitleOption(TruecallerSdkScope.SDK_CONSENT_TITLE_GET_STARTED)
//                .footerType(TruecallerSdkScope.FOOTER_TYPE_CONTINUE)
//                .sdkOptions(TruecallerSdkScope.SDK_OPTION_WITHOUT_OTP).build();
//        TruecallerSDK.init(trueScope);
//        this.truecallerSDK = TruecallerSDK.getInstance();
//        this.isTruecallerSdkUsable = this.truecallerSDK.isUsable();
//        reactContext.addActivityEventListener(this);
//    }
//
//    @Nonnull
//    @Override
//    public String getName() {
//        return "Truecaller";
//    }
//
//    @ReactMethod()
//    public void requestUserProfile() {
//        this.truecallerSDK.getUserProfile(MainActivity.mainActivity);
//    }
//
//    @ReactMethod()
//    public void isUsable(Callback callback) {
//        callback.invoke(null, this.isTruecallerSdkUsable);
//    }
//
//
//
//    @Override
//    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
//
//        if (data != null && this.truecallerSDK.isUsable()) {
//            this.truecallerSDK.onActivityResultObtained(MainActivity.mainActivity, requestCode, resultCode, data);
//        }
//
//    }
//
//    @Override
//    public void onNewIntent(Intent intent) {
//    }
//
//    @Override
//    public void onSuccessProfileShared(@NonNull TrueProfile trueProfile) {
//        WritableMap params = Arguments.createMap();
//
//        params.putString("firstName", trueProfile.firstName);
//
//        params.putString("lastName", trueProfile.lastName);
//
//        params.putString("phoneNumber", trueProfile.phoneNumber);
//
//        params.putString("gender", trueProfile.gender);
//
//        params.putString("street", trueProfile.street);
//
//        params.putString("city", trueProfile.city);
//
//        params.putString("zipcode", trueProfile.zipcode);
//
//        params.putString("countryCode", trueProfile.countryCode);
//
//        params.putString("facebookId", trueProfile.facebookId);
//
//        params.putString("twitterId", trueProfile.twitterId);
//
//        params.putString("email", trueProfile.email);
//
//        params.putString("url", trueProfile.url);
//
//        params.putString("avatarUrl", trueProfile.avatarUrl);
//
//        params.putBoolean("isTrueName", trueProfile.isTrueName);
//
//        params.putBoolean("isAmbassador", trueProfile.isAmbassador);
//
//        params.putString("companyName", trueProfile.companyName);
//
//        params.putString("jobTitle", trueProfile.jobTitle);
//
//        params.putString("payload", trueProfile.payload);
//
//        params.putString("signature", trueProfile.signature);
//
//        params.putString("signatureAlgorithm", trueProfile.signatureAlgorithm);
//
//        params.putString("requestNonce", trueProfile.requestNonce);
//
//        params.putBoolean("isSimChanged", trueProfile.isSimChanged);
//
//        params.putString("verificationMode", trueProfile.verificationMode);
//
//        sendEvent(context, "didReceiveTrueProfileResponse", params);
//
//    }
//
//    @Override
//    public void onFailureProfileShared(@NonNull TrueError trueError) {
//        // Truecaller triggering this error before every requestUserProfile() call.
//        // Event nothing found about this error in there official Doc
//        if (trueError.getErrorType() == 5) {
//            return;
//        }
//        WritableMap params = Arguments.createMap();
//        params.putString("error", "" + trueError.getErrorType());
//        sendEvent(context, "didReceiveError", params);
//    }
//
//    @Override
//    public void onVerificationRequired(TrueError trueError) {
//
//    }
//
//
//    private void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
//        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
//    }
//}
