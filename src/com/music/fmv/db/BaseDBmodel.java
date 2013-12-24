package com.music.fmv.db;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created with IntelliJ IDEA.
 * User: Vitalii Lebedynskyi
 * Date: 12/24/13
 * Time: 6:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseDBmodel {
    @DatabaseField(generatedId = true)
    private int _id;
}
