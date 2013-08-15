package com.music.fmv.models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: vitaliylebedinskiy
 * Date: 7/17/13
 * Time: 4:04 PM
 */
public class PlayableSong extends BaseSerializableModel{
    public static int PAGE_AVAILABLE = -1;
    public static final SimpleDateFormat SD = new SimpleDateFormat("mm:ss");

    private String url;
    private String artist;
    private String title;
    private int duration;
    private String id;
    private String rate;

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

    public String getNiceDuration(){
        Date date = new Date(duration * 1000);
        return SD.format(date);

    }

    public boolean isPlayable(){
        return url != null;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRate() {
        return rate;
    }
}
