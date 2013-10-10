package com.music.fmv.tasks;

import android.content.Context;
import com.music.fmv.core.Core;
import com.music.fmv.models.SearchBandModel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 7/14/13
 * Time: 8:53 AM
 * To change this template use File | Settings | File Templates.
 */

public abstract class SearchBandTask extends BaseAsyncTask<List<SearchBandModel>> {
    private String searchQuery;
    private Integer page;

    public SearchBandTask(String searchQuery, Integer page, Context context, boolean isShowLoader) {
        super(context, isShowLoader);
        this.searchQuery = searchQuery;
        this.page = page;
    }

    @Override
    protected final List<SearchBandModel> doInBackground(Object... voids) {
        String language = Core.getInstance(context).getSettingsManager().getResultLanguage();
        try {
            return api.searchBand(searchQuery, language, page);
        } catch (Exception e) {
            e.printStackTrace();
            isError = true;
        }
        return null;
    }
}
