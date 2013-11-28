package com.music.fmv.models;

import com.j256.ormlite.field.DatabaseField;

/**
 * User: Vitalii Lebedynskyi
 * Date: 10/11/13
 * Time: 10:59 AM
 */
public abstract class BaseDBModel {
    @DatabaseField (generatedId = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
