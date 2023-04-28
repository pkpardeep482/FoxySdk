//package com.pardeep.foxy_dynamic.foxynativemodules.shortcutcreator;
//
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.AsyncTask;
//import android.widget.Toast;
//
//import androidx.core.content.pm.ShortcutInfoCompat;
//import androidx.core.content.pm.ShortcutManagerCompat;
//import androidx.core.graphics.drawable.IconCompat;
//
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import in.foxy.MainActivity;
//import in.foxy.MainApplication;
//
//public class ShortcutCreator extends AsyncTask<String, Void, Bitmap> {
//    private final String shortcut_id = "id_warhol";
//    private final Context context = MainApplication.mainApplication.getApplicationContext();
//    private final String message = "Shortcut updated successfully!";
//    private final String errorMsg = "Sorry, shortcut could not be added!";
//
//    @Override
//    protected Bitmap doInBackground(String... urls) {
//        try {
//            return Picasso.get().load(urls[0]).get();
//        } catch (Exception e) {
//        }
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(Bitmap bitmap) {
//        if (bitmap == null) return;
//        try {
//            Intent launchIntent = new Intent(Intent.ACTION_VIEW_LOCUS, null, context, MainActivity.class);
//            ShortcutInfoCompat shortcut = new ShortcutInfoCompat.Builder(context, shortcut_id)
//                    .setShortLabel("Foxy")
//                    .setIcon(IconCompat.createWithBitmap(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getWidth())))
//                    .setIntent(launchIntent)
//                    .build();
//            List<ShortcutInfoCompat> listShortcuts = new ArrayList<>();
//            listShortcuts.add(shortcut);
//            ShortcutManagerCompat.addDynamicShortcuts(context, listShortcuts);
//
//            if (ShortcutManagerCompat.getDynamicShortcuts(context).size() > 0) {
//                ShortcutManagerCompat.updateShortcuts(context, listShortcuts);
//            } else {
//                Intent pinnedShortcutCallbackIntent = ShortcutManagerCompat.createShortcutResultIntent(context, shortcut);
//                PendingIntent successCallback = PendingIntent.getBroadcast(context, 27, pinnedShortcutCallbackIntent, PendingIntent.FLAG_MUTABLE);
//                ShortcutManagerCompat.requestPinShortcut(context, shortcut, successCallback.getIntentSender());
//            }
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
//        }
//    }
//}
