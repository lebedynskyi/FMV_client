package com.music.fmv.views;

import android.app.AlertDialog;
import android.content.Context;
import com.music.fmv.R;
import com.music.fmv.tasks.BaseAsyncTask;

/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 16.05.13
 * Time: 21:24
 * To change this template use File | Settings | File Templates.
 */
public class LoadDialog extends AlertDialog{
    private BaseAsyncTask task;

    public LoadDialog(Context context, BaseAsyncTask t) {
        super(context);
        this.task = t;
        initUI();
    }

    public LoadDialog(Context context, int theme) {
        super(context, theme);
        initUI();
    }

    public LoadDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initUI();
    }

    private void initUI(){
        setCancelable(false);
    }

    @Override
    public void onBackPressed() {
        try{
            if (task != null) task.cancel(true);
            this.dismiss();
        }catch (Exception e){
            e.printStackTrace();
        }
        super.onBackPressed();
    }

    @Override
    public void show() {
        super.show();
        setContentView(R.layout.load_dialog);
    }
}
