package com.music.fms.activities;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import com.music.fms.R;
import com.music.fms.core.BaseActivity;

/**
 * User: vitaliylebedinskiy
 * Date: 7/12/13
 * Time: 5:55 PM
 */

public class BandInfoActivity extends BaseActivity {
    public static final String BAND_KEY = "BAND_KEY";

    private LinearLayout similarContainer;
    private LinearLayout topSongs;
    private LinearLayout topAlbums;

    @Override
    protected void onCreated(Bundle state) {
        setContentView(R.layout.band_info_activity);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
}
