//package com.pardeep.foxy_dynamic.foxynativemodules.payment;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import androidx.fragment.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Toast;
//import android.util.Log;
//import com.facebook.react.bridge.ActivityEventListener;
//import com.facebook.react.bridge.Arguments;
//import com.facebook.react.bridge.Promise;
//import com.facebook.react.bridge.ReactApplicationContext;
//import com.facebook.react.bridge.ReactContext;
//import com.facebook.react.bridge.ReactContextBaseJavaModule;
//import com.facebook.react.bridge.ReactMethod;
//import com.facebook.react.bridge.ReadableMap;
//import com.facebook.react.bridge.WritableArray;
//import com.facebook.react.bridge.WritableMap;
//import com.facebook.react.bridge.WritableNativeArray;
//import com.facebook.react.bridge.WritableNativeMap;
//import com.facebook.react.modules.core.DeviceEventManagerModule;
//import com.payu.india.Interfaces.PaymentRelatedDetailsListener;
//import com.payu.india.Model.MerchantWebService;
//import com.payu.india.Model.PaymentDetails;
//import com.payu.india.Tasks.DeleteCardTask;
//import com.payu.india.Model.PaymentParams;
//import com.payu.india.Interfaces.DeleteCardApiListener;
//import com.payu.india.Model.PayuConfig;
//import com.payu.india.Model.PayuResponse;
//import com.payu.paymentparamhelper.PostData;
//import com.payu.upisdk.generatepostdata.PaymentParamsUpiSdk;
//import com.payu.upisdk.generatepostdata.PostDataGenerate;
//import com.payu.india.Model.StoredCard;
//import com.payu.india.Payu.Payu;
//import com.payu.india.Payu.PayuConstants;
//import androidx.fragment.app.FragmentActivity;
//import com.payu.india.Payu.PayuErrors;
//import com.payu.upisdk.util.UpiConstant;
//import com.payu.upisdk.PaymentOption;
//import com.payu.upisdk.Upi;
//import com.payu.upisdk.bean.UpiConfig;
//import com.payu.upisdk.callbacks.PayUUPICallback;
//import com.payu.upisdk.generatepostdata.PostDataUpiSdk;
//import com.payu.india.PostParams.MerchantWebServicePostParams;
//import com.payu.india.PostParams.PaymentPostParams;
//import com.payu.india.Tasks.GetPaymentRelatedDetailsTask;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import android.content.Intent;
//import java.util.ArrayList;
//import java.util.Iterator;
//import javax.annotation.Nonnull;
//
//import com.pardeep.foxy_dynamic.foxynativemodules.Model.PayuPaymentParams;
//
//public class PayuPayment extends ReactContextBaseJavaModule implements ActivityEventListener {
//
//    PaymentParams mPaymentParams;
//    UpiConfig upiConfig;
//    private Upi upi;
//    String merchantKey;
//    String hash;
//    private PaymentParamsUpiSdk mPaymentParamsUpiSdk;
//    PayuConfig payuConfig = new PayuConfig();
//    String postDataFromUpiSdk;
//    private Promise payuPromise;
//    private int environment = PayuConstants.PRODUCTION_ENV;
//    ArrayList<String> paymentOptionsList = new ArrayList<String>();
//    private Object UpiConfig;
//    private Promise promise;
//    private View view;
//
//    private ReactApplicationContext myReactContext;
//
//    public PayuPayment(@Nonnull ReactApplicationContext reactContext) {
//        super(reactContext);
//        reactContext.addActivityEventListener(this);
//        myReactContext = reactContext;
//        Payu.setInstance(reactContext);
//    }
//
//    @Nonnull
//    @Override
//    public String getName() {
//        return "PayuPayment";
//    }
//
//    @Override
//    public boolean hasConstants() {
//        return super.hasConstants();
//    }
//
//    @ReactMethod
//    public void setPaymentParams(ReadableMap data, Promise promise) {
//        if (data == null){
//            return;
//        }
//        merchantKey = data.getString("key");
//        hash = data.getString("paymentHash");
//        if (data.getString("environment") == null){
//            return;
//        }
//        if (data.getString("environment").equals("ENVIRONMENT_TEST")) {
//            environment = PayuConstants.STAGING_ENV;
//        } else {
//            environment = PayuConstants.PRODUCTION_ENV;
//        }
//
//        mPaymentParams = new PaymentParams();
//        mPaymentParams.setKey(data.getString("key"));
//        mPaymentParams.setAmount(data.getString("amount"));
//        mPaymentParams.setProductInfo(data.getString("productInfo"));
//        mPaymentParams.setFirstName(data.getString("firstName"));
//        mPaymentParams.setEmail(data.getString("email"));
//        mPaymentParams.setTxnId(data.getString("txnId"));
//        mPaymentParams.setSurl(data.getString("surl"));
//        mPaymentParams.setFurl(data.getString("furl"));
//        mPaymentParams.setUserCredentials(data.getString("userCredentials"));
//        mPaymentParams.setPhone(data.getString("phoneNumber"));
//        mPaymentParams.setOfferKey("");
//        mPaymentParams.setUdf1("");
//        mPaymentParams.setUdf2("");
//        mPaymentParams.setUdf3("");
//        mPaymentParams.setUdf4("");
//        mPaymentParams.setUdf5("");
//        mPaymentParams.setHash(data.getString("paymentHash"));
//        MerchantWebService merchantWebService = new MerchantWebService();
//        merchantWebService.setKey(data.getString("key")); // Merchant key
//        merchantWebService.setCommand(PayuConstants.PAYMENT_RELATED_DETAILS_FOR_MOBILE_SDK); // Command for fetching
//                                                                                             // payment related details
//        merchantWebService.setVar1(data.getString("userCredentials")); // User Credential of the merchant
//        merchantWebService.setHash(data.getString("paymentRelatedDetailsHash"));
//        PostData postData = new MerchantWebServicePostParams(merchantWebService).getMerchantWebServicePostParams();
//        if (postData.getCode() == PayuErrors.NO_ERROR) {
//            payuConfig.setData(postData.getResult());
//            payuConfig.setEnvironment(environment);
//            GetPaymentRelatedDetailsTask paymentRelatedDetailsTask = new GetPaymentRelatedDetailsTask(
//                    new PaymentRelatedDetailsListener() {
//                        @Override
//                        public void onPaymentRelatedDetailsResponse(PayuResponse payuResponse) {
//                            if (payuResponse == null) {
//                                promise.reject("001", "error in getting payment related details");
//                            }
//                            payuResponse.getCreditCard();
//                            payuResponse.getStoredCards();
//                            WritableArray availablePaymentOptions = Arguments.createArray();
//                            WritableArray availableSavedCards = Arguments.createArray();
//                            WritableArray availableNetBanking = Arguments.createArray();
//                            WritableMap responseToJS = Arguments.createMap();
//                            ArrayList<StoredCard> storeCards = payuResponse.getStoredCards();
//                            if (storeCards != null && storeCards.size() != 0) {
//                                for (StoredCard storedCard : storeCards) {
//                                    WritableMap cardDetails = Arguments.createMap();
//                                    cardDetails.putString("cardName", storedCard.getCardName());
//                                    cardDetails.putString("cardNumber", storedCard.getMaskedCardNumber());
//                                    cardDetails.putString("cardBin", storedCard.getCardBin());
//                                    cardDetails.putString("cardToken", storedCard.getCardToken());
//                                    cardDetails.putString("cardBrand", storedCard.getCardBrand());
//                                    cardDetails.putString("expiryMonth", storedCard.getExpiryMonth());
//                                    cardDetails.putString("expiryYear", storedCard.getExpiryYear());
//                                    cardDetails.putString("nameOnCard", storedCard.getNameOnCard());
//                                    cardDetails.putString("bankName", storedCard.getIssuingBank());
//                                    availableSavedCards.pushMap(cardDetails);
//                                }
//                            }
//                            if (payuResponse.getNetBanks() != null) {
//                                for (PaymentDetails netBank : payuResponse.getNetBanks()) {
//                                    WritableMap netBankDetail = Arguments.createMap();
//                                    netBankDetail.putString("title", netBank.getBankName());
//                                    netBankDetail.putString("bankCode", netBank.getBankCode());
//                                    availableNetBanking.pushMap(netBankDetail);
//                                }
//                            }
//                            responseToJS.putArray("availableSavedCards", availableSavedCards);
//                            responseToJS.putArray("availablePaymentOptions", availablePaymentOptions);
//                            responseToJS.putArray("availableNetBanking", availableNetBanking);
//                            promise.resolve(responseToJS);
//                        }
//                    });
//            paymentRelatedDetailsTask.execute(payuConfig);
//        } else {
//            promise.reject("001", "error in fetching payment options");
//        }
//    }
//
//    @ReactMethod
//    public void setPayuParamsForIntent(ReadableMap data, Promise promise) {
//        merchantKey = data.getString("key");
//        hash = data.getString("paymentHash");
//        try {
//
//            environment = PayuConstants.PRODUCTION_ENV;
//            mPaymentParamsUpiSdk = new PaymentParamsUpiSdk();
//            mPaymentParamsUpiSdk.setKey(data.getString("key"));
//            mPaymentParamsUpiSdk.setAmount(data.getString("amount"));
//            mPaymentParamsUpiSdk.setProductInfo(data.getString("productInfo"));
//            mPaymentParamsUpiSdk.setFirstName(data.getString("firstName"));
//            mPaymentParamsUpiSdk.setEmail(data.getString("email"));
//            mPaymentParamsUpiSdk.setTxnId(data.getString("txnId"));
//            mPaymentParamsUpiSdk.setSurl(data.getString("surl"));
//            mPaymentParamsUpiSdk.setFurl(data.getString("furl"));
//            mPaymentParamsUpiSdk.setUserCredentials(data.getString("userCredentials"));
//            mPaymentParamsUpiSdk.setPhone(data.getString("phoneNumber"));
//            mPaymentParamsUpiSdk.setOfferKey("");
//            mPaymentParamsUpiSdk.setUdf1("");
//            mPaymentParamsUpiSdk.setUdf2("");
//            mPaymentParamsUpiSdk.setUdf3("");
//            mPaymentParamsUpiSdk.setUdf4("");
//            mPaymentParamsUpiSdk.setUdf5("");
//            mPaymentParamsUpiSdk.setHash(data.getString("paymentHash"));
//
//            postDataFromUpiSdk = new PostDataGenerate.PostDataBuilder(getReactApplicationContext())
//                    .setPaymentMode(UpiConstant.UPI_INTENT).setPaymentParamUpiSdk(mPaymentParamsUpiSdk).build()
//                    .toString();
//            promise.resolve(true);
//        } catch (Exception e) {
//            e.printStackTrace();
//            promise.reject("Error", "Error While intent ");
//        }
//
//    }
//
//    @ReactMethod
//    public void payWithIntent(String appPackage, Promise promise) {
//        this.promise = promise;
//
//        try {
//            if (postDataFromUpiSdk != null) {
//                final Activity activity = getCurrentActivity();
//                upiConfig = new UpiConfig();
//                upiConfig.setMerchantKey(merchantKey);
//                upiConfig.setPayuPostData(postDataFromUpiSdk);
//                upiConfig.setPackageNameForSpecificApp(appPackage);
//                upi = Upi.getInstance();
//                upi.makePayment(payUUpiSdkCallbackUpiSdk, activity, upiConfig);
//            }
//        } catch (Exception e) {
//            this.promise.reject("Error", e.toString());
//        }
//    }
//
//    PayUUPICallback payUUpiSdkCallbackUpiSdk = new PayUUPICallback() {
//        @Override
//        public void onPaymentFailure(String payuResult, String merchantResponse) {
//            super.onPaymentFailure(payuResult, merchantResponse);
//            promise.reject("Error", payuResult);
//        }
//
//        // @Override
//        // public void onPaymentTerminate() {
//        // super.onPaymentTerminate();
//        // }
//
//        @Override
//        public void onPaymentSuccess(String payuResult, String merchantResponse) {
//            super.onPaymentSuccess(payuResult, merchantResponse);
//            myReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
//                    .emit("payu_payment_success", payuResult);
//            new PaymentStatusNetworkCall(promise, payuResult).execute();
//        }
//
//        @Override
//        public void onUpiErrorReceived(int code, String errormsg) {
//            super.onUpiErrorReceived(code, errormsg);
//            myReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("payu_upi_error",
//                    errormsg);
//        }
//
//        // @Override
//        // public void onBackButton(AlertDialog.Builder alertDialogBuilder) {
//        // super.onBackButton(alertDialogBuilder);
//        // }
//
//        // @Override
//        // public void onBackApprove() {
//        // super.onBackApprove();
//        // }
//
//        // @Override
//        // public void onBackDismiss() {
//        // super.onBackDismiss();
//        // }
//
//        // @Override
//        // public void isPaymentOptionAvailable(boolean isAvaialble, String paymentType) {
//        //     super.isPaymentOptionAvailable(isAvaialble, paymentType);
//        // }
//    };
//
//    @ReactMethod
//    public void payWithCard(ReadableMap cardParams, boolean saveCard, Promise promise) {
//        payuPromise = promise;
//        mPaymentParams.setCardNumber(cardParams.getString("cardNumber"));
//        mPaymentParams.setCardName(cardParams.getString("cardName"));
//        mPaymentParams.setNameOnCard(cardParams.getString("cardName"));
//        mPaymentParams.setExpiryMonth(cardParams.getString("expiryMonth"));// MM
//        mPaymentParams.setExpiryYear(cardParams.getString("expiryYear"));// YYYY
//        mPaymentParams.setCvv(cardParams.getString("cvv"));
//        if (saveCard) {
//            mPaymentParams.setStoreCard(1);
//        }
//        try {
//            PostData postData = new PaymentPostParams(mPaymentParams, PayuConstants.CC).getPaymentPostParams();
//            if (postData.getCode() == PayuErrors.NO_ERROR) {
//                // launch webview
//                PayuConfig payuConfig = new PayuConfig();
//                payuConfig.setEnvironment(environment);
//                payuConfig.setData(postData.getResult());
//
//                Intent intent = new Intent(getReactApplicationContext(), PaymentWebView.class);
//                intent.putExtra(PayuConstants.PAYU_CONFIG, payuConfig);
//                intent.putExtra("surl", mPaymentParams.getSurl());
//                intent.putExtra("furl", mPaymentParams.getFurl());
//                getReactApplicationContext().startActivityForResult(intent, PayuAndroidConstants.PAYMENT_CODE, null);
//            } else {
//                // something went wrong
//                payuPromise.reject("", postData.getResult());
//            }
//        } catch (Exception e) {
//            payuPromise.reject("", e.getMessage());
//        }
//    }
//
//    @ReactMethod
//    public void payWithSavedCard(ReadableMap cardParams, Promise promise) {
//        payuPromise = promise;
//        mPaymentParams.setCardToken(cardParams.getString("cardToken"));
//        mPaymentParams.setCvv(cardParams.getString("cvv"));
//        try {
//            PostData postData = new PaymentPostParams(mPaymentParams, PayuConstants.CC).getPaymentPostParams();
//            if (postData.getCode() == PayuErrors.NO_ERROR) {
//                // launch webview
//                PayuConfig payuConfig = new PayuConfig();
//                payuConfig.setEnvironment(environment);
//                payuConfig.setData(postData.getResult());
//                Intent intent = new Intent(getReactApplicationContext(), PaymentWebView.class);
//                intent.putExtra("surl", mPaymentParams.getSurl());
//                intent.putExtra("furl", mPaymentParams.getFurl());
//                intent.putExtra(PayuConstants.PAYU_CONFIG, payuConfig);
//                getReactApplicationContext().startActivityForResult(intent, PayuAndroidConstants.PAYMENT_CODE, null);
//            } else {
//                // something went wrong
//                payuPromise.reject("", postData.getResult());
//            }
//        } catch (Exception e) {
//            payuPromise.reject("", e.getMessage());
//        }
//    }
//
//    @ReactMethod
//    public void payWithNetBanking(ReadableMap netBankingParams, Promise promise) {
//        payuPromise = promise;
//        mPaymentParams.setBankCode(netBankingParams.getString("bankCode"));
//        try {
//            PostData postData = new PaymentPostParams(mPaymentParams, PayuConstants.NB).getPaymentPostParams();
//            if (postData.getCode() == PayuErrors.NO_ERROR) {
//                // launch webview
//                PayuConfig payuConfig = new PayuConfig();
//                payuConfig.setEnvironment(environment);
//                payuConfig.setData(postData.getResult());
//                Intent intent = new Intent(getReactApplicationContext(), PaymentWebView.class);
//                intent.putExtra(PayuConstants.PAYU_CONFIG, payuConfig);
//                intent.putExtra("surl", mPaymentParams.getSurl());
//                intent.putExtra("furl", mPaymentParams.getFurl());
//                getReactApplicationContext().startActivityForResult(intent, PayuAndroidConstants.PAYMENT_CODE, null);
//            } else {
//                // something went wrong
//                payuPromise.reject("", postData.getResult());
//            }
//        } catch (Exception e) {
//            payuPromise.reject("", e.getMessage());
//        }
//    }
//
//    @ReactMethod
//    public void payWithUPI(ReadableMap upiParams, Promise promise) {
//        payuPromise = promise;
//        mPaymentParams.setVpa(upiParams.getString("vpa"));
//        try {
//            PostData postData = new PaymentPostParams(mPaymentParams, PayuConstants.UPI).getPaymentPostParams();
//            if (postData.getCode() == PayuErrors.NO_ERROR) {
//                // launch webview
//                PayuConfig payuConfig = new PayuConfig();
//                payuConfig.setEnvironment(environment);
//                payuConfig.setData(postData.getResult());
//                Intent intent = new Intent(getReactApplicationContext(), PaymentWebView.class);
//                intent.putExtra(PayuConstants.PAYU_CONFIG, payuConfig);
//                intent.putExtra("surl", mPaymentParams.getSurl());
//                intent.putExtra("furl", mPaymentParams.getFurl());
//                getReactApplicationContext().startActivityForResult(intent, PayuAndroidConstants.PAYMENT_CODE, null);
//            } else {
//                // something went wrong
//                payuPromise.reject("", postData.getResult());
//            }
//        } catch (Exception e) {
//            payuPromise.reject("", e.getMessage());
//        }
//    }
//
//    @ReactMethod
//    public void payWithPayUMoney(Promise promise) {
//        try {
//            PostData postData = new PaymentPostParams(mPaymentParams, PayuConstants.PAYU_MONEY).getPaymentPostParams();
//            if (postData.getCode() == PayuErrors.NO_ERROR) {
//                // launch webview
//                PayuConfig payuConfig = new PayuConfig();
//                payuConfig.setEnvironment(environment);
//                payuConfig.setData(postData.getResult());
//                Intent intent = new Intent(getReactApplicationContext(), PaymentWebView.class);
//                intent.putExtra(PayuConstants.PAYU_CONFIG, payuConfig);
//                intent.putExtra("surl", mPaymentParams.getSurl());
//                intent.putExtra("furl", mPaymentParams.getFurl());
//                getReactApplicationContext().startActivityForResult(intent, PayuAndroidConstants.PAYMENT_CODE, null);
//            } else {
//                // something went wrong
//                promise.reject("", postData.getResult());
//            }
//        } catch (Exception e) {
//            promise.reject("", e.getMessage());
//        }
//    }
//
//    @ReactMethod
//    public void deleteSavedCard(ReadableMap cardParams, Promise promise) {
//        MerchantWebService merchantWebService = new MerchantWebService();
//
//        Log.e("Params ", cardParams.getString("userCredentials"));
//        Log.e("Params ", cardParams.getString("cardToken"));
//        Log.e("Params ", cardParams.getString("deleteCardHash"));
//        Log.e("Params ", cardParams.getString("key"));
//
//        merchantWebService.setKey(cardParams.getString("key"));
//        merchantWebService.setCommand("delete_user_card");
//        merchantWebService.setVar1(cardParams.getString("userCredentials")); // user credentials
//        merchantWebService.setVar2(cardParams.getString("cardToken")); // Card Token
//        merchantWebService.setHash(cardParams.getString("deleteCardHash")); // Delete Card Hash
//        PostData postData = new MerchantWebServicePostParams(merchantWebService).getMerchantWebServicePostParams();
//        Log.e("State", "" + postData.getCode());
//
//        if (postData.getCode() == PayuErrors.NO_ERROR) {
//            PayuConfig payuConfig = new PayuConfig();
//            payuConfig.setEnvironment(environment);
//            payuConfig.setData(postData.getResult());
//            DeleteCardTask deleteCardTask = new DeleteCardTask(new DeleteCardApiListener() {
//                @Override
//                public void onDeleteCardApiResponse(PayuResponse payuResponse) {
//                    promise.resolve(true);
//
//                }
//            });
//            deleteCardTask.execute(payuConfig);
//
//        } else {
//
//            promise.reject("Error", "Error While Deleting card");
//        }
//    }
//
//    @Override
//    public void onNewIntent(Intent intent) {
//
//    }
//
//    @Override
//    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
//        // Check which request we're responding to
//        if (requestCode == PayuAndroidConstants.PAYMENT_CODE) {
//            WritableMap responseToJS = Arguments.createMap();
//            if (resultCode == PayuAndroidConstants.RESULT_PAYMENT_SUCCESS) {
//                String response = data.getStringExtra("payuResponse");
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    responseToJS = convertJsonToMap(jsonObject);
//                    payuPromise.resolve(responseToJS);
//
//                } catch (Exception e) {
//                    payuPromise.reject("001", "Payment Failed");
//                }
//            } else if (resultCode == PayuAndroidConstants.RESULT_GO_BACK) {
//                payuPromise.reject("cancelled", "Payment Failed");
//            } else {
//                payuPromise.reject("002", "Payment Failed");
//            }
//        }
//
//    }
//
//    private static WritableMap convertJsonToMap(JSONObject jsonObject) throws JSONException {
//        WritableMap map = new WritableNativeMap();
//        Iterator<String> iterator = jsonObject.keys();
//        while (iterator.hasNext()) {
//            String key = iterator.next();
//            Object value = jsonObject.get(key);
//            if (value instanceof JSONObject) {
//                map.putMap(key, convertJsonToMap((JSONObject) value));
//            } else if (value instanceof JSONArray) {
//                map.putArray(key, convertJsonToArray((JSONArray) value));
//            } else if (value instanceof Boolean) {
//                map.putBoolean(key, (Boolean) value);
//            } else if (value instanceof Integer) {
//                map.putInt(key, (Integer) value);
//            } else if (value instanceof Double) {
//                map.putDouble(key, (Double) value);
//            } else if (value instanceof String) {
//                map.putString(key, (String) value);
//            } else {
//                map.putString(key, value.toString());
//            }
//        }
//        return map;
//    }
//
//    private static WritableArray convertJsonToArray(JSONArray jsonArray) throws JSONException {
//        WritableArray array = new WritableNativeArray();
//        for (int i = 0; i < jsonArray.length(); i++) {
//            Object value = jsonArray.get(i);
//            if (value instanceof JSONObject) {
//                array.pushMap(convertJsonToMap((JSONObject) value));
//            } else if (value instanceof JSONArray) {
//                array.pushArray(convertJsonToArray((JSONArray) value));
//            } else if (value instanceof Boolean) {
//                array.pushBoolean((Boolean) value);
//            } else if (value instanceof Integer) {
//                array.pushInt((Integer) value);
//            } else if (value instanceof Double) {
//                array.pushDouble((Double) value);
//            } else if (value instanceof String) {
//                array.pushString((String) value);
//            } else {
//                array.pushString(value.toString());
//            }
//        }
//        return array;
//    }
//}