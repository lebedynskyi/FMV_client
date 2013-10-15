package com.music.fmv.activities;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.widget.Toast;
import com.music.fmv.R;

import java.io.File;

/**
 * User: vitaliylebedinskiy
 * Date: 10/7/13
 * Time: 10:59 AM
 */
public class SettingsActivity extends PreferenceActivity {
    private final int DOWNLOAD_FOLDER_REQUEST = 1522;
    private final int SONGS_FOLDER_REQUEST = 1523;
    private final int IMAGES_FOLDER_REQUEST = 1524;
    private final int ALBUMS_FOLDER_REQUEST = 1525;

    private FileChooserCallback fileChooserCallback;

    private PreferenceCategory storageCategory;
    private Preference downloadFodlerPref;
    private Preference songsFodlerPref;
    private Preference albumsFodlerPref;
    private Preference imagesFodlerPref;

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
        downloadFodlerPref = findPreference(getString(R.string.DOWNLOAD_FOLDER_CACHE_KEY));
        songsFodlerPref = findPreference(getString(R.string.SONG_FOLDER_CACHE_KEY));
        albumsFodlerPref = findPreference(getString(R.string.ALBUMS_CACHE_FOLDER_KEY));
        imagesFodlerPref = findPreference(getString(R.string.IMAGE_CACHE_FOLDER_KEY));

        downloadFodlerPref.setOnPreferenceClickListener(new FileChooserListener(DOWNLOAD_FOLDER_REQUEST));
        songsFodlerPref.setOnPreferenceClickListener(new FileChooserListener(SONGS_FOLDER_REQUEST));
        albumsFodlerPref.setOnPreferenceClickListener(new FileChooserListener(ALBUMS_FOLDER_REQUEST));
        imagesFodlerPref.setOnPreferenceClickListener(new FileChooserListener(IMAGES_FOLDER_REQUEST));

        CheckBoxPreference useOneFolderCheck = (CheckBoxPreference) findPreference(getString(R.string.USE_ONE_FOlDER_KEY));
        applyAppearForStorrageSettings(useOneFolderCheck.getPreferenceManager().getSharedPreferences().getBoolean("use_one_folder", false));

        useOneFolderCheck.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                applyAppearForStorrageSettings((Boolean) newValue);
                return true;
            }
        });
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
        Toast.makeText(this, file.getAbsolutePath(), Toast.LENGTH_SHORT).show();

        if (requestCode == DOWNLOAD_FOLDER_REQUEST){
            prefs.edit().putString(getString(R.string.DOWNLOAD_FOLDER_CACHE_KEY), file.getAbsolutePath()).commit();
        }else if (requestCode == SONGS_FOLDER_REQUEST){
            prefs.edit().putString(getString(R.string.SONG_FOLDER_CACHE_KEY), file.getAbsolutePath() + "/music/").commit();
        }else if (requestCode == IMAGES_FOLDER_REQUEST){
            prefs.edit().putString(getString(R.string.IMAGE_CACHE_FOLDER_KEY), file.getAbsolutePath() + "/images/").commit();
        }else if (requestCode == ALBUMS_FOLDER_REQUEST){
            prefs.edit().putString(getString(R.string.ALBUMS_CACHE_FOLDER_KEY), file.getAbsolutePath() + "/albums/").commit();
        }
    }

    public static interface FileChooserCallback {
        public void onFileChooserClicked(int reqCode);
    }

    private void applyAppearForStorrageSettings(boolean isOneDownload) {
        if (isOneDownload) {
            storageCategory.removePreference(songsFodlerPref);
            storageCategory.removePreference(albumsFodlerPref);
            storageCategory.removePreference(imagesFodlerPref);
            storageCategory.addPreference(downloadFodlerPref);
        } else {
            storageCategory.removePreference(downloadFodlerPref);
            storageCategory.addPreference(songsFodlerPref);
            storageCategory.addPreference(albumsFodlerPref);
            storageCategory.addPreference(imagesFodlerPref);
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
}
