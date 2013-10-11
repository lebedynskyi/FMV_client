package com.music.fmv.core.managers;

import com.music.fmv.core.Core;
import com.music.fmv.db.DBHelper;
import com.music.fmv.models.dbmodels.SearchQueryCache;
import com.music.fmv.models.notdbmodels.PlayableSong;
import com.music.fmv.utils.FileUtils;
import com.music.fmv.utils.Log;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * User: Vitalii Lebedynskyi
 * Date: 7/12/13
 * Time: 6:08 PM
 */
public class CacheManager extends Manager {
    private DBHelper dbHelper;

    public CacheManager(Core core) {
        super(core);
        dbHelper = DBHelper.getInstance(core.getContext());
    }

    public boolean isSongExists(PlayableSong song) {
        String loadFolder = core.getSettingsManager().getDownloadFolder();
        File songFile = FileUtils.getAbsolutheFile(loadFolder, song);
        return songFile.exists();
    }

    public String getSongPath(PlayableSong song) {
        String loadFolder = core.getSettingsManager().getDownloadFolder();
        return FileUtils.getAbsoluthePath(loadFolder, song);
    }

    @Override
    protected void finish() {

    }

    public void addSearchQueryToCache(SearchQueryCache model) {
        dbHelper.getQueryCacheDAO().createOrUpdate(model);

        List<SearchQueryCache> ll = dbHelper.getQueryCacheDAO().queryForAll();
        Log.e("QURIES !!!!!", Arrays.toString(ll.toArray()));
    }
}
