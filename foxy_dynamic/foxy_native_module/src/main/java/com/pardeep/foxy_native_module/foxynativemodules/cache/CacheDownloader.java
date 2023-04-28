package com.pardeep.foxy_native_module.foxynativemodules.cache;

import android.content.Context;
import android.util.Log;


import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pardeep.foxy_native_module.foxynativemodules.UserPreferences.UserPreferences;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CacheDownloader {

    private String pathDir;
    private String cacheFolderPath;
    private String chunkPreUrl;
    private String videoFolderPath;
    private List<String> chunksUrlList;
    private HashMap<Integer,String> keyFileMap;
    private Context context;
    private String indexUrl;
    private String str="";
    private int iteration=0;

    //iteration 0: to extract subVersion urls for different video qualities; iteration 1: to extract the chunk urls of the video

    public void initiateCaching(Context context,String indexUrl) {
        this.context = context;
        this.indexUrl = indexUrl;
        str = "";
        iteration=0;
        cacheFolderPath = context.getExternalCacheDir()+"/";
        chunkPreUrl = indexUrl.substring(0,indexUrl.lastIndexOf("/")+1);
        Log.d("chunkPreUrl", "main: "+chunkPreUrl);
//        cacheFolderPath = pathDir+"/cache/";
        videoFolderPath = cacheFolderPath + indexUrl.substring(indexUrl.lastIndexOf("/")+1,indexUrl.lastIndexOf(".")) + "/";
        //videoFolderPath = cacheFolderPath + playlist-name from IndexUrl
        chunksUrlList = new ArrayList<String>();
        keyFileMap = new HashMap<Integer,String>();

        getIndexFile(indexUrl);
    }


    private void getIndexFile(String urlPath){
        try{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(urlPath)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        Log.e("RESPONSE_FAILURE", "onResponse: "+response.body().string());
                    } else {
                        str = response.body().string();
//                        System.out.println(str);
                        extractUrlsFromIndex(str, iteration < 1);
                    }
                }

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void extractUrlsFromIndex(String content, boolean containsQualitySubUrls){
        Pattern pattern = Pattern.compile(".*ts");
        if (containsQualitySubUrls) pattern = Pattern.compile(".*m3u8");
        Matcher ma = pattern.matcher(content);

        List<String> list = new ArrayList<String>();
        while(ma.find()){
            String s = ma.group();
            list.add(s);
//            System.out.println(s);
        }
        if(iteration == 0) {
            getIndexFile(list.get(0));
            iteration += 1;
        } else if (iteration == 1){
            for (String chunkName: list){
                chunksUrlList.add(chunkPreUrl+chunkName);
            }
            Log.d("INDEX's", "main: "+chunksUrlList.toString());
            startDownloading();
        }
    }

    private void startDownloading() {
        for (String chunkUrl: chunksUrlList) {
            new DownloadNode(chunkUrl).start();
        }

        while (keyFileMap.size()<chunksUrlList.size()){
            try {
//                System.out.println("Sleeping cuz downloading");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        composeFile(keyFileMap);
    }

    private void composeFile(HashMap<Integer,String> keyFileMap){
        String name = "video.ts";
        try {
            File dir = new File(videoFolderPath);
            File cache = new File(videoFolderPath+name);
//            System.out.println(dir +"\n"+cache);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (cache.exists()) {
                if (cache.delete()) {
//                    System.out.println("file Deleted :");
                } else {
//                    System.out.println("file not Deleted :");
                }
            }
            cache.createNewFile();
            FileOutputStream out = new FileOutputStream(videoFolderPath+name);

            for(int k=0;k<keyFileMap.size();k++) {
                File chunk = new File(keyFileMap.get(k));
                FileInputStream input = new FileInputStream(chunk);
                byte[] buf = new byte[8192];
                int len;
                while ((len = input.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                input.close();
                chunk.delete();
            }
            out.close();
//            System.out.println("Cache Created");

            updateCacheUrls();
        }catch (Exception e){
            Log.e("CACHE_SYNTHESIS_ERROR", "composeFile: ", e);
        }
    }

    private void updateCacheUrls() {
        ArrayList<String> indexUrlsArray = UserPreferences.getListOfCacheUrls(context);
        if(indexUrlsArray == null) return;
        indexUrlsArray.remove(indexUrl);
        String urls="";
        if(indexUrlsArray.size()>1 ) {
            urls = indexUrlsArray.get(0);
            for (int i=1;i<indexUrlsArray.size();i++) {
                urls += "," + indexUrlsArray.get(i);
            }
        }
        UserPreferences.setCacheUrls(urls);
//        System.out.println("updated array: "+indexUrlsArray);
    }

    class DownloadNode extends  Thread{
        private String downloadUrl;

        public  DownloadNode(String downloadUrl){
            this.downloadUrl = downloadUrl;
        }

        @Override
        public void run(){
            try{
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(downloadUrl).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        saveVideo(response.body().bytes());
                    }

                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e("CACHING_REQUEST_ERROR", "Request: "+request.toString(),e);

                    }
                });

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public void saveVideo(byte[] bytes){
            String videoFileName = downloadUrl.substring(downloadUrl.lastIndexOf("_")+1);
            String chunksPath = videoFolderPath;
            File dir = new File(chunksPath);
            File file = new File(chunksPath+videoFileName);
            try {
                if (!dir.exists()) {
                    dir.mkdir();
                }
                if (file.exists()) {
                    if (file.delete()) {
//                        System.out.println("file Deleted :");
                    } else {
//                        System.out.println("file not Deleted :");
                    }
                }
                file.createNewFile();
                FileOutputStream out = new FileOutputStream(chunksPath+videoFileName);
                InputStream input = new ByteArrayInputStream(bytes);

                byte[] buf = new byte[8192];
                int len;
                while ((len = input.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                input.close();
                out.close();
            } catch (Exception e) {
                Log.e("EXPORT", "saveVideo: ",e);
                e.printStackTrace();
                return;
            }
            int key = Integer.parseInt(videoFileName.substring(0,videoFileName.lastIndexOf(".")));
            keyFileMap.put(key,file.getPath());
        }
    }
}
