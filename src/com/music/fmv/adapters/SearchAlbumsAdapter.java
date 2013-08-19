package com.music.fmv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.fortysevendeg.swipelistview.SwipeListView;
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
public class SearchAlbumsAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private final SwipeListView listView;
    private ArrayList<SearchAlbumModel> mData;
    private ImageLoader imageLoader;
    private AdapterCallback callback;

    public SearchAlbumsAdapter(ArrayList<SearchAlbumModel> mData, Context context, SwipeListView lv) {
        this.listView = lv;
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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.search_album_row, parent, false);
            holder = new ViewHolder();
            holder.descr = (TextView) convertView.findViewById(R.id.alum_brief);
            holder.icon = (ImageView) convertView.findViewById(R.id.album_icon);
            holder.artistName = (TextView) convertView.findViewById(R.id.album_owner);
            holder.play = convertView.findViewById(R.id.album_play);
            holder.addToQueue = convertView.findViewById(R.id.album_add_to_queu);
            holder.show = convertView.findViewById(R.id.album_show);
            holder.download = convertView.findViewById(R.id.album_download);
            holder.name = (TextView) convertView.findViewById(R.id.album_name);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        SearchAlbumModel model = mData.get(position);
        holder.descr.setText(model.getBriefDescr());
        holder.artistName.setText(model.getArtistName());
        holder.name.setText(model.getAlbumName());

        holder.play.setOnClickListener(new ButtonListener(model, position));
        holder.addToQueue.setOnClickListener(new ButtonListener(model, position));
        holder.show.setOnClickListener(new ButtonListener(model, position));
        holder.download.setOnClickListener(new ButtonListener(model, position));

        imageLoader.displayImage(model.getImage(), holder.icon, Core.getInstance(convertView.getContext()).getNotCachedOptions());
        return convertView;
    }

    private class ViewHolder {
        TextView artistName;
        TextView name;
        TextView descr;
        ImageView icon;

        View play;
        View addToQueue;
        View show;
        View download;
    }

    private class ButtonListener implements View.OnClickListener {
        private SearchAlbumModel model;
        private int position;

        private ButtonListener(SearchAlbumModel model, int position) {
            this.model = model;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            listView.closeAnimate(position);
            switch (v.getId()) {
                case R.id.album_add_to_queu:
                    if (callback != null) callback.addToQueueClicked(model);
                    break;
                case R.id.album_download:
                    if (callback != null) callback.downloadClicked(model);
                    break;
                case R.id.album_play:
                    if (callback != null) callback.playClicked(model);
                    break;
                case R.id.album_show:
                    if (callback != null) callback.showClicked(model);
            }
        }
    }


    public interface AdapterCallback {
        public void playClicked(SearchAlbumModel model);

        public void addToQueueClicked(SearchAlbumModel model);

        public void showClicked(SearchAlbumModel mdel);

        public void downloadClicked(SearchAlbumModel model);
    }
}
