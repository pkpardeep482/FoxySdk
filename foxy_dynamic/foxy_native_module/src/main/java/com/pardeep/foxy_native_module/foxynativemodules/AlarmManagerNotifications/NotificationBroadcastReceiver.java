// package com.pardeep.foxy_dynamic.foxynativemodules.AlarmManagerNotifications;


// import android.app.PendingIntent;
// import android.content.BroadcastReceiver;
// import android.content.Context;
// import android.content.Intent;
// import android.graphics.Bitmap;
// import android.os.AsyncTask;
// import android.os.Build;
// import android.os.Bundle;
// import android.widget.RemoteViews;

// import androidx.annotation.RequiresApi;
// import androidx.core.app.NotificationManagerCompat;

// import com.facebook.react.bridge.Arguments;
// import com.squareup.picasso.Picasso;

// import org.json.JSONException;
// import org.json.JSONObject;

// import java.util.Calendar;
// import java.util.HashMap;
// import java.util.Map;

// import in.foxy.MainActivity;
// import in.foxy.R;
// import com.pardeep.foxy_dynamic.foxynativemodules.AnalyticsManager.AnalyticsManager;
// import com.pardeep.foxy_dynamic.foxynativemodules.UserPreferences.UserPreferences;
// import com.pardeep.foxy_dynamic.foxynativemodules.enums.Events;
// import com.pardeep.foxy_dynamic.foxynativemodules.enums.Type;
// import com.pardeep.foxy_dynamic.foxynativemodules.utilities.AlarmCreator;
// import com.pardeep.foxy_dynamic.foxynativemodules.utilities.FoxyNotificationManager;

// public class NotificationBroadcastReceiver extends BroadcastReceiver {
//     private static Boolean throughAlarm = false;
//     private final String reviewReminderUrl = "https://www.foxy.in/post_review?slug=";
//     Bundle intentExtras = null;
//     String notificationSource = null;
//     private int headingIndex = 0;
//     private RemoteViews collapsedView;
//     private RemoteViews headsupView;
//     private RemoteViews expandedView;
//     private FoxyNotificationManager foxyNotificationManager;
//     private int currentNotificationIdentifier = 0;
//     private static final String CART_REMINDER_IDENTIFIER = "10002";

//     public NotificationBroadcastReceiver() {
//     }

//     public NotificationBroadcastReceiver(String notificationSource) {
//         this.notificationSource = notificationSource;
//     }

//     @RequiresApi(api = Build.VERSION_CODES.O)
//     @Override
//     public void onReceive(Context context, Intent intent) {
//         Bundle extras = intent.getExtras();
//         String notificationData = "";
//         String notificationIdentifier = "1";
//         if (extras != null) {
//             intentExtras = extras;
//             String action = intent.getStringExtra("action");
//             String cta = intent.getStringExtra("cta");
//             notificationData = extras.getString("notification_data");
//             notificationIdentifier = extras.getString("notification_identifier", "1");
//             if ("NOTIFY".equalsIgnoreCase(action)) {
//                 String type = intent.getStringExtra("type");
//                 switch (type) {
//                     case "daily_deals":
//                         sendDailyDealsReminder(getApplicationContext());
//                         break;
//                     case "review_reminder":
//                         showReviewReminderNotification(getApplicationContext());
//                         break;
//                     default:
//                         break;
//                 }
//                 return;
//             }


//             if (action != null && "DELETE".equalsIgnoreCase(action) || (cta != null && "DISMISS".equalsIgnoreCase(cta))) {
//                 int notificationIdentifierInt;
//                 try {
//                     notificationIdentifierInt = Integer.parseInt(notificationIdentifier);
//                 } catch (NumberFormatException ex){
//                     notificationIdentifierInt = 1;
//                 }
//                 NotificationManagerCompat.from(getApplicationContext()).cancel(notificationIdentifierInt);
//                 String type = intent.getStringExtra("type");
//                 if (type == null) return;
//                 sendNotificationActionEvent(type, "DISMISS", "user_dismissed");
//                 return;
//             } else if (notificationIdentifier.equals(LocalNotificationsModule.FRIDAY_DROP_IDENTIFIER)) {
//                 Calendar calendar = Calendar.getInstance();
//                 int today = calendar.get(Calendar.DAY_OF_WEEK);
//                 int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
//                 int dayOfWeek = extras.getInt("dayOfWeek");
//                 int hourOfDay = extras.getInt("hourOfDay");
//                 if (today != dayOfWeek || hourOfDay > currentHour) {
//                     return;
//                 }
//             }
//         }
//         showNotification(context, notificationIdentifier, notificationData);
//     }

//     @RequiresApi(api = Build.VERSION_CODES.O)
//     public void showNotification(Context context, String notificationIdentifier, String notificationData) {
//         createAlarmForNotification(context, notificationIdentifier);
//         createNotification(context, notificationIdentifier, notificationData);
//     }



//     @RequiresApi(api = Build.VERSION_CODES.O)
//     public void createNotification(Context context, String notificationIdentifier, String notificationData) {
//         if (notificationData == null || "".equals(notificationData)) {
//             return;
//         }
//         if (intentExtras != null) {
//             String source = intentExtras.getString("notification_source");
//             if ("cart_reminder_alarm".equalsIgnoreCase(source)) {
//                 notificationSource = "cart_reminder_alarm";
//                 throughAlarm = true;
//             }
//         }
//         UserPreferences.initPref(context);
//         Calendar cal = Calendar.getInstance();

//         String heading = "", imageUrl = "", subheading = "", phrase1 = "", phrase3 = "", productImage = "", type = notificationIdentifier;
//         Integer delayAfter = 0;
//         Boolean isSticky = true;
//         String primary_destination = "", primary_cta = "", secondary_destination = "", secondary_cta = "";
//         JSONObject extraData = new JSONObject();
//         PendingIntent pendingPrimaryAction = null, pendingSecondaryAction = null;
//         int importance = NotificationManagerCompat.IMPORTANCE_HIGH;
//         try {
//             JSONObject data = new JSONObject(notificationData);
//             heading = data.getString("title");
//             subheading = data.getString("description");
//             if (data.has("image")) {
//                 imageUrl = data.getString("image");
//             }
//             if (data.has("primary_cta") && data.has("primary_destination")) {
//                 primary_cta = data.getString("primary_cta");
//                 primary_destination = data.getString("primary_destination");
//             }
//             if (data.has("secondary_cta") && data.has("secondary_destination")) {
//                 secondary_cta = data.getString("secondary_cta");
//                 secondary_destination = data.getString("secondary_destination");
//             }
//             if (data.has("isSticky")) {
//                 isSticky = data.getBoolean("isSticky");
//             }
//             if (data.has("type")) {
//                 type = data.getString("type");
//             }
//             if (data.has("extraData")) {
//                 extraData = data.getJSONObject("extraData");
//                 if (extraData.has("phrase1")) {
//                     phrase1 = extraData.getString("phrase1");
//                 }
//                 if (extraData.has("phrase3")) {
//                     phrase3 = extraData.getString("phrase3");
//                 }
//                 if (extraData.has("product_image")) {
//                     productImage = extraData.getString("product_image");
//                 }
//             }
//         } catch (JSONException e) {
//             e.printStackTrace();
//             return;
//         }
//         if (primary_destination != null && !"".equals(primary_destination)) {
//             Intent primaryAction = new Intent(getApplicationContext(), MainActivity.class);
//             primaryAction.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//             primaryAction.putExtra("notification_type", "persistentNotificationClick");
//             primaryAction.putExtra("action", primary_destination);
//             primaryAction.putExtra("cta", primary_cta);
//             primaryAction.putExtra("type", type);
//             primaryAction.putExtra("notification_identifier", notificationIdentifier);
//             pendingPrimaryAction = PendingIntent.getActivity(context, 20, primaryAction, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
//         }
//         if (secondary_destination != null && !"".equals(secondary_destination)) {
//             Intent secondaryAction = new Intent(getApplicationContext(), NotificationBroadcastReceiver.class);
//             secondaryAction.putExtra("action", secondary_destination);
//             secondaryAction.putExtra("cta", secondary_cta);
//             secondaryAction.putExtra("type", type);
//             secondaryAction.putExtra("notification_identifier", notificationIdentifier);
//             pendingSecondaryAction = PendingIntent.getBroadcast(context, 21, secondaryAction, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
//         }


//         setViews(heading, subheading, phrase1 + phrase3);

//         foxyNotificationManager = new FoxyNotificationManager();
//         foxyNotificationManager.createNotification(context, heading, subheading, isSticky, importance);

//         if (pendingSecondaryAction != null) {
//             foxyNotificationManager.addActionToNotification(secondary_cta, pendingSecondaryAction);
//         }
//         if (pendingPrimaryAction != null) {
//             foxyNotificationManager.addActionToNotification(primary_cta, pendingPrimaryAction);
//             foxyNotificationManager.addContentIntent(pendingPrimaryAction);
//         }
//         foxyNotificationManager.addBigText(subheading);

//         try {
//             currentNotificationIdentifier = Integer.parseInt(notificationIdentifier);
//         } catch (NumberFormatException ex){
//             currentNotificationIdentifier = 1;
//         }

//         UserPreferences.setNotificationShownTimeStamp(cal.getTimeInMillis());
//         String source = notificationSource != null ? notificationSource : "app";
//         sendNotificationShownEvent(type, source, isSticky.toString());

//         Boolean isImageUrlPresent = imageUrl != null && !"".equals(imageUrl);
//         Boolean isProductPresent = productImage != null && !"".equals(productImage);
//         if (isImageUrlPresent || isProductPresent) {
//             Map map = new HashMap<String, String>();
//             if (isImageUrlPresent) {
//                 map.put("image", imageUrl);
//             }
//             if (isProductPresent) {
//                 map.put("productImage", productImage);
//             }

//             new BitmapFromURLAsync().execute(map);
//         } else {
//             foxyNotificationManager.notify(currentNotificationIdentifier);
//         }
//     }

//     public void sendDailyDealsReminder(Context context) {
//         UserPreferences.initPref(context);
//         AlarmCreator.createAlarm(context, AlarmCreator.DAILY_DEALS_NOTIFICATION, 0, Calendar.HOUR_OF_DAY);
//         String data = UserPreferences.getStringPref(UserPreferences.DAILY_DEALS_REMAINDER_DATA);
//         String heading = "", sub_heading = "";
//         Calendar cal = Calendar.getInstance();
//         String day = String.valueOf(cal.get(Calendar.DAY_OF_WEEK) - 1);
//         JSONObject todayMessage = null;
//         try {
//             JSONObject notificationData = new JSONObject(data);
//             todayMessage = notificationData.getJSONObject(day);
//             heading = todayMessage.getString("heading");
//             sub_heading = todayMessage.getString("subheading");
//         } catch (JSONException e) {
//             e.printStackTrace();
//             return;
//         }
//         Intent primaryAction = new Intent(getApplicationContext(), MainActivity.class);
//         primaryAction.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//         primaryAction.putExtra("notification_type", "persistentNotificationClick");
//         primaryAction.putExtra("action", "https://www.foxy.in/today-deals");
//         primaryAction.putExtra("cta", "EXPLORE");
//         primaryAction.putExtra("type", Type.Notification.daily_deals_notification.name());
//         PendingIntent pendingPrimaryAction = PendingIntent.getActivity(context, 20, primaryAction, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
//         FoxyNotificationManager foxyNotificationManager = new FoxyNotificationManager();
//         foxyNotificationManager.createNotification(context, heading, sub_heading, false)
//                 .addActionToNotification("EXPLORE", pendingPrimaryAction)
//                 .addContentIntent(pendingPrimaryAction)
//                 .addBigText(sub_heading)
//                 .notify(FoxyNotificationManager.ID_DAILY_DEALS_NOTIFICATION);
//         sendNotificationShownEvent("daily_deals", "app", "false");
//     }


//     @RequiresApi(api = Build.VERSION_CODES.O)
//     public void clearCartNotifications(Context context) {
//         FoxyNotificationManager.cancel(context, FoxyNotificationManager.ID_CART_REMINDER);
//     }

//     @RequiresApi(api = Build.VERSION_CODES.O)
//     public void createAlarmForNotification(Context context, String notificationIdentifier) {
//         switch (notificationIdentifier) {
//             case NotificationBroadcastReceiver.CART_REMINDER_IDENTIFIER:
//                 AlarmCreator.createAlarm(context, AlarmCreator.CART_REMINDER_NOTIFICATION, 1, Calendar.DAY_OF_MONTH);
//                 UserPreferences.initPref(context);
//                 Calendar prev = Calendar.getInstance();
//                 Calendar cartUpdatedAt = Calendar.getInstance();
//                 long prevNotification = UserPreferences.getLongPref(UserPreferences.CART_REMINDER_SHOWN_AT);
//                 long cartUpdatedAtTimestamp = UserPreferences.getLongPref(UserPreferences.CART_UPDATED_AT);
//                 prev.setTimeInMillis(prevNotification);
//                 cartUpdatedAt.setTimeInMillis(cartUpdatedAtTimestamp);
//                 break;
// //           case LocalNotificationsModule.FRIDAY_DROP_IDENTIFIER:
// //               AlarmCreator.createAlarm(context, AlarmCreator.FRIDAY_DROP_NOTIFICATION, 20, Calendar.HOUR_OF_DAY);
//             default:
//                 break;
//         }
//     }

//     @RequiresApi(api = Build.VERSION_CODES.O)
//     public void setViews(String heading, String subheading, String headUpViewText) {
//         String packageName = getApplicationContext().getPackageName();
//         collapsedView = new RemoteViews(packageName,
//                 R.layout.notification_collapsed);
//         collapsedView.setTextViewText(R.id.collapsedNotificationTitle, heading);

//         expandedView = new RemoteViews(packageName,
//                 R.layout.notification_expanded);
//         expandedView.setTextViewText(R.id.expandedNotificationTitle, heading);

//         if (!"".equalsIgnoreCase(subheading)) {
//             expandedView.setTextViewText(R.id.expandedNotificationInfo, subheading);
//             collapsedView.setTextViewText(R.id.collapsedNotificationInfo, subheading);
//         }
//         if (!"".equals(headUpViewText)) {
//             headsupView = new RemoteViews(packageName,
//                     R.layout.notification_headsup);
//             headsupView.setTextViewText(R.id.headsUPNotificationTitle, heading);
//             headsupView.setTextViewText(R.id.headsUPNotificationInfo, headUpViewText);
//         }
//     }

//     public void sendNotificationShownEvent(String type, String source, String isSticky) {
//         Bundle bundle = new Bundle();
//         if (Type.Notification.cart_abandon_notification.name().equalsIgnoreCase(type)) {
//             bundle.putString("template_id", "" + headingIndex);
//         }
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


//     @RequiresApi(api = Build.VERSION_CODES.O)
//     public void showReviewReminderNotification(Context context) {
//         UserPreferences.initPref(context);
//         String notificationData = UserPreferences.getStringPref(UserPreferences.REVIEW_REMINDER_NOTIFICATION_DATA);
//         String slug = "";
//         String productImage = "";
//         String heading = "";
//         String subHeading = "";
//         try {
//             JSONObject dataJson = new JSONObject(notificationData);
//             slug = dataJson.getString("slug");
//             productImage = dataJson.getString("image");
//             heading = dataJson.getString("heading");
//             subHeading = dataJson.getString("subheading");
//         } catch (JSONException e) {
//             e.printStackTrace();
//             return;
//         }
//         if (heading.equals("0") || subHeading.equals("0") || slug.equals("")) return;
//         String action = reviewReminderUrl + slug + "&rating=";  //added the rating with respect to the pending intent of star clicked
//         expandedView = new RemoteViews(getApplicationContext().getPackageName(),
//                 R.layout.review_reminder_notification_expanded);

//         PendingIntent pendingIntent = null;
//         Intent intent = null;
//         int[] viewIds = {R.id.star1, R.id.star2, R.id.star3, R.id.star4, R.id.star5};
//         for (int index = 0; index < 5; index++) {
//             intent = new Intent(getApplicationContext(), MainActivity.class);
//             intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//             intent.putExtra("notification_type", "persistentNotificationClick");
//             intent.putExtra("action", action + (index + 1));
//             intent.putExtra("type", Type.Notification.review_reminder_notification.name());
//             pendingIntent = PendingIntent.getActivity(context, 59 + (index * 10), intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
//             expandedView.setOnClickPendingIntent(viewIds[index], pendingIntent);
//         }

//         foxyNotificationManager = new FoxyNotificationManager();
//         foxyNotificationManager.createNotification(context, heading, subHeading, false, NotificationManagerCompat.IMPORTANCE_HIGH)
//                 .addContentIntent(pendingIntent)
//                 .setDecoratedCustomViewStyle();
//         currentNotificationIdentifier = FoxyNotificationManager.ID_REVIEW_REMINDER_NOTIFICATION;
//         Map map = new HashMap<String, String>();
//         map.put("image", productImage);
//         new BitmapFromURLAsync().execute(map);
//     }

//     public class BitmapFromURLAsync extends AsyncTask<Map, Void, Map<String, Bitmap>> {
//         private RemoteViews imageView = null;
//         private int viewId = -1;

//         public BitmapFromURLAsync() {
//         }

//         @Override
//         protected Map<String, Bitmap> doInBackground(Map... maps) {
//             Map<String, String> map = maps[0];
//             String productImage = map.get("productImage");
//             String imageUrl = map.get("image");

//             Bitmap productImageBitmap = null;
//             Bitmap imageBitmap = null;
//             Map<String, Bitmap> images = new HashMap<String, Bitmap>();

//             try {
//                 if (productImage != null) {
//                     productImageBitmap = Picasso.get().load(productImage).get();
//                     images.put("productImageBitmap", productImageBitmap);
//                 }
//                 if (imageUrl != null) {
//                     imageBitmap = Picasso.get().load(imageUrl).get();
//                     images.put("imageBitmap", imageBitmap);
//                 }
//             } catch (Exception e) {
//                 e.printStackTrace();
//             }
//             return images;
//         }

//         public BitmapFromURLAsync(RemoteViews imageView, int viewId) {
//             this.imageView = imageView;
//             this.viewId = viewId;
//         }

//         @Override
//         protected void onPostExecute(Map<String, Bitmap> map) {
//             super.onPostExecute(map);
//             if (map == null || currentNotificationIdentifier == 0) {
//                 return;
//             }

//             Bitmap productImage = map.get("productImageBitmap");
//             Bitmap image = map.get("imageBitmap");

//             Boolean isProductImagePresent = productImage != null;
//             Boolean isImagePresent = image != null;

//             Bitmap expandedImage = (isProductImagePresent && isImagePresent || isImagePresent) ? image : productImage;
//             Bitmap collapsedImage = isProductImagePresent ? productImage : image;
//             Bitmap headsUpImage = isProductImagePresent ? productImage : image;
//             if (collapsedView != null && collapsedImage != null) {
//                 collapsedView.setImageViewBitmap(R.id.collapsedImageView, collapsedImage);
//                 foxyNotificationManager
//                         .addCustomCollapsedView(collapsedView);
//             }
//             if (headsupView != null && headsUpImage != null) {
//                 headsupView.setImageViewBitmap(R.id.headsUpImageView, headsUpImage);
//                 foxyNotificationManager
//                         .addCustomHeadsUpView(headsupView);
//             }
//             if (expandedView != null && expandedImage != null) {
//                 expandedView.setImageViewBitmap(R.id.image_view_expanded, expandedImage);
//                 foxyNotificationManager
//                         .addCustomExpandedView(expandedView)
//                         .setDecoratedCustomViewStyle();
//             }
//             foxyNotificationManager.notify(currentNotificationIdentifier);

//             currentNotificationIdentifier = 0;
//         }
//     }
// }
