package com.music.fmv.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.music.fmv.R;
import com.music.fmv.core.BaseActivity;

/**
 * User: vitaliylebedinskiy
 * Date: 8/1/13
 * Time: 10:33 AM
 */
public class PlayerActivity extends BaseActivity{
    private Button prev;
    private Button pausePlay;
    private Button next;
    private TextView sngtitle;
    private ImageView songCover;

    @Override
    protected void onCreated(Bundle state) {
        setContentView(R.layout.player_activity);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //TODO Register as listener
    }

    @Override
    protected void onStop() {
        super.onStop();
        //TODO Unregister as listener
    }

    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

            }
        }
    };
}
