package com.music.fmv.core;

import android.app.ActivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import com.music.fmv.R;
import com.music.fmv.services.PlayerService;
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

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCoreManager = Core.getInstance();
        mMediator = new ActivityMediator(this);
        onCreated(savedInstanceState);
        ViewUtils.setUpKeyBoardHider(findViewById(android.R.id.content), this);
    }

    protected boolean runTask(AsyncTask task){
        if (NetworkUtil.isOnline(this)){
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

    protected abstract void onCreated(Bundle state);
}
