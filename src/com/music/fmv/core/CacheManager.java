package com.music.fmv.core;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    public Map<String, WeakReference<Object>> getAllCache(){
        return mCache;
    }

    public Object getValue(String key){
        return mCache.get(key).get();
    }

    public void setValue(String key, Object v){
        mCache.put(key, new WeakReference<Object>(v));
    }

    public Set<String> getKeys(){
        return mCache.keySet();
    }
}
