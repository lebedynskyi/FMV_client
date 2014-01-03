package com.music.fms.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.widget.Toast;

import com.music.fms.R;
import com.music.fms.core.Core;
import com.music.fms.core.Player;
import com.music.fms.core.PlayerManager;
import com.music.fms.models.InternetSong;
import com.music.fms.models.PlayAbleSong;
import com.music.fms.utils.Log;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Vitalii Lebedynskyi
 * Date: 12/13/13
 * Time: 2:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class newPlayerService extends Service implements Player, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener{
    private final ArrayList<PlayAbleSong> queue = new ArrayList<PlayAbleSong>();
    private final ArrayList<PlayAbleSong> playedSongs = new ArrayList<PlayAbleSong>();

    private Handler handler;
    private Core core;

    private boolean isShuffle;

    private PlayAbleSong currentSong;
    private PlayerListener listener;
    private MediaPlayer currentMediaPayer;
    private PlayerUrlRetriever currentUrltask;

    @Override
    public IBinder onBind(Intent intent) {
        PlayerManager.Bind binder = new PlayerManager.Bind();
        binder.setService(this);
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
        if (currentMediaPayer != null) {
            if (currentMediaPayer.isPlaying()){
                currentMediaPayer.pause();
                core.getNotificationManager().removePlayer();
            }else {
                currentMediaPayer.start();
                core.getNotificationManager().notifyPlayer(currentSong.toString());
            }
        }
    }

    @Override
    public void previous() {
        PlayAbleSong song = null;
        for (PlayAbleSong playedSong : playedSongs) {
            song = playedSong;
        }
        playSong(song);
    }

    @Override
    public void next() {
        int curPosition = queue.indexOf(currentSong);
        if (curPosition == -1 || curPosition + 1 >= queue.size() || playedSongs.containsAll(queue)){
            return;
        }

        if (isShuffle()){
            nextRandom();
        }else {
            playSong(queue.get(curPosition + 1));
        }
    }

    @Override
    public void setShuffle(boolean value) {
        isShuffle = value;
        if (listener != null) listener.onActionApplied();
    }

    @Override
    public void setLoop(boolean value) {
        if (currentMediaPayer != null) currentMediaPayer.setLooping(value);
        if (listener != null) listener.onActionApplied();
    }

    @Override
    public boolean isShuffle() {
        return isShuffle;
    }

    @Override
    public boolean isLoop() {
        return currentMediaPayer != null && currentMediaPayer.isLooping();
    }

    @Override
    public void seek(int position) {
        if (currentMediaPayer != null) currentMediaPayer.seekTo(position);
    }

    @Override
    public void play(List<? extends PlayAbleSong> songs, int position) {
        queue.clear();
        queue.addAll(songs);
        playSong(queue.get(position));
    }

    @Override
    public void play(int position) {
        playSong(queue.get(position));
    }

    @Override
    public void setPlayerListener(PlayerListener listener) {
        this.listener = listener;
    }

    @Override
    public void addSong(PlayAbleSong model) {
        queue.add(model);
    }

    @Override
    public PlayerStatus getStatus() {
        int duration = 0;
        int currentProgress = 0;
        boolean isPlaying = false;
        boolean isLoop = false;

        if (currentMediaPayer != null) {
            duration = currentMediaPayer.getDuration();
            currentProgress = currentMediaPayer.getCurrentPosition();
            isPlaying = currentMediaPayer.isPlaying();
            isLoop = currentMediaPayer.isLooping();
        }
        return new PlayerStatus(duration, currentProgress, currentSong, queue, isShuffle, isLoop, isPlaying);
    }

    @Override
    public PlayAbleSong getCurrentSong() {
        return currentSong;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        playedSongs.add(currentSong);
        releasePlayer();
        next();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        releasePlayer();
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        currentMediaPayer = mp;
    }

    private void playSong(PlayAbleSong song){
        try {
            currentSong = song;
            core.getNotificationManager().notifyPlayer(currentSong.toString());

            if (TextUtils.isEmpty(song.getUrl()) && song instanceof InternetSong){
                if (core.getCacheManager().isSongExists(song)){
                    song.setUrl(core.getCacheManager().getSongPath(song));
                }else {
                    playFromHTTp(song);
                }
            }
            playFromPATH(song);
        }catch (Exception e){
            e.printStackTrace();
            releasePlayer();
        }
    }

    private void playFromHTTp(PlayAbleSong song){
        if (currentUrltask != null) currentUrltask.canceledByUser();

        currentUrltask = new PlayerUrlRetriever(this, song){
            @Override
            protected void onPostExecute(PlayAbleSong result) {
                currentUrltask = null;

                if (isCancelled()){
                    return;
                }

                if (isError){
                    releasePlayer();
                    return;
                }

                playSong(result);
            }
        };
    }

    private void playFromPATH(PlayAbleSong song) throws Exception{
        if (TextUtils.isEmpty(song.getUrl())) throw new Exception("Url is empty");

        MediaPlayer mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setOnPreparedListener(this);
        mp.setOnBufferingUpdateListener(this);
        mp.setOnCompletionListener(this);
        mp.setDataSource(this, Uri.parse(song.getUrl()));
        mp.prepareAsync();
    }

    private void nextRandom(){
        ArrayList<PlayAbleSong> tempSongs = new ArrayList<PlayAbleSong>(queue);
        tempSongs.removeAll(playedSongs);
        playSong(tempSongs.get(new Random().nextInt(tempSongs.size())));
    }

    private void releasePlayer(){
        if (currentMediaPayer != null){
            currentMediaPayer.stop();
            currentMediaPayer.release();
            currentMediaPayer = null;
        }
        currentSong = null;
        core.getNotificationManager().removePlayer();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        Log.e(getClass().getSimpleName(), "On buffering" + percent);
    }
}
