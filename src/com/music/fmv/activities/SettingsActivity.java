package com.music.fmv.activities;

import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.Toast;
import com.music.fmv.R;

/**
 * User: vitaliylebedinskiy
 * Date: 10/7/13
 * Time: 10:59 AM
 */
public class SettingsActivity extends PreferenceActivity{
    private FileChooserCallback fileChooserCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        Preference mainFolderChooser = findPreference("songs_fodler");
        mainFolderChooser.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startChooserActivity(150);
                return false;
            }
        });
    }

    public void startChooserActivity(int requestCode){
        if(fileChooserCallback != null) fileChooserCallback.onFileChooserClicked(requestCode);
    }

    public void setFileChooserCallback(FileChooserCallback fileChooserCallback) {
        this.fileChooserCallback = fileChooserCallback;
    }

    public void onFilePicked(int requestCode, Uri fileUri) {
        Toast.makeText(this, fileUri.toString(), Toast.LENGTH_SHORT).show();
    }

    public static interface FileChooserCallback{
        public void onFileChooserClicked(int reqCode);
    }
}
