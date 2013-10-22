package com.music.fmv.services;

import com.music.fmv.models.notdbmodels.InternetSong;

import java.util.List;

/**
 * User: Vitalii Lebedynskyi
 * Date: 8/12/13
 * Time: 12:16 PM
 */
public interface Player {
    public void pause();

    public void previous();

    public void next();

    public void stop();

    public boolean isPlaying();

    public void setShuffle(boolean value);

    public boolean isShuffle();

    public void setLoop(boolean value);

    public boolean isLoop();

    public void seek(int position);

    public void play(List<InternetSong> songs, int position);

    public PlayerStatus getStatus();

    public void setPlayerListener(PlayerListener listener);

    public void add(InternetSong model);

    public interface PlayerListener {
        public void onSongPlaying(InternetSong song);

        public void onPlayingStopped();

        public void needRefreshControls();
    }

    public class PlayerStatus {
        private int duration;
        private int currentProgress;
        private InternetSong currentSong;
        private List<InternetSong> currentQueue;
        private boolean isShuffle;
        private boolean isLoop;
        private boolean isPlaying;

        public PlayerStatus(int duration, int currentProgress, InternetSong currentSong, List<InternetSong> currentQueue, boolean shuffle, boolean loop, boolean playing) {
            this.duration = duration;
            this.currentProgress = currentProgress;
            this.currentSong = currentSong;
            this.currentQueue = currentQueue;
            this.isShuffle = shuffle;
            this.isLoop = loop;
            this.isPlaying = playing;
        }

        public int getDuration() {
            return duration;
        }

        public int getCurrentProgress() {
            return currentProgress;
        }

        public InternetSong getCurrentSong() {
            return currentSong;
        }

        public boolean isShuffle() {
            return isShuffle;
        }

        public boolean isLoop() {
            return isLoop;
        }

        public boolean isPlaying() {
            return isPlaying;
        }

        public List<InternetSong> getCurrentQueue() {
            return currentQueue;
        }
    }
}
