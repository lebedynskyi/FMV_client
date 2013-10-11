package com.music.fmv.tasks;

import android.content.Context;
import android.text.TextUtils;
import com.music.fmv.R;
import com.music.fmv.models.dbmodels.SearchQueryCache;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Vitalii Lebedynskyi
 * Date: 10/11/13
 * Time: 3:08 PM
 */

public class AutocompleterTask extends BaseAsyncTask<ArrayList<String>> {
    private final boolean showDialog;
    private SearchQueryCache.QUERY_TYPE queryType;
    private final String cuurentString;

    public AutocompleterTask(Context context, boolean showDialog, SearchQueryCache.QUERY_TYPE queryType, String cuurentString) {
        super(context, showDialog);
        this.showDialog = showDialog;
        this.queryType = queryType;
        this.cuurentString = cuurentString;
    }

    @Override
    protected ArrayList<String> doInBackground(Object... params) {
        try {
            List<SearchQueryCache> cache = core.getCacheManager().getCachedQueries(queryType, 5, cuurentString);
            return extractString(cache);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<String> extractString(List<SearchQueryCache> cache) {
        ArrayList<String> words = new ArrayList<String>();
        int length = cuurentString.length();
        for (SearchQueryCache c: cache){
            String query = c.getQuery();
            if (TextUtils.isEmpty(query) || length == 0) continue;

            int queryLength = query.length();

            if (queryLength <= length + 5) {
                String color = core.getContext().getString(R.color.blue_button).substring(3);
                words.add(query.replace(cuurentString, "<font color=#" + color + ">" + cuurentString + "</font>"));
            }
        }
        return words;
    }
}
