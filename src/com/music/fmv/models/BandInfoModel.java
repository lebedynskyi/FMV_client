package com.music.fmv.models;

import java.util.ArrayList;

/**
 * User: vitaliylebedinskiy
 * Date: 7/12/13
 * Time: 5:54 PM
 */

public class BandInfoModel extends BaseSerializableModel{
    private ArrayList<String> images;
    private ArrayList<SimilarBandModel> similars;
    private String descr;

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<SimilarBandModel> getSimilars(){
        return similars;
    }

    public void setSimilars(ArrayList<SimilarBandModel> similars) {
        this.similars = similars;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
}
