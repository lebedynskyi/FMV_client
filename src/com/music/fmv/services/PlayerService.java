package com.music.fmv.services;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 7/14/13
 * Time: 8:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class PlayerService extends IntentService{
    public PlayerService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
