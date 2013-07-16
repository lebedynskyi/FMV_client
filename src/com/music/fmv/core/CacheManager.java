package com.music.fmv.core;

import android.util.LruCache;
import com.music.fmv.core.utils.CacheConst;
import com.music.fmv.models.SearchBandModel;

import java.util.List;

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

    public void putBandSearchResult(List<SearchBandModel> list){
        mCache.put(CacheConst.BAND_SEARCH_RESULT_KEY, list);
    }

    public List<SearchBandModel> getBandSearchResult(){
        return (List<SearchBandModel>) mCache.get(CacheConst.BAND_SEARCH_RESULT_KEY);
    }

}
