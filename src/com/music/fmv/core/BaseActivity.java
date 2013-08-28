package com.music.fmv.core;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import com.google.analytics.tracking.android.*;
import com.music.fmv.R;
import com.music.fmv.models.Captcha;
import com.music.fmv.utils.ActivityMediator;
import com.music.fmv.utils.NetworkUtil;
import com.music.fmv.utils.ViewUtils;
import com.music.fmv.widgets.CaptchaDialog;
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
    protected Tracker tracker;

    @Override
    protected final void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mCore = Core.getInstance(this);
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

    public void sendScreenCount(String screenName){
        tracker.set(Fields.SCREEN_NAME, screenName + " -> " + this.getClass().getName());
        tracker.send(MapBuilder.createAppView().build());
    }

    @Override
    protected void onStop() {
        super.onStop();
        tracker.set(Fields.SESSION_CONTROL, "end");
        EasyTracker.getInstance(this).activityStop(this);
    }

    private void checkAdvert() {
        try {
            ViewGroup advertView = (ViewGroup)findViewById(R.id.advert_layout);
            if (advertView == null) return;
            if (NetworkUtil.isNetworkAvailable(this) && false){
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
        mCore.showToast(R.string.network_unavailable);
        return false;
    }

    public void showCaptchaDialog(Captcha c, CaptchaDialog.CaptchaCallBack callBack){
        CaptchaDialog dialog = new CaptchaDialog();
        dialog.setCaptcha(c);
        dialog.setCaptchaCallBack(callBack);
        dialog.show(getSupportFragmentManager(), c.getUrl());
    }
    protected abstract void onCreated(Bundle state);
}
