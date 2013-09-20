package com.music.fmv.tasks;

import android.content.Context;
import android.os.AsyncTask;
import com.music.fmv.api.Api;
import com.music.fmv.views.LoadDialog;

/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 7/14/13
 * Time: 8:49 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseAsyncTask<T> extends AsyncTask<Object, Object, T>{
    protected Context context;
    private boolean showLoader;
    protected Api api = new Api();
    protected boolean isError;
    private LoadDialog loadDialog;

    protected BaseAsyncTask(Context context, boolean isShowLoader){
        this.context = context;
        showLoader = isShowLoader;
    }

    @Override
    protected void onPreExecute() {
        if (showLoader){
            loadDialog = new LoadDialog(context, this);
            loadDialog.show();
        }
    }

    @Override
    protected void onPostExecute(T t) {
        if (loadDialog != null) loadDialog.dismiss();
    }

    public abstract void canceledByUser();
}
