package com.music.fmv.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.music.fmv.R;
import com.music.fmv.adapters.PlayerListAdapter;
import com.music.fmv.core.BaseActivity;
import com.music.fmv.core.Player;
import com.music.fmv.core.PlayerManager;
import com.music.fmv.views.GlowButton;
import com.music.fmv.widgets.PlayerSliding;
import com.nineoldandroids.animation.ObjectAnimator;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: Vitalii Lebedynskyi
 * Date: 8/1/13
 * Time: 10:33 AM
 */
public class PlayerActivity extends BaseActivity  implements Player.PlayerListener{
    public static final String FROM_NOTIFY_FLAG = "FROM_NOTIFY_FLAG";
    private static final SimpleDateFormat TIME_SD = new SimpleDateFormat("mm.ss");

    private boolean progressBlocked = false;

    private TextView songNameTV;
    private TextView songArtistTV;
    private TextView curProgressTV;
    private TextView durationTV;

    private SeekBar progressSlider;
    private ImageView songCover;
    private PlayerSliding playerSlider;
    private View backBTN;
    private GlowButton playListBTN;

    private Player player;

    private RefreshTimer refresher;

    private boolean fromNitification;
    private View pausePlayBTN;
    private View shuffleBTN;
    private View loopBTN;
    private PlayerListAdapter playListAdater;

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

        playerSlider = (PlayerSliding) findViewById(R.id.drawer);
        playerSlider.setListener(sliderListener);
        backBTN = playerSlider.getHandle().findViewById(R.id.back_btn);
        playListBTN = (GlowButton) playerSlider.getHandle().findViewById(R.id.show_playlist_btn);

        playListBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerSlider.isOpen()) {
                    playListBTN.setText(getResources().getString(R.string.show_playlist));
                    playerSlider.close();
                } else {
                    playListBTN.setText(getResources().getString(R.string.hide_playlist));
                    playerSlider.open();
                }
            }
        });

        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromNitification) {
                    mMediator.startMain();
                } else onBackPressed();
            }
        });
    }

    private void refreshProgress() {
        if (player == null) return;
        Player.PlayerStatus status = player.getStatus();

        String curTime = "0.0";
        String fulTime = "0.0";
        int progress = 0;

        if (status != null){
            fulTime= TIME_SD.format(new Date(status.getDuration()));
            curTime = TIME_SD.format(new Date(status.getCurrentProgress()));
            progress = status.getCurrentProgress();
        }

        durationTV.setText(fulTime);
        curProgressTV.setText(curTime);
        if (!progressBlocked){
            progressSlider.setProgress(progress);
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
        public int newProgress;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                this.newProgress = progress;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            progressBlocked = true;
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (player != null){
                player.seek(newProgress);
            }
            progressBlocked = false;
        }
    };


    private PlayerManager.PostInitializationListener playerRetrieverListener = new PlayerManager.PostInitializationListener() {
        @Override
        public void onPlayerAvailable(Player p) {
            if (p != null){
                player = p;
                p.setPlayerListener(PlayerActivity.this);
                preparePlayList(p);
            }
            onActionApplied();
            onNewSong();
        }
    };

    private void preparePlayList(Player p) {
        Player.PlayerStatus st = p.getStatus();
        if (st == null) return;

        ListView lv = (ListView) playerSlider.findViewById(R.id.content_c);
        playListAdater = new PlayerListAdapter(st.getCurrentQueue(), this);
        lv.setAdapter(playListAdater);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                player.play(position);
                playListAdater.notifyDataSetChanged();
            }
        });
    }

    private View.OnClickListener controlsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(playerSlider.isOpen()) return;

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

    private PlayerSliding.SliderListener sliderListener = new PlayerSliding.SliderListener() {
        @Override
        public void onOpened() {
            ObjectAnimator playeListOpener = ObjectAnimator.ofFloat(playListBTN, "translationX", backBTN.getMeasuredWidth() / -2);
            playeListOpener.setDuration(200);
            playeListOpener.start();

            ObjectAnimator backOpener = ObjectAnimator.ofFloat(backBTN, "translationX", -backBTN.getMeasuredWidth());
            backOpener.setDuration(50);
            backOpener.start();
        }

        @Override
        public void onClose() {
            ObjectAnimator playeListOpener = ObjectAnimator.ofFloat(playListBTN, "translationX", 0);
            playeListOpener.setDuration(50);
            playeListOpener.start();

            ObjectAnimator backOpener = ObjectAnimator.ofFloat(backBTN, "translationX", 0);
            backOpener.setDuration(200);
            backOpener.start();
        }
    };
}
