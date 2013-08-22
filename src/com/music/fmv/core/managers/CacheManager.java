package com.music.fmv.core.managers;

import android.text.TextUtils;
import com.music.fmv.core.Core;

import java.lang.ref.WeakReference;
import java.util.*;

/**
 * User: vitaliylebedinskiy
 * Date: 7/12/13
 * Time: 6:08 PM
 */
public class CacheManager extends Manager{
    Map<String, WeakReference<Object>>  mCache;
    private ArrayList<String> autocompleteWords;

    public CacheManager(Core coreManager) {
        super(coreManager);
        mCache = new HashMap<String, WeakReference<Object>>();
        autocompleteWords = new ArrayList<String>();
    }

    @Override
    protected void finish() {

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
