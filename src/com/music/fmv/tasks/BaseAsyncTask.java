package com.music.fmv.tasks;

import android.content.Context;
import android.os.AsyncTask;
import com.music.fmv.core.Core;
import com.music.fmv.models.dbmodels.SearchQueryCache;
import com.music.fmv.views.LoadDialog;

/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 7/14/13
 * Time: 8:49 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseAsyncTask<T> extends AsyncTask<Object, Object, T> {
    protected Context context;
    private boolean isShowDialog;
    protected boolean isError;
    private LoadDialog loadDialog;
    protected Core core;

    protected BaseAsyncTask(Context context, boolean showDialog) {
        this.context = context;
        isShowDialog = showDialog;
        core = Core.getInstance(context);
    }

    @Override
    protected void onPreExecute() {
        if (isShowDialog) {
            loadDialog = new LoadDialog(context, this);
            loadDialog.show();
        }
    }

    @Override
    protected void onPostExecute(T t) {
        if (loadDialog != null) loadDialog.dismiss();
    }

    public void canceledByUser() {
        cancel(true);
    }

    public void addQueryToDB(String query, SearchQueryCache.QUERY_TYPE type){
        SearchQueryCache model = new SearchQueryCache(query, type);
        Core.getInstance(context).getCacheManager().addSearchQueryToCache(model);
    }
}
