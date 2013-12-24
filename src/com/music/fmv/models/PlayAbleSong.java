package com.music.fmv.models;

import android.text.TextUtils;
import com.j256.ormlite.field.DatabaseField;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: Vitalii Lebedynskyi
 * Date: 10/22/13
 * Time: 11:09 AM
 */


public abstract class PlayAbleSong extends BaseSerializableModel{
    public static final SimpleDateFormat MM_SS_DATE_FORMAT = new SimpleDateFormat("mm:ss");

    @DatabaseField
    private String url;
    @DatabaseField
    private String artist;
    @DatabaseField
    private String name;
    @DatabaseField
    private int duration;

    private String niceDuration;
    private long size;

    // url == null will be used getUrlForUrl and getUrlKey
    public String getUrlKey(){
        throw new IllegalStateException("PlayAbleSong doesn't have urlKey");
    }

    // url == null will be used getUrlForUrl and getUrlKey
    public String getUrlForUrl(){
        throw new IllegalStateException("PlayAbleSong doesn't have UrlForUrl");
    }

    public String getId(){
        throw new IllegalStateException("PlayAbleSong doesn't have id");
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

        PlayAbleSong that = (PlayAbleSong) o;

        return !(artist != null ? !artist.equals(that.getArtist()) : that.getArtist()!= null) && !(name != null ? !name.equals(that.getName()) : that.getName() != null);
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


    public String getNiceDuration(){
        if (TextUtils.isEmpty(niceDuration)) {
            Date d = new Date(duration);
            niceDuration = MM_SS_DATE_FORMAT.format(d);
        }

        return niceDuration;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getSize() {
        return size;
    }
}
