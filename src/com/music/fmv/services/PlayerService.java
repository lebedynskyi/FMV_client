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
import com.music.fmv.core.BaseActivity;
import com.music.fmv.core.Core;
import com.music.fmv.models.PlayableSong;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 7/14/13
 * Time: 8:29 AM
 * To change this template use File | Settings | File Templates.
 */

public class PlayerService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnInfoListener {
    public static final String RECEIVER_ACTION = "RECEIVER_ACTION";
    public static final String ACTION_KEY = "ACTION_KEY";

    public static final String EXTRAS_SONGS_KEY = "EXTRAS_SONGS_KEY";

    private MediaPlayer mPlayer;
    private Core core;
    private ArrayList<PlayableSong> playerQueue;

    public enum PLAYER_STATUS {
        PLAYING, PAUSED, STOPPED, STARTED
    }

    public enum ACTION {
        PlAY, ADD, STOP, PAUSE, NEXT, PREV
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service created", Toast.LENGTH_SHORT).show();
        super.onCreate();
        playerQueue = new ArrayList<PlayableSong>();
        core = Core.getInstance(this);
        registerReceiver(receiver, new IntentFilter(RECEIVER_ACTION));
    }

    @Override
    public void onDestroy() {
        releasePlayer();
        clearNotify();
        unregisterReceiver(receiver);
        Toast.makeText(this, "Service destroyed", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //Clears all notifications linked with player
    private void clearNotify() {
        core.getNotificationManager().removePlayer(this);
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

    public void playSong(PlayableSong song){
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
        if (mPlayer == null) return;
        mPlayer.stop();
        mPlayer.release();
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
            String action = intent.getAction();
            if (TextUtils.isEmpty(action) || !action.equals(RECEIVER_ACTION) || intent.getExtras() == null) return;
            ACTION act = (ACTION) intent.getSerializableExtra(ACTION_KEY);

            if (act == null) return;

            Toast.makeText(context, act.name(), Toast.LENGTH_SHORT).show();

            switch (act){
                case PREV:
                    previous();
                    break;
                case PAUSE:
                    pause();
                    break;
                case NEXT:
                    next();
                    break;
                case ADD:
                    ArrayList<PlayableSong> songs = (ArrayList<PlayableSong>) intent.getSerializableExtra(EXTRAS_SONGS_KEY);
                    playerQueue.addAll(songs);
                    break;
                case PlAY:
                    PlayableSong song = (PlayableSong) intent.getSerializableExtra(EXTRAS_SONGS_KEY);
                    playerQueue.clear();
                    playerQueue.add(song);
                    playSong(song);
                    break;
                case STOP:
                    releasePlayer();
            }
        }
    };
}
