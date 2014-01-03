package com.music.fms.models;

import android.text.TextUtils;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.music.fms.db.BaseDBmodel;

/**
 * User: Vitalii Lebedynskyi
 * Date: 7/19/13
 * Time: 12:15 PM
 */

@DatabaseTable
public class SearchAlbumModel extends BaseDBmodel {
    public static int AVAILABLE_PAGES = -1;
    @DatabaseField
    private String artistName;
    @DatabaseField
    private String albumName;
    @DatabaseField
    private String artistUrl;
    @DatabaseField
    private String albumUrl;
    @DatabaseField
    private String briefDescr;
    @DatabaseField
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBriefDescr() {
        return briefDescr;
    }

    public void setBriefDescr(String briefDescr) {
        this.briefDescr = briefDescr;
    }

    public String getAlbumUrl() {
        return albumUrl;
    }

    public void setAlbumUrl(String albumUrl) {
        this.albumUrl = albumUrl;
    }

    public String getArtistUrl() {
        return artistUrl;
    }

    public void setArtistUrl(String artistUrl) {
        this.artistUrl = artistUrl;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getTitle() {
        if (TextUtils.isEmpty(artistName) && TextUtils.isEmpty(albumName)) return "Unknown song";
        if (TextUtils.isEmpty(artistName)) return albumName;
        if (TextUtils.isEmpty(albumName)) return artistName;
        return artistName + " - " + albumName;
    }
}
