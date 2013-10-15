package com.music.fmv.tasks;

import android.content.Context;
import com.music.fmv.api.Api;
import com.music.fmv.models.dbmodels.ModelType;
import com.music.fmv.models.notdbmodels.PlayableSong;

import java.util.ArrayList;

/**
 * User: vitaliylebedinskiy
 * Date: 8/14/13
 * Time: 11:33 AM
 */

public class SearchSongsTask extends BaseAsyncTask<ArrayList<PlayableSong>> {
    private static final ModelType queryType = ModelType.SONG;

    private final String query;
    private final Integer page;

    protected SearchSongsTask(Context context, String query, Integer page, boolean isShowLoader) {
        super(context, isShowLoader);
        this.query = query;
        this.page = page;
    }

    @Override
    protected ArrayList<PlayableSong> doInBackground(Object... params) {
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
