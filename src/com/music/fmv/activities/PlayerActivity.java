package com.music.fmv.activities;

import android.os.Bundle;
import com.music.fmv.R;
import com.music.fmv.core.BaseActivity;

/**
 * User: vitaliylebedinskiy
 * Date: 8/1/13
 * Time: 10:33 AM
 */
public class PlayerActivity extends BaseActivity{

    @Override
    protected void onCreated(Bundle state) {
        setContentView(R.layout.band_info_activity);
    }
}
