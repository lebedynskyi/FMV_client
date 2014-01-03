package com.music.fms.activities;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.widget.Toast;
import com.music.fms.R;
import com.music.fms.core.Core;

import java.io.File;

/**
 * User: vitaliylebedinskiy
 * Date: 10/7/13
 * Time: 10:59 AM
 */
public class SettingsActivity extends PreferenceActivity {
    private static final int DOWNLOAD_FOLDER_REQUEST = 1522;
    private static final int SONGS_FOLDER_REQUEST = 1523;
    private static final int IMAGES_FOLDER_REQUEST = 1524;
    private static final int ALBUMS_FOLDER_REQUEST = 1525;

    private FileChooserCallback fileChooserCallback;

    private PreferenceCategory storageCategory;
    private Preference downloadFolderPref;
    private Preference songsFolderPref;
    private Preference albumsFolderPref;
    private Preference imagesFolderPref;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        initPrefs();
        prefs = getPreferenceManager().getSharedPreferences();
    }

    private void initPrefs() {
        storageCategory = (PreferenceCategory) findPreference("pref_key_storage_settings");
        downloadFolderPref = findPreference(getString(R.string.DOWNLOAD_FOLDER_CACHE_KEY));
        songsFolderPref = findPreference(getString(R.string.SONG_FOLDER_CACHE_KEY));
        albumsFolderPref = findPreference(getString(R.string.ALBUMS_CACHE_FOLDER_KEY));
        imagesFolderPref = findPreference(getString(R.string.IMAGE_CACHE_FOLDER_KEY));

        downloadFolderPref.setOnPreferenceClickListener(new FileChooserListener(DOWNLOAD_FOLDER_REQUEST));
        songsFolderPref.setOnPreferenceClickListener(new FileChooserListener(SONGS_FOLDER_REQUEST));
        albumsFolderPref.setOnPreferenceClickListener(new FileChooserListener(ALBUMS_FOLDER_REQUEST));
        imagesFolderPref.setOnPreferenceClickListener(new FileChooserListener(IMAGES_FOLDER_REQUEST));

        CheckBoxPreference useOneFolderCheck = (CheckBoxPreference) findPreference(getString(R.string.USE_ONE_FOlDER_KEY));
        useOneFolderCheck.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                applyAppearForStorrageSettings((Boolean) newValue);
                return true;
            }
        });

        applyAppearForStorrageSettings(getPreferenceManager().getSharedPreferences().getBoolean(getString(R.string.USE_ONE_FOlDER_KEY), false));
        refreshDescriptions();
    }

    private void refreshDescriptions() {
        downloadFolderPref.setSummary(prepareDescr(R.string.pref_download_folder_descr, Core.getInstance(this).getSettingsManager().getDownloadFolder()));
        songsFolderPref.setSummary(prepareDescr(R.string.pref_song_folder_descr, Core.getInstance(this).getSettingsManager().getSongsFolder()));
        albumsFolderPref.setSummary(prepareDescr(R.string.pref_album_folder_descr, Core.getInstance(this).getSettingsManager().getAlbumsFolder()));
        imagesFolderPref.setSummary(prepareDescr(R.string.pref_image_folder_descr, Core.getInstance(this).getSettingsManager().getImageCacheFolder()));
    }

    public void startChooserActivity(int requestCode) {
        if (fileChooserCallback != null) fileChooserCallback.onFileChooserClicked(requestCode);
    }

    public void setFileChooserCallback(FileChooserCallback fileChooserCallback) {
        this.fileChooserCallback = fileChooserCallback;
    }

    public void onFilePicked(int requestCode, Uri fileUri) {
        Toast.makeText(this, fileUri.toString(), Toast.LENGTH_SHORT).show();
    }

    public void onFilePicked(int requestCode, File file) {
        if (requestCode == DOWNLOAD_FOLDER_REQUEST){
            prefs.edit().putString(getString(R.string.DOWNLOAD_FOLDER_CACHE_KEY), file.getAbsolutePath() + "/").commit();
        }else if (requestCode == SONGS_FOLDER_REQUEST){
            prefs.edit().putString(getString(R.string.SONG_FOLDER_CACHE_KEY), file.getAbsolutePath() + "/").commit();
        }else if (requestCode == IMAGES_FOLDER_REQUEST){
            prefs.edit().putString(getString(R.string.IMAGE_CACHE_FOLDER_KEY), file.getAbsolutePath() + "/").commit();
        }else if (requestCode == ALBUMS_FOLDER_REQUEST){
            prefs.edit().putString(getString(R.string.ALBUMS_CACHE_FOLDER_KEY), file.getAbsolutePath() + "/").commit();
        }
        refreshDescriptions();
    }

    public static interface FileChooserCallback {
        public void onFileChooserClicked(int reqCode);
    }

    private void applyAppearForStorrageSettings(boolean isOneDownload) {
        if (isOneDownload) {
            storageCategory.removePreference(songsFolderPref);
            storageCategory.removePreference(albumsFolderPref);
            storageCategory.removePreference(imagesFolderPref);
            storageCategory.addPreference(downloadFolderPref);
        } else {
            storageCategory.removePreference(downloadFolderPref);
            storageCategory.addPreference(songsFolderPref);
            storageCategory.addPreference(albumsFolderPref);
            storageCategory.addPreference(imagesFolderPref);
        }
    }

    private class FileChooserListener implements Preference.OnPreferenceClickListener {
        private int requestCode;

        private FileChooserListener(int requestCode) {
            this.requestCode = requestCode;
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            startChooserActivity(requestCode);
            return true;
        }
    }

    private String prepareDescr(int stringID, String part2){
        StringBuilder sb = new StringBuilder();
        sb.append(getString(stringID)).append("\n").append(getString(R.string.current)).append(part2);
        return sb.toString();
    }
}
