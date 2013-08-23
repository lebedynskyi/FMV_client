package com.music.fmv.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.music.fmv.R;
import com.music.fmv.core.BaseActivity;
import com.music.fmv.models.PlayableSong;
import com.music.fmv.services.Player;

/**
 * User: vitaliylebedinskiy
 * Date: 8/1/13
 * Time: 10:33 AM
 */
public class PlayerActivity extends BaseActivity{

    private View prev;
    private View pausePlay;
    private View next;
    private View shuffle;
    private View loop;

    private TextView songName;
    private TextView songOwner;
    private TextView fullTime;
    private TextView currentTime;
    private ProgressBar currProgress;

    private ImageView songCover;
    private View imageContainer;

    @Override
    protected void onCreated(Bundle state) {
        setContentView(R.layout.player_activity);
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        connectToPlayer();
    }

    public void initViews(){
        shuffle = findViewById(R.id.shuffle);
        prev = findViewById(R.id.prev);
        pausePlay = findViewById(R.id.play_pause);
        next = findViewById(R.id.next);
        loop = findViewById(R.id.loop);

        songCover = (ImageView) findViewById(R.id.song_image);
        imageContainer = findViewById(R.id.image_container);

        songName = (TextView) findViewById(R.id.song_name);
        songOwner = (TextView) findViewById(R.id.song_owner);
        fullTime = (TextView) findViewById(R.id.full_time);
        currentTime = (TextView) findViewById(R.id.curr_time);
        currProgress = (ProgressBar) findViewById(R.id.curr_progress);
    }

    public void connectToPlayer(){
        mCore.getPlayerManager().setPlayerStatusListener(statusListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCore.getPlayerManager().setPlayerStatusListener(null);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int width = imageContainer.getWidth();
        int height = imageContainer.getHeight();
        int minimum = width < height ? width: height;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(minimum, minimum);
        songCover.setLayoutParams(params);
    }

    private View.OnClickListener controlsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.next:
                    mCore.getPlayerManager().next();
                    break;
                case R.id.shuffle:
                    mCore.getPlayerManager().shuffle();
                    break;
                case R.id.play_pause:
                    mCore.getPlayerManager().pause();
                    break;
                case R.id.prev:
                    mCore.getPlayerManager().previous();
                    break;
                case R.id.loop:
                    mCore.getPlayerManager().loop();
            }
        }
    };

    private Player.PlayerStatusListener statusListener = new Player.PlayerStatusListener() {
        @Override
        public void onStatus() {

        }
    };
}
