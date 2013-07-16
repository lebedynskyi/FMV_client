package com.music.fmv.core;

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
}
