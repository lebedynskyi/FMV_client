package com.music.fmv.core;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * User: vitaliylebedinskiy
 * Date: 7/12/13
 * Time: 6:08 PM
 */
public class CacheManager extends Manager{
    Map<String, WeakReference<Object>>  mCache;
    public CacheManager(Core coreManager) {
        super(coreManager);
        mCache = new HashMap<String, WeakReference<Object>>();
    }
}
