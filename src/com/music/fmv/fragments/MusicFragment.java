package com.music.fmv.fragments;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.music.fmv.R;
import com.music.fmv.adapters.FragmentAdapter;
import com.music.fmv.core.BaseFragment;
import com.music.fmv.utils.ViewUtils;
import com.music.fmv.widgets.RefreshableViewPager;

import java.util.ArrayList;

/**
 * User: Vitalii Lebedynskyi
 * Date: 7/22/13
 * Time: 12:08 PM
 */
public class MusicFragment extends BaseFragment{
    public static final int NATIVE_MUSIC_TAB = 0;
    public static final int LOADED_MUSIC_TAB = 1;

    private View loadedB;
    private View nativeB;
    private ViewPager pager;

    @Override
    protected View createView(Bundle savedInstanceState) {
        View v = inflateView(R.layout.music_fragment);
        nativeB = v.findViewById(R.id.native_music_tab);
        loadedB = v.findViewById(R.id.loaded_music_tab);
        pager = (ViewPager) v.findViewById(R.id.music_pager);

        nativeB.setOnClickListener(tabsListener);
        loadedB.setOnClickListener(tabsListener);

        ArrayList<BaseFragment> fragments = new ArrayList<BaseFragment>();
        fragments.add(NATIVE_MUSIC_TAB, new NativeMusicListfragment());
        fragments.add(LOADED_MUSIC_TAB, new LoadedMusicListfragment());
        pager.setAdapter(new FragmentAdapter(baseActivity.getSupportFragmentManager(), fragments));
        pager.setOnPageChangeListener(pagerListener);

        ViewUtils.selectButton(nativeB, loadedB);

        return v;
    }

    public void nativeMusicClicked(){
        ViewUtils.selectButton(nativeB, loadedB);
        pager.setCurrentItem(NATIVE_MUSIC_TAB);
    }

    public void loadedMusicClicked(){
        ViewUtils.selectButton(loadedB, nativeB);
        pager.setCurrentItem(LOADED_MUSIC_TAB);
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

    private RefreshableViewPager.BasePageChangeListener pagerListener = new RefreshableViewPager.BasePageChangeListener() {
        @Override
        public void onPageSelected(int i) {
            switch (i){
                case NATIVE_MUSIC_TAB:
                    ViewUtils.selectButton(nativeB, loadedB);
                    break;
                case LOADED_MUSIC_TAB:
                    ViewUtils.selectButton(loadedB, nativeB);
            }
        }
    };
}
