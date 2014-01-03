package com.music.fms.tasks;

import android.content.Context;
import android.os.AsyncTask;
import com.music.fms.core.Core;
import com.music.fms.models.ModelType;
import com.music.fms.models.SearchQueryCache;
import com.music.fms.views.LoadDialog;

/**
 * Created with IntelliJ IDEA.
 * User: Vitalii Lebednskyi
 * Date: 7/14/13
 * Time: 8:49 AM
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
    protected void onPostExecute(T result) {
        if (loadDialog != null) loadDialog.dismiss();
    }

    public void canceledByUser() {
        cancel(true);
    }

    public void addQueryToDB(String query, ModelType  type){
        SearchQueryCache model = new SearchQueryCache(query, type);
        Core.getInstance(context).getCacheManager().addSearchQueryToCache(model);
    }
}
