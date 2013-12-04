package com.music.fmv.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.music.fmv.R;
import com.music.fmv.core.Player;
import com.music.fmv.core.PlayerManager;
import com.music.fmv.models.PlayAbleSong;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vitalii Lebedynskyi
 * Date: 12/4/13
 * Time: 11:25 AM
 * To change this template use File | Settings | File Templates.
 */

public class PlayerListAdapter extends FixedBaseAdapter<PlayAbleSong> {
    public PlayerListAdapter(List<PlayAbleSong> mData, Context context) {
        super(mData, context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent, final PlayAbleSong item) {
        final Holder h;
        if (convertView == null){
            h = new Holder();
            convertView = inflateView(R.layout.player_list_row);
            h.artist = (TextView) convertView.findViewById(R.id.song_artist);
            h.name = (TextView) convertView.findViewById(R.id.song_name);
            h.duration = (TextView) convertView.findViewById(R.id.song_duration);
            convertView.setTag(h);
        }else h = (Holder) convertView.getTag();

        h.artist.setText(item.getArtist());
        h.duration.setText(item.getNiceDuration());
        h.name.setText(item.getName());
        h.name.setTextColor(context.getResources().getColor(R.color.white));

        core.getPlayerManager().getPlayer(new PlayerManager.PostInitializationListener() {
            @Override
            public void onPlayerAvailable(Player p) {
                if (p == null) return;
                Player.PlayerStatus st = p.getStatus();
                if (st == null) return;

                if (st.getCurrentSong() != null && st.getCurrentSong().equals(item)){
                    h.name.setTextColor(context.getResources().getColor(R.color.blue_button));
                }
            }
        });
        return convertView;
    }

    private class Holder{
        TextView name;
        TextView artist;
        TextView duration;
    }
}
