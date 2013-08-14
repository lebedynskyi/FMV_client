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
import com.music.fmv.models.SearchBandModel;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * User: vitaliylebedinskiy
 * Date: 7/18/13
 * Time: 6:05 PM
 */

public class SearchBandAdapter extends BaseAdapter{
    private final LayoutInflater inflater;
    private List<SearchBandModel> mList;
    private ImageLoader imageLoader;

    public SearchBandAdapter(List<SearchBandModel> mList, Context context) {
        this.mList = mList;
        this.inflater = LayoutInflater.from(context);
        imageLoader = ImageLoader.getInstance();
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
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.search_band_row, parent, false);
            holder.briefDescr = (TextView) convertView.findViewById(R.id.band_descr);
            holder.icon = (ImageView) convertView.findViewById(R.id.band_icon);
            holder.name = (TextView) convertView.findViewById(R.id.band_name);
            convertView.setTag(holder);
        }else holder = (ViewHolder) convertView.getTag();
        SearchBandModel model = mList.get(position);
        holder.name.setText(model.getName());
        imageLoader.displayImage(model.getImage(), holder.icon, Core.getInstance(convertView.getContext()).getNotCachedOptions());
        holder.briefDescr.setText(model.getDescr());
        return convertView;
    }

    private class ViewHolder{
        ImageView icon;
        TextView name;
        TextView briefDescr;
    }
}
