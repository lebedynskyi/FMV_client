package com.music.fmv.core;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;
import com.music.fmv.R;
import com.music.fmv.db.DBHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * User: vitaliylebedinskiy
 * Date: 7/12/13
 * Time: 5:46 PM
 */
public final class Core {
    private static Core instance;
    private final Context app;
    private DBHelper dbHelper;
    private Handler handler;

    private NotifyManager mNotificationManager;
    private CacheManager mCacheManager;
    private SettingsManager mSettingsManager;
    private DisplayImageOptions notCachedOptions;
    private DownloadManager downloadManager;
    private PlayerManager playerManager;

    public static Core getInstance(Context app) {
        if (instance == null) {
            synchronized (Core.class) {
                if (instance == null) {
                    instance = new Core(app);
                }
            }
        }
        return instance;
    }

    private Core(Context app) {
        if (app == null) throw new IllegalArgumentException("Context cannot be null");
        this.app = app;
        mNotificationManager = new NotifyManager(this);
        mCacheManager = new CacheManager(this);
        mSettingsManager = new SettingsManager(this);
        downloadManager = new DownloadManager(this);
        playerManager = new PlayerManager(this);
        dbHelper = DBHelper.getInstance(app);
        handler = new Handler();
    }

    public NotifyManager getNotificationManager() {
        return mNotificationManager;
    }

    public CacheManager getCacheManager() {
        return mCacheManager;
    }

    public SettingsManager getSettingsManager() {
        return mSettingsManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public DownloadManager getDownloadManager() {
        return downloadManager;
    }

    public Context getContext() {
        return app;
    }

    public DBHelper getDbHelper(){
        return dbHelper;
    }

    public Handler getHandler() {
        return handler;
    }

    public DisplayImageOptions getNotCachedOptions() {
        if (notCachedOptions == null) {
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

    public void finish() {

    }


    public class NotifyDownloadListener implements DownloadManager.IDownloadListener{
        @Override
        public void onDownloadStarted(String name) {
            getNotificationManager().notifyDownloading(name, 0, 100);
        }

        @Override
        public void onDownload(String name, int cur, int max) {
            getNotificationManager().notifyDownloading(name, cur, max);
        }

        @Override
        public void onDownloadFinished(String name) {
            getNotificationManager().removeDownloading();
            getNotificationManager().notifySuccessDownloading();
        }

        @Override
        public void onError(String name, DownloadManager.ERRORS err) {
            getNotificationManager().removeDownloading();
            getNotificationManager().notifyErrorDownloading(name);
        }
    }
}
