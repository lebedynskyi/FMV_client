package com.music.fmv.fragments;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.music.fmv.R;
import com.music.fmv.core.BaseFragment;
import com.music.fmv.utils.ViewUtils;

import java.util.ArrayList;

/**
 * User: Vitalii Lebedynskyi
 * Date: 7/22/13
 * Time: 12:08 PM
 */
public class MusicFragment extends BaseFragment {
    private View loadedB;
    private View nativeB;
    private ViewPager pager;

    private ArrayList<BaseFragment> fragments;

    @Override
    protected View createView(Bundle savedInstanceState) {
        View v = inflateView(R.layout.music_fragment);
        nativeB = v.findViewById(R.id.native_music_tab);
        loadedB = v.findViewById(R.id.loaded_music_tab);
        pager = (ViewPager) v.findViewById(R.id.music_pager);

        fragments = new ArrayList<BaseFragment>();

        nativeB.setOnClickListener(tabsListener);
        loadedB.setOnClickListener(tabsListener);
        return v;
    }

    public void nativeMusicClicked(){
        ViewUtils.selectButton(nativeB, loadedB);
        pager.setCurrentItem(0);
    }

    public void loadedMusicClicked(){
        ViewUtils.selectButton(loadedB, nativeB);
        pager.setCurrentItem(1);
    }


    private View.OnClickListener tabsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.native_music_tab:
                    nativeMusicClicked();
                    break;
                case R.id.loaded_music_tab:
                    loadedMusicClicked();
                    break;
            }
        }
    };
}
