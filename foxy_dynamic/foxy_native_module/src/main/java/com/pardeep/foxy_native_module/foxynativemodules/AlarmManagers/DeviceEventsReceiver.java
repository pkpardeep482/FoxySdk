// package com.pardeep.foxy_dynamic.foxynativemodules.AlarmManagers;


// import android.app.KeyguardManager;
// import android.app.Notification;
// import android.app.NotificationManager;
// import android.app.PendingIntent;
// import android.bluetooth.BluetoothDevice;
// import android.content.BroadcastReceiver;
// import android.content.Intent;
// import android.content.Context;
// import android.net.wifi.WifiManager;
// import android.nfc.Tag;
// import android.os.Build;
// import android.os.Handler;
// import android.util.Log;
// import android.widget.RemoteViews;

// import androidx.annotation.RequiresApi;
// import androidx.core.app.NotificationManagerCompat;

// import org.json.JSONArray;
// import org.json.JSONException;
// import org.json.JSONObject;

// import java.io.IOException;
// import java.util.Calendar;

// import in.foxy.MainActivity;
// import in.foxy.R;
// import com.pardeep.foxy_dynamic.foxynativemodules.AlarmManagerNotifications.NotificationBroadcastReceiver;
// import com.pardeep.foxy_dynamic.foxynativemodules.UserPreferences.UserPreferences;
// import com.pardeep.foxy_dynamic.foxynativemodules.enums.Type;
// import com.pardeep.foxy_dynamic.foxynativemodules.utilities.AlarmCreator;
// import com.pardeep.foxy_dynamic.foxynativemodules.utilities.FoxyNotificationManager;
// import com.pardeep.foxy_dynamic.foxynativemodules.utilities.Utility;

// public class DeviceEventsReceiver extends BroadcastReceiver {

//     @RequiresApi(api = Build.VERSION_CODES.O)
//     @Override
//     public void onReceive(Context context, Intent intent) {
//         if (intent.getAction() == "android.intent.action.TIME_SET") {
//          AlarmCreator.createAlarm(context, AlarmCreator.FRIDAY_DROP_NOTIFICATION, 20, Calendar.HOUR_OF_DAY);
//           return;
//         }
//         showNotification(context, intent);
//     }

//     public JSONObject getNotificationForIntent(Context context, Intent intent) {

//         try {
//             UserPreferences.initPref(context);
//             String notificationData = UserPreferences.getStringPref(UserPreferences.NOTIFICATIONS_API_DATA);
//             JSONObject jsonResponse = new JSONObject(notificationData);
//             JSONArray notifications = jsonResponse.getJSONArray("notifications");

//             String intentStr = intent.getAction();
//             for (int index = 0; index < notifications.length(); index++) {
//                 JSONObject notification = notifications.getJSONObject(index);
//                 String notificationIntent = notification.getString("intent");
//                 switch (intentStr) {
//                     case Intent.ACTION_USER_PRESENT:
//                        if (notificationIntent.equals("screen_unlock")) {
//                             return notification;
//                        }
//                     case BluetoothDevice.ACTION_ACL_CONNECTED:
//                         if (notificationIntent.equals("bluetooth")) {
//                             return notification;
//                         }
//                     case WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION:
//                         if (intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false) &&
//                                 notificationIntent.equals("wifi")) {
//                             return notification;
//                         }
//                         break;

//                     default:
//                         break;
//                 }
//             }
//             return null;
//         } catch ( JSONException e) {
//             return null;
//         }
//     }

//     @RequiresApi(api = Build.VERSION_CODES.O)
//     private Boolean isNotificationExpired(JSONObject notification) throws JSONException {
//         String timeStr = notification.getString("expire_in");
//         if (timeStr == null) {
//             return false;
//         }
//         return Utility.isDatePast(timeStr);
//     }

//     @RequiresApi(api = Build.VERSION_CODES.O)
//     public void showNotification(Context context, Intent intent) {

//         try {
//             JSONObject notification = getNotificationForIntent(context, intent);
//             if (notification == null) {
//                 return;
//             }
//            if (isNotificationExpired(notification)) {
//                return;
//            }

//             String timeStr = notification.getString("expire_in");
//             String action = intent.getAction();
//             String identifier = notification.getString("id");
//            if (
//                    timeStr == null ||
//                    timeStr.equals(UserPreferences.getStringPref(action)) ||    // Notification is displayed already
//                    identifier == null
//            ) {
//                return;
//            }
//            UserPreferences.saveStringPref(action, timeStr);
//            NotificationBroadcastReceiver receiver = new NotificationBroadcastReceiver();
//            receiver.createNotification(context, identifier, notification.toString());

//         } catch (JSONException e) {
//             Log.e("json error", "error");
//         }


//     }
// }