package com.music.fmv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.music.fmv.R;
import com.music.fmv.models.PlayableSong;

import java.util.ArrayList;

/**
 * User: vitaliylebedinskiy
 * Date: 8/1/13
 * Time: 2:43 PM
 */
public class SearchSongAdapter extends BaseAdapter{
    private ArrayList<PlayableSong>mdata;
    private LayoutInflater inflater;

    public SearchSongAdapter(Context c ,ArrayList<PlayableSong> list){
        mdata = list;
        inflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return mdata.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.search_songs_row, parent, false);
        }else holder = (ViewHolder) convertView.getTag();


        return convertView;
    }

    private class ViewHolder{
        ImageView icon;
        TextView name;
        TextView album;
        TextView duration;
    }
}
