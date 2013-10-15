package com.music.fmv.fragments;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;
import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.music.fmv.R;
import com.music.fmv.adapters.SearchAlbumsAdapter;
import com.music.fmv.core.BaseFragment;
import com.music.fmv.models.dbmodels.ModelType;
import com.music.fmv.models.notdbmodels.SearchAlbumModel;
import com.music.fmv.tasks.SearchAlbumsTask;
import com.music.fmv.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: vitaliylebedinskiy
 * Date: 8/5/13
 * Time: 12:32 PM
 */
public class SearchAlbumFragment extends BaseFragment {
    private boolean albumTaskRunned;
    private Integer futureAlbumPage = 1;
    private int albumPageAvailable = 0;
    private String lastRequest;

    private SwipeListView albumsListsView;
    private ArrayList<SearchAlbumModel> albumsList = new ArrayList<SearchAlbumModel>(21);
    private SearchAlbumsAdapter adapter;
    private View rotateFooter;
    private TextView emptyView;

    @Override
    protected View createView(Bundle savedInstanceState) {
        View mainView = inflateView(R.layout.search_album_fagment);
        albumsListsView = (SwipeListView) mainView.findViewById(R.id.albums_list);
        adapter = new SearchAlbumsAdapter(albumsList, baseActivity, albumsListsView);

        emptyView = (TextView) mainView.findViewById(R.id.empty_view);


        albumsListsView.addHeaderView(createSearchHeader(searchListener, ModelType.ALBUM));
        albumsListsView.setAdapter(adapter);
        albumsListsView.setHeaderDividersEnabled(false);
        albumsListsView.setOnScrollListener(scrollListener);
        albumsListsView.setSwipeListViewListener(albumsLIstener);
        rotateFooter = inflateView(R.layout.rotate_footer);
        return mainView;
    }

    private void searchAlbum(String query, final Integer page) {
        if (query == null || query.trim().length() == 0 || albumTaskRunned) return;
        query = query.trim();

        SearchAlbumsTask task = new SearchAlbumsTask(baseActivity, query, page, page == null) {
            protected void onPreExecute() {
                super.onPreExecute();
                albumTaskRunned = true;
            }

            @Override
            protected void onPostExecute(List<SearchAlbumModel> albums) {
                super.onPostExecute(albums);
                albumPageAvailable = SearchAlbumModel.AVAILABLE_PAGES;
                albumTaskRunned = false;

                if (isCancelled()) return;

                if (isError || albums == null) {
                    Toast.makeText(baseActivity, getString(R.string.request_error), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (albums.size() > 0) {
                    updateAlbumList(albums, page == null);
                } else Toast.makeText(baseActivity, getString(R.string.empty_result), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void canceledByUser() {
                super.canceledByUser();
                albumTaskRunned = false;
            }
        };

        if (runTask(task)) {
            lastRequest = query;
        }
    }

    private void getNextAlbumPage() {
        if (albumPageAvailable > 0 && futureAlbumPage <= albumPageAvailable) {
            searchAlbum(lastRequest, futureAlbumPage);
        }
    }

    private void updateAlbumList(List<SearchAlbumModel> albums, boolean needClear) {
        if (needClear) {
            albumsList.clear();
            futureAlbumPage = 1;
        }
        albumsList.addAll(albums);
        adapter.notifyDataSetChanged();
        futureAlbumPage += 1;

        if (albumPageAvailable <= 0) {
            albumsListsView.removeFooterView(rotateFooter);
        } else if (albumPageAvailable > 0 && albumsListsView.getFooterViewsCount() == 0) {
            albumsListsView.addFooterView(rotateFooter);
        }

        if (albumsListsView.getCount() == 0) {
            emptyView.setVisibility(View.VISIBLE);
        } else emptyView.setVisibility(View.GONE);
    }

    //Scroll listeners for lists, call method when last item in list is visible
    private AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            //TODO Looks like a bug
            albumsListsView.closeOpenedItems();
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            final int lastItem = firstVisibleItem + visibleItemCount;
            if (lastItem == totalItemCount) {
                getNextAlbumPage();
            }
        }
    };

    //Called when user click search on the soft keyboard
    private TextView.OnEditorActionListener searchListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            ViewUtils.hideSoftKeyboard(baseActivity);
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchAlbum(v.getText().toString(), null);
                return true;
            }
            return false;
        }
    };

    private BaseSwipeListViewListener albumsLIstener = new BaseSwipeListViewListener() {
        @Override
        public void onClickFrontView(int position) {
            if (albumsListsView.isOpened(position)) {
                albumsListsView.closeAnimate(position);
            } else albumsListsView.openAnimate(position);
        }

        @Override
        public void onClickBackView(int position) {
            if (albumsListsView.isOpened(position)) {
                albumsListsView.closeAnimate(position);
            } else albumsListsView.openAnimate(position);
        }
    };
}
