// package com.pardeep.foxy_dynamic.foxynativemodules.AlarmManagerNotifications;

// import android.content.Context;
// import android.os.Build;

// import androidx.annotation.RequiresApi;

// import com.facebook.react.bridge.ReactApplicationContext;
// import com.facebook.react.bridge.ReactContextBaseJavaModule;
// import com.facebook.react.bridge.ReactMethod;

// import java.util.Calendar;

// import javax.annotation.Nonnull;

// import com.pardeep.foxy_dynamic.foxynativemodules.AnalyticsManager.AnalyticsManager;
// import com.pardeep.foxy_dynamic.foxynativemodules.UploadManager.UploadManagerModule;
// import com.pardeep.foxy_dynamic.foxynativemodules.UserPreferences.UserPreferences;
// import com.pardeep.foxy_dynamic.foxynativemodules.utilities.AlarmCreator;
// import org.json.JSONObject;
// import org.json.JSONException;

// public class LocalNotificationsModule extends ReactContextBaseJavaModule {

//     ReactApplicationContext reactContext;
//     private static final String PAYMENT_SUCCESS_IDENTIFIER = "10002";
//     private static final String PAYMENT_FAILED_IDENTIFIER = "10002";
//     private static final String PAYMENT_IN_PROGRESS_IDENTIFIER = "10002";
//     public static final String CART_REMINDER_IDENTIFIER = "10002";
//     private static final String PROFILE_PIC_UPLOAD_IDENTIFIER = "10005";
//     public static final String FRIDAY_DROP_IDENTIFIER = "10006";
//     public static String backgroundNotificationPayload = null;


//     public LocalNotificationsModule(@Nonnull ReactApplicationContext reactContext) {
//         super(reactContext);
//         this.reactContext = reactContext;
//         UserPreferences.initPref(reactContext);
//     }

//     @Nonnull
//     @Override
//     public String getName() {
//         return "LocalNotificationsPackage";
//     }

//     @RequiresApi(api = Build.VERSION_CODES.O)
//     @ReactMethod
//     public void createNotification(String notificationIdentifier, String payload) {
//         NotificationBroadcastReceiver receiver = new NotificationBroadcastReceiver();
//         switch (notificationIdentifier) {
//             case LocalNotificationsModule.CART_REMINDER_IDENTIFIER:
//                 if (payload == null || "".equals(payload)) return;
//                 boolean showNotificationInBackground = false;
//                 String type = "";
//                 int num = 0;
//                 try {
//                     JSONObject notificationData = new JSONObject(payload);
//                     if (notificationData.has("type")) {
//                         type = notificationData.getString("type");
//                     }
//                     if (notificationData.has("showInBackground")) {
//                         showNotificationInBackground = notificationData.getBoolean("showInBackground");
//                     }
//                     if (notificationData.has("extraData")) {
//                         JSONObject extraData = notificationData.getJSONObject("extraData");
//                         num = Integer.parseInt(extraData.getString("cartCount"));
//                     }
//                 }  catch (JSONException e) {
//                     e.printStackTrace();
//                     return;
//                 }
//                 if (num < 1 && "cart_reminder_notification".equals(type)) {
//                     backgroundNotificationPayload = null;
//                     AlarmCreator.cancelAlarm(reactContext, AlarmCreator.REQUEST_CODE_CART_REMINDER_NOTIFICATION);
//                     return;
//                 }
//                 if (showNotificationInBackground) {
//                     backgroundNotificationPayload = payload;
//                 } else {
//                     if ("payment_success_notification".equals(type)) {
//                         AlarmCreator.cancelAlarm(reactContext, AlarmCreator.REQUEST_CODE_CART_REMINDER_NOTIFICATION);
//                     }
//                     receiver.showNotification(reactContext, notificationIdentifier, payload);
//                 }
//                 break;
//             case LocalNotificationsModule.PROFILE_PIC_UPLOAD_IDENTIFIER:
//                 UploadManagerModule uploadManagerPackage = new UploadManagerModule(this.reactContext);
//                 String title = "", description = "";
//                 try {
//                     JSONObject data = new JSONObject(payload);
//                     title = data.getString("title");
//                     description = data.getString("description");
//                 } catch (JSONException e) {
//                     e.printStackTrace();
//                 }
//                 uploadManagerPackage.setNotificationText(title, description);
//                 break;
//             case LocalNotificationsModule.FRIDAY_DROP_IDENTIFIER:
//                 if (payload == null || "".equals(payload)) return;
//                 int hourOfDay = 20;
//                 try {
//                     JSONObject notificationData = new JSONObject(payload);
//                     if (notificationData.has("hourOfDay")) {
//                         hourOfDay = notificationData.getInt("hourOfDay");
//                     }
//                     UserPreferences.saveStringPref(UserPreferences.FRIDAY_DROP_NOTIFICATION_DATA, payload);
//                     AlarmCreator.createAlarm(reactContext, AlarmCreator.FRIDAY_DROP_NOTIFICATION, hourOfDay, Calendar.HOUR_OF_DAY);
//                 }  catch (JSONException e) {
//                     e.printStackTrace();
//                     return;
//                 }
//                 break;
// //            case LocalNotificationsModule.PAYMENT_SUCCESS_IDENTIFIER:
// ////                Bundle bundle = new Bundle();
// ////                bundle.putString("type", "payment_success");
// ////                AnalyticsManager.LogEvent(reactContext, "notification_shown", bundle);
// //                receiver.showNotification(reactContext, notificationIdentifier, payload);
// //                break;
// //            case LocalNotificationsModule.PAYMENT_FAILED_IDENTIFIER:
// //            case LocalNotificationsModule.PAYMENT_IN_PROGRESS_IDENTIFIER:
// //                receiver.showNotification(reactContext, notificationIdentifier, payload);
// //                break;
//             default:
//                 AlarmCreator.createGenericAlarm(reactContext, notificationIdentifier, payload);
//                 break;
//         }
//     }

//     @RequiresApi(api = Build.VERSION_CODES.O)
//     @ReactMethod

//     public static void createAlarmForBackgroundDisplay(Context context, int type, String payload, int displayAfter) {
//         UserPreferences.saveStringPref(UserPreferences.CART_REMINDER_NOTIFICATION_DATA, payload);
//         AlarmCreator.createAlarm(context, type, displayAfter, Calendar.SECOND);
//         //Resetting background notification payload to avoid re-firing.
//         LocalNotificationsModule.backgroundNotificationPayload = null;
//     }

//     @RequiresApi(api = Build.VERSION_CODES.O)
//     @ReactMethod
//     public void setDailyDealsReminders(String data) {
//         UserPreferences.saveDailyDealsRemainderData(data);
//         AlarmCreator.createAlarm(reactContext, AlarmCreator.DAILY_DEALS_NOTIFICATION, 0, Calendar.HOUR_OF_DAY);
//     }

//     @RequiresApi(api = Build.VERSION_CODES.O)
//     @ReactMethod
//     public void sendReviewReminderNotification(String data) {
//         UserPreferences.saveReviewReminderNotificationData(data);
//         AlarmCreator.createAlarm(reactContext, AlarmCreator.REVIEW_REMINDER_NOTIFICATION, 2, Calendar.HOUR_OF_DAY);
//     }

//     @RequiresApi(api = Build.VERSION_CODES.O)
//     @ReactMethod
//     public void clearNotifications() {
//         NotificationBroadcastReceiver receiver = new NotificationBroadcastReceiver();
//         receiver.clearCartNotifications(reactContext);
//     }

// }
