package com.music.fmv.core;

import android.graphics.Bitmap;
import com.nostra13.universalimageloader.cache.disc.impl.FileCountLimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sileria.android.Application;

import java.io.File;

/**
 * User: vitaliylebedinskiy
 * Date: 7/19/13
 * Time: 12:16 PM
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //Initialization of core Manager
        Core core = Core.getInstance(this);
//
//        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread thread, Throwable ex) {
//                Core.getInstance(App.this).getNotificationManager().removeDownloading();
//                Core.getInstance(App.this).getNotificationManager().removePlayer();
//
//                ex.printStackTrace();
//                if (ex instanceof RuntimeException)
//                    throw (RuntimeException) ex;
//                throw new RuntimeException("Re-throw", ex);
//            }
//        });

        File imageCache = new File(core.getSettingsManager().getImageCacheFolder());
        imageCache.mkdirs();

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .denyCacheImageMultipleSizesInMemory()
                .discCache(new FileCountLimitedDiscCache(imageCache, 50))
                .discCacheExtraOptions(1024, 1024, Bitmap.CompressFormat.PNG, 75, null)
                .discCacheFileNameGenerator(new FileNameGenerator() {
                    @Override
                    public String generate(String imageUri) {
                        return imageUri.hashCode() + ".png";
                    }
                })
                .memoryCache(new LruMemoryCache(4 * 1024 * 1024))
                .threadPoolSize(3)
                .enableLogging()
                .threadPriority(Thread.NORM_PRIORITY)
                .build();

        ImageLoader.getInstance().init(configuration);
    }
}
