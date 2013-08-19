package com.music.fmv.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;

/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 7/14/13
 * Time: 9:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class SettingsManager extends Manager{
    public static final String DEFAULT_CACHE_FOLDER = "FMV";
    private static final String DEFAULT_IMAGES_FOLDER = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + DEFAULT_CACHE_FOLDER + "/images/";
    private static final String DEFAULT_SONGS_FOLDER = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + DEFAULT_CACHE_FOLDER + "/music/";

    private SharedPreferences prefs;

    protected SettingsManager(Core coreManager) {
        super(coreManager);
        prefs = PreferenceManager.getDefaultSharedPreferences(coreManager.getContext());
    }

    public String getResultLanguage(){
        return prefs.getString(Const.LANGUAGE_CACHE_KEY, "ru");
    }

    public void setResultLanguage(String lan){
        puString(Const.LANGUAGE_CACHE_KEY, lan);
    }

    public String getImageCacheFolder() {
        return prefs.getString(Const.IMAGE_CACHE_KEY, DEFAULT_IMAGES_FOLDER);
    }

    public void setImageCacheFolder(String folder) {
        puString(Const.IMAGE_CACHE_KEY, folder);
    }

    private void puString(String key, String value){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value).commit();
    }

    @SuppressWarnings("notUsed")
    @Override protected void finish() {}

    public String getDownloadFolder() {
        return prefs.getString(Const.SONGS_FOLDER_CACHE_KEY, DEFAULT_SONGS_FOLDER);
    }
}
