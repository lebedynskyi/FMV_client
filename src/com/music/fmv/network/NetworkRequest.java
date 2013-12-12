package com.music.fmv.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Vitalii Lebedynskyi
 * Date: 12/12/13
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class NetworkRequest {
    public enum Method {
        POST, GET
    }

    private HashMap<String, String> headers;
    private HashMap<String, String> params;
    private String url;
    private Method method;
    private int timeOut = 5000;

    private byte[] data;

    public NetworkRequest(String url) {
        this(url, null);
    }

    public NetworkRequest(String url, HashMap<String, String> params) {
        this(url, params, Method.GET);
    }

    public NetworkRequest(String url, HashMap<String, String> params, Method method) {
        this(url, params, method, null);
    }

    public NetworkRequest(String url, HashMap<String, String> params, Method method, HashMap<String, String> headers) {
        this.url = url;
        this.params = params;
        this.method = method;

        this.headers = headers;
    }

    public void setData(String data) {
        setData(data.getBytes());
    }

    public void setData(Object data) throws IOException {
        setData(getBytesFromObject(data));
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public Method getMethod() {
        return method;
    }

    public byte[] getData() {
        return data;
    }

    public int getTimeOut() {
        return timeOut;
    }

    private byte[] getBytesFromObject(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
       try {
           out = new ObjectOutputStream(bos);
           out.writeObject(obj);
           return bos.toByteArray();
       }finally {
           if (out != null)  out.close();
           bos.close();
       }
    }
}
