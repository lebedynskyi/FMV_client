package com.music.fmv.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import com.music.fmv.R;

/**
 * User: vitaliylebedinskiy
 * Date: 10/7/13
 * Time: 10:59 AM
 */
public class SettingsActivity extends PreferenceActivity{

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
        Intent chooserIntent = new Intent(this, FileChooserActivity.class);
        startActivityForResult(chooserIntent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
