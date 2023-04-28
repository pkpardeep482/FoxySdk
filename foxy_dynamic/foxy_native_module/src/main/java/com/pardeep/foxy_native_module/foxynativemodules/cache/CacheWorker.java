//package com.pardeep.foxy_dynamic.foxynativemodules.cache;
//
//import android.content.Context;
//import android.os.Build;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.RequiresApi;
////import androidx.work.Worker;
////import androidx.work.WorkerParameters;
//
//import java.util.ArrayList;
//
////import in.foxy.MainActivity;
//import com.pardeep.foxy_dynamic.foxynativemodules.UserPreferences.UserPreferences;
//
//public class CacheWorker extends Worker {
//
//    private Context context;
//    public static boolean isCachingComplete = false;
//    public CacheWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
//        super(context, workerParams);
//        this.context = context;
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @NonNull
//    @Override
//    public Result doWork() {
//
//        ArrayList<String> indexUrlsArray = UserPreferences.getListOfCacheUrls(context);
//        if(indexUrlsArray == null)
//        {
//            System.out.println("No urls remaining");
//            return Result.success();
//        }
//        Log.d("WORKER", "doWork: ArrayUrls"+indexUrlsArray.toString());
//        for(String indexUrl: indexUrlsArray) {
//            if(! indexUrl.contains(".m3u8")) continue;
//            isCachingComplete = false;
//            new CacheDownloader().initiateCaching(context,indexUrl);
//            int k = 0;
//            while(!isCachingComplete) {
//                k += 1;
//                if(k>10) {
//                    continue;
//                }
//                try {
//                    System.out.println("CacheDownloader running");
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    continue;
//                }
//            }
//        }
//        return Result.success();
//    }
//}
//
