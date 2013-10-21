package com.music.fmv.models.dbmodels;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * User: Vitalii Lebedynskyi
 * Date: 10/15/13
 * Time: 11:49 AM
 */
@DatabaseTable
public class CachedResultModel extends BaseDBModel{
    @DatabaseField
    private String title;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
