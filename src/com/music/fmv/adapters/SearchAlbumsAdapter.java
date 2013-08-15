package com.music.fmv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
    private AdapterCallback callback;

    public SearchAlbumsAdapter(ArrayList<SearchAlbumModel> mData, Context context) {
        this.mData = mData;
        this.inflater = LayoutInflater.from(context);
        this.imageLoader = ImageLoader.getInstance();
    }

    public void setCallback(AdapterCallback callback) {
        this.callback = callback;
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
            holder.play = (Button) convertView.findViewById(R.id.album_play);
            holder.addToQueue= (Button) convertView.findViewById(R.id.album_add_to_queu);
            holder.show = (Button) convertView.findViewById(R.id.album_show);
            holder.download = (Button) convertView.findViewById(R.id.album_download);
            holder.name = (TextView) convertView.findViewById(R.id.album_name);
            convertView.setTag(holder);
        }holder = (ViewHolder) convertView.getTag();
        SearchAlbumModel model = mData.get(position);
        holder.setListener(model);
        holder.descr.setText(model.getBriefDescr());
        holder.artistName.setText(model.getArtistName());
        holder.name.setText(model.getAlbumName());
        imageLoader.displayImage(model.getImage(), holder.icon, Core.getInstance(convertView.getContext()).getNotCachedOptions());
        return convertView;
    }

    private class ViewHolder{
        TextView artistName;
        TextView name;
        TextView descr;
        ImageView icon;

        Button play;
        Button addToQueue;
        Button show;
        Button download;

        void setListener(SearchAlbumModel model){
            play.setOnClickListener(new ButtonListener(model));
        }
    }

    private class ButtonListener implements View.OnClickListener{
        private SearchAlbumModel model;

        private ButtonListener(SearchAlbumModel model) {
            this.model = model;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){

            }
        }
    }

    public interface AdapterCallback{
        public void playClicked(SearchAlbumModel model);
        public void addToQueueClicked(SearchAlbumModel model);
        public void showClicked(SearchAlbumModel mdel);
        public void downloadClicked(SearchAlbumModel model);
    }
}
