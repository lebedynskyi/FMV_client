package com.music.fmv.core;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;
import com.music.fmv.R;
import com.music.fmv.models.PlayableSong;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * User: vitaliylebedinskiy
 * Date: 7/12/13
 * Time: 5:46 PM
 */
public final class Core {
    public static final PlayableSong TEST_SONG = new PlayableSong();

    private static Core instance;
    private final Context app;
    private Handler handler;

    private NotifyManager mNotificationManager;
    private CacheManager mCacheManager ;
    private SettingsManager mSettingsManager;
    private DisplayImageOptions notCachedOptions;
    private DownloadManager downloadManager;

    public static Core getInstance(Context app) {
        if (instance == null){
            synchronized (Core.class){
                if(instance == null){
                    instance = new Core(app);
                }
            }
        }
        return instance;
    }

    private Core(Context app){
        if (app == null) throw new IllegalArgumentException("Context cannot be null");
        this.app = app;
        handler = new Handler();
        mNotificationManager = new NotifyManager(this);
        mCacheManager = new CacheManager(this);
        mSettingsManager = new SettingsManager(this);
        downloadManager = new DownloadManager(this);
    }

    public NotifyManager getNotificationManager() {
        return mNotificationManager;
    }

    public CacheManager getCacheManager() {
        return mCacheManager;
    }

    public SettingsManager getSettingsManager(){
        return mSettingsManager;
    }

    public DisplayImageOptions getNotCachedOptions(){
        if (notCachedOptions == null){
            notCachedOptions = new DisplayImageOptions.Builder()
                    .showStubImage(R.drawable.default_artist_medium)
                    .cacheInMemory(true)
                    .displayer(new FadeInBitmapDisplayer(200))
                    .showImageForEmptyUri(R.drawable.default_artist_medium)
                    .cacheOnDisc(false)
                    .build();
        }
        return notCachedOptions;
    }

    public DownloadManager getDownloadManager() {
        return downloadManager;
    }

    public Context getContext() {
        return app;
    }

    public void finish(){

    }

    public void showToast(int strID) {
        Toast.makeText(app, strID, Toast.LENGTH_SHORT).show();
    }
}
