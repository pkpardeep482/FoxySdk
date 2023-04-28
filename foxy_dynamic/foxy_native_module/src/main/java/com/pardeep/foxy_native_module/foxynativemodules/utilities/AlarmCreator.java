// // TODO: Refractor the whole file
// package com.pardeep.foxy_dynamic.foxynativemodules.utilities;

// import android.app.AlarmManager;
// import android.app.PendingIntent;
// import android.content.Context;
// import android.content.Intent;

// import com.google.api.client.repackaged.org.apache.commons.codec.binary.StringUtils;
// import com.google.api.client.util.DateTime;
// import com.google.api.client.util.Strings;

// import org.json.JSONArray;
// import org.json.JSONException;
// import org.json.JSONObject;

// import java.time.Instant;
// import java.time.format.DateTimeFormatter;
// import java.time.temporal.TemporalAccessor;
// import java.util.Calendar;
// import java.util.Date;
// import java.util.Random;

// import com.pardeep.foxy_dynamic.foxynativemodules.AlarmManagerNotifications.LocalNotificationsModule;
// // import com.pardeep.foxy_dynamic.foxynativemodules.AlarmManagerNotifications.NotificationBroadcastReceiver;
// import com.pardeep.foxy_dynamic.foxynativemodules.UserPreferences.UserPreferences;
// import com.pardeep.foxy_dynamic.foxynativemodules.cache.ApiAlarmReceiver;
// import com.pardeep.foxy_dynamic.foxynativemodules.cache.CachingAlarmReceiver;
// // import com.pardeep.foxy_dynamic.foxynativemodules.cache.StoryNotificationAlarmReceiver;

// public class AlarmCreator {
//     public static final int DOWNLOAD_CACHE = 1;         //createAlarm takes delay in minutes
//     public static final int CALL_STORY_API = 2;         //createAlarm takes delay in hours
//     public static final int SHOW_STORY_NOTIFICATION = 3;//createAlarm takes no delay
//     public static final int DAILY_DEALS_NOTIFICATION = 4;   //createAlarm for everyday 8am
//     public static final int CART_REMINDER_NOTIFICATION = 5; //createAlarm takes no delay
//     public static final int REVIEW_REMINDER_NOTIFICATION = 6;//createAlarm for everyday 2pm
//     public static final int FRIDAY_DROP_NOTIFICATION = 7;

//     private static final int REQUEST_CODE_DOWNLOAD_CACHE = 24;
//     private static final int REQUEST_CODE_CALL_STORY_API = 25;
//     private static final int REQUEST_CODE_SHOW_STORY_NOTIFICATION = 26;
//     public static final int REQUEST_CODE_CART_REMINDER_NOTIFICATION = 27;
//     private static final int REQUEST_CODE_DAILY_DEALS_NOTIFICATION = 28;
//     private static final int REQUEST_CODE_REVIEW_REMINDER_NOTIFICATION = 29;
//     public static final int REQUEST_CODE_FRIDAY_DROP_NOTIFICATION=30;

//     public static JSONObject getRandom(JSONArray array) {
//         int rnd = new Random().nextInt(array.length());
//         try {
//             return array.getJSONObject(rnd);
//         } catch (JSONException e) {
//             e.printStackTrace();
//             return new JSONObject();
//         }
//     }

//     public static Date getDateFromString(String isoDate) {
//         TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse(isoDate);
//         Instant i = Instant.from(ta);
//         Date date = Date.from(i);
//          return date;
//     }

//     public static void createGenericAlarm(Context context, String notificationIdentifier, String notificationPayload) {
//         AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//         Calendar calendar = Calendar.getInstance();
//         Intent reviveIntent = new Intent(context, NotificationBroadcastReceiver.class);
//         try {
//             JSONObject notification = new JSONObject(notificationPayload);
//             String isoDate = notification.getString("display_time");
//             if (Strings.isNullOrEmpty(isoDate)) {
//                 NotificationBroadcastReceiver receiver = new NotificationBroadcastReceiver();
//                 receiver.createNotification(context, notificationIdentifier, notificationPayload);
//                 return;
//             }
//             calendar.setTime(getDateFromString(isoDate));
//             reviveIntent.putExtra("notification_data", notification.toString());
//             reviveIntent.putExtra("notification_identifier", notificationIdentifier);
//             PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(notificationIdentifier), reviveIntent,  PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE);
//                     alarmManager.cancel(pendingIntent);
//                     alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//         }  catch (JSONException e) {
//             e.printStackTrace();
//             return;
//         }
//     }


//     // unitTime = Calendar.HOUR_OF_DAY || Calendar.MINUTE || Calendar.DAY_OF_MONTH
//     public static void createAlarm(Context context, int alarmType, int delay, int unitTime) {
//         AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//         Calendar calendar = Calendar.getInstance();
//         calendar.setTimeInMillis(System.currentTimeMillis());
//         calendar.add(unitTime, delay);
//         Intent reviveIntent = null;
//         PendingIntent pendingIntent = null;
//         switch (alarmType) {
//             case DOWNLOAD_CACHE:
//                 reviveIntent = new Intent(context, CachingAlarmReceiver.class);
//                 pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE_DOWNLOAD_CACHE, reviveIntent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE);
//                 break;
//             case CALL_STORY_API:
//                 reviveIntent = new Intent(context, ApiAlarmReceiver.class);
//                 pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE_CALL_STORY_API, reviveIntent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE);
//                 break;
//             case SHOW_STORY_NOTIFICATION:
//                 // reviveIntent = new Intent(context, StoryNotificationAlarmReceiver.class);
//                 // pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE_SHOW_STORY_NOTIFICATION, reviveIntent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE);
//                 break;
//             case CART_REMINDER_NOTIFICATION:
//                 String notificationData = UserPreferences.getStringPref(UserPreferences.CART_REMINDER_NOTIFICATION_DATA);
//                 if (notificationData == null || "".equals(notificationData)) return;
//                 reviveIntent = new Intent(context, NotificationBroadcastReceiver.class);
//                 reviveIntent.putExtra("notification_source", "cart_reminder_alarm");
//                 reviveIntent.putExtra("notification_data", notificationData);
//                 reviveIntent.putExtra("notification_identifier", LocalNotificationsModule.CART_REMINDER_IDENTIFIER);
//                 pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE_CART_REMINDER_NOTIFICATION, reviveIntent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE);
//                 break;
//             case DAILY_DEALS_NOTIFICATION:
//                 calendar = Calendar.getInstance();
//                 calendar.set(Calendar.HOUR_OF_DAY, 8);
//                 calendar.set(Calendar.MINUTE, 0);
//                 calendar.set(Calendar.SECOND, 0);
//                 calendar.add(Calendar.DAY_OF_MONTH, 1);
//                 reviveIntent = new Intent(context, NotificationBroadcastReceiver.class);
//                 reviveIntent.putExtra("action", "NOTIFY");
//                 reviveIntent.putExtra("type", "daily_deals");
//                 pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE_DAILY_DEALS_NOTIFICATION, reviveIntent,  PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE);
//                 break;
//             case REVIEW_REMINDER_NOTIFICATION:
//                 reviveIntent = new Intent(context, NotificationBroadcastReceiver.class);
//                 reviveIntent.putExtra("action", "NOTIFY");
//                 reviveIntent.putExtra("type", "review_reminder");
//                 pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE_REVIEW_REMINDER_NOTIFICATION, reviveIntent,  PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE);
//                 break;
//             case FRIDAY_DROP_NOTIFICATION:
//                 String notificationPayload = UserPreferences.getStringPref(UserPreferences.FRIDAY_DROP_NOTIFICATION_DATA);
//                 int hourOfDay = 20;
//                 int dayOfWeek = 6;
//                 try {
//                     JSONObject notification = new JSONObject(notificationPayload);

//                     if (notification.has("hourOfDay")) {
//                         hourOfDay = notification.getInt("hourOfDay");
//                     }
//                     if (notification.has("dayOfWeek")) {
//                         dayOfWeek = notification.getInt("dayOfWeek");
//                     }
//                     if (notification.has("notifications")) {
//                         JSONArray notifications = notification.getJSONArray("notifications");
//                         if (notifications.length() > 0) {
//                             long weekInMiliSecs = 7*24*60*60*1000;
//                             JSONObject notificationToDisplay = getRandom(notifications);
//                             calendar = Calendar.getInstance();
//                             calendar.setTimeInMillis(System.currentTimeMillis());
//                             calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
//                             calendar.set(Calendar.MINUTE, 0 );
//                             calendar.set(Calendar.SECOND, 0);
// //                            calendar.add(Calendar.DAY_OF_WEEK, dayOfWeek);
//                             reviveIntent = new Intent(context, NotificationBroadcastReceiver.class);
//                             reviveIntent.putExtra("notification_data", notificationToDisplay.toString());
//                             reviveIntent.putExtra("notification_identifier", LocalNotificationsModule.FRIDAY_DROP_IDENTIFIER);
//                             reviveIntent.putExtra("dayOfWeek", dayOfWeek);
//                             reviveIntent.putExtra("hourOfDay", hourOfDay);
//                             pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE_FRIDAY_DROP_NOTIFICATION, reviveIntent,  PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE);
//                             alarmManager.cancel(pendingIntent);
//                             alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
//                             return;
//                         }
//                     }
//                 }  catch (JSONException e) {
//                     e.printStackTrace();
//                     return;
//                 }
//                 break;
//             default:
//                 break;

//         }
//         if (pendingIntent == null) {
//             return;
//         }
//         alarmManager.cancel(pendingIntent);
//         alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//     }

//     public static void cancelAlarm(Context context, Integer requestCode) {
//         AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//         Intent reviveIntent = new Intent(context, NotificationBroadcastReceiver.class);
//         PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, reviveIntent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE);

//         alarmManager.cancel(pendingIntent);
//     }
// }
