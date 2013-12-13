package com.music.fmv.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import com.music.fmv.core.Core;
import com.music.fmv.core.Player;
import com.music.fmv.core.PlayerManager;
import com.music.fmv.models.InternetSong;
import com.music.fmv.models.PlayAbleSong;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Vitalii Lebedynskyi
 * Date: 12/13/13
 * Time: 2:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class newPlayerService extends Service implements Player, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener{
    private final ArrayList<PlayAbleSong> queue = new ArrayList<PlayAbleSong>();
    private final ArrayList<PlayAbleSong> playedSongs = new ArrayList<PlayAbleSong>();

    private Handler handler;
    private Core core;

    private PlayAbleSong currentSong;
    private PlayerListener listener;
    private MediaPlayer currentMediaPayer;

    @Override
    public IBinder onBind(Intent intent) {
        PlayerManager.Bind binder = new PlayerManager.Bind();
//        binder.setService(this);
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        core = Core.getInstance(this);
        handler = new Handler();
    }

    @Override
    public void pause() {

    }

    @Override
    public void previous() {

    }

    @Override
    public void next() {

    }

    @Override
    public void setShuffle(boolean value) {

    }

    @Override
    public void setLoop(boolean value) {

    }

    @Override
    public boolean isShuffle() {
        return false;
    }

    @Override
    public boolean isLoop() {
        return false;
    }

    @Override
    public void seek(int position) {

    }

    @Override
    public void play(List<PlayAbleSong> songs, int position) {

    }

    @Override
    public void play(int position) {

    }

    @Override
    public void setPlayerListener(PlayerListener listener) {
        this.listener = listener;
    }

    @Override
    public void addSong(PlayAbleSong model) {

    }

    @Override
    public PlayerStatus getStatus() {
        return null;
    }

    @Override
    public PlayAbleSong getCurrentSong() {
        return currentSong;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        playedSongs.add(currentSong);
        next();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    private void playSong(PlayAbleSong song){
        if (TextUtils.isEmpty(song.getUrl()) && song instanceof InternetSong){
            playFromHTTp(song);
        }else {
            playFromPATH(song);
        }
    }

    private void playFromHTTp(PlayAbleSong song){

    }

    private void playFromPATH(PlayAbleSong song){

    }

    private void nextRandom(){
        if (queue.isEmpty() || queue.size() == playedSongs.size()) return;

        ArrayList<PlayAbleSong> temp = new ArrayList<PlayAbleSong>();
        for (PlayAbleSong s: queue){
            boolean needAdd = true;
            for (PlayAbleSong s1: playedSongs){
                if (s.equals(s1)){
                    needAdd = false;
                    break;
                }
            }
            if (needAdd) temp.add(s);
        }

        if (temp.isEmpty()) return;

        playSong(temp.get(new Random().nextInt(temp.size())));
    }

    private void releasePlayer(){
        if (currentMediaPayer != null){
            currentMediaPayer.stop();
            currentMediaPayer.release();
            currentMediaPayer = null;
        }
        core.getNotificationManager().removePlayer();
    }
}
