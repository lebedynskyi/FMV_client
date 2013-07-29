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
    private NotificationManager mNotificationManager;
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
        mNotificationManager = new NotificationManager(this);
        mCacheManager = new CacheManager(this);
        mSettingsManager = new SettingsManager(this);
    }

    public NotificationManager getNotificationManager() {
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
                    .showStubImage(R.drawable.empty_band_star)
                    .cacheInMemory(true)
                    .displayer(new FadeInBitmapDisplayer(500))
                    .showImageForEmptyUri(R.drawable.empty_band_star)
                    .cacheOnDisc(false)
                    .build();
        }

        return notCachedOptions;
    }
}
