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
    private final Set<WeakReference<IUpdateListener>> updateListeners;

    private static Core instance;
    private final Context app;
    private Handler handler;
    private DBHelper dbHelper;

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
        handler = new Handler();
        mNotificationManager = new NotifyManager(this);
        mCacheManager = new CacheManager(this);
        mSettingsManager = new SettingsManager(this);
        downloadManager = new DownloadManager(this);
        updateListeners = new HashSet<WeakReference<IUpdateListener>>();
        playerManager = new PlayerManager(this);
        dbHelper = DBHelper.getInstance(app);
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

    public Context getContext() {
        return app;
    }

    public void finish() {

    }

    public void showToast(int strID) {
        Toast.makeText(app, strID, Toast.LENGTH_SHORT).show();
    }

    public void registerForUpdates(IUpdateListener listener) {
        updateListeners.add(new WeakReference<IUpdateListener>(listener));
    }

    public void callUpdateUI() {
        Iterator<WeakReference<IUpdateListener>> it = updateListeners.iterator();
        while (it.hasNext()) {
            WeakReference<IUpdateListener> ref = it.next();
            final IUpdateListener l = ref.get();
            if (l == null) {
                it.remove();
                continue;
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    l.needUpdate();
                }
            });
        }
    }

    public void unregisterListener(IUpdateListener listener) {
        Iterator<WeakReference<IUpdateListener>> it = updateListeners.iterator();
        while (it.hasNext()) {
            WeakReference<IUpdateListener> ref = it.next();
            IUpdateListener l = ref.get();
            if (l == null) {
                it.remove();
                continue;
            }

            if (l == listener) {
                it.remove();
                break;
            }
        }
    }

    public Handler getHandler() {
        return handler;
    }

    public interface IUpdateListener {
        public void needUpdate();
    }

    public DBHelper getDbHelper(){
        return dbHelper;
    }
}
