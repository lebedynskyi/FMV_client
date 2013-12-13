package com.music.fmv.core;

import android.app.Application;
import android.graphics.Bitmap;
import com.nostra13.universalimageloader.cache.disc.impl.FileCountLimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

/**
 * User: Vitalii Lebedynskyi
 * Date: 7/19/13
 * Time: 12:16 PM
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Core core = Core.getInstance(this);
        initImageLoader(core);
    }

    private void initImageLoader(Core core) {
        File imageCache = new File(core.getSettingsManager().getImageCacheFolder());
        imageCache.mkdirs();

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .denyCacheImageMultipleSizesInMemory()
                .discCache(new FileCountLimitedDiscCache(imageCache, 50))
                .discCacheExtraOptions(1024, 1024, Bitmap.CompressFormat.PNG, 75, null)
                .discCacheFileNameGenerator(generator)
                .memoryCache(new LruMemoryCache(4 * 1024 * 1024))
                .threadPoolSize(3)
                .enableLogging()
                .threadPriority(Thread.NORM_PRIORITY)
                .build();

        ImageLoader.getInstance().init(configuration);
    }

    private FileNameGenerator generator = new FileNameGenerator() {
        @Override
        public String generate(String imageUri) {
            return imageUri.hashCode() + ".png";
        }
    };
}
