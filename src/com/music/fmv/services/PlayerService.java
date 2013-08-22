package com.music.fmv.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.text.TextUtils;
import android.widget.Toast;
import com.music.fmv.R;
import com.music.fmv.core.Core;
import com.music.fmv.core.managers.PlayerManager;
import com.music.fmv.models.PlayableSong;

import java.util.ArrayList;


/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 7/14/13
 * Time: 8:29 AM
 * To change this template use File | Settings | File Templates.
 */

public class PlayerService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnInfoListener, Player {
    public static final String RECEIVER_ACTION = "RECEIVER_ACTION";
    public static final String ACTION_KEY = "ACTION_KEY";

    private MediaPlayer mPlayer;
    private Core core;
    private ArrayList<PlayableSong> playerQueue;
    private PlayerStatusListener statusListener;

    public enum PLAYER_STATUS {
        PLAYING, PAUSED, STOPPED, STARTED
    }

    public enum NOTIFICATION_ACTIONS {
        STOP, PAUSE, NEXT, PREV
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Player created", Toast.LENGTH_SHORT).show();
        super.onCreate();
        playerQueue = new ArrayList<PlayableSong>();
        core = Core.getInstance(this);
        registerReceiver(receiver, new IntentFilter(RECEIVER_ACTION));

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                Toast.makeText(PlayerService.this, "Exception in PLAYEr SERVICe!! BLEa", Toast.LENGTH_SHORT).show();
                stopSelf();
            }
        });
    }

    @Override
    public void onDestroy() {
        releasePlayer();
        clearNotify();
        unregisterReceiver(receiver);
        Toast.makeText(this, "Player destroyed", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        PlayerManager.Bind binder = new PlayerManager.Bind();
        binder.setService(this);
        return binder;
    }

    //Clears all notifications linked with player
    private void clearNotify() {
        core.getNotificationManager().removePlayer();
    }

    private void notifyState() {

    }

    public void pause() {

    }

    public void previous() {

    }

    public void next() {

    }

    public void stop() {

    }

    @Override
    public void setPlayerStatusListener(PlayerStatusListener listener) {
        this.statusListener = listener;
    }

    private void playSong(PlayableSong song){
        try {
            releasePlayer();
            mPlayer = new MediaPlayer();
            mPlayer.setDataSource(song.getUrl());
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnInfoListener(this);
            mPlayer.setOnBufferingUpdateListener(this);
            mPlayer.setOnPreparedListener(this);
            mPlayer.setOnCompletionListener(this);
        }catch (Exception e){
            Toast.makeText(this, getString(R.string.cannot_play_song) + " " + song.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void releasePlayer() {
        playerQueue.clear();
        if (mPlayer != null){
            mPlayer.stop();
            mPlayer.release();
        }
    }


    //Players listeners
    @Override
    public void onCompletion(MediaPlayer mp) {
        notifyState();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        notifyState();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        notifyState();
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String actionStr = intent.getAction();
            if (TextUtils.isEmpty(actionStr) || !actionStr.equals(RECEIVER_ACTION) || intent.getExtras() == null) return;

            Object act = intent.getSerializableExtra(ACTION_KEY);
            if (act == null) return;

            NOTIFICATION_ACTIONS action = (NOTIFICATION_ACTIONS) act;

            Toast.makeText(context, action.name(), Toast.LENGTH_SHORT).show();

            switch (action){
                case PREV:
                    previous();
                    break;
                case PAUSE:
                    pause();
                    break;
                case NEXT:
                    next();
                    break;
                case STOP:
                    stop();
            }
        }
    };
}
