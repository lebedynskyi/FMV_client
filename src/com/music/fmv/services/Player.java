package com.music.fmv.services;

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
    public void setPlayerStatusListener(PlayerStatusListener listener);

    public interface PlayerStatusListener{
        public void onStatus();
    }
}
