package com.music.fmv.core.managers;

import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import com.music.fmv.R;
import com.music.fmv.core.Core;

/**
 * Created with IntelliJ IDEA.
 * User: Vitalii Lebedynskyi
 * Date: 7/14/13
 * Time: 9:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class SettingsManager extends Manager {
    private static final String DEFAULT_CACHE_FOLDER = "FMV";
    private static final String DEFAULT_IMAGES_FOLDER = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + DEFAULT_CACHE_FOLDER + "/images/";
    private static final String DEFAULT_SONGS_FOLDER = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + DEFAULT_CACHE_FOLDER + "/music/";

    private SharedPreferences prefs;

    public SettingsManager(Core coreManager) {
        super(coreManager);
        prefs = PreferenceManager.getDefaultSharedPreferences(coreManager.getContext());
    }

    public String getResultLanguage() {
        return prefs.getString(getString(R.string.DEFAULT_LAGUAGE_KEY), "en");
    }

    public String getImageCacheFolder() {
        if (isUseOneFolder()){
            return prefs.getString(getString(R.string.DOWNLOAD_FOLDER_CACHE_KEY), DEFAULT_SONGS_FOLDER) + "/images/";
        }else return prefs.getString(getString(R.string.IMAGE_CACHE_FOLDER_KEY), DEFAULT_IMAGES_FOLDER);
    }

    private void puString(String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value).commit();
    }

    @Override protected void finish() {}

    public String getDownloadFolder() {
        return prefs.getString(getString(R.string.DOWNLOAD_FOLDER_CACHE_KEY), DEFAULT_SONGS_FOLDER);
    }


    public String getSongsFolder(){
        if (isUseOneFolder()){
            return prefs.getString(getString(R.string.DOWNLOAD_FOLDER_CACHE_KEY), DEFAULT_SONGS_FOLDER) + "/music/";
        }else return prefs.getString(getString(R.string.SONG_FOLDER_CACHE_KEY), DEFAULT_SONGS_FOLDER);
    }

    private boolean isUseOneFolder() {
        return prefs.getBoolean(getString(R.string.USE_ONE_FOlDER_KEY), true);
    }
}
