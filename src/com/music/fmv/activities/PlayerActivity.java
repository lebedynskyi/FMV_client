package com.music.fmv.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.music.fmv.R;
import com.music.fmv.core.BaseActivity;
import com.music.fmv.core.managers.PlayerManager;
import com.music.fmv.models.notdbmodels.PlayableSong;
import com.music.fmv.services.Player;
import com.music.fmv.widgets.FixedSlidingTray;

import java.text.SimpleDateFormat;

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
    //backgrounds for buttons
    private Drawable pauseDrawable;
    private Drawable playDrawable;
    private Drawable loopNormDrawable;
    private Drawable loopActiveDrawable;
    private Drawable shuffleNormDrawable;
    private Drawable shuffleActiveDrawable;

    private RefreshTimer refresher;
    private boolean fromNitification;
    private View backBTN;

    @Override
    protected void onCreated(Bundle state) {
        setContentView(R.layout.player_activity);
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        playerListener.needRefreshControls();

        refreshProgress();
        refresher = new RefreshTimer(1 * 60 * 1000, 500);
        refresher.start();
        player = mCore.getPlayerManager().getPlayer(initListener);

        fromNitification = getIntent().getIntExtra(FROM_NOTIFY_FLAG, -1) != -1;

        if (player == null) return;
        player.setPlayerListener(playerListener);
        Player.PlayerStatus status = player.getStatus();
        if (status != null) {
            playerListener.onSongPlaying(status.getCurrentSong());
        }
    }

    private void initPlayList() {
        if (player != null) {
            Player.PlayerStatus status = player.getStatus();
            if (status == null) return;

        }
    }

    public void backClicked(View v) {
        if (fromNitification) {
            mMediator.startMain();
            finish();
        }
        super.onBackPressed();
    }

    public void playListClicked(View v) {

    }

    public void initViews() {
        shuffleBTN = findViewById(R.id.shuffle);
        pausePlayBTN = findViewById(R.id.play_pause);
        loopBTN = findViewById(R.id.loop);
        shuffleBTN.setOnClickListener(controlsListener);
        pausePlayBTN.setOnClickListener(controlsListener);
        loopBTN.setOnClickListener(controlsListener);

        View prev = findViewById(R.id.prev);
        View next = findViewById(R.id.next);
        prev.setOnClickListener(controlsListener);
        next.setOnClickListener(controlsListener);

        songCover = (ImageView) findViewById(R.id.song_image);
        imageContainer = findViewById(R.id.image_container);

        songNameTV = (TextView) findViewById(R.id.song_name);
        songArtistTV = (TextView) findViewById(R.id.song_owner);
        durationTV = (TextView) findViewById(R.id.full_time);
        curProgressTV = (TextView) findViewById(R.id.current_time);
        progressSlider = (SeekBar) findViewById(R.id.curr_progress);
        progressSlider.setOnSeekBarChangeListener(seekBarListener);

        pauseDrawable = getResources().getDrawable(R.drawable.player_pause_selector);
        playDrawable = getResources().getDrawable(R.drawable.player_play_selector);
        loopNormDrawable = getResources().getDrawable(R.drawable.player_loop_selector);
        loopActiveDrawable = getResources().getDrawable(R.drawable.ic_audio_repeat_down);
        shuffleNormDrawable = getResources().getDrawable(R.drawable.player_shuffle_selector);
        shuffleActiveDrawable = getResources().getDrawable(R.drawable.ic_audio_shuffle_down);

        View handle = findViewById(R.id.handle);
        final FixedSlidingTray tray = (FixedSlidingTray) findViewById(R.id.drawer);
        handle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tray.isOpened()) {
                    tray.animateClose();
                } else tray.animateOpen();
            }
        });
    }

    private void refreshProgress() {
        if (player == null) return;
        Player.PlayerStatus status = player.getStatus();
        if (status != null) {
            progressSlider.setProgress(status.getCurrentProgress());
            curProgressTV.setText(TIME_SD.format(status.getCurrentProgress()));
        }
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
            if (player == null) return;
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
                songNameTV.setText(song.getName());
                songArtistTV.setText(song.getArtist());
                pausePlayBTN.setBackgroundDrawable(pauseDrawable);
                startImageTask();
            }

            Player.PlayerStatus status = player.getStatus();
            if (status != null) {
                durationTV.setText(TIME_SD.format(status.getDuration()));
                progressSlider.setMax(status.getDuration());
            }
        }

        @Override
        public void onPlayingStopped() {
            pausePlayBTN.setBackgroundDrawable(playDrawable);
            progressSlider.setProgress(0);
        }
    };

    private void startImageTask() {
        //TODO implemented
    }

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


    private PlayerManager.PostInitializationListener initListener = new PlayerManager.PostInitializationListener() {
        @Override
        public void onPlayerCreated(Player p) {
            PlayerActivity.this.player = p;
            player = mCore.getPlayerManager().getPlayer(this);
            if (player == null) {
                return;
            }

            player.setPlayerListener(playerListener);
            Player.PlayerStatus status = player.getStatus();
            if (status != null) {
                playerListener.onSongPlaying(status.getCurrentSong());
            }

            playerListener.needRefreshControls();
            refreshProgress();
        }
    };
}
