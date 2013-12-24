package com.music.fmv.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.text.TextUtils;
import android.widget.Toast;
import com.music.fmv.R;
import com.music.fmv.api.Api;
import com.music.fmv.core.Core;
import com.music.fmv.core.Player;
import com.music.fmv.core.PlayerManager;
import com.music.fmv.models.PlayAbleSong;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vitalii Lebedynskyi
 * Date: 7/14/13
 * Time: 8:29 AM
 * To change this template use File | Settings | File Templates.
 */

//TODO REFACTOR IT
public class PlayerService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, Player {
    private MediaPlayer mPlayer;
    private Core core;
    private final ArrayList<PlayAbleSong> playerQueue = new ArrayList<PlayAbleSong>();
    private PlayerListener playerListener;
    private PlayAbleSong currentSong;
    private boolean isShuffle;
    private int errorCount;

    //         SERVICE methods
    @Override
    public void onCreate() {
        super.onCreate();
        core = Core.getInstance(this);
    }

    @Override
    public void onDestroy() {
        releasePlayer();
        playerQueue.clear();
        clearNotify();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        PlayerManager.Bind binder = new PlayerManager.Bind();
//        binder.setService(this);
        return binder;
    }

    public void pause() {
        if (mPlayer == null) return;

        if (mPlayer.isPlaying()) {
            mPlayer.pause();
            core.getNotificationManager().removePlayer();
        } else {
            mPlayer.start();
            showNotification();
        }
        notifyStateCallback();
    }

    public void previous() {
        int curPosition = playerQueue.indexOf(currentSong);
        if (curPosition <= 0) return;
        playSong(playerQueue.get(curPosition - 1));
    }

    public void next() {
        if (isShuffle()){
            playNextSong();
        }else {
            playNextSong();
        }
    }

    @Override
    public void setShuffle(boolean v) {
        isShuffle = v;
        notifyStateCallback();
    }

    @Override
    public void seek(int value) {
        if (mPlayer != null) mPlayer.seekTo(value);
    }

    @Override
    public void setLoop(boolean v) {
        mPlayer.setLooping(v);
        notifyStateCallback();
    }

    @Override
    public void addSong(PlayAbleSong model) {
        if (mPlayer == null) {
            ArrayList<PlayAbleSong> ss = new ArrayList<PlayAbleSong>();
            ss.add(model);
            play(ss, 0);
            return;
        }

        playerQueue.add(model);
    }

    @Override
    public void play(List<PlayAbleSong> songs, int position) {
        if (songs == null || songs.size() == 0 || position >= songs.size()) {
            return;
        }
        playerQueue.clear();
        playerQueue.addAll(songs);
        playSong(songs.get(position));
    }

    @Override
    public void play(int position) {
        playSong(playerQueue.get(position));
    }
    //       Control buttons

    private void playNextRandomSong(){

    }

    @Override
    public void setPlayerListener(PlayerListener listener) {
        this.playerListener = listener;
    }

    private void playNextSong(){
        int curPosition = playerQueue.indexOf(currentSong);

        if (curPosition + 1 < playerQueue.size() && curPosition != -1) {
            playSong(playerQueue.get(curPosition + 1));
        } else {
            releasePlayer();
            clearNotify();
            notifyStateCallback();
        }
    }


    @Override
    public PlayAbleSong getCurrentSong() {
        return currentSong;
    }

    @Override
    public boolean isShuffle() {
        return isShuffle;
    }


    @Override
    public boolean isLoop() {
        return mPlayer != null && mPlayer.isLooping();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        releasePlayer();
        next();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        notifyNewSong(currentSong);
    }

    @Override
    public PlayerStatus getStatus() {
        int duration = 0;
        int currentPos = 0;
        boolean isPlaying = false;
        boolean isLoop = false;

        if (mPlayer != null) {
            duration = mPlayer.getDuration();
            currentPos = mPlayer.getCurrentPosition();
            isPlaying = mPlayer.isPlaying();
            isLoop = mPlayer.isLooping();
        }
        return new PlayerStatus(duration, currentPos, currentSong, playerQueue, isShuffle, isLoop, isPlaying);
    }

    private synchronized void playSong(final PlayAbleSong song) {
        if (song == null) return;
        if (!playerQueue.contains(song)) {
            playerQueue.add(song);
        }

        releasePlayer();
        currentSong = song;
        notifyNewSong(song);
        showNotification();
        if (core.getCacheManager().isSongExists(song)) {
            playFromPath(core.getCacheManager().getSongPath(song));
        } else playAsyncFromHttp(song);
    }

    private void playAsyncFromHttp(final PlayAbleSong song) {
        if (!TextUtils.isEmpty(song.getUrl())) {
            playFromPath(song.getUrl());
        } else {
            AsyncHttpRunner runner = new AsyncHttpRunner(song);
            runner.start();
        }
    }

    private void playFromPath(String source) {
        try {
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(this);
            mPlayer.setOnCompletionListener(this);
            mPlayer.setOnErrorListener(this);
            mPlayer.setDataSource(this, Uri.parse(source));
            mPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
            releasePlayer();
            clearNotify();
        }
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    //Clears all notifications linked with player
    private void clearNotify() {
        core.getNotificationManager().removePlayer();
    }

    private void showNotification() {
        if (currentSong != null) {
            core.getNotificationManager().notifyPlayer(currentSong.toString());
        }
    }

    private void notifyStateCallback() {
        if (playerListener != null) playerListener.onActionApplied();
    }

    private void notifyNewSong(PlayAbleSong song) {
        if (playerListener != null) playerListener.onNewSong(song);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        System.err.println("+++++++++++++++++++++++++++   ERROR  ++++++++++++++++++++++++++++");
        System.err.println("mp = [" + mp + "], what = [" + what + "], extra = [" + extra + "]");

        if (currentSong != null){
            Toast.makeText(this, getString(R.string.cannot_play_song) + " - " + currentSong.toString(), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, R.string.cannot_play_song, Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private class AsyncHttpRunner extends Thread {
        private PlayAbleSong song;

        private AsyncHttpRunner(PlayAbleSong song) {
            this.song = song;
        }

        @Override
        public void run() {
            try {
                String songUrl = new Api().getUrlOfSong(song);
                song.setUrl(songUrl);
                playFromPath(song.getUrl());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
