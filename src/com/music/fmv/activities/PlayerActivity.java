package com.music.fmv.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.music.fmv.R;
import com.music.fmv.core.BaseActivity;
import com.music.fmv.core.PlayerManager;
import com.music.fmv.core.Player;
import com.music.fmv.utils.TimeUtils;
import com.music.fmv.widgets.PlayerSliding;

/**
 * User: Vitalii Lebedynskyi
 * Date: 8/1/13
 * Time: 10:33 AM
 */
public class PlayerActivity extends BaseActivity  implements Player.PlayerListener{
    public static final String FROM_NOTIFY_FLAG = "FROM_NOTIFY_FLAG";

    private TextView songNameTV;
    private TextView songArtistTV;
    private TextView curProgressTV;
    private TextView durationTV;

    private SeekBar progressSlider;
    private ImageView songCover;
    private PlayerSliding sliding;

    private Player player;

    private RefreshTimer refresher;

    private boolean fromNitification;
    private View pausePlayBTN;
    private View shuffleBTN;
    private View loopBTN;

    @Override
    protected void onCreated(Bundle state) {
        setContentView(R.layout.audio_player_activity);
        fromNitification = getIntent().getBooleanExtra(FROM_NOTIFY_FLAG, false);

        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCore.getPlayerManager().getPlayer(playerRetrieverListener);
        if (refresher != null){
            refresher.cancel();
        }
        refresher = new RefreshTimer(1 * 60 * 1000, 250);
        refresher.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player != null){
            player.setPlayerListener(null);
        }

        if (refresher != null){
            refresher.cancel();
            refresher = null;
        }
    }

    public void initViews() {
        shuffleBTN = findViewById(R.id.shuffle);
        pausePlayBTN = findViewById(R.id.play_pause);
        loopBTN = findViewById(R.id.loop);
        View prev = findViewById(R.id.prev);
        View next = findViewById(R.id.next);

        shuffleBTN.setOnClickListener(controlsListener);
        pausePlayBTN.setOnClickListener(controlsListener);
        loopBTN.setOnClickListener(controlsListener);
        prev.setOnClickListener(controlsListener);
        next.setOnClickListener(controlsListener);

        songCover = (ImageView) findViewById(R.id.cover_image);
        songCover.setImageDrawable(getResources().getDrawable(R.drawable.empty_cover_image));

        songNameTV = (TextView) findViewById(R.id.aplayer_title);
        songArtistTV = (TextView) findViewById(R.id.aplayer_artist);
        durationTV = (TextView) findViewById(R.id.full_time);
        curProgressTV = (TextView) findViewById(R.id.current_time);
        progressSlider = (SeekBar) findViewById(R.id.curr_progress);
        progressSlider.setOnSeekBarChangeListener(seekBarListener);
        sliding = (PlayerSliding) findViewById(R.id.drawer);

        sliding.getHandle().findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sliding.isOpen()){
                    sliding.close();
                }sliding.open();
            }
        });
    }

    private void refreshProgress() {
        if (player == null) return;
        Player.PlayerStatus status = player.getStatus();

        if (status != null){
            durationTV.setText(TimeUtils.extractTimeFromSong(status.getDuration()));
            curProgressTV.setText(TimeUtils.extractTimeFromSong(status.getCurrentProgress()));
            progressSlider.setProgress(status.getCurrentProgress());
        }else {
            durationTV.setText("0.0");
            curProgressTV.setText("0.0");
            progressSlider.setProgress(0);
        }
    }

    @Override
    public void onActionApplied() {
        if (player == null) return;
        Player.PlayerStatus status = player.getStatus();

        if (status == null || !status.isPlaying()){
            pausePlayBTN.setSelected(false);
        }else pausePlayBTN.setSelected(true);

        if (status == null || !status.isLoop()){
            loopBTN.setSelected(false);
        }else loopBTN.setSelected(true);

        if (status == null || !status.isShuffle()){
            shuffleBTN.setSelected(false);
        }else shuffleBTN.setSelected(true);
    }

    @Override
    public void onNewSong() {
        if (player == null) return;
        Player.PlayerStatus st = player.getStatus();
        if (st == null || st.getCurrentSong() == null) return;

        songNameTV.setText(st.getCurrentSong().getName());
        songArtistTV.setText(st.getCurrentSong().getArtist());
        progressSlider.setMax(st.getCurrentSong().getDuration());
        onActionApplied();
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
        private boolean blocked = false;
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser && !blocked) {
                player.seek(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            blocked = true;

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            blocked = false;
        }
    };


    private PlayerManager.PostInitializationListener playerRetrieverListener = new PlayerManager.PostInitializationListener() {
        @Override
        public void onPlayerAvailable(Player p) {
            if (p != null){
                player = p;
                p.setPlayerListener(PlayerActivity.this);
            }
            onActionApplied();
            onNewSong();
        }
    };

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
}
