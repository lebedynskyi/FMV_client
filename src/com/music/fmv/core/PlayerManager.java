package com.music.fmv.core;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import com.music.fmv.services.Player;
import com.music.fmv.services.PlayerService;

/**
 * User: vitaliylebedinskiy
 * Date: 8/12/13
 * Time: 12:07 PM
 */
public class PlayerManager extends Manager{
    private Player player;
    private SourceConnection connection;

    protected PlayerManager(Core coreManager){
        super(coreManager);
        connection = new SourceConnection();
        coreManager.getContext().bindService(new Intent(coreManager.getContext(), PlayerService.class),
                connection, Service.BIND_AUTO_CREATE);
    }

    @Override
    protected void finish() {
        core.getContext().unbindService(connection);
    }

    private class SourceConnection implements ServiceConnection{
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Bind b = (Bind)service;
            player = b.getService();
        }
        @Override public void onServiceDisconnected(ComponentName name) {}
    }

    public class Bind extends Binder{
        private PlayerService service;
        public void setService(PlayerService service){
            this.service = service;
        }

        public PlayerService getService() {
            return service;
        }
    }
}
