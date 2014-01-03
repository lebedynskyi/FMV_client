package com.music.fms.activities;

import android.os.Bundle;
import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.fortysevendeg.swipelistview.SwipeListViewListener;
import com.music.fms.R;
import com.music.fms.adapters.NativeSongAdapter;
import com.music.fms.core.BaseActivity;

/**
 * User: vitaliylebedinskiy
 * Date: 8/29/13
 * Time: 4:18 PM
 */
public class ArtistSongsActivity extends BaseActivity {
    private SwipeListView songsListViwe;
    private NativeSongAdapter adapter;

    @Override
    protected void onCreated(Bundle state) {
        setContentView(R.layout.artist_songs_activity);
    }

    private SwipeListViewListener listListener = new BaseSwipeListViewListener() {
        @Override
        public void onOpened(int position, boolean toRight) {

        }

        @Override
        public void onClickFrontView(int position) {

        }

        @Override
        public void onClickBackView(int position) {

        }
    };
}

