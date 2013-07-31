package com.music.fmv.services;

import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;
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

public class PlayerService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
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
//
//    public PlayerService(String name) {
//        super(name);
//    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service created", Toast.LENGTH_SHORT).show();
        super.onCreate();
        playerQueue = new ArrayList<PlayableSong>();
        mPlayer = new MediaPlayer();
        core = Core.getInstance();
        registerReceiver(receiver, new IntentFilter("test"));
    }

    @Override
    public void onDestroy() {
        releasePlayer();
        clearNotify();
        unregisterReceiver(receiver);
        Toast.makeText(this, "Service destroyed", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
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

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(PlayerService.this, "ON RECEIVE", Toast.LENGTH_SHORT).show();
//            System.out.print("dasdasdasdasdasdasdas");
        }
    };
}
