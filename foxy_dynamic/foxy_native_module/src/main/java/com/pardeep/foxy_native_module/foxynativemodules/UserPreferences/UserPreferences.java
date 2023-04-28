package com.pardeep.foxy_native_module.foxynativemodules.UserPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class UserPreferences
{
    private static SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "Foxy-UserPreference";
    public static final String AUTH_TOKEN = "x-auth-token";
    public static final String USER_ID = "user-id";
    public static final String GUEST_TOKEN = "x-guest-token";
    public static final String CART_ITEMS = "cart-items";
    public static final String CART_ITEMS_COUNT = "cart-items-count";
    public static final String NOTIFICATION_SUBJECT = "notification-subject";
    public static final String NOTIFICATION_BODY = "notification-body";
    public static final String NOTIFICATION_IS_STICKY = "notification-is-sticky";
    public static final String NOTIFICATION_EXPIRES_IN = "notification-expires-in";
    public static final String NOTIFICATION_PRIMARY_CTA = "notification-primary-cta";
    public static final String NOTIFICATION_PRIMARY_DESTINATION = "notification-primary-destination";
    public static final String NOTIFICATION_SECONDARY_CTA = "notification-secondary-cta";
    public static final String NOTIFICATION_SECONDARY_DESTINATION = "notification-secondary-destination";
    public static final String NOTIFICATION_CHANNEL = "notification-channel";
    public static final String WARHOL_IMAGE_URL = "warhol-image-url";

    public static final String CACHE_URLS = "cache-urls";
    public static final String TIME_LAST_CACHE_API_CALL = "time-of-last-cache-save";
    public static final String ALARM_DELAY = "alarm-delay";
    public static final String CART_REMINDER_NOTIFICATION_DATA = "cart-reminder-notification-data";
    public static final String DAILY_DEALS_REMAINDER_DATA = "daily-deals-remainder-data";
    public static final String CART_REMINDER_SHOWN_AT="cart-reminder-shown-at";
    public static final String CART_UPDATED_AT="cart-updated-at";
    public static final String STORY_NOTIFICATION_DATA = "story-notification-data";
    public static final String MAX_VIDEO_CACHE_LIMIT = "max_video_cache_limit";
    public static final String CART_DATA = "cart-data";
    public static final String REVIEW_REMINDER_NOTIFICATION_DATA = "review_reminder_notification_data";
    public static final String NOTIFICATIONS_API_DATA = "notifications_api_data";
    public static final String FRIDAY_DROP_NOTIFICATION_DATA = "friday_drop_notification_data";

    public static void initPref (Context reactContext) {
        sharedPreferences = reactContext.getSharedPreferences(PREF_NAME, reactContext.MODE_PRIVATE);
    }

    public static void saveStringPref (String key, String value) {
        if (sharedPreferences==null) {
            Log.e("no pref", "shared pref " + sharedPreferences);
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private static void saveIntPref (String key, int value) {
        if (sharedPreferences==null) {
            Log.e("no pref", "shared pref " + sharedPreferences);
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static String getStringPref (String key) {
        if (sharedPreferences==null) return "";
        return sharedPreferences.getString(key,"0");
    }

    public static int getIntPref (String key) {
        if (sharedPreferences==null) return -1;
        return sharedPreferences.getInt(key,0);
    }

    private static void saveLongPref (String key, long value) {
        if (sharedPreferences==null) {
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static long getLongPref (String key) {
        if (sharedPreferences==null) return (long) 0;
        return (long) sharedPreferences.getLong(key, (long) 0);
    }

    public static void resetPrefs () {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public static void setUserAuthToken (String authToken) {
        Log.e("authToken", "saved " + authToken);
        saveStringPref(AUTH_TOKEN, authToken);
    }

    public static void setUserId (String id) {
        Log.e("id", "saved " + id);
        saveStringPref(USER_ID, id);
    }

    public static void setUserGuestToken (String guestToken) {
        Log.e("guestToken", "saved " + guestToken);
        saveStringPref(GUEST_TOKEN, guestToken);
    }

    public static void setCartItems (String items) {
        Log.e("items", "saved " + items);
        saveStringPref(CART_ITEMS, items);
    }

    public static void setNumberOfCartItems (String itemCount) {
        Log.e("itemCount", "saved " + itemCount);
        saveStringPref(CART_ITEMS_COUNT, itemCount);
    }

    public static void setCartUpdatedAt (long timestamp) {
        saveLongPref(CART_UPDATED_AT, timestamp);
    }

    public static void setNotificationShownTimeStamp (long timeStamp) {
        saveLongPref(CART_REMINDER_SHOWN_AT, timeStamp);
    }

    public static void notificationRemoteValues (String subject, String body, String sticky, String expire_in, String primary_cta, String primary_destination, String secondary_cta, String secondary_destination, String channel) {
        Log.e("notifications", "saved " + subject + ' ' + body + ' ' + sticky + ' ' + expire_in + ' ' + primary_cta + ' ' + primary_destination + ' ' + secondary_cta + ' ' + secondary_destination);
        saveStringPref(NOTIFICATION_SUBJECT, subject);
        saveStringPref(NOTIFICATION_BODY, body);
        saveStringPref(NOTIFICATION_IS_STICKY, sticky);
        saveStringPref(NOTIFICATION_EXPIRES_IN, expire_in);
        saveStringPref(NOTIFICATION_PRIMARY_CTA, primary_cta);
        saveStringPref(NOTIFICATION_PRIMARY_DESTINATION, primary_destination);
        saveStringPref(NOTIFICATION_SECONDARY_CTA, secondary_cta);
        saveStringPref(NOTIFICATION_SECONDARY_DESTINATION, secondary_destination);
        saveStringPref(NOTIFICATION_CHANNEL, channel);
    }

    public static void setCacheUrls (String cacheUrls) {
        Log.e("cacheUrls", "saved " + cacheUrls);
        saveStringPref(CACHE_URLS, cacheUrls);
    }

    public static void setAlarmDelay (String delay) {
        Log.e("itemCount", "saved " + delay);
        saveStringPref(ALARM_DELAY, delay);
    }

    public static void setWarholImageUrl (String warholImageUrl) {
        Log.e("warholImageUr;", "saved " + warholImageUrl);
        saveStringPref(WARHOL_IMAGE_URL, warholImageUrl);
    }


    public static void saveReminderNotificationData(String data) {
        Log.e("reminderNotificationData", "saved " + data);
        saveStringPref(CART_REMINDER_NOTIFICATION_DATA, data);
    }

    public static void saveDailyDealsRemainderData(String data) {
        Log.e("reminderNotificationData", "saved " + data);
        saveStringPref(DAILY_DEALS_REMAINDER_DATA, data);
    }

    public static void saveFridayDropNotificationData(String data) {
        saveStringPref(FRIDAY_DROP_NOTIFICATION_DATA, data);
    }

    public static ArrayList<String> getListOfCacheUrls(Context context){
        UserPreferences.initPref(context);
        String urls = getStringPref(CACHE_URLS);
        int limit = getIntPref(MAX_VIDEO_CACHE_LIMIT);
        if(urls.equalsIgnoreCase("")) return null;
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(urls.split(",")));
        ArrayList<String> finalList = new ArrayList<String>();
        for (String str : list) {
            if (!str.contains(".m3u8")) {
                continue;
            }
            if (finalList.size()>limit) {
                break;
            }
            finalList.add(str);
        }
        Log.d("ARRAY_CACHE_URLS", "getListOfCacheUrls: "+finalList.toString());
        return finalList;
    }

    public static void setCachingTime(Long cachingTime){
        UserPreferences.saveStringPref(UserPreferences.TIME_LAST_CACHE_API_CALL,cachingTime.toString());
    }

    public static void saveStoryNotificationData(String data){
        Log.d("STORY_NOTIFICATION_DATA", "data: "+data);
        UserPreferences.saveStringPref(UserPreferences.STORY_NOTIFICATION_DATA,data);
    }

    public static void saveMaxCacheLimit(int limit){
        Log.d("MAX_CACHE_LIMIT", "LIMIT: "+limit);
        UserPreferences.saveIntPref(UserPreferences.MAX_VIDEO_CACHE_LIMIT,limit);
    }

    public static void saveCartData(String data){
        UserPreferences.saveStringPref(UserPreferences.CART_DATA,data);
    }

    public static void saveReviewReminderNotificationData(String data){
        UserPreferences.saveStringPref(UserPreferences.REVIEW_REMINDER_NOTIFICATION_DATA,data);
    }

    public static void saveNotificationsApiData(String data) {
        UserPreferences.saveStringPref(UserPreferences.NOTIFICATIONS_API_DATA,data);
    }
}
