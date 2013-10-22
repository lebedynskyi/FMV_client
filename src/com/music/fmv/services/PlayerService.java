package com.music.fmv.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.text.TextUtils;
import com.music.fmv.api.Api;
import com.music.fmv.core.Core;
import com.music.fmv.core.managers.PlayerManager;
import com.music.fmv.models.notdbmodels.InternetSong;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 7/14/13
 * Time: 8:29 AM
 * To change this template use File | Settings | File Templates.
 */

public class PlayerService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, Player {

    private MediaPlayer mPlayer;
    private Core core;
    private final ArrayList<InternetSong> playerQueue = new ArrayList<InternetSong>();
    private PlayerListener playerListener;

    private Set<MediaPlayer> preparedPlayers = new HashSet<MediaPlayer>(2);
    private InternetSong currentSong;
    private boolean isShuffle;

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
        binder.setService(this);
        return binder;
    }

    public void pause() {
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
                core.getNotificationManager().removePlayer();
            } else {
                mPlayer.start();
                showNotification();
            }
        } else {
            playSong(currentSong);
        }
        notifyStateCallback();
    }

    public void previous() {
        int curPosition = playerQueue.indexOf(currentSong);
        if (curPosition <= 0) return;
        playSong(playerQueue.get(curPosition - 1));
    }

    public void next() {
        int curPosition = playerQueue.indexOf(currentSong);

        if (curPosition + 1 < playerQueue.size() && curPosition != -1) {
            playSong(playerQueue.get(curPosition + 1));
        } else {
            releasePlayer();
            clearNotify();
            notifyStateCallback();
        }
    }

    public void stop() {
        if (mPlayer != null) mPlayer.stop();
    }

    @Override
    public boolean isPlaying() {
        return mPlayer != null && mPlayer.isPlaying();
    }

    @Override
    public void setShuffle(boolean v) {
        isShuffle = v;
        notifyStateCallback();
    }

    @Override
    public void setPlayerListener(PlayerListener listener) {
        this.playerListener = listener;
    }

    @Override
    public void add(InternetSong model) {
        if (mPlayer == null) {
            ArrayList<InternetSong> ss = new ArrayList<InternetSong>();
            ss.add(model);
            play(ss, 0);
            return;
        }

        playerQueue.add(model);
    }

    @Override
    public boolean isShuffle() {
        return isShuffle;
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
        preparedPlayers.add(mp);
        mp.start();
        notifyNewSong(currentSong);
    }

    @Override
    public void play(List<InternetSong> songs, int position) {
        if (songs == null || songs.size() == 0 || position >= songs.size()) {
            return;
        }
        playerQueue.clear();
        playerQueue.addAll(songs);
        playSong(songs.get(position));
    }

    @Override
    public PlayerStatus getStatus() {
        int duration = 0;
        int currentPos = 0;
        boolean isPlaying = false;
        boolean isLoop = false;

        if (mPlayer != null && preparedPlayers.contains(mPlayer)) {
            duration = mPlayer.getDuration();
            currentPos = mPlayer.getCurrentPosition();
            isPlaying = mPlayer.isPlaying();
            isLoop = mPlayer.isLooping();
        }
        return new PlayerStatus(duration, currentPos, currentSong, playerQueue, isShuffle, isLoop, isPlaying);
    }

    private synchronized void playSong(final InternetSong song) {
        if (song == null) return;
        if (!playerQueue.contains(song)) {
            playerQueue.add(song);
        }

        releasePlayer();
        currentSong = song;
        notifyNewSong(song);
        showNotification();
        if (core.getCacheManager().isSongExists(song)) {
            playFromFile(song);
        } else playAsyncFromHttp(song);
    }

    private void playFromFile(InternetSong song) {
        playFromPath(core.getCacheManager().getSongPath(song));
    }

    private void playAsyncFromHttp(final InternetSong song) {
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
            preparedPlayers.remove(mPlayer);
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
            core.getNotificationManager().notifyPlayer(currentSong.getName(), currentSong.getArtist());
        }
    }

    private void notifyStateCallback() {
        if (playerListener != null) playerListener.needRefreshControls();
    }

    private void notifyNewSong(InternetSong song) {
        if (playerListener != null) playerListener.onSongPlaying(song);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        System.err.println("+++++++++++++++++++++++++++   ERROR  ++++++++++++++++++++++++++++");
        System.err.println("mp = [" + mp + "], what = [" + what + "], extra = [" + extra + "]");
        return false;
    }

    private class AsyncHttpRunner extends Thread {
        private InternetSong song;

        private AsyncHttpRunner(InternetSong song) {
            this.song = song;
        }

        @Override
        public void run() {
            try {
                String songUrl = new Api().getUrlOfSong(song.getUrlForUrl(), song.getUrlKey());
                if (!TextUtils.isEmpty(songUrl)) {
                    song.setUrl(songUrl);
                    core.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            playFromPath(song.getUrl());
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
