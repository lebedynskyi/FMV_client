package com.music.fmv.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * User: vitaliylebedinskiy
 * Date: 7/12/13
 * Time: 6:10 PM
 */
public class LinkedAdapter<T> extends BaseAdapter{
    private List<T> mList;
    private BaseHolder<T> mHolder;

    public LinkedAdapter(List<T> list, BaseHolder<T> holder) {
        if (mList == null || holder == null){
            throw new IllegalStateException("List and Holder cannot be null");
        }

        this.mList = list;
        this.mHolder = holder;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return mHolder.fillView(mList.get(position), convertView, parent);
    }
}
