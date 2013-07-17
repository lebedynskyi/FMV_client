package com.music.fmv.services;

import com.music.fmv.models.PlayableSong;

/**
 * User: vitaliylebedinskiy
 * Date: 7/17/13
 * Time: 4:04 PM
 */
public interface PlayerListener {
    public void onSongStarted(PlayableSong song);
    public void onPaused();
    public void onFinished();
    public void onProgressChanged(PlayerService.PLAY_STATUS status, float cur, float max, int progress1, int progress2);
}
