package com.music.fmv.services;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import com.music.fmv.core.Core;
import com.music.fmv.models.PlayableSong;

import java.util.ArrayList;


/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 7/14/13
 * Time: 8:29 AM
 * To change this template use File | Settings | File Templates.
 */

public class PlayerService extends IntentService implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnInfoListener {
    public static final String TRACK_KEY = "TRACK_KEY";
    public static final String LIST_KEY = "LIST_KEY";
    public static final String ACTION_KEY = "ACTION_KEY";

    private MediaPlayer mPlayer;
    private Core core;
//    private Handler mHandler;
    private ArrayList<PlayableSong> playerQueue;

    public enum PLAYER_STATUS{
        PLAYING, PAUSED, STOPPED, STARTED
    }

    public enum ACTION{
        PlAY, ADD, STOP
    }

    public PlayerService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        playerQueue = new ArrayList<PlayableSong>();
        mPlayer = new MediaPlayer();
        core = Core.getInstance();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ACTION act = (ACTION) intent.getExtras().getSerializable(ACTION_KEY);
        if (act == null) return;
        if (act == ACTION.STOP){
            onDestroy();
        }
    }

    @Override
    public void onDestroy() {
        releasePlayer();
        clearNotify();
        super.onDestroy();
    }

    //Clears all notifications linked with player
    private void clearNotify() {
        core.getNotificationManager().removeProgress(this);
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
