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

public class SearchBandTask extends BaseAsyncTask<List<SearchBandModel>> {
    private String searchQuery;

    public SearchBandTask(String searchQuery, Context context) {
        super(context);
        this.searchQuery = searchQuery;
    }

    @Override
    protected List<SearchBandModel> doInBackground(Void... voids) {
        String language = Core.getInstance().getSettingsManager().getResultLanguage(context);
        return api.searchBand(searchQuery, language);
    }
}
