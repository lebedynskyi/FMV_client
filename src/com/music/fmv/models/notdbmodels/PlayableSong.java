package com.music.fmv.models.notdbmodels;

import android.text.TextUtils;

/**
 * User: vitaliylebedinskiy
 * Date: 7/17/13
 * Time: 4:04 PM
 */
public class PlayableSong extends BaseSerializableModel {
    public static int PAGE_AVAILABLE = -1;

    private String url;
    private String artist;
    private String name;
    private int duration;
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
        return "http://pleer.com/site_api/files/get_url?id=" + id;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRate() {
        return rate;
    }

    public String getFutureFileName() {
        if (TextUtils.isEmpty(name) && TextUtils.isEmpty(artist)) return "Unknown song.mp3";

        if (TextUtils.isEmpty(name)) return artist + ".mp3";

        if (TextUtils.isEmpty(artist)) return name + ".mp3";

        return artist + " - " + name + ".mp3";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayableSong that = (PlayableSong) o;

        return !(artist != null ? !artist.equals(that.artist) : that.artist != null) && !(name != null ? !name.equals(that.name) : that.name != null);
    }

    @Override
    public int hashCode() {
        int result = artist != null ? artist.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        if (artist == null) return name == null ? "" : name;
        if (name == null) return artist;
        return artist + " - " + name;
    }
}
