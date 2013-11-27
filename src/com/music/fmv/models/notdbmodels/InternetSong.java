package com.music.fmv.models.notdbmodels;

/**
 * User: vitaliylebedinskiy
 * Date: 7/17/13
 * Time: 4:04 PM
 */

public class InternetSong extends PlayAbleSong {
    public static int PAGE_AVAILABLE = -1;
    private String id;
    private String rate;

    private String urlKey = "track_link";
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
