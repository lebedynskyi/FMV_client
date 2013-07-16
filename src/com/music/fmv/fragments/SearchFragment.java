package com.music.fmv.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.music.fmv.R;
import com.music.fmv.core.BaseFragment;
import com.music.fmv.models.SearchBandModel;
import com.music.fmv.tasks.SearchBandTask;
import com.music.fmv.utils.ViewUtils;
import com.music.fmv.views.GlowButton;

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
    private ListView artist_result_list;
    private ListView album_result_list;
    private ListView name_result_list;

    private GlowButton artistTab;
    private GlowButton albumTab;
    private GlowButton nameTab;

    private ArrayList<SearchBandModel> searchBandResult = new ArrayList<SearchBandModel>();

    @Override
    public void createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.search_fragment, container, false);
        initUI();
        artistTabClicked();
    }

    //-------------------------------------------------------------//
    public void searchBand(String query){

    }

    public void getNextBandPage(Integer page){

    }

    public void updateBandList(){

    }

    //-------------------------------------------------------------//
    public void searchAlbum(String query){

    }

    public void getNextAlbumPage(){

    }

    public void updateAlbumList(){

    }

    //-------------------------------------------------------------//
    public void searchSongs(String query){

    }

    public void getNextSongsPage(){

    }

    public void updateSongsList(){

    }

    private void initUI() {
        artist_result_list = (ListView) mainView.findViewById(R.id.artist_result_list);
        name_result_list = (ListView) mainView.findViewById(R.id.name_result_list);
        album_result_list = (ListView) mainView.findViewById(R.id.album_result_list);

        artistTab = (GlowButton) mainView.findViewById(R.id.artist_tab);
        albumTab = (GlowButton) mainView.findViewById(R.id.album_tab);
        nameTab = (GlowButton) mainView.findViewById(R.id.name_tab);

        artistTab.setOnClickListener(tabListener);
        albumTab.setOnClickListener(tabListener);
        nameTab.setOnClickListener(tabListener);
    }

    private View.OnClickListener tabListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.artist_tab:
                    artistTabClicked();
                    break;
                case R.id.album_tab:
                    albumTabClicked();
                    break;
                case R.id.name_tab:
                    nameTabClicked();

            }
        }
    };

    private void albumTabClicked() {
        ViewUtils.setVisible(album_result_list, View.GONE, name_result_list, artist_result_list);
    }

    private void nameTabClicked() {
        ViewUtils.setVisible(name_result_list, View.GONE, artist_result_list, album_result_list);
    }

    private void artistTabClicked() {
        ViewUtils.setVisible(artist_result_list, View.GONE, name_result_list, album_result_list);
    }

    private AdapterView.OnItemClickListener bandClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };

    private AdapterView.OnItemClickListener albumClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };

    private AdapterView.OnItemClickListener songsClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };
}
