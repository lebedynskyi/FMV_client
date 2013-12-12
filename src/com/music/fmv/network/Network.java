package com.music.fmv.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Vitalii Lebedynskyi
 * Date: 12/12/13
 * Time: 4:22 PM
 * To change this template use File | Settings | File Templates.
 */

public class Network {
    public static NetworkResponse doRequest(NetworkRequest request) throws IOException{
        URL preparedURL  = new URL(appendParamsToUrl(request.getUrl(), request.getParams()));
        HttpURLConnection connection = (HttpURLConnection) preparedURL.openConnection();
        connection.setConnectTimeout(request.getTimeOut());

        if (request.getHeaders() != null){
            for (String key : request.getHeaders().keySet()){
                connection.addRequestProperty(key, request.getHeaders().get(key));
            }
        }

        NetworkRequest.Method method = request.getMethod();
        connection.setRequestMethod(method.name());
        if (method == NetworkRequest.Method.POST && request.getData() != null){
            BufferedOutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
            outputStream.write(request.getData());
            outputStream.flush();
            outputStream.close();
        }

        NetworkResponse response = new NetworkResponse(connection);
        return response;
    }

    public static String appendParamsToUrl(String baseUrl, HashMap<String, String> params){
        if (params == null || params.size() == 0) return baseUrl;


        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(baseUrl);

        int counter = 0;
        for (String key : params.keySet()) {
            urlBuilder.append(counter++ == 0 ? "?" : "&").append(key).append("=").append(encodeString(params.get(key), "utf-8"));
        }

        return urlBuilder.toString();
    }

    public static String encodeString(String s, String charSet) {
        if (TextUtils.isEmpty(s)) return "";

        try {
            return URLEncoder.encode(s, charSet);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return URLEncoder.encode(s);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }
}
