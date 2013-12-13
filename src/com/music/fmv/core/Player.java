package com.music.fmv.core;

import com.music.fmv.models.PlayAbleSong;

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
    public void setShuffle(boolean value);
    public void setLoop(boolean value);

    public boolean isShuffle();
    public boolean isLoop();

    public void seek(int position);
    public void play(List<PlayAbleSong> songs, int position);
    public void play(int position);

    public void setPlayerListener(PlayerListener listener);
    public void addSong(PlayAbleSong model);

    public PlayerStatus getStatus();
    public PlayAbleSong getCurrentSong();

    public interface PlayerListener {
        public void onNewSong(PlayAbleSong song);
        public void onActionApplied();
    }

    public class PlayerStatus {
        private int duration;
        private int currentProgress;
        private PlayAbleSong currentSong;
        private List<PlayAbleSong> currentQueue;
        private boolean isShuffle;
        private boolean isLoop;
        private boolean isPlaying;

        public PlayerStatus(int duration, int currentProgress, PlayAbleSong currentSong, List<PlayAbleSong> currentQueue, boolean shuffle, boolean loop, boolean playing) {
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

        public PlayAbleSong getCurrentSong() {
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

        public List<PlayAbleSong> getCurrentQueue() {
            return currentQueue;
        }
    }
}
