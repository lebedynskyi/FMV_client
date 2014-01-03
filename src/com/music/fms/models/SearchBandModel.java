package com.music.fms.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.music.fms.db.BaseDBmodel;

/**
 * User: Vitalii Lebedynskyi
 * Date: 7/12/13
 * Time: 5:49 PM
 */

@DatabaseTable
public class SearchBandModel extends BaseDBmodel {
    public static int AVAILABLE_PAGES = -1;

    @DatabaseField
    private String url;
    @DatabaseField
    private String image;
    @DatabaseField
    private String descr;
    @DatabaseField
    private String name;
    @DatabaseField
    private String genres;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
