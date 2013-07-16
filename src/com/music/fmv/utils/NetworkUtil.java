package com.music.fmv.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Lebedynskyi
 * Date: 1/28/13
 * Time: 12:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class NetworkUtil {
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        if (nInfo != null && nInfo.isConnected()) {
            Log.d("NetworkUtil", "ONLINE");
            return true;
        } else {
            Log.d("NetworkUtil", "OFFLINE");
            return false;
        }
    }

    public static String doRequest(String urlRequest) throws IOException {
        URL url = new URL(urlRequest);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

        String s = null;
        StringBuilder sb  = new StringBuilder();
        while ((s = reader.readLine()) != null){
            sb.append(s);
        }
        return s.toString();
    }
}
