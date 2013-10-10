package com.music.fmv.core.managers;

import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import com.music.fmv.R;
import com.music.fmv.core.Core;

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

    public SettingsManager(Core coreManager) {
        super(coreManager);
        prefs = PreferenceManager.getDefaultSharedPreferences(coreManager.getContext());
    }

    public String getResultLanguage(){
        return prefs.getString(getString(R.string.DEFAULT_LAGUAGE_KEY), "ru");
    }

    public void setResultLanguage(String lan){
        puString(getString(R.string.DEFAULT_LAGUAGE_KEY), lan);
    }

    public String getImageCacheFolder() {
        return prefs.getString(getString(R.string.IMAGE_CACHE_FOLDER_KEY), DEFAULT_IMAGES_FOLDER);
    }

    public void setImageCacheFolder(String folder) {
        puString(getString(R.string.IMAGE_CACHE_FOLDER_KEY), folder);
    }

    private void puString(String key, String value){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value).commit();
    }

    @Override protected void finish() {}

    public String getDownloadFolder() {
        return prefs.getString(getString(R.string.DOWNLOAD_FOLDER_CACHE_KEY), DEFAULT_SONGS_FOLDER);
    }
}
