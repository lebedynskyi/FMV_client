package com.music.fmv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.music.fmv.R;
import com.music.fmv.core.Core;
import com.music.fmv.models.SearchAlbumModel;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * User: vitaliylebedinskiy
 * Date: 7/22/13
 * Time: 3:45 PM
 */
public class SearchAlbumsAdapter extends BaseAdapter{
    private final LayoutInflater inflater;
    private ArrayList<SearchAlbumModel> mData;
    private ImageLoader imageLoader;

    public SearchAlbumsAdapter(ArrayList<SearchAlbumModel> mData, Context context) {
        this.mData = mData;
        this.inflater = LayoutInflater.from(context);
        imageLoader = ImageLoader.getInstance();
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
            convertView = inflater.inflate(R.layout.search_album_row, parent, false);
            holder = new ViewHolder();
            holder.descr = (TextView) convertView.findViewById(R.id.alum_brief);
            holder.icon = (ImageView) convertView.findViewById(R.id.album_icon);
            holder.artistName = (TextView) convertView.findViewById(R.id.album_owner);
            holder.name = (TextView) convertView.findViewById(R.id.album_name);
            convertView.setTag(holder);
        }holder = (ViewHolder) convertView.getTag();
        SearchAlbumModel model = mData.get(position);
        holder.descr.setText(model.getBriefDescr());
        holder.artistName.setText(model.getArtistName());
        holder.name.setText(model.getAlbumName());
        imageLoader.displayImage(model.getImage(), holder.icon, Core.getInstance().getNotcachedOptions());
        return convertView;
    }

    private class ViewHolder{
        TextView artistName;
        TextView name;
        TextView descr;
        ImageView icon;
    }
}
