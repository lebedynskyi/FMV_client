package com.music.fmv.activities;

import android.os.Bundle;
import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.fortysevendeg.swipelistview.SwipeListViewListener;
import com.music.fmv.R;
import com.music.fmv.adapters.SearchAlbumsAdapter;
import com.music.fmv.core.BaseActivity;

/**
 * User: vitaliylebedinskiy
 * Date: 8/29/13
 * Time: 4:18 PM
 */
public class ArtistAlbumsActivity extends BaseActivity {
    private SwipeListView albumsListView;
    private SearchAlbumsAdapter adapter;

    @Override
    protected void onCreated(Bundle state) {
        setContentView(R.layout.artist_album_activity);
    }

    private SwipeListViewListener listListener = new BaseSwipeListViewListener(){
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
