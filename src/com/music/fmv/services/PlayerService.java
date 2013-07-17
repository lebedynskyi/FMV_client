package com.music.fmv.services;

import android.app.IntentService;
import android.content.Intent;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 7/14/13
 * Time: 8:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class PlayerService extends IntentService{
    private Set<PlayerListener> listeners = new HashSet<PlayerListener>();

    public enum PLAY_STATUS{
        PLAYING, PAUSED
    }

    public PlayerService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    public void addListener(PlayerListener listener){
        if (listeners.contains(listener)){
            listeners.remove(listener);
        }
        listeners.add(listener);
        notifyState();
    }

    private void notifyState() {

    }
}
