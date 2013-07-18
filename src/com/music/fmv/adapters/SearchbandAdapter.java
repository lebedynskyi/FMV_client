package com.music.fmv.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.music.fmv.models.SearchBandModel;

import java.util.List;

/**
 * User: vitaliylebedinskiy
 * Date: 7/18/13
 * Time: 6:05 PM
 */

public class SearchBandAdapter extends BaseAdapter{
    private List<SearchBandModel> mList;
    private Context context;

    public SearchBandAdapter(List<SearchBandModel> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
