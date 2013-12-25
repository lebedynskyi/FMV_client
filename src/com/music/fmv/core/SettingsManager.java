package com.music.fmv.core;

import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import com.music.fmv.R;

/**
 * Created with IntelliJ IDEA.
 * User: Vitalii Lebedynskyi
 * Date: 7/14/13
 * Time: 9:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class SettingsManager extends Manager {
    private final String DEFAULT_CACHE_FOLDER    = "FMV";
    private final String DEFAULT_DOWNLOAD_FOLDER = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + DEFAULT_CACHE_FOLDER + "/";
    private final String DEFAULT_IMAGES_FOLDER   = DEFAULT_DOWNLOAD_FOLDER + "images/";
    private final String DEFAULT_SONGS_FOLDER    = DEFAULT_DOWNLOAD_FOLDER + "songs/";
    private final String DEFAULT_ALBUMS_FOLDER   = DEFAULT_DOWNLOAD_FOLDER + "albums/";

    private SharedPreferences prefs;

    SettingsManager(Core coreManager) {
        super(coreManager);
        prefs = PreferenceManager.getDefaultSharedPreferences(coreManager.getContext());
    }

    public String getResultLanguage() {
        return prefs.getString(getString(R.string.DEFAULT_LAGUAGE_KEY), "en");
    }

    public String getDownloadFolder() {
        return prefs.getString(getString(R.string.DOWNLOAD_FOLDER_CACHE_KEY), DEFAULT_DOWNLOAD_FOLDER);
    }

    private boolean isUseOneFolder() {
        return prefs.getBoolean(getString(R.string.USE_ONE_FOlDER_KEY), true);
    }

    public String getSongsFolder(){
        if (isUseOneFolder()){
            return getDownloadFolder() + "songs/";
        }else {
            return prefs.getString(getString(R.string.SONG_FOLDER_CACHE_KEY), DEFAULT_SONGS_FOLDER);
        }
    }

    public String getAlbumsFolder(){
        if (isUseOneFolder()){
            return getDownloadFolder() + "albums/";
        }else {
            return prefs.getString(getString(R.string.ALBUMS_CACHE_FOLDER_KEY), DEFAULT_ALBUMS_FOLDER);
        }
    }

    public String getImageCacheFolder() {
        if (isUseOneFolder()){
            return getDownloadFolder() + "images/";
        }else {
            return prefs.getString(getString(R.string.IMAGE_CACHE_FOLDER_KEY), DEFAULT_IMAGES_FOLDER);
        }
    }

    @Override protected void finish() {}
}
