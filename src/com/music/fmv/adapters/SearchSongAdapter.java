package com.music.fmv.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.music.fmv.models.PlayableSong;

import java.util.ArrayList;

/**
 * User: vitaliylebedinskiy
 * Date: 8/1/13
 * Time: 2:43 PM
 */
public class SearchSongAdapter extends BaseAdapter{
    private ArrayList<PlayableSong>mdata;
    private Context c;

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
        return null;
    }

    private class ViewHolder{
        ImageView icon;
        TextView name;
        TextView album;
        TextView duration;
    }
}
