package com.music.fmv.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import com.music.fmv.R;
import com.music.fmv.adapters.SearchAlbumsAdapter;
import com.music.fmv.core.BaseFragment;
import com.music.fmv.models.SearchAlbumModel;
import com.music.fmv.tasks.SearchAlbumsTask;
import com.music.fmv.utils.ViewUtils;
import com.music.fmv.views.LoadDialog;

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

    private ListView albumsListsView;
    private ArrayList<SearchAlbumModel> albumsList = new ArrayList<SearchAlbumModel>(21);
    private SearchAlbumsAdapter adapter;
    private View rotateFooter;

    @Override
    protected void createView(Bundle savedInstanceState) {
        mainView =inflateView(R.layout.search_album_fagment);
        adapter = new SearchAlbumsAdapter(albumsList, baseActivity);

        albumsListsView = (ListView) mainView.findViewById(R.id.albums_list);
        albumsListsView.addHeaderView(createSearchHeader(searchListener));
        albumsListsView.setAdapter(adapter);
        albumsListsView.setOnItemClickListener(albumListener);
        albumsListsView.setHeaderDividersEnabled(false);
        albumsListsView.setOnScrollListener(scrollListener);

        rotateFooter = inflateView(R.layout.rotate_footer);
    }

    private void searchAlbum(String query, final Integer page){
        if (TextUtils.isEmpty(query) || albumTaskRunned) return;
        query = query.trim();

        SearchAlbumsTask task = new SearchAlbumsTask(baseActivity, query, page){
            public LoadDialog dialog;

            protected void onPreExecute() {
                albumTaskRunned = true;
                if (page == null) {
                    dialog = new LoadDialog(baseActivity, this);
                    dialog.show();
                }
            }

            @Override
            protected void onPostExecute(List<SearchAlbumModel> albums) {
                if(dialog != null) dialog.dismiss();
                dialog = null;
                albumPageAvailable = SearchAlbumModel.AVAILABLE_PAGES;
                albumTaskRunned = false;

                if (isError || albums == null){
                    Toast.makeText(baseActivity, getString(R.string.request_error), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (albums.size() > 0){
                    updateAlbumList(albums, page == null);
                }else Toast.makeText(baseActivity, getString(R.string.empty_result), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void canceledByUser() {
                this.cancel(true);
                albumTaskRunned = false;
            }
        };

        if (runTask(task)) {
            albumTaskRunned = true;
            lastRequest = query;
        }
    }

    private void getNextAlbumPage(){
        if (albumPageAvailable > 0) {
            searchAlbum(lastRequest, futureAlbumPage);
        }
    }

    private void updateAlbumList(List<SearchAlbumModel> albums, boolean needClear){
        if(needClear) {
            albumsList.clear();
            futureAlbumPage = 1;
        }
        albumsList.addAll(albums);
        adapter.notifyDataSetChanged();
        futureAlbumPage += 1;

        if (albumPageAvailable <= 0){
            albumsListsView.removeFooterView(rotateFooter);
        }else if (albumPageAvailable > 0 && albumsListsView.getFooterViewsCount() == 0){
            albumsListsView.addFooterView(rotateFooter);
        }
    }

    //Scroll listeners for lists, call method when last item in list is visible
    private AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        @Override public void onScrollStateChanged(AbsListView view, int scrollState) {}

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            final int lastItem = firstVisibleItem + visibleItemCount;
            if(lastItem == totalItemCount) {
                getNextAlbumPage();
            }
        }
    };

    //Called when user click search on the soft keyboard
    private TextView.OnEditorActionListener searchListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                ViewUtils.hideSoftKeyboard(baseActivity);
                searchAlbum(v.getText().toString(), null);
                return true;
            }
            return false;
        }
    };

    private AdapterView.OnItemClickListener albumListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(baseActivity, "Not implemented", Toast.LENGTH_SHORT).show();
        }
    };
}
