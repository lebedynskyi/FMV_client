package com.music.fmv.models;

/**
 * User: vitaliylebedinskiy
 * Date: 7/17/13
 * Time: 4:04 PM
 */
public class PlayableSong extends BaseSerializableModel{
    private String url;
    private String artist;
    private String title;
    private int duration;
    private String id;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        if (artist == null && title == null) return "";
        if (artist == null) return title;
        if (title == null) return artist;
        return artist + " -" + title;
    }

    public boolean isPlayable(){
        return url != null;
    }
}
