package com.music.fmv.fragments;

import android.os.Bundle;
import android.view.View;
import com.music.fmv.R;
import com.music.fmv.adapters.FragmentAdapter;
import com.music.fmv.core.BaseFragment;
import com.music.fmv.utils.ViewUtils;
import com.music.fmv.views.GlowButton;
import com.music.fmv.widgets.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * User: vitaliylebedinskiy
 * Date: 8/5/13
 * Time: 12:39 PM
 */
public class SearchFragmentNew extends BaseFragment {
    public static int ARTIST_TAB = 0;
    public static int ALBUM_TAB = 1;
    public static int SONG_TAB = 2;

    private CustomViewPager pager;
    private GlowButton artistBtn;
    private  GlowButton albumButton;
    private GlowButton songsButton;

    @Override
    protected void createView(Bundle savedInstanceState) {
        mainView = inflateView(R.layout.search_fragment_new);
        pager = (CustomViewPager) mainView.findViewById(R.id.search_pager);
        pager.setCanScroll(false);

        artistBtn = (GlowButton) mainView.findViewById(R.id.artist_tab);
        albumButton = (GlowButton) mainView.findViewById(R.id.album_tab);
        songsButton = (GlowButton) mainView.findViewById(R.id.song_tab);

        artistBtn  .setOnClickListener(tabListener);
        albumButton.setOnClickListener(tabListener);
        songsButton.setOnClickListener(tabListener);

        List<BaseFragment> fragments = new ArrayList<BaseFragment>(3);
        fragments.add(ARTIST_TAB ,new SearchArtistFragment());
        fragments.add(ALBUM_TAB, new SearchAlbumFragment());
        fragments.add(SONG_TAB, new SearchSongsFragment());
        pager.setAdapter(new FragmentAdapter(getActivity().getSupportFragmentManager(), fragments));
        ViewUtils.selectButton(artistBtn, albumButton, songsButton);
    }


    private View.OnClickListener tabListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.artist_tab:
                    pager.setCurrentItem(ARTIST_TAB);
                    ViewUtils.selectButton(artistBtn, albumButton, songsButton);
                    break;
                case R.id.album_tab:
                    pager.setCurrentItem(ALBUM_TAB);
                    ViewUtils.selectButton(albumButton, artistBtn, songsButton);
                    break;
                case R.id.song_tab:
                    pager.setCurrentItem(SONG_TAB);
                    ViewUtils.selectButton(songsButton, albumButton, artistBtn);
            }
        }
    };
}
