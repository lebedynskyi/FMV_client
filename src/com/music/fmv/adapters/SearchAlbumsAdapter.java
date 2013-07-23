package com.music.fmv.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.music.fmv.models.SearchAlbumModel;

import java.util.ArrayList;

/**
 * User: vitaliylebedinskiy
 * Date: 7/22/13
 * Time: 3:45 PM
 */
public class SearchAlbumsAdapter extends BaseAdapter{
    private ArrayList<SearchAlbumModel> mData;
    private Context context;

    public SearchAlbumsAdapter(ArrayList<SearchAlbumModel> mData, Context context) {
        this.mData = mData;
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


    private class ViewHolder{
        TextView name;
        TextView descr;
        ImageView icon;
    }
}
