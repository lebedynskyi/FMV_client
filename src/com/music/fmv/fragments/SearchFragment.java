package com.music.fmv.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import com.music.fmv.R;
import com.music.fmv.adapters.SearchBandAdapter;
import com.music.fmv.core.BaseFragment;
import com.music.fmv.models.SearchBandModel;
import com.music.fmv.tasks.SearchBandTask;
import com.music.fmv.utils.ViewUtils;
import com.music.fmv.views.GlowButton;
import com.music.fmv.views.LoadDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 7/14/13
 * Time: 9:03 AM
 * To change this template use File | Settings | File Templates.
 */

public class SearchFragment  extends BaseFragment{
    private ListView band_result_list;
    private ListView album_result_list;
    private ListView name_result_list;

    private GlowButton artistTab;
    private GlowButton albumTab;
    private GlowButton nameTab;

    private EditText searchField;

    private String lastArtistRequest;
    private String lastAlbumRequest;
    private String lastNameRequest;

    private SearchType searchType;


    private Integer futureArtistPage = 1;
    private int artistsPageAvaialble = 0;

    private ArrayList<SearchBandModel> searchBandList = new ArrayList<SearchBandModel>();
    private SearchBandAdapter searchBandAdapter;
    private View rotateFooter;
    private TextView empty_list_view;
    private View searchHeader;
    private LoadDialog dialog;

    private boolean artistTaskRunned;

    public enum SearchType {
        ARTIST, NAME, ALBUM
    }

    @Override
    public void createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.search_fragment, container, false);
        initUI(inflater);
        artistTabClicked();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void searchBand(String query, final Integer page){
        if (TextUtils.isEmpty(query) || artistTaskRunned) return;

        SearchBandTask task = new SearchBandTask(query, page, baseActivity){
            @Override
            protected void onPreExecute() {
                artistTaskRunned = true;
                if (page == null) {
                    dialog = new LoadDialog(baseActivity, this);
                    dialog.show();
                }
            }

            @Override
            protected void onPostExecute(List<SearchBandModel> searchBandModels) {
                if(dialog != null) dialog.dismiss();
                dialog = null;
                artistTaskRunned = false;
                artistsPageAvaialble = countOfPage;

                if (searchBandModels != null && searchBandModels.size() > 0){
                    updateBandList(searchBandModels, page == null);
                    return;
                }

                if (searchBandModels != null && searchBandModels.size() == 0){
                    Toast.makeText(baseActivity, getString(R.string.empty_result), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isError || searchBandModels == null){
                    Toast.makeText(baseActivity, getString(R.string.request_error), Toast.LENGTH_SHORT).show();
                }
            }
        };
        if (runTask(task)){
            lastArtistRequest = query;
        }
    }

    private void getNextBandPage(){
        if (artistsPageAvaialble == 0)  return;
        searchBand(lastArtistRequest, futureArtistPage);
    }

    private void updateBandList(List<SearchBandModel> searchBandModels,boolean isClear){
        if(isClear) {
            searchBandList.clear();
            futureArtistPage = 1;
        }
        searchBandList.addAll(searchBandModels);
        searchBandAdapter.notifyDataSetChanged();
        futureArtistPage += 1;
        //TODO CHECK COUNT OF AVAILABLE ITEMS
        if ((artistsPageAvaialble > 0)  && band_result_list.getFooterViewsCount() == 0){
            band_result_list.addFooterView(rotateFooter);
        }
        checkEmptyView();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void searchAlbum(String query){

    }

    private void getNextAlbumPage(){

    }

    private void updateAlbumList(){

    }

    //----------------------------------------------------------------------------------------------------------------//
    private void searchSongs(String query){

    }

    private void getNextSongsPage(){

    }

    private void updateSongsList(){

    }

    //called when last item on listViews is visible
    private void lastItemVisible() {
        //Happens on the first creating of fragment
        if(searchType == null) return;

        switch (searchType){
            case ALBUM:
                getNextAlbumPage();
                break;
            case NAME:
                getNextSongsPage();
                break;
            case ARTIST:
                getNextBandPage();
        }
    }

    //Called when user clicked search btn on soft keyboard.
    private void processSearch(String query) {
        ViewUtils.hideSoftKeyboard(getActivity());
        switch (searchType){
            case ALBUM:
                searchAlbum(query);
                break;
            case NAME:
                searchSongs(query);
                break;
            case ARTIST:
                searchBand(query, null);
        }
    }

    private void initUI(LayoutInflater inflater) {
        //Initialization of search result lists
        band_result_list = (ListView) mainView.findViewById(R.id.artist_result_list);
        name_result_list = (ListView) mainView.findViewById(R.id.name_result_list);
        album_result_list = (ListView) mainView.findViewById(R.id.album_result_list);

        //initialization of top tabs
        artistTab = (GlowButton) mainView.findViewById(R.id.by_artist);
        albumTab = (GlowButton) mainView.findViewById(R.id.by_album);
        nameTab = (GlowButton) mainView.findViewById(R.id.by_name);

        //Adding click listeneres to top tabs
        artistTab.setOnClickListener(tabListener);
        albumTab.setOnClickListener(tabListener);
        nameTab.setOnClickListener(tabListener);

        //Adding search field as header to lists
        searchHeader = initSearchHeader(inflater);
        band_result_list.addHeaderView(searchHeader);
        name_result_list.addHeaderView(searchHeader);
        album_result_list.addHeaderView(searchHeader);

        //Initialization of empty view for lists
        empty_list_view = (TextView) mainView.findViewById(R.id.empty_list_view);

        //Initialization of progress footer view
        rotateFooter = inflater.inflate(R.layout.rotate_footer, null, false);

        //Configuration of band_result_list
        searchBandAdapter = new SearchBandAdapter(searchBandList, baseActivity);
        band_result_list.setAdapter(searchBandAdapter);
        band_result_list.setOnScrollListener(scrollListener);
        band_result_list.setOnItemClickListener(bandClickListener);
    }

    //returns view with edit text for search.
    private View initSearchHeader(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.search_header, null, false);
        searchField = (EditText) v.findViewById(R.id.search_field);
        searchField.setOnEditorActionListener(searchListener);
        return v;
    }

    //Called when tab clicked
    private View.OnClickListener tabListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.by_artist:
                    artistTabClicked();
                    break;
                case R.id.by_album:
                    albumTabClicked();
                    break;
                case R.id.by_name:
                    nameTabClicked();
            }
        }
    };

    //Called when album tab clicked
    private void albumTabClicked() {
        ViewUtils.selectButton(albumTab, nameTab, artistTab);
        ViewUtils.setVisible(album_result_list, View.GONE, name_result_list, band_result_list);

        searchType = SearchType.ALBUM;
        checkEmptyView();
    }

    //Called when name tab clicked
    private void nameTabClicked() {
        ViewUtils.selectButton(nameTab, albumTab, artistTab);
        ViewUtils.setVisible(name_result_list, View.GONE, band_result_list, album_result_list);

        searchType = SearchType.NAME;
        checkEmptyView();
    }

    //Called when artist tab clicked
    private void artistTabClicked() {
        ViewUtils.selectButton(artistTab, albumTab, nameTab);
        ViewUtils.setVisible(band_result_list, View.GONE, name_result_list, album_result_list);

        searchType = SearchType.ARTIST;
        checkEmptyView();
    }

    private void checkEmptyView(){
        if(searchType == null) {
            empty_list_view.setVisibility(View.VISIBLE);
            return;
        }

        boolean hide = false;

        switch (searchType){
            case ARTIST:
                if (searchBandAdapter.getCount() > 0) hide = true;
                break;
            case NAME:
//                if (searchBandAdapter.getCount() > 0) hide = true;
                hide = false;
                break;
            case ALBUM:
                hide = false;
//                if (searchBandAdapter.getCount() > 0) hide = true;
        }

        if (hide) empty_list_view.setVisibility(View.GONE);
        else empty_list_view.setVisibility(View.VISIBLE);
    }


    //Click listener for list with bands.
    private AdapterView.OnItemClickListener bandClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int pos = position - band_result_list.getHeaderViewsCount();
            System.err.println("Position !!!!!!!!!    --->  " + pos);
        }
    };

    //Click listener for list with albums.
    private AdapterView.OnItemClickListener albumClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };

    //Click listener for list with songs.
    private AdapterView.OnItemClickListener songsClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };

    //Scroll listeners for lists, call method when last item in list is visible
    private AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        @Override public void onScrollStateChanged(AbsListView view, int scrollState) {}
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            final int lastItem = firstVisibleItem + visibleItemCount;
            if(lastItem == totalItemCount) {
                lastItemVisible();
            }
        }
    };

    //Called when user click search on the soft keyboard
    private TextView.OnEditorActionListener searchListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                processSearch(searchField.getText().toString());
                return true;
            }
            return false;
        }
    };
}
