package com.music.fmv.services;

import android.os.Binder;
import com.music.fmv.core.BaseActivity;

/**
 * User: vitaliylebedinskiy
 * Date: 8/1/13
 * Time: 2:53 PM
 */
public class ServiceBus extends Binder{
    private PlayerService player;
    private BaseActivity activity;

    public PlayerService getPlayer() {
        return player;
    }

    public void setPlayer(PlayerService player) {
        this.player = player;
    }

    public BaseActivity getActivity() {
        return activity;
    }

    public void setActivity(BaseActivity activity) {
        this.activity = activity;
    }
}
