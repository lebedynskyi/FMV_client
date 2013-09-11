package com.music.fmv.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.music.fmv.R;
import com.music.fmv.core.BaseActivity;
import com.music.fmv.models.PlayableSong;
import com.music.fmv.services.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: vitaliylebedinskiy
 * Date: 8/1/13
 * Time: 10:33 AM
 */
public class PlayerActivity extends BaseActivity {
    public static final String FROM_NOTIFY_FLAG = "FROM_NOTIFY_FLAG";
    private static final SimpleDateFormat TIME_SD = new SimpleDateFormat("mm.ss");

    private View pausePlayBTN;
    private View shuffleBTN;
    private View loopBTN;

    private TextView songNameTV;
    private TextView songArtistTV;
    private TextView curProgressTV;
    private TextView durationTV;
    private SeekBar progressSlider;

    private ImageView songCover;
    private View imageContainer;

    private Player player;

    //TODO create selectors
    //background for buttons
    private Drawable pauseDrawable;
    private Drawable playDrawable;
    private Drawable loopNormDrawable;
    private Drawable loopActiveDrawable;
    private Drawable shuffleNormDrawable;
    private Drawable shuffleActiveDrawable;

    private RefreshTimer refresher;

    @Override
    protected void onCreated(Bundle state) {
        setContentView(R.layout.player_activity);
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        player = mCore.getPlayerManager().getPlayer();
        player.setPlayerListener(playerListener);
        refresher = new RefreshTimer(1 * 60 * 1000, 500);
        if (player != null) {
            playerListener.needRefreshControls();
            Player.PlayerStatus status = player.getStatus();
            if (status != null) {
                playerListener.onSongPlaying(status.getCurrentSong());
                progressSlider.setMax(status.getDuration());
                durationTV.setText(TIME_SD.format(new Date(status.getDuration())));
                curProgressTV.setText(TIME_SD.format(status.getCurrentProgress()));
                if (status.isPlaying()) {
                    pausePlayBTN.setBackgroundDrawable(pauseDrawable);
                } else {
                    pausePlayBTN.setBackgroundDrawable(playDrawable);
                }
            }
        }
        refreshProgress();
        refresher.start();
    }

    public void initViews() {
        shuffleBTN = findViewById(R.id.shuffle);
        View prev = findViewById(R.id.prev);
        pausePlayBTN = findViewById(R.id.play_pause);
        View next = findViewById(R.id.next);
        loopBTN = findViewById(R.id.loop);

        shuffleBTN.setOnClickListener(controlsListener);
        prev.setOnClickListener(controlsListener);
        pausePlayBTN.setOnClickListener(controlsListener);
        next.setOnClickListener(controlsListener);
        loopBTN.setOnClickListener(controlsListener);

        songCover = (ImageView) findViewById(R.id.song_image);
        imageContainer = findViewById(R.id.image_container);

        songNameTV = (TextView) findViewById(R.id.song_name);
        songArtistTV = (TextView) findViewById(R.id.song_owner);
        durationTV = (TextView) findViewById(R.id.full_time);
        curProgressTV = (TextView) findViewById(R.id.current_time);
        progressSlider = (SeekBar) findViewById(R.id.curr_progress);

        pauseDrawable = getResources().getDrawable(R.drawable.player_pause_selector);
        playDrawable = getResources().getDrawable(R.drawable.player_play_selector);
        loopNormDrawable = getResources().getDrawable(R.drawable.player_loop_selector);
        loopActiveDrawable = getResources().getDrawable(R.drawable.ic_audio_repeat_down);
        shuffleNormDrawable = getResources().getDrawable(R.drawable.player_shuffle_selector);
        shuffleActiveDrawable = getResources().getDrawable(R.drawable.ic_audio_shuffle_down);
        progressSlider.setOnSeekBarChangeListener(seekBarListener);
    }

    private void refreshProgress() {
        if (player == null) {
            return;
        }

        Player.PlayerStatus status = player.getStatus();
        if (status != null) {
            progressSlider.setProgress(status.getCurrentProgress());
        }
    }

    private void fillEmptyPlayer() {

    }

    @Override
    protected void onStop() {
        super.onStop();
        player.setPlayerListener(null);
        if (refresher != null) {
            refresher.cancel();
            refresher = null;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int width = imageContainer.getWidth();
        int height = imageContainer.getHeight();
        int minimum = width < height ? width : height;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(minimum, minimum);
        songCover.setLayoutParams(params);
    }

    private View.OnClickListener controlsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.next:
                    player.next();
                    break;
                case R.id.shuffle:
                    player.setShuffle(!player.isShuffle());
                    break;
                case R.id.play_pause:
                    player.pause();
                    break;
                case R.id.prev:
                    player.previous();
                    break;
                case R.id.loop:
                    player.setLoop(!player.isLoop());
            }
        }
    };

    private Player.PlayerListener playerListener = new Player.PlayerListener() {
        @Override
        public void needRefreshControls() {
            Player.PlayerStatus status = player.getStatus();
            if (status == null) return;
            if (status.isPlaying()) {
                pausePlayBTN.setBackgroundDrawable(pauseDrawable);
            } else pausePlayBTN.setBackgroundDrawable(playDrawable);

            if (status.isShuffle()) {
                shuffleBTN.setBackgroundDrawable(shuffleActiveDrawable);
            } else shuffleBTN.setBackgroundDrawable(shuffleNormDrawable);

            if (status.isLoop()) {
                loopBTN.setBackgroundDrawable(loopActiveDrawable);
            } else loopBTN.setBackgroundDrawable(loopNormDrawable);
        }

        @Override
        public void onSongPlaying(PlayableSong song) {
            if (song != null) {
                songNameTV.setText(song.getTitle());
                songArtistTV.setText(song.getArtist());
                pausePlayBTN.setBackgroundDrawable(pauseDrawable);
            }

            if (player != null) {
                Player.PlayerStatus status = player.getStatus();
                if (status != null) {
                    durationTV.setText(TIME_SD.format(status.getDuration()));
                    progressSlider.setMax(status.getDuration());
                }
            }
        }

        @Override
        public void onPlayingStopped() {
            pausePlayBTN.setBackgroundDrawable(playDrawable);
            progressSlider.setProgress(0);
        }

        @Override
        public void onBuffering(PlayableSong song, int cur, int max) {
            progressSlider.setSecondaryProgress(cur);
        }

        @Override
        public void bufferingFinished(PlayableSong song) {
            progressSlider.setSecondaryProgress(100);
        }
    };

    private class RefreshTimer extends CountDownTimer {
        public RefreshTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            refreshProgress();
        }

        @Override
        public void onFinish() {
            refresher = null;
            refresher = new RefreshTimer(1 * 60 * 1000, 250);
            refresher.start();
        }
    }

    private SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                player.seek(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
