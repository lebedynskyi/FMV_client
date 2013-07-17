package com.music.fmv.core;

import android.support.v4.util.LruCache;

/**
 * User: vitaliylebedinskiy
 * Date: 7/12/13
 * Time: 6:08 PM
 */
public class CacheManager extends Manager{
    public static final int CACHE_SIZE = 4 * 1024 * 1024; // 4MiB
    private LruCache<String, Object> mCache;
    public CacheManager(Core coreManager) {
        super(coreManager);
        mCache = new LruCache<String, Object>(CACHE_SIZE);
    }
}
