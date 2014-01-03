package com.music.fms.tasks;

import android.content.Context;
import com.music.fms.api.Api;
import com.music.fms.models.ModelType;
import com.music.fms.models.SearchBandModel;

import java.util.List;

public class SearchBandTask extends BaseAsyncTask<List<SearchBandModel>> {
    private static final ModelType queryType = ModelType.ARTIST;

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
