package com.music.fmv.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.text.TextUtils;
import android.widget.Toast;
import com.music.fmv.core.Core;
import com.music.fmv.core.managers.PlayerManager;
import com.music.fmv.models.PlayableSong;
import com.music.fmv.tasks.threads.IDownloadListener;

import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 7/14/13
 * Time: 8:29 AM
 * To change this template use File | Settings | File Templates.
 */

public class PlayerService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, Player {

    private MediaPlayer mPlayer;
    private Core core;
    private final ArrayList<PlayableSong> playerQueue = new ArrayList<PlayableSong>();
    private PlayerListener playerListener;

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
    public void play(List<PlayableSong> songs, int position) {
        if (songs == null || songs.size() == 0 || position >= songs.size()) return;
        playerQueue.clear();
        releasePlayer();
        playerQueue.addAll(songs);
        playSong(songs.get(position));
    }

    @Override
    public PlayerStatus getStatus() {
        if (mPlayer == null) return null;
        return new PlayerStatus(mPlayer.getDuration(), mPlayer.getCurrentPosition(), currentSong,
                playerQueue, isShuffle, mPlayer.isLooping(), mPlayer.isPlaying());
    }

    private synchronized void playSong(final PlayableSong song) {
        currentSong = song;
        notifyNewSong(song);
        showNotification();
        if (core.getCacheManager().isSongExists(song)) {
            playFromFile(song);
        } else playAsyncFromHttp(song);
    }

    private void playFromFile(PlayableSong song) {
        playFromSource(core.getCacheManager().getSongPath(song));
    }

    //TODO redesign for FileDescritor
    private void playAsyncFromHttp(final PlayableSong song) {
        if (!TextUtils.isEmpty(song.getUrl())) {
            playFromSource(song.getUrl());
            return;
        }
        AsyncHttpRunner runner = new AsyncHttpRunner(song);
        runner.start();
    }

    private void playFromSource(Object source) {
        try {
            releasePlayer();
            mPlayer = new MediaPlayer();

            if (source instanceof String) {
                mPlayer.setDataSource((String) source);
            } else if (source instanceof FileDescriptor) {
                mPlayer.setDataSource((FileDescriptor) source);
            } else throw new RuntimeException("Unknown type of source");

            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(this);
            mPlayer.setOnCompletionListener(this);
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
            core.getNotificationManager().notifyPlayer(currentSong.getTitle(), currentSong.getArtist());
        }
    }

    private void notifyStateCallback() {
        if (playerListener != null) playerListener.needRefreshControls();
    }

    private void notifyNewSong(PlayableSong song) {
        if (playerListener != null) playerListener.onSongPlaying(song);
    }


    private class AsyncHttpRunner extends Thread{
        private PlayableSong song;
        private boolean needIgnore;

        private AsyncHttpRunner(PlayableSong song) {
            this.song = song;
        }

        @Override
        public void run() {
            try {
                core.getDownloadManager().download(song, downloadListener);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private IDownloadListener downloadListener = new IDownloadListener() {
            @Override
            public void onDownload(String name, final int cur, final int max) {
                if (playerListener != null) {
                    core.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            playerListener.onBuffering(song, cur, max);
                        }
                    });
                }
            }

            @Override
            public void onDownloadFinished() {
                if (playerListener != null) {
                    core.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            playerListener.bufferingFinished(song);
                        }
                    });
                }
            }

            @Override public void onError(String name) {}
        };
    }
}
