package com.music.fmv.services;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;


/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 7/14/13
 * Time: 8:29 AM
 * To change this template use File | Settings | File Templates.
 */

public class PlayerService extends IntentService implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnInfoListener {
    private MediaPlayer mPlayer;
    private Handler mHandler;

    public enum PLAYER_STATUS{
        PLAYING, PAUSED, STOPPED, STARTED
    }

    public PlayerService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public void onDestroy() {
        releasePlayer();
        super.onDestroy();
    }

    private void notifyState() {

    }

    public void pause(){

    }

    public void previous(){

    }

    public void next(){

    }

    public void stop(){

    }

    public void playSong(){

    }

    public void playByHttp(){

    }

    private void releasePlayer(){

    }

    //Players listeners
    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        notifyState();
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }
}
