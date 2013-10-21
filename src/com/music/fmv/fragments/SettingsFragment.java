package com.music.fmv.fragments;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import com.music.fmv.activities.FileChooserActivity;
import com.music.fmv.activities.SettingsActivity;
import com.music.fmv.core.BaseFragment;

import java.io.File;

/**
 * User: Vitalii Lebedynskyi
 * Date: 7/22/13
 * Time: 12:07 PM
 */
public class SettingsFragment extends BaseFragment {
    private static final String ACTIVITY_TAG = "ACTIVITY_TAG";

    private LocalActivityManager mLocalActivityManager;
    private SettingsActivity settingsActivity;

    @Override
    protected View createView(Bundle savedInstanceState) {
        mLocalActivityManager = new LocalActivityManager(baseActivity, true);
        mLocalActivityManager.dispatchCreate(savedInstanceState);
        Intent prefActivityIntent = new Intent(baseActivity, SettingsActivity.class);
        Window w = mLocalActivityManager.startActivity(ACTIVITY_TAG, prefActivityIntent);
        settingsActivity = (SettingsActivity) mLocalActivityManager.getActivity(ACTIVITY_TAG);
        settingsActivity.setFileChooserCallback(fileChooserCallback);
        View v  = w.getDecorView();
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        mLocalActivityManager.dispatchPause(false);
    }

    @Override
    public void onStop() {
        super.onStop();
        mLocalActivityManager.dispatchStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mLocalActivityManager.dispatchResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLocalActivityManager.dispatchDestroy(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) return;

        settingsActivity.onFilePicked(requestCode, (File) data.getSerializableExtra(FileChooserActivity.RESULT_FILE));
    }

    private SettingsActivity.FileChooserCallback fileChooserCallback = new SettingsActivity.FileChooserCallback() {
        @Override
        public void onFileChooserClicked(int requestCode) {
            Intent chooserIntent = new Intent(baseActivity, FileChooserActivity.class);
            startActivityForResult(chooserIntent, requestCode);
        }
    };
}
