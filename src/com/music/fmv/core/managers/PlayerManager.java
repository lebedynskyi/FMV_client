package com.music.fmv.core.managers;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import com.music.fmv.core.Core;
import com.music.fmv.models.PlayableSong;
import com.music.fmv.services.Player;
import com.music.fmv.services.PlayerService;

/**
 * User: vitaliylebedinskiy
 * Date: 8/12/13
 * Time: 12:07 PM
 */
public class PlayerManager extends Manager implements Player{
    private Player player;
    private SourceConnection connection;


    public PlayerManager(Core coreManager){
        super(coreManager);
        connection = new SourceConnection();
        bindToPlayer();
    }

    @Override
    protected void finish() {
        core.getContext().unbindService(connection);
    }

    public boolean isShuffle() {
        return false;
    }

    private class SourceConnection implements ServiceConnection{
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Bind b = (Bind)service;
            player = b.getService();
        }
        @Override public void onServiceDisconnected(ComponentName name) {
            player = null;
        }
    }

    public static class Bind extends Binder{
        private PlayerService service;
        public void setService(PlayerService service){
            this.service = service;
        }

        public PlayerService getService() {
            return service;
        }
    }

    @Override
    public void pause() {
        if (player == null) bindToPlayer();
        player.pause();
    }

    @Override
    public void previous() {
        if (player == null) bindToPlayer();
        player.previous();
    }

    @Override
    public void next() {
        if (player == null) bindToPlayer();
        player.next();
    }

    @Override
    public void stop() {
        if (player == null) bindToPlayer();
        player.stop();
    }

    @Override
    public void play(PlayableSong song) {
        if (player == null) bindToPlayer();
        player.play(song);
    }

    @Override
    public void shuffle() {

    }

    @Override
    public void setPlayerStatusListener(PlayerStatusListener listener) {

    }

    @Override
    public void loop() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private void bindToPlayer(){
        core.getContext().bindService(new Intent(core.getContext(), PlayerService.class),connection,Service.BIND_AUTO_CREATE);
    }
}
