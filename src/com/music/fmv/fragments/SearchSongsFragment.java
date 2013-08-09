package com.music.fmv.fragments;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.music.fmv.R;
import com.music.fmv.core.BaseFragment;
import com.music.fmv.models.SearchSongModel;

import java.util.ArrayList;

/**
 * User: vitaliylebedinskiy
 * Date: 8/5/13
 * Time: 12:32 PM
 */
public class SearchSongsFragment extends BaseFragment{
    private int availableSongsCount = 0;

    private ListView songsListView;
    private ArrayList<SearchSongModel> reserveSongs;
    private ArrayList<SearchSongModel> songsInAdapter = new ArrayList<SearchSongModel>();


    @Override
    protected void createView(Bundle savedInstanceState) {
        mainView = inflateView(R.layout.search_songs_fragment);
        songsListView = (ListView) mainView.findViewById(R.id.songs_list);
        songsListView.setOnItemClickListener(songsListener);
        songsListView.addHeaderView(createSearchHeader(searchListener));
        songsListView.setHeaderDividersEnabled(false);
        songsListView.setAdapter(null);
    }

    private AdapterView.OnItemClickListener songsListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };

    private void processSearch(String s) {
        Toast.makeText(baseActivity, "Not implemented", Toast.LENGTH_SHORT).show();
    }

    //Called when user click search on the soft keyboard
    private TextView.OnEditorActionListener searchListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                processSearch(v.getText().toString());
                return true;
            }
            return false;
        }
    };
}
