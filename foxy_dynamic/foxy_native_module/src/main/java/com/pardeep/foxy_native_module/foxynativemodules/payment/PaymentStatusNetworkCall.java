//package com.pardeep.foxy_dynamic.foxynativemodules.payment;
//
//import android.os.AsyncTask;
//import android.util.Log;
//
//import com.facebook.react.bridge.Arguments;
//import com.facebook.react.bridge.Promise;
//import com.facebook.react.bridge.WritableArray;
//import com.facebook.react.bridge.WritableMap;
//import com.facebook.react.bridge.WritableNativeArray;
//import com.facebook.react.bridge.WritableNativeMap;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.Iterator;
//
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//public class PaymentStatusNetworkCall extends AsyncTask<Void, Void, WritableMap> {
//
//    private Promise promise;
//    private String payuResult;
//    private OkHttpClient okHttpClient;
//    private JSONObject bodyJson;
//    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
//
//    public PaymentStatusNetworkCall(Promise promise, String payuResult) {
//
//        okHttpClient = new OkHttpClient();
//        this.promise = promise;
//        // this.payuResult=payuResult;
//        try {
//            bodyJson = new JSONObject(payuResult);
//            this.payuResult = bodyJson.getJSONObject("result").toString();
//            Log.e("String", this.payuResult);
//        } catch (JSONException e) {
//            Log.e("Error json", e.toString());
//        }
//        Log.d("Payu Result", payuResult);
//    }
//
//    @Override
//    protected WritableMap doInBackground(Void... voids) {
//        Log.e("Do in background", "Api init");
//        RequestBody body = RequestBody.create(JSON, this.payuResult);
//        Log.e("Sending params", "" + body);
//        Request request = new Request.Builder().url(PayuAndroidConstants.API_URL).post(body)
//                .build();
//        WritableMap map = Arguments.createMap();
//        try {
//            Response response = okHttpClient.newCall(request).execute();
//
//            String strResponse = response.body().string();
//            Log.e("Response recieve", strResponse);
//            JSONObject jsonResponse = new JSONObject(strResponse);
//            Log.e("Json", jsonResponse.toString());
//            map = convertJsonToMap(jsonResponse);
//        } catch (IOException e) {
//            Log.e("Error IO", e.toString());
//            return null;
//        } catch (JSONException e) {
//            Log.e("Json error", e.toString());
//            return null;
//        }
//        return map;
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
//
//    @Override
//    protected void onPostExecute(WritableMap writableMap) {
//
//        super.onPostExecute(writableMap);
//
//        if (writableMap == null) {
//            this.promise.reject("Error", "No response");
//        } else {
//            Log.e("String", writableMap.getString("status"));
//            if (writableMap.getString("status").equals("failure")) {
//                this.promise.reject("Error", writableMap.getString("error_message"));
//            } else {
//                this.promise.resolve(writableMap);
//            }
//        }
//    }
//}
