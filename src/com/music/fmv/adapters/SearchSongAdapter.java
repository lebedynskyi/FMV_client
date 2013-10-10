package com.music.fmv.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.music.fmv.R;
import com.music.fmv.models.PlayableSong;
import com.music.fmv.utils.TimeUtils;
import com.music.fmv.widgets.FixedBaseAdapter;

import java.util.ArrayList;
import java.util.Date;

/**
 * User: vitaliylebedinskiy
 * Date: 8/1/13
 * Time: 2:43 PM
 */

public class SearchSongAdapter extends FixedBaseAdapter<PlayableSong> {
    private AdapterCallback callback;
    private SwipeListView listView;

    public SearchSongAdapter(Context c, ArrayList<PlayableSong> list, SwipeListView listView) {
        super(list, c);
        this.listView = listView;
    }

    public void setCallback(AdapterCallback callback) {
        this.callback = callback;
    }

    @Override
    public PlayableSong getItem(int position) {
        return mData.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflateView(R.layout.search_songs_row, parent);
            holder.owner = (TextView) convertView.findViewById(R.id.song_owner);
            holder.duration = (TextView) convertView.findViewById(R.id.duration);
            holder.isCached = convertView.findViewById(R.id.is_cached);
            holder.name = (TextView) convertView.findViewById(R.id.song_name);
            holder.play = convertView.findViewById(R.id.song_play);
            holder.addToQueue = convertView.findViewById(R.id.song_add_to_queu);
            holder.download = convertView.findViewById(R.id.song_download);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();
        PlayableSong song = mData.get(position);

        holder.download.setOnClickListener(new ButtonListener(song, position));
        holder.addToQueue.setOnClickListener(new ButtonListener(song, position));
        holder.play.setOnClickListener(new ButtonListener(song, position));

        holder.owner.setText(song.getArtist());
        holder.name.setText(song.getName());
        holder.duration.setText(TimeUtils.getNiceTimeForSongs(song.getDuration()));

        if (core.getCacheManager().isSongExists(song)) {
            holder.isCached.setVisibility(View.VISIBLE);
        } else holder.isCached.setVisibility(View.GONE);

        return convertView;
    }

    private class ViewHolder {
        TextView name;
        TextView owner;
        TextView duration;

        View isCached;

        View play;
        View addToQueue;
        View download;
    }

    private class ButtonListener implements View.OnClickListener {
        private PlayableSong model;
        private int position;

        private ButtonListener(PlayableSong model, int position) {
            this.model = model;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            listView.closeAnimate(position + 1);
            switch (v.getId()) {
                case R.id.song_play:
                    if (callback != null) callback.playClicked(model, position);
                    break;
                case R.id.song_add_to_queu:
                    if (callback != null) callback.addToQueueClicked(model);
                    break;
                case R.id.song_download:
                    if (callback != null) callback.downloadClicked(model);
            }
        }
    }

    public interface AdapterCallback {
        public void playClicked(PlayableSong model, int pos);

        public void addToQueueClicked(PlayableSong model);

        public void downloadClicked(PlayableSong model);
    }
}
