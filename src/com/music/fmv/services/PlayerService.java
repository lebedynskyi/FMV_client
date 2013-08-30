package com.music.fmv.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.text.TextUtils;
import android.widget.Toast;
import com.music.fmv.R;
import com.music.fmv.api.Api;
import com.music.fmv.core.Core;
import com.music.fmv.core.managers.PlayerManager;
import com.music.fmv.models.PlayableSong;

import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 7/14/13
 * Time: 8:29 AM
 * To change this template use File | Settings | File Templates.
 */

public class PlayerService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnInfoListener, Player {

    private MediaPlayer mPlayer;
    private Core core;
    private final ArrayList<PlayableSong> playerQueue = new ArrayList<PlayableSong>();
    private PlayerStatusListener statusListener;

    private PlayableSong currentSong;
    private boolean isShuffle;

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Player created", Toast.LENGTH_SHORT).show();
        core = Core.getInstance(this);
    }

    @Override
    public void onDestroy() {
        releasePlayer();
        playerQueue.clear();
        clearNotify();
        Toast.makeText(this, "Player destroyed", Toast.LENGTH_SHORT).show();
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
        int newPosition = curPosition + 1;

        if (newPosition >= playerQueue.size() || curPosition == -1) {
            core.getNotificationManager().removePlayer();
        } else {
            playSong(playerQueue.get(newPosition));
        }
    }

    public void stop() {
        if (mPlayer != null) mPlayer.stop();
    }

    @Override
    public void shuffle() {
        isShuffle = !isShuffle;
        notifyStateCallback();
    }

    @Override
    public void setPlayerStatusListener(PlayerStatusListener listener) {
        this.statusListener = listener;
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
    public int getProgress() {
        if (mPlayer != null) return mPlayer.getCurrentPosition();
        return 0;
    }

    @Override
    public int getDuration() {
        if (mPlayer != null) return mPlayer.getDuration();
        return 0;
    }

    @Override
    public boolean isPlaying() {
        return mPlayer != null && mPlayer.isPlaying();
    }

    @Override
    public void loop() {
        mPlayer.setLooping(!mPlayer.isLooping());
        notifyStateCallback();
    }

    //Players listeners
    @Override
    public void onCompletion(MediaPlayer mp) {
        next();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        notifyNewSong(currentSong);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        if (statusListener != null) statusListener.onBuffering(percent);
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public boolean isLooping() {
        return mPlayer != null && mPlayer.isLooping();
    }

    @Override
    public void play(List<PlayableSong> songs, int position) {
        if (songs == null || songs.size() == 0 || position >= songs.size()) return;
        playerQueue.clear();
        releasePlayer();
        playerQueue.addAll(songs);
        playSong(songs.get(position));
    }

    @Override
    public PlayableSong getCurrentSong() {
        return currentSong;
    }

    private synchronized void playSong(final PlayableSong song) {
        if (statusListener != null) statusListener.onNewSong(song);
        currentSong = song;
        notifyNewSong(song);
        showNotification();
        if (core.getCacheManager().isSongExists(song)) {
            playFromFile(song);
        } else playFromHttp(song);
    }

    private void playFromFile(PlayableSong song) {
        playFromPath(core.getCacheManager().getSongPath(song));
    }

    private void playFromHttp(final PlayableSong song) {
        final Api api = new Api();
        if (TextUtils.isEmpty(song.getUrl())) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final String url = api.getUrlOfSong(song.getId());
                        song.setUrl(url);
                        core.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                playFromPath(url);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            playFromPath(song.getUrl());
        }
    }

    private void playFromPath(String path) {
        try {
            releasePlayer();
            mPlayer = new MediaPlayer();
            mPlayer.setDataSource(path);
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnInfoListener(this);
            mPlayer.setOnBufferingUpdateListener(this);
            mPlayer.setOnPreparedListener(this);
            mPlayer.setOnCompletionListener(this);
            mPlayer.prepareAsync();
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.cannot_play_song), Toast.LENGTH_SHORT).show();
        }
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
        }
        mPlayer = null;
    }

    //Clears all notifications linked with player
    private void clearNotify() {
        core.getNotificationManager().removePlayer();
    }

    private void showNotification() {
        if (currentSong != null) {
            core.getNotificationManager().notifyPlayer(currentSong.getTitle(), currentSong.getArtist());
        }
    }

    private void notifyStateCallback() {
        if (statusListener != null) statusListener.onControllCallBack();
    }

    private void notifyNewSong(PlayableSong song) {
        if (statusListener != null) statusListener.onNewSong(song);
    }
}
