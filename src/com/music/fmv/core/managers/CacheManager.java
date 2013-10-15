package com.music.fmv.core.managers;

import com.music.fmv.core.Core;
import com.music.fmv.db.DBHelper;
import com.music.fmv.models.dbmodels.ModelType;
import com.music.fmv.models.dbmodels.SearchQueryCache;
import com.music.fmv.models.notdbmodels.PlayableSong;
import com.music.fmv.utils.FileUtils;

import java.io.File;
import java.sql.SQLException;
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
        String loadFolder = core.getSettingsManager().getSongsFolder();
        File songFile = FileUtils.getAbsolutheFile(loadFolder, song);
        return songFile.exists();
    }

    public String getSongPath(PlayableSong song) {
        String loadFolder = core.getSettingsManager().getSongsFolder();
        return FileUtils.getAbsoluthePath(loadFolder, song);
    }

    @Override
    protected void finish() {

    }

    public void addSearchQueryToCache(SearchQueryCache model) {
        dbHelper.getQueryCacheDAO().createOrUpdate(model);
    }

    public List<SearchQueryCache> getCachedQueries(ModelType  queryType, int i, String query) throws SQLException {
        List<SearchQueryCache> list = dbHelper.getQueryCacheDAO().queryBuilder()
                .distinct()
                .selectColumns("query")
                .where()
                .eq("queryType", queryType.name())
                .and()
                .like("query", "%" + query + "%")
                .query();
        if (list.size() > i) {
            return list.subList(0, i);
        }else return list;
    }
}
