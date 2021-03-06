package com.music.fms.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.music.fms.R;
import com.music.fms.core.Core;
import com.music.fms.models.SearchBandModel;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * User: vitaliylebedinskiy
 * Date: 7/18/13
 * Time: 6:05 PM
 */

public class SearchBandAdapter extends FixedBaseAdapter<SearchBandModel> {
    private List<SearchBandModel> mList;
    private ImageLoader imageLoader;

    public SearchBandAdapter(List<SearchBandModel> mList, Context context) {
        super(mList, context);
        this.mList = mList;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public SearchBandModel getItem(int position) {
        return mList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent, SearchBandModel model) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflateView(R.layout.search_band_row, parent);
            holder.briefDescr = (TextView) convertView.findViewById(R.id.band_descr);
            holder.icon = (ImageView) convertView.findViewById(R.id.band_icon);
            holder.name = (TextView) convertView.findViewById(R.id.band_name);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();

        holder.name.setText(model.getName());
        imageLoader.displayImage(model.getImage(), holder.icon, Core.getInstance(convertView.getContext()).getNotCachedOptions());
        holder.briefDescr.setText(model.getDescr());
        return convertView;
    }

    private class ViewHolder {
        ImageView icon;
        TextView name;
        TextView briefDescr;
    }
}
