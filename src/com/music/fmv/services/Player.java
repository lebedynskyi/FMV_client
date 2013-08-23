package com.music.fmv.services;

import com.music.fmv.models.PlayableSong;

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
    public void play(PlayableSong song);
    public void shuffle();
    public void setPlayerStatusListener(PlayerStatusListener listener);

    void loop();

    public interface PlayerStatusListener{
        public void onStatus();
    }
}
