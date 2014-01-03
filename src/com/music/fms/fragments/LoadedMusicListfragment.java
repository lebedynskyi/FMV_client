package com.music.fms.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.music.fms.R;
import com.music.fms.adapters.NativeSongAdapter;
import com.music.fms.core.BaseFragment;
import com.music.fms.core.PlayerManager;
import com.music.fms.models.FileSystemSong;
import com.music.fms.models.PlayAbleSong;
import com.music.fms.core.Player;
import com.music.fms.tasks.GetAudioFromStore;
import com.music.fms.widgets.RefreshableViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Vitalii Lebedynskyi
 * Date: 10/22/13
 * Time: 12:25 PM
 */
public class LoadedMusicListfragment extends BaseFragment implements RefreshableViewPager.Refreshable{
    private View emptyView;
    private View progressView;
    private SwipeListView songsListView;
    private ArrayList<PlayAbleSong> songs = new ArrayList<PlayAbleSong>();
    private NativeSongAdapter adapter;

    @Override
    protected View createView(Bundle savedInstanceState) {
        View v = inflateView(R.layout.native_music_fragment);
        emptyView = v.findViewById(R.id.empty_text);
        progressView = v.findViewById(R.id.progress_bar);
        songsListView = (SwipeListView) v.findViewById(R.id.songs_list);
        adapter = new NativeSongAdapter(baseActivity, songs, songsListView);
        adapter.setCallback(adapterCallback);
        songsListView.setAdapter(adapter);
        songsListView.setSwipeListViewListener(listViewListener);
        initSongs();
        return v;
    }

    public void initSongs() {
        prepareProgress(true);
        GetAudioFromStore task= new GetAudioFromStore(baseActivity, false, core.getSettingsManager().getSongsFolder()){
            @Override
            protected void onPostExecute(List<FileSystemSong> result) {
                super.onPostExecute(result);
                prepareProgress(false);
                songs.clear();
                songs.addAll(result);
                adapter.notifyDataSetChanged();
            }
        };

        task.execute();
    }

    private void prepareProgress(boolean isLoading) {
        if (isLoading){
            songsListView.setEmptyView(null);
            emptyView.setVisibility(View.GONE);
            progressView.setVisibility(View.VISIBLE);
        }else {
            songsListView.setEmptyView(emptyView);
            progressView.setVisibility(View.GONE);
        }
    }

    @Override
    public void refresh() {
        initSongs();
    }

    private BaseSwipeListViewListener listViewListener = new BaseSwipeListViewListener() {
        @Override
        public void onClickBackView(int position) {
            songsListView.closeAnimate(position);
        }

        @Override
        public void onClickFrontView(int position) {
            songsListView.openAnimate(position);
        }
    };


    private NativeSongAdapter.AdapterCallback adapterCallback = new NativeSongAdapter.AdapterCallback() {
        @Override
        public void playClicked(PlayAbleSong model, final int pos) {
            songsListView.closeOpenedItems();
            core.getPlayerManager().getPlayer(new PlayerManager.PostInitializationListener() {
                @Override
                public void onPlayerAvailable(Player p) {
                    p.play(songs, pos);
                    mMediator.startPlayerActivity();
                }
            });
        }

        @Override
        public void addToQueueClicked(final PlayAbleSong model) {
            songsListView.closeOpenedItems();
            core.getPlayerManager().getPlayer(new PlayerManager.PostInitializationListener() {
                @Override
                public void onPlayerAvailable(Player p) {
                    p.addSong(model);
                    Toast.makeText(baseActivity, String.format(getString(R.string.song_added_to_current_list), model.toString()), Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
}
