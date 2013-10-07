package com.music.fmv.fragments;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import com.music.fmv.activities.SettingsActivity;
import com.music.fmv.core.BaseFragment;

/**
 * User: vitaliylebedinskiy
 * Date: 7/22/13
 * Time: 12:07 PM
 */
public class SettingsFragment extends BaseFragment{
    private LocalActivityManager mLocalActivityManager;

    @Override
    protected View createView(Bundle savedInstanceState) {
        mLocalActivityManager = new LocalActivityManager(baseActivity, true);
        mLocalActivityManager.dispatchCreate(savedInstanceState);
        Intent prefActivityIntent = new Intent(baseActivity, SettingsActivity.class);
        Window w = mLocalActivityManager.startActivity("tag", prefActivityIntent);
        mainView = w.getDecorView();
        return mainView;
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
        super.onActivityResult(requestCode, resultCode, data);
    }
}
