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
//import org.jetbrains.annotations.NotNull;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Calendar;
//
//import com.pardeep.foxy_dynamic.foxynativemodules.UserPreferences.UserPreferences;
//// import com.pardeep.foxy_dynamic.foxynativemodules.utilities.AlarmCreator;
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//
//public class StoryApiWorker extends Worker {
//    private Context context;
//    private final String storyUrl = "https://api.foxy.in/api/v4/lists/stories";
//    private static boolean isRequestCompleted = false;
//    public StoryApiWorker(@NonNull @NotNull Context context, @NonNull @NotNull WorkerParameters workerParams) {
//        super(context, workerParams);
//        this.context = context;
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @NotNull
//    @Override
//    public Result doWork() {
//        UserPreferences.initPref(context);
//        String authToken = UserPreferences.getStringPref(UserPreferences.AUTH_TOKEN);
//        String guestToken = UserPreferences.getStringPref(UserPreferences.GUEST_TOKEN);
//
//
//        try{
//            OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder()
//                    .addHeader("x-auth-token",authToken)
//                    .addHeader("x-guest-token",guestToken)
//                    .url(storyUrl).build();
//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                    JSONArray videoObjects = null;
//                    try {
//                        JSONObject json = new JSONObject(response.body().string());
//                        JSONArray jsonObjectsArray = json.getJSONArray("objects");
//                        for (int i=0;i<jsonObjectsArray.length();i++){
//                            String content = ((JSONObject)jsonObjectsArray.get(i)).getString("content");
//                            if(content.equalsIgnoreCase("media")){
//                                if ( !((JSONObject) jsonObjectsArray.get(i)).has("objects") ) continue;
//                                videoObjects = ((JSONObject) jsonObjectsArray.get(i)).getJSONArray("objects");
//                                break;
//                            }
//                        }
//                        if(videoObjects == null) {
//                            Log.d("STORY_RESPONSE", "No media objects found");
//                            return;
//                        }
//                        String videoUrls="";
//                        for (int j=0;j<videoObjects.length();j++) {
//                            JSONObject video = videoObjects.getJSONObject(j);
//                            if ( !((JSONObject) videoObjects.get(j)).has("metadata") ) continue;
//                            JSONObject metadata = video.getJSONObject("metadata");
//
//                            if (!metadata.has("videoUrl")) continue;
//                            String url = metadata.getString("videoUrl");
//                            if (url.equalsIgnoreCase("") || !url.contains(".m3u8")) continue;
//                            videoUrls += url + ",";
//                        }
//
//                        UserPreferences.setCacheUrls(videoUrls);
//                        isRequestCompleted = true;
//                        File cacheDir = new File(context.getExternalCacheDir()+"");
//                        if (cacheDir.delete()) Log.d("STORY_API_CALL", "CLEARED CACHE ");
//                        else Log.d("STORY_API_CALL", "COULD NOT CLEAR CACHE ");
//                        Log.d("STORY_URLS", "onResponse: "+videoUrls);
//                    } catch (Exception e) {
//                        setRetryAlarm();
//                        Log.e("STORY_EXCEPTION", "onResponse: ", e);
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                    setRetryAlarm();
//                    Log.e("STORY_REQUEST_ERROR", "Request: "+request.toString(),e);
//                }
//            });
//
//        }catch (Exception e){
//            setRetryAlarm();
//            Log.e("STORY_REQUEST_ERROR", "Exception: ",e);
//        }
//        return Result.success();
//    }
//
//    private void setRetryAlarm() {
//        // AlarmCreator.createAlarm(context,AlarmCreator.CALL_STORY_API,12, Calendar.HOUR_OF_DAY);
//    }
//}
