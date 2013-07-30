package com.music.fmv.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    public static String doRequest(String urlRequest) throws IOException {
        URL url = new URL(urlRequest);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String s = null;
        StringBuilder sb  = new StringBuilder();
        while ((s = reader.readLine()) != null){
            sb.append(s);
        }
        return sb.toString();
    }
}
