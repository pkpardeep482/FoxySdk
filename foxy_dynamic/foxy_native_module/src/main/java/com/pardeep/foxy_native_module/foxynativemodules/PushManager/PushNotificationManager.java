//package com.pardeep.foxy_dynamic.foxynativemodules.PushManager;
//
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.lang.reflect.InvocationTargetException;
//
//import in.foxy.BuildConfig;
//
//public class PushNotificationManager extends FirebaseMessagingService {
//
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        try {
//            Class<?> notificationHandler = Class.forName(BuildConfig.NOTIFICATIONS_HANDLER);
//            notificationHandler.getMethod("onMessageReceived", RemoteMessage.class)
//                .invoke(null,  remoteMessage);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onNewToken(@NotNull String token) {
//
//        try {
//            Class<?> notificationHandler = Class.forName(BuildConfig.NOTIFICATIONS_HANDLER);
//            notificationHandler.getMethod("onNewToken", String.class)
//                    .invoke(null,  token);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }
//}
