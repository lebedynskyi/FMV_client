package com.music.fms.tasks;

import android.content.Context;
import com.music.fms.api.Api;
import com.music.fms.models.ModelType;
import com.music.fms.models.InternetSong;

import java.util.ArrayList;

/**
 * User: vitaliylebedinskiy
 * Date: 8/14/13
 * Time: 11:33 AM
 */

public class SearchSongsTask extends BaseAsyncTask<ArrayList<InternetSong>> {
    private static final ModelType queryType = ModelType.SONG;

    private final String query;
    private final Integer page;

    protected SearchSongsTask(Context context, String query, Integer page, boolean isShowLoader) {
        super(context, isShowLoader);
        this.query = query;
        this.page = page;
    }

    @Override
    protected ArrayList<InternetSong> doInBackground(Object... params) {
        try {
            addQueryToDB(query, queryType);
            return new Api().searchSongs(query, page);
        } catch (Exception e) {
            e.printStackTrace();
            isError = true;
        }

        return null;
    }
}
