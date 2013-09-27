package com.music.fmv.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;import com.music.fmv.core.Core;

import java.util.List;

/**
 * User: vitaliylebedinskiy
 * Date: 9/27/13
 * Time: 1:57 PM
 */
public abstract class FixedBaseAdapter<T> extends BaseAdapter {
    protected List<T> mData;
    protected Context context;
    protected Core core;

    private LayoutInflater inflater;

    protected FixedBaseAdapter(List<T> mData, Context context) {
        this.mData = mData;
        this.context = context;
        inflater = LayoutInflater.from(context);
        core = Core.getInstance(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected View inflateView(int id){
        return inflater.inflate(id, null, false);
    }

    protected View inflateView(int id, ViewGroup parent){
        return inflater.inflate(id, parent, false);
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

}
