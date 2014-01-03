package com.music.fms.models;

/**
 * User: vitaliylebedinskiy
 * Date: 8/13/13
 * Time: 5:09 PM
 */
public class Captcha {
    private String url;
    private int sid;
    private String key;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
