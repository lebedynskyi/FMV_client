package com.music.fmv.core;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;
import com.music.fmv.R;
import com.music.fmv.services.PlayerService;
import com.music.fmv.services.ServiceBus;
import com.music.fmv.utils.ActivityMediator;
import com.music.fmv.utils.NetworkUtil;
import com.music.fmv.utils.ViewUtils;

/**
 * User: vitaliylebedinskiy
 * Date: 7/12/13
 * Time: 5:38 PM
 */
public abstract class BaseActivity extends FragmentActivity{
    protected Core mCoreManager;
    protected ActivityMediator mMediator;
    private ServiceBus serviceBus;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCoreManager = Core.getInstance();
        mMediator = new ActivityMediator(this);
        onCreated(savedInstanceState);
        ViewUtils.setUpKeyBoardHider(findViewById(android.R.id.content), this);
        checkAdvert();
    }

    protected void onStart(){
        super.onStart();
        bindService(new Intent(this, PlayerService.class), mConnection, BIND_AUTO_CREATE);
    }

    private void checkAdvert() {
        View advertView = findViewById(R.id.advert_layout);
        if (advertView == null) return;
        if (NetworkUtil.isNetworkAvailable(this)){
            initAdvert(advertView);
        }else advertView.setVisibility(View.GONE);
    }

    private void initAdvert(View advertView) {

    }

    protected boolean runTask(AsyncTask task){
        if (NetworkUtil.isNetworkAvailable(this)){
            task.execute();
            return true;
        }
        Toast.makeText(this, getString(R.string.network_unavailable), Toast.LENGTH_SHORT).show();
        return false;
    }

    protected final boolean isPlayerRunned() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (PlayerService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            if (service instanceof ServiceBus){
                serviceBus = (ServiceBus)service;
                serviceBus.setActivity(BaseActivity.this);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {

        }
    };

    protected abstract void onCreated(Bundle state);

    @Override
    protected void onStop() {
        super.onStop();
        //TODO CHECK STATE. IF NOT PLAYING -> UNBIND
        if (serviceBus != null) {
            serviceBus.setActivity(null);
        }
        unbindService(mConnection);
    }
}
