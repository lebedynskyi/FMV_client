package com.music.fmv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
    private ArrayList<PlayableSong> mData;
    private LayoutInflater inflater;

    public SearchSongAdapter(Context c ,ArrayList<PlayableSong> list){
        mData = list;
        inflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
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
            holder.owner = (TextView) convertView.findViewById(R.id.song_owner);
            holder.duration = (TextView) convertView.findViewById(R.id.song_duration);
            holder.name = (TextView) convertView.findViewById(R.id.song_name);
        }else holder = (ViewHolder) convertView.getTag();
        PlayableSong song = mData.get(position);
        holder.owner.setText(song.getArtist());
        holder.name.setText(song.getTitle());
        holder.duration.setText(song.getDuration());
        return convertView;
    }

    private class ViewHolder{
        TextView name;
        TextView owner;
        TextView duration;
    }
}
