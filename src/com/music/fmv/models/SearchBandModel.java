package com.music.fmv.models;

import java.util.ArrayList;

/**
 * User: vitaliylebedinskiy
 * Date: 7/12/13
 * Time: 5:49 PM
 */
public class SearchBandModel extends BaseSerializableModel {
    public static int AVAILABLE_PAGES = 0;

    private String url;
    private String image;
    private String descr;
    private String name;
    private ArrayList<String> genres;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
