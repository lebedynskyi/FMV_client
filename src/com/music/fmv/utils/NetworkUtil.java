package com.music.fmv.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.music.fmv.tasks.threads.IDownloadListener;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Lebedynskyi
 * Date: 1/28/13
 * Time: 12:10 PM
 * To change this template use File | Settings | File Templates.
 */

public class NetworkUtil {
    //returns true if network available
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        if (nInfo != null && nInfo.isConnected()) {
            Log.d("NetworkUtil", "Network available");
            return true;
        } else {
            Log.d("NetworkUtil", "Network unavailable");
            return false;
        }
    }

    //Method downloads a string from the url
    public static String doRequest(String urlRequest) throws Exception{
        URL url = new URL(urlRequest);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(10000); // 10 second for timeout
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String s = null;
        StringBuilder sb  = new StringBuilder();
        while ((s = reader.readLine()) != null){
            sb.append(s);
        }
        return sb.toString();
    }

    public static void downloadFile(File f, String songUrl, IDownloadListener listener)throws Exception{
        URL url = new URL(songUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(5000);
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        InputStream inStream = connection.getInputStream();
        FileOutputStream outStream = new FileOutputStream(f);

        Integer fileLength = 0;
        try {
            fileLength = connection.getContentLength();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (fileLength == 0) fileLength = 1;

        try {
            byte data[] = new byte[5120];

            int lastPercentNotify = -1, curPercent;
            int count;
            int total = 0;
            while ((count = inStream.read(data, 0, 5120)) != -1){
                total += count;
                outStream.write(data, 0, count);

                curPercent = (total * 100) / fileLength;
                if (listener != null && curPercent != lastPercentNotify && curPercent % 10 == 0){
                    listener.onDownload(f.getName(), curPercent, 100);
                    lastPercentNotify = curPercent;
                }
            }
        }finally {
            inStream.close();
            outStream.flush();
            outStream.close();
        }
    }
}
