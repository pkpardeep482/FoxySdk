//package com.pardeep.foxy_dynamic.foxynativemodules.shortcutcreator;
//
//import androidx.core.content.pm.ShortcutManagerCompat;
//
//import com.facebook.react.bridge.ReactApplicationContext;
//import com.facebook.react.bridge.ReactContextBaseJavaModule;
//import com.facebook.react.bridge.ReactMethod;
//
//import org.jetbrains.annotations.NotNull;
//
//import com.pardeep.foxy_dynamic.foxynativemodules.UserPreferences.UserPreferences;
//
//public class ShortcutCreatorModule extends ReactContextBaseJavaModule {
//    public ShortcutCreatorModule(@NotNull ReactApplicationContext reactContext) {
//        super(reactContext);
//    }
//
//    @NotNull
//    @Override
//    public String getName() {
//        return "ShortcutCreator";
//    }
//
//    @ReactMethod
//    public void updateShortcut(String url) {
//        if (url.equalsIgnoreCase(UserPreferences.getStringPref(UserPreferences.WARHOL_IMAGE_URL)) ||
//                !ShortcutManagerCompat.isRequestPinShortcutSupported(getReactApplicationContext())
//        ) {
//            return;
//        }
//
//       createShortcut(url);
//    }
//
//    @ReactMethod
//    public void createShortcut(String url) {
//        UserPreferences.initPref(getReactApplicationContext());
//        UserPreferences.setWarholImageUrl(url);
////        new ShortcutCreator().execute(url);
//    }
//}
