package com.music.fmv.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.music.fmv.models.InternetSong;
import com.music.fmv.models.SearchAlbumModel;
import com.music.fmv.models.SearchBandModel;
import com.music.fmv.models.SearchQueryCache;

import java.sql.SQLException;

/**
 * User: Vitalii Lebedynskyi
 * Date: 10/11/13
 * Time: 10:46 AM
 */
public class DBHelper extends OrmLiteSqliteOpenHelper{
    private final static int DB_VERSION = 1;
    private final static String DB_NAME = "fms.db";

    private static DBHelper instance = null;

    public static DBHelper getInstance(Context c){
        if (instance == null){
            synchronized (DBHelper.class){
                if (instance == null){
                    instance = new DBHelper(c);
                }
            }
        }
        return instance;
    }

    private RuntimeExceptionDao<SearchQueryCache, Integer> queryCacheDAO;
    private RuntimeExceptionDao<SearchAlbumModel, Integer> searchAlbumDAO;
    private RuntimeExceptionDao<SearchBandModel, Integer> secrahBandDAO;
    private RuntimeExceptionDao<InternetSong, Integer> searchSongsDAO;


    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, SearchQueryCache.class);
            TableUtils.createTableIfNotExists(connectionSource, SearchAlbumModel.class);
            TableUtils.createTableIfNotExists(connectionSource, SearchBandModel.class);
            TableUtils.createTableIfNotExists(connectionSource, InternetSong.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public void clearTable(Class clz) {
        try {
            TableUtils.clearTable(getConnectionSource(), clz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RuntimeExceptionDao<SearchQueryCache, Integer> getQueryCacheDAO() {
        if (queryCacheDAO == null) {
            queryCacheDAO = getRuntimeExceptionDao(SearchQueryCache.class);
        }
        return queryCacheDAO;
    }

    public RuntimeExceptionDao<SearchAlbumModel, Integer> getSearchAlbumDAO() {
        if (searchAlbumDAO == null) {
            searchAlbumDAO = getRuntimeExceptionDao(SearchAlbumModel.class);
        }

        return searchAlbumDAO;
    }

    public RuntimeExceptionDao<SearchBandModel, Integer> getSecrahBandDAO() {
        if (secrahBandDAO == null) {
            secrahBandDAO = getRuntimeExceptionDao(SearchBandModel.class);
        }
        return secrahBandDAO;
    }

    public RuntimeExceptionDao<InternetSong, Integer> getSearchSongsDAO() {
        if (searchSongsDAO == null) {
            searchSongsDAO = getRuntimeExceptionDao(InternetSong.class);
        }
        return searchSongsDAO;
    }
}
