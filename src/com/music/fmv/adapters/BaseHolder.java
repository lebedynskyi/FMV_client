package com.music.fmv.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * User: vitaliylebedinskiy
 * Date: 7/12/13
 * Time: 6:11 PM
 */
public abstract class BaseHolder<Model> {
    protected Context context;
    protected int viewId;

    protected BaseHolder(Context context, int viewId) {
        this.context = context;
        this.viewId = viewId;
    }

    protected abstract View fillView(Model m, View convertView, ViewGroup parent);
}
