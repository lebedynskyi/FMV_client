package com.music.fmv.models;

/**
 * User: vitaliylebedinskiy
 * Date: 7/16/13
 * Time: 2:48 PM
 */
public class SimilarBandModel extends BaseSerializableModel{
    private String image;
    private String name;
    private String url;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
