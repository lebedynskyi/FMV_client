package com.music.fmv.tasks;

import android.content.Context;
import com.music.fmv.models.PlayableSong;

import java.util.ArrayList;

/**
 * User: vitaliylebedinskiy
 * Date: 8/14/13
 * Time: 11:33 AM
 */

public abstract class SearchSongsTask extends BaseAsyncTask<ArrayList<PlayableSong>> {
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
            return api.searchSongs(query, page);
        } catch (Exception e) {
            e.printStackTrace();
            isError = true;
        }

        return null;
    }
}
