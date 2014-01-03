package com.music.fms.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.music.fms.R;
import com.music.fms.models.PlayAbleSong;

import java.util.ArrayList;

/**
 * User: Vitalii Lebedinskiy
 * Date: 8/1/13
 * Time: 2:43 PM
 */

public class NativeSongAdapter extends FixedBaseAdapter<PlayAbleSong> {
    private AdapterCallback callback;
    private SwipeListView listView;

    public NativeSongAdapter(Context c, ArrayList<PlayAbleSong> list, SwipeListView listView) {
        super(list, c);
        this.listView = listView;
    }

    public void setCallback(AdapterCallback callback) {
        this.callback = callback;
    }

    @Override
    public PlayAbleSong getItem(int position) {
        return mData.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent, PlayAbleSong song) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflateView(R.layout.search_songs_row, parent);
            holder.owner = (TextView) convertView.findViewById(R.id.song_owner);
            holder.duration = (TextView) convertView.findViewById(R.id.duration);
            holder.name = (TextView) convertView.findViewById(R.id.song_name);
            holder.play = convertView.findViewById(R.id.song_play);
            convertView.findViewById(R.id.song_download).setVisibility(View.GONE);
            convertView.findViewById(R.id.is_cached).setVisibility(View.GONE);
            holder.addToQueue = convertView.findViewById(R.id.song_add_to_queu);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();

        holder.addToQueue.setOnClickListener(new ButtonListener(song, position));
        holder.play.setOnClickListener(new ButtonListener(song, position));

        holder.owner.setText(song.getArtist());
        holder.name.setText(song.getName());
        holder.duration.setText(song.getNiceDuration());
        return convertView;
    }

    private class ViewHolder {
        TextView name;
        TextView owner;
        TextView duration;

        View play;
        View addToQueue;
    }

    private class ButtonListener implements View.OnClickListener {
        private PlayAbleSong model;
        private int position;

        private ButtonListener(PlayAbleSong model, int position) {
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
            }
        }
    }

    public interface AdapterCallback {
        public void playClicked(PlayAbleSong model, int pos);
        public void addToQueueClicked(PlayAbleSong model);
    }
}
