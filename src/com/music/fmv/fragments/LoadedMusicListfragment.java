package com.music.fmv.fragments;

import android.os.Bundle;
import android.view.View;
import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.music.fmv.R;
import com.music.fmv.adapters.NativeSongAdapter;
import com.music.fmv.core.BaseFragment;
import com.music.fmv.models.notdbmodels.FileSystemSong;
import com.music.fmv.tasks.GetAudioFromStore;
import com.music.fmv.widgets.RefreshableViewPager;

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
    private SwipeListView songsLIstView;
    private ArrayList<FileSystemSong> songs = new ArrayList<FileSystemSong>();
    private NativeSongAdapter adapter;

    @Override
    protected View createView(Bundle savedInstanceState) {
        View v = inflateView(R.layout.native_music_fragment);
        emptyView = v.findViewById(R.id.empty_text);
        progressView = v.findViewById(R.id.progress_bar);
        songsLIstView = (SwipeListView) v.findViewById(R.id.songs_list);
        adapter = new NativeSongAdapter(baseActivity, songs, songsLIstView);
        songsLIstView.setAdapter(adapter);
        songsLIstView.setSwipeListViewListener(listViewListener);
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
            songsLIstView.setEmptyView(null);
            emptyView.setVisibility(View.GONE);
            progressView.setVisibility(View.VISIBLE);
        }else {
            songsLIstView.setEmptyView(emptyView);
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
            songsLIstView.closeAnimate(position);
        }

        @Override
        public void onClickFrontView(int position) {
            songsLIstView.openAnimate(position);
        }
    };
}
