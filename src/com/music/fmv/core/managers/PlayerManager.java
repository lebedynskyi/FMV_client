package com.music.fmv.core.managers;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import com.music.fmv.core.Core;
import com.music.fmv.services.Player;
import com.music.fmv.services.PlayerService;

/**
 * User: vitaliylebedinskiy
 * Date: 8/12/13
 * Time: 12:07 PM
 */
public class PlayerManager extends Manager {
    private Player player;
    private SourceConnection connection;
    private PostInitializationListener initListener;


    public PlayerManager(Core coreManager) {
        super(coreManager);
        connection = new SourceConnection();
        bindToPlayer();
    }

    public Player getPlayer(PostInitializationListener initializationListener) {
        if (player == null) {
            this.initListener = initializationListener;
            bindToPlayer();
        }
        return player;
    }

    @Override
    protected void finish() {
        core.getContext().unbindService(connection);
    }

    private class SourceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Bind b = (Bind) service;
            player = b.getService();
            if (initListener != null) initListener.onPlayerCreated(player);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            player = null;
        }
    }

    public static class Bind extends Binder {
        private PlayerService service;

        public void setService(PlayerService service) {
            this.service = service;
        }

        public PlayerService getService() {
            return service;
        }
    }

    private void bindToPlayer() {
        core.getContext().bindService(new Intent(core.getContext(), PlayerService.class), connection, Service.BIND_AUTO_CREATE);
    }

    public interface PostInitializationListener {
        public void onPlayerCreated(Player p);
    }
}
