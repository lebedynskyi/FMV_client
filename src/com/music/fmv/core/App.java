package com.music.fmv.core;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Environment;
import com.nostra13.universalimageloader.cache.disc.impl.FileCountLimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;

import java.io.File;

/**
 * User: vitaliylebedinskiy
 * Date: 7/19/13
 * Time: 12:16 PM
 */
public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        //Initialization of core Manager
        Core.getInstance();

        File imageCache = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "fmv/cache_images/");
        imageCache.mkdirs();

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheSize(5 * 1024 * 1024)
                .discCache(new FileCountLimitedDiscCache(imageCache, 50))
                .discCacheExtraOptions(480, 800, Bitmap.CompressFormat.PNG, 75, null)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(4 * 1024 * 1024))
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY)
                .imageDecoder(new BaseImageDecoder(false)).build();
    }
}
