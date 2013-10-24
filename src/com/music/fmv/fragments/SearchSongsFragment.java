package com.music.fmv.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.music.fmv.R;
import com.music.fmv.adapters.SearchSongAdapter;
import com.music.fmv.core.Core;
import com.music.fmv.models.dbmodels.ModelType;
import com.music.fmv.models.notdbmodels.InternetSong;
import com.music.fmv.models.notdbmodels.PlayAbleSong;
import com.music.fmv.tasks.SearchSongsTask;
import com.music.fmv.utils.ViewUtils;

import java.util.ArrayList;

/**
 * User: vitaliylebedinskiy
 * Date: 8/5/13
 * Time: 12:32 PM
 */
public class SearchSongsFragment extends BaseSearchFragment implements Core.IUpdateListener {
    private SwipeListView songsListView;
    private ArrayList<PlayAbleSong> songsInAdapter = new ArrayList<PlayAbleSong>();
    private SearchSongAdapter adapter;
    private boolean songTaskRunned;
    private String lastQuery;
    private int songsPageAvailable;
    private int futureSongPage;
    private View rotateFooter;
    private TextView emptyView;

    @Override
    public void onResume() {
        super.onResume();
        core.registerForUpdates(this);
    }

    @Override
    protected View createView(Bundle savedInstanceState) {
        View mainView = inflateView(R.layout.search_songs_fragment);
        rotateFooter = inflateView(R.layout.rotate_footer);

        emptyView = (TextView) mainView.findViewById(R.id.empty_view);

        songsListView = (SwipeListView) mainView.findViewById(R.id.songs_list);
        songsListView.setOnItemClickListener(songsListener);
        songsListView.addHeaderView(createSearchHeader(searchListener, ModelType.SONG));
        songsListView.setHeaderDividersEnabled(false);
        adapter = new SearchSongAdapter(baseActivity, songsInAdapter, songsListView);
        adapter.setCallback(adapterCallback);
        songsListView.setAdapter(adapter);
        songsListView.setSwipeListViewListener(listViewListener);
        songsListView.setOnScrollListener(scrollListener);
        return mainView;
    }

    private AdapterView.OnItemClickListener songsListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };

    @Override
    protected void processSearch(String query, final Integer page) {
        if (TextUtils.isEmpty(query) || songTaskRunned) return;
        query = query.trim();

        SearchSongsTask task = new SearchSongsTask(baseActivity, query, page, page == null) {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                songTaskRunned = true;
            }

            @Override
            protected void onPostExecute(ArrayList<InternetSong> songs) {
                super.onPostExecute(songs);
                songsPageAvailable = InternetSong.PAGE_AVAILABLE;
                songTaskRunned = false;

                if (isCancelled()) return;

                if (isError || songs == null) {
                    Toast.makeText(baseActivity, getString(R.string.request_error), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (songs.size() > 0) {
                    updateSongsList(songs, page == null);
                } else Toast.makeText(baseActivity, getString(R.string.empty_result), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void canceledByUser() {
                super.canceledByUser();
                songTaskRunned = false;
            }
        };

        if (runTask(task)) {
            lastQuery = query;
        }
    }

    private void updateSongsList(ArrayList<InternetSong> songs, boolean isClear) {
        if (isClear) {
            songsInAdapter.clear();
            futureSongPage = 1;
        }
        songsInAdapter.addAll(songs);
        adapter.notifyDataSetChanged();
        futureSongPage += 1;

        if (songsPageAvailable <= 0) {
            songsListView.removeFooterView(rotateFooter);
        } else if (songsPageAvailable > 0 && songsListView.getFooterViewsCount() == 0) {
            songsListView.addFooterView(rotateFooter);
        }

        if (songsListView.getCount() == 0) {
            emptyView.setVisibility(View.VISIBLE);
        } else emptyView.setVisibility(View.GONE);
    }

    private void getNextSongsPage() {
        if (songsPageAvailable > 0 && futureSongPage <= songsPageAvailable) {
            processSearch(lastQuery, futureSongPage);
        }
    }

    //Called when user click search on the soft keyboard
    private TextView.OnEditorActionListener searchListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            ViewUtils.hideSoftKeyboard(baseActivity);
            songsListView.closeOpenedItems();
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String text = v.getText().toString();
                processSearch(text, null);
                return true;
            }
            return false;
        }
    };

    private AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            //TODO Looks like a bug
            songsListView.closeOpenedItems();
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            final int lastItem = firstVisibleItem + visibleItemCount;
            if (lastItem == totalItemCount) {
                getNextSongsPage();
            }
        }
    };

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

    private SearchSongAdapter.AdapterCallback adapterCallback = new SearchSongAdapter.AdapterCallback() {
        @Override
        public void playClicked(PlayAbleSong model, int pos) {
            core.getPlayerManager().getPlayer(null).play(songsInAdapter, pos);
            mMediator.startPlayerActivity();
        }

        @Override
        public void addToQueueClicked(PlayAbleSong model) {
            core.getPlayerManager().getPlayer(null).add(model);
            Toast.makeText(baseActivity, String.format(getString(R.string.song_added_to_current_list), model.toString()), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void downloadClicked(PlayAbleSong model) {
            core.getDownloadManager().download(model);
        }
    };

    @Override
    public void needUpdate() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        core.unregisterListener(this);
    }
}
