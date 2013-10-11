package com.music.fmv.tasks;

import android.content.Context;
import com.music.fmv.api.Api;
import com.music.fmv.models.dbmodels.SearchQueryCache;
import com.music.fmv.models.notdbmodels.SearchBandModel;

import java.util.List;

public abstract class SearchBandTask extends BaseAsyncTask<List<SearchBandModel>> {
    private static final SearchQueryCache.QUERY_TYPE queryType = SearchQueryCache.QUERY_TYPE.ARTIST;

    private String searchQuery;
    private Integer page;

    public SearchBandTask(String searchQuery, Integer page, Context context, boolean isShowLoader) {
        super(context, isShowLoader);
        this.searchQuery = searchQuery;
        this.page = page;
    }

    @Override
    protected final List<SearchBandModel> doInBackground(Object... voids) {
        try {
            addQueryToDB(searchQuery, queryType);
            String language = core.getSettingsManager().getResultLanguage();
            return new Api().searchBand(searchQuery, language, page);
        } catch (Exception e) {
            e.printStackTrace();
            isError = true;
        }
        return null;
    }
}
