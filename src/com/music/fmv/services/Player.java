package com.music.fmv.services;

import com.music.fmv.models.PlayableSong;

import java.util.List;

/**
 * User: vitaliylebedinskiy
 * Date: 8/12/13
 * Time: 12:16 PM
 */
public interface Player {
    public void pause();
    public void previous();
    public void next();
    public void stop();
    public void shuffle();
    public boolean isPlaying();
    public void loop();
    public boolean isLooping();
    public void setPlayerStatusListener(PlayerStatusListener listener);
    public boolean isShuffle();
    public void seek(int position);
    public int getProgress();
    public int getDuration();
    public void play(List<PlayableSong> songs, int position);
    public PlayableSong getCurrentSong();

    public interface PlayerStatusListener{
        public void onNewSong(PlayableSong song);
        public void onControllCallBack();
        public void onBuffering(int progress);
    }
}
