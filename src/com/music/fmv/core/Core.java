package com.music.fmv.core;

import com.music.fmv.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * User: vitaliylebedinskiy
 * Date: 7/12/13
 * Time: 5:46 PM
 */
public class Core {
    private static Core instance;
    private NotifyManager mNotificationManager;
    private CacheManager mCacheManager ;
    private SettingsManager mSettingsManager;
    private DisplayImageOptions notCachedOptions;

    public static Core getInstance() {
        if (instance == null){
            synchronized (Core.class){
                if(instance == null){
                    instance = new Core();
                }
            }
        }
        return instance;
    }

    private Core(){
        mNotificationManager = new NotifyManager(this);
        mCacheManager = new CacheManager(this);
        mSettingsManager = new SettingsManager(this);
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

    public DisplayImageOptions getNotcachedOptions(){
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
}
