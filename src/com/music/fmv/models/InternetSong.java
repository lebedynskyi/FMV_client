package com.music.fmv.models;

import com.j256.ormlite.field.DatabaseField;

/**
 * User: vitaliylebedinskiy
 * Date: 7/17/13
 * Time: 4:04 PM
 */

public class InternetSong extends PlayAbleSong {
    public static int PAGE_AVAILABLE = -1;
    @DatabaseField
    private String id;
    @DatabaseField
    private String rate;
    @DatabaseField
    private String urlKey;
    @DatabaseField
    private String urlForUrl;

    public String getUrlKey() {
        return urlKey;
    }

    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }

    public String getUrlForUrl() {
        return urlForUrl;
    }

    public void setUrlForUrl(String urlForUrl) {
        this.urlForUrl = urlForUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRate() {
        return rate;
    }
}
