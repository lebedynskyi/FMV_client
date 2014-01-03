package com.music.fms.core;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.analytics.tracking.android.*;
import com.music.fms.R;
import com.music.fms.network.Network;
import com.music.fms.utils.ActivityMediator;
import com.music.fms.utils.ViewUtils;

/**
 * User: vitaliylebedinskiy
 * Date: 7/12/13
 * Time: 5:38 PM
 */
public abstract class BaseActivity extends ActionBarActivity {
    protected Core mCore;
    protected ActivityMediator mMediator;
    protected Handler handler;
    protected Tracker tracker;
    private ActionBar actionBar;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getSupportActionBar();
        mCore = Core.getInstance(this);
        hideActionBar();
        mMediator = new ActivityMediator(this);
        handler = new Handler();
        tracker = GoogleAnalytics.getInstance(this).getTracker("UA-43469464-1");
        onCreated(savedInstanceState);
        ViewUtils.setUpKeyBoardHider(findViewById(android.R.id.content), this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        tracker.set(Fields.SESSION_CONTROL, "start");
        EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkAdvert();
            }
        }, 500);
    }

    @Override
    public void setTitle(int titleId) {
        setTitle(getString(titleId));
    }

    @Override
    public void setTitle(CharSequence title) {
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    public void sendScreenStatistic(String screenName) {
        tracker.set(Fields.SCREEN_NAME, screenName + " -> " + this.getClass().getName());
        tracker.send(MapBuilder.createAppView().build());
    }

    public void hideActionBar() {
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    public void showActionBar() {
        if (actionBar != null) {
            actionBar.show();
        }
    }

    public void showToast(int id){
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
    }

    public void showToast(String ss){
        Toast.makeText(this, ss, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        tracker.set(Fields.SESSION_CONTROL, "end");
        EasyTracker.getInstance(this).activityStop(this);
    }

    private void checkAdvert() {
        try {
            ViewGroup advertView = (ViewGroup) findViewById(R.id.advert_layout);
            if (advertView == null) return;

            if (Network.isNetworkAvailable(this)) {
                initAdvert(advertView);
            } else advertView.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAdvert(ViewGroup advertView) {

    }

    protected boolean runTask(AsyncTask task) {
        if (Network.isNetworkAvailable(this)) {
            task.execute();
            return true;
        }
        showToast(R.string.network_unavailable);
        return false;
    }

    protected abstract void onCreated(Bundle state);
}
