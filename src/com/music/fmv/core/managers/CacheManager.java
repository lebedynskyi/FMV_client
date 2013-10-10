package com.music.fmv.core.managers;

import com.music.fmv.core.Core;
import com.music.fmv.models.PlayableSong;
import com.music.fmv.utils.FileUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * User: vitaliylebedinskiy
 * Date: 7/12/13
 * Time: 6:08 PM
 */
public class CacheManager extends Manager {
    Map<String, WeakReference<Object>> mCache;
    private ArrayList<String> autocompleteWords;

    public CacheManager(Core coreManager) {
        super(coreManager);
        mCache = new HashMap<String, WeakReference<Object>>();
        autocompleteWords = new ArrayList<String>();
    }

    @Override
    protected void finish() {

    }

    public Map<String, WeakReference<Object>> getAllCache() {
        return mCache;
    }

    public Object getValue(String key) {
        return mCache.get(key).get();
    }

    public void setValue(String key, Object v) {
        mCache.put(key, new WeakReference<Object>(v));
    }

    public Set<String> getKeys() {
        return mCache.keySet();
    }

    public boolean isSongExists(PlayableSong song) {
        String loadFolder = core.getSettingsManager().getDownloadFolder();
        File folder = new File(loadFolder);
        folder.mkdirs();
        File newSongFile = FileUtils.getAbsolutheFile(folder, song);
        return newSongFile.exists();
    }

    public String getSongPath(PlayableSong song) {
        String loadFolder = core.getSettingsManager().getDownloadFolder();
        File folder = new File(loadFolder);
        folder.mkdirs();
        File newSongFile = FileUtils.getAbsolutheFile(folder, song);
        return newSongFile.getAbsolutePath();
    }
}
