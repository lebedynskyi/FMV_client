package com.music.fmv.core;

import com.music.fmv.models.dbmodels.ModelType;
import com.music.fmv.models.dbmodels.SearchQueryCache;
import com.music.fmv.models.notdbmodels.PlayAbleSong;
import com.music.fmv.utils.FileUtils;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Vitalii Lebedynskyi
 * Date: 7/12/13
 * Time: 6:08 PM
 */
public class CacheManager extends Manager {
    CacheManager(Core core) {
        super(core);

    }

    public boolean isSongExists(PlayAbleSong song) {
        String loadFolder = core.getSettingsManager().getSongsFolder();
        File songFile = FileUtils.getAbsolutheFile(loadFolder, song);
        return songFile.exists();
    }

    public String getSongPath(PlayAbleSong song) {
        String loadFolder = core.getSettingsManager().getSongsFolder();
        return FileUtils.getAbsoluthePath(loadFolder, song);
    }

    @Override
    protected void finish() {

    }

    public void addSearchQueryToCache(SearchQueryCache model) {
        try {
            List<SearchQueryCache> list = core.getDbHelper().getQueryCacheDAO().queryBuilder().where().eq("query", model.getQuery()).and().eq("queryType", model.getQueryType()).query();
            if (list.isEmpty()){
                core.getDbHelper().getQueryCacheDAO().createOrUpdate(model);
                return;
            }

            for (SearchQueryCache c: list){
                c.setRate(c.getRate() + 1);
                core.getDbHelper().getQueryCacheDAO().createOrUpdate(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<SearchQueryCache> getCachedQueries(ModelType  queryType, int i, String query) throws SQLException {
        return core.getDbHelper().getQueryCacheDAO().queryBuilder()
                .distinct()
                .selectColumns("query", "queryType")
                .limit(i > 0 ? (long)i : null)
                .orderBy("rate", false)
                .where()
                .eq("queryType", queryType.name())
                .and()
                .like("query", "%" + query + "%")
                .query();
    }

    public List<SearchQueryCache> getHistoryItems() {
        List<SearchQueryCache> fullList = new ArrayList<SearchQueryCache>();
        try {
            List<SearchQueryCache> artistList = getCachedQueries(ModelType.ARTIST, 10, "");
            List<SearchQueryCache> albumsList = getCachedQueries(ModelType.ALBUM, 10, "");
            List<SearchQueryCache> songsList = getCachedQueries(ModelType.SONG, 10, "");
            fullList.addAll(artistList);
            fullList.addAll(albumsList);
            fullList.addAll(songsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fullList;
    }
}
