package com.music.fmv.core;

import android.app.ActivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.music.fmv.R;
import com.music.fmv.services.PlayerService;
import com.music.fmv.utils.ActivityMediator;
import com.music.fmv.utils.NetworkUtil;
import com.music.fmv.utils.ViewUtils;
import com.smaato.soma.AdType;
import com.smaato.soma.BannerView;

/**
 * User: vitaliylebedinskiy
 * Date: 7/12/13
 * Time: 5:38 PM
 */
public abstract class BaseActivity extends FragmentActivity{
    protected Core mCore;
    protected ActivityMediator mMediator;
    protected Handler handler;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCore = Core.getInstance(this);
        mMediator = new ActivityMediator(this);
        handler = new Handler();
        onCreated(savedInstanceState);
        ViewUtils.setUpKeyBoardHider(findViewById(android.R.id.content), this);
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

    private void checkAdvert() {
        try {
            ViewGroup advertView = (ViewGroup)findViewById(R.id.advert_layout);
            if (advertView == null) return;
            if (NetworkUtil.isNetworkAvailable(this)){
                initAdvert(advertView);
            }else advertView.setVisibility(View.GONE);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initAdvert(ViewGroup advertView) {
        BannerView banner = new BannerView(this);
        banner.getAdSettings().setPublisherId(Const.ADS_PUBLISHER_ID);
        banner.getAdSettings().setAdspaceId(Const.ADS_BLOCK_ID);
        banner.getAdSettings().setBannerWidth(ViewUtils.getScrenWidth(this));
        banner.getAdSettings().setAdType(AdType.ALL);
        banner.asyncLoadNewBanner();
        banner.setAutoReloadFrequency(30000);
        advertView.addView(banner);
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

    protected abstract void onCreated(Bundle state);
}
