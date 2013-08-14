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
    public static final String DEFAULT_CACHE_FOLDER = "fmv_cache";
    private static final String DEFAULT_IMAGES_FOLDER = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + DEFAULT_CACHE_FOLDER + "/images/";
    private static final String DEFAULT_SONGS_FOLDER = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + DEFAULT_CACHE_FOLDER + "/music/";

    protected SettingsManager(Core coreManager) {
        super(coreManager);
    }

    public String getResultLanguage(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Const.LANGUAGE_CACHE_KEY, "ru");
    }

    public void setResultLanguage(Context context, String lan){
        puString(context, Const.LANGUAGE_CACHE_KEY, lan);
    }

    public static String getImageCacheFolder(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Const.IMAGE_CACHE_KEY, DEFAULT_IMAGES_FOLDER);
    }

    public static void setImageCacheFolder(Context context, String folder) {
        puString(context, Const.IMAGE_CACHE_KEY, folder);
    }

    private static void puString(Context c, String key, String value){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value).commit();
    }

    //not used
    @Override protected void finish() {}
}
