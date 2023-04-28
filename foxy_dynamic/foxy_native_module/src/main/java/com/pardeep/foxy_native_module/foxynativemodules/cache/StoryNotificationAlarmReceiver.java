// package com.pardeep.foxy_dynamic.foxynativemodules.cache;

// import android.app.PendingIntent;
// import android.content.BroadcastReceiver;
// import android.content.Context;
// import android.content.Intent;
// import android.os.Bundle;
// import android.util.Log;

// import androidx.core.app.NotificationManagerCompat;

// import org.json.JSONArray;
// import org.json.JSONException;
// import org.json.JSONObject;
// import java.util.Calendar;

// import in.foxy.MainActivity;
// import com.pardeep.foxy_dynamic.foxynativemodules.AlarmManagerNotifications.NotificationBroadcastReceiver;
// import com.pardeep.foxy_dynamic.foxynativemodules.AnalyticsManager.AnalyticsManager;
// import com.pardeep.foxy_dynamic.foxynativemodules.UserPreferences.UserPreferences;
// import com.pardeep.foxy_dynamic.foxynativemodules.enums.Events;
// import com.pardeep.foxy_dynamic.foxynativemodules.enums.Type;
// import com.pardeep.foxy_dynamic.foxynativemodules.utilities.AlarmCreator;
// import com.pardeep.foxy_dynamic.foxynativemodules.utilities.FoxyNotificationManager;


// public class StoryNotificationAlarmReceiver extends BroadcastReceiver {
//     @Override
//     public void onReceive(Context context, Intent intent) {
// //        Log.d("STORY_NOTIFICATION", "BROADCAST_RECEIVED");
//         AlarmCreator.createAlarm(context,AlarmCreator.SHOW_STORY_NOTIFICATION,1, Calendar.DAY_OF_MONTH);
//         Bundle extras = intent.getExtras();
//         if (extras != null) {
//             String action = intent.getStringExtra("action");
//             if(action!= null && action.equalsIgnoreCase("DELETE")){
//                 NotificationManagerCompat.from(getApplicationContext()).cancel(FoxyNotificationManager.ID_STORY_NOTIFICATION);
//                 sendNotificationActionEvent(Type.Notification.story_reminder_notification.name(), "DISMISS", "user_dismissed");
//                 return;
//             }
//         }
//         UserPreferences.initPref(context);
//         String json = UserPreferences.getStringPref(UserPreferences.STORY_NOTIFICATION_DATA);
//         Calendar cal = Calendar.getInstance();
//         int day = cal.get(Calendar.DAY_OF_MONTH);
//         String title = "";
//         String desc = "";
//         boolean isPersistent = false;
//         try {
//             JSONObject data = new JSONObject(json);
//             isPersistent = data.getBoolean("isPersistent");
//             JSONArray array = data.getJSONArray("array");
//             int index = day % array.length();
//             JSONObject stringObject = array.getJSONObject(index);
//             if (stringObject.has("title") && stringObject.has("description")) {
//                 title = stringObject.getString("title");
//                 desc = stringObject.getString("description");
//             }
//         } catch (JSONException e) {
//             e.printStackTrace();
//             return;
//         }
//         Log.d("STORY_Notification", "Notification data: title"+title+" "+desc);
//         if ("0".equalsIgnoreCase(title) || "".equalsIgnoreCase(title)) {
//             return;
//         }
//         Intent primaryAction = new Intent(context.getApplicationContext(), MainActivity.class);
//         primaryAction.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//         primaryAction.putExtra("notification_type", "persistentNotificationClick");
//         primaryAction.putExtra("action", "https://www.foxy.in/feed");
//         primaryAction.putExtra("cta", "WATCH NOW");
//         primaryAction.putExtra("type", Type.Notification.story_reminder_notification.name());
//         PendingIntent pendingPrimaryAction = PendingIntent.getActivity(context, 20, primaryAction, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE);

//         Intent secondaryAction = new Intent(context.getApplicationContext(), StoryNotificationAlarmReceiver.class);
//         secondaryAction.putExtra("action", "DELETE");
//         secondaryAction.putExtra("cta", "DISMISS");
//         secondaryAction.putExtra("type", Type.Notification.story_reminder_notification.name());
//         PendingIntent pendingSecondaryAction = PendingIntent.getBroadcast(context, 21, secondaryAction, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE);

//         if (title.equalsIgnoreCase("") || desc.equalsIgnoreCase("")) return;
//         FoxyNotificationManager notificationManager = new FoxyNotificationManager();
//         notificationManager.createNotification(context, title, desc, isPersistent)
//                 .addContentIntent(pendingPrimaryAction)
//                 .addActionToNotification("WATCH NOW", pendingPrimaryAction)
//                 .addActionToNotification("DISMISS", pendingSecondaryAction)
//                 .notify(FoxyNotificationManager.ID_STORY_NOTIFICATION);

//         Bundle bundle = new Bundle();
//         bundle.putString("type", "story_notification");
//         bundle.putString("source", "alarm_manager");
//         AnalyticsManager.LogEvent(context, "notification_shown", bundle);
//         sendNotificationShownEvent(Type.Notification.story_reminder_notification.name(), "alarm_manager", String.valueOf(isPersistent));
//     }

//     public void sendNotificationShownEvent(String type, String source, String isSticky) {
//         Bundle bundle = new Bundle();
//         bundle.putString("type", type);
//         bundle.putString("source", source);
//         bundle.putString("isSticky", isSticky);
//         AnalyticsManager.LogEvent(getApplicationContext(), Events.Notification.notification_shown.name(), bundle);
//     }

//     public void sendNotificationActionEvent(String type, String cta, String action) {
//         Bundle bundle = new Bundle();
//         bundle.putString("type", type);
//         bundle.putString("cta", cta);
//         bundle.putString("action", action);
//         AnalyticsManager.LogEvent(getApplicationContext(), Events.Notification.notification_action.name(), bundle);
//     }
// }
