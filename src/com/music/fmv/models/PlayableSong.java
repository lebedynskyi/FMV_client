package com.music.fmv.models;

import android.text.TextUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: vitaliylebedinskiy
 * Date: 7/17/13
 * Time: 4:04 PM
 */
public class PlayableSong extends BaseSerializableModel {
    public static int PAGE_AVAILABLE = -1;
    public static final SimpleDateFormat SD = new SimpleDateFormat("mm:ss");

    private String url;
    private String artist;
    private String name;
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

    public String getNiceDuration() {
        Date date = new Date(duration * 1000);
        return SD.format(date);

    }

    public boolean isPlayable() {
        return url != null;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRate() {
        return rate;
    }

    public File getAbsolutheFile(String folder) {
        return new File(folder, this.getFutureFileName());
    }

    public File getAbsolutheFile(File folder) {
        return new File(folder, this.getFutureFileName());
    }

    public String getFutureFileName() {
        if (TextUtils.isEmpty(name) && TextUtils.isEmpty(artist)) return "Unknown song";
        if (TextUtils.isEmpty(name)) return artist;
        if (TextUtils.isEmpty(artist)) return name;
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
