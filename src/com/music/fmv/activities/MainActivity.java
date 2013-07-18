package com.music.fmv.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.music.fmv.R;
import com.music.fmv.adapters.FragmentAdapter;
import com.music.fmv.core.BaseActivity;
import com.music.fmv.core.BaseFragment;
import com.music.fmv.fragments.SearchFragment;
import com.music.fmv.utils.ViewUtils;
import com.music.fmv.views.TabButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    public static final int SEARCH_TAB = 0;
    public static final int HISTORY_TAB = 1;
    public static final int MUSIC_TAB = 2;
    public static final int SETTINGS_TAB = 3;

    private ViewPager pager;

    private TabButton searchBTN;
    private TabButton historyBTN;
    private TabButton musicBTN;
    private TabButton settingsBTN;


    private List<BaseFragment> fragments;

    @Override
    protected void onCreated(Bundle state) {
        setContentView(R.layout.main_activity);
        pager = (ViewPager) findViewById(R.id.pager);

        searchBTN = (TabButton) findViewById(R.id.search_btn);
        historyBTN = (TabButton) findViewById(R.id.history_btn);
        musicBTN = (TabButton) findViewById(R.id.music_btn);
        settingsBTN = (TabButton) findViewById(R.id.settings_tab);

        searchBTN.initUI(getResources().getDrawable(R.drawable.search_tab_selector), getString(R.string.search), tabListener);
        historyBTN.initUI(getResources().getDrawable(R.drawable.history_tab_selector), getString(R.string.history), tabListener);
        musicBTN.initUI(getResources().getDrawable(R.drawable.music_tab_selector), getString(R.string.music), tabListener);
        settingsBTN.initUI(getResources().getDrawable(R.drawable.settings_tab_selector), getString(R.string.settings), tabListener);

        fragments = new ArrayList<BaseFragment>(4);
        fragments.add(SEARCH_TAB, createSearchTab());
        fragments.add(HISTORY_TAB, createSearchTab());
        fragments.add(MUSIC_TAB, createSearchTab());
        fragments.add(SETTINGS_TAB, createSearchTab());

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(4);
        searchTabClicked();
    }

    private BaseFragment createSearchTab() {
        return new SearchFragment();
    }

    private TabButton.ClickCallBack tabListener = new TabButton.ClickCallBack() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.search_btn:
                    searchTabClicked();
                    break;
                case R.id.music_btn:
                    musicTabClicked();
                    break;
                case R.id.settings_tab:
                    settingsTabClicked();
                    break;
                case R.id.history_btn:
                    historyClicked();
            }
        }
    };

     public void searchTabClicked(){
         ViewUtils.selectButton(searchBTN, musicBTN, historyBTN, settingsBTN);
         pager.setCurrentItem(SEARCH_TAB);
     }

     public void musicTabClicked(){
         ViewUtils.selectButton(musicBTN, searchBTN, historyBTN, settingsBTN);
         pager.setCurrentItem(MUSIC_TAB);

     }

     public void settingsTabClicked(){
         ViewUtils.selectButton(settingsBTN, searchBTN, musicBTN, historyBTN);
         pager.setCurrentItem(SETTINGS_TAB);

     }

     public void historyClicked(){
         ViewUtils.selectButton(historyBTN, searchBTN, musicBTN, settingsBTN);
         pager.setCurrentItem(HISTORY_TAB);
     }
}
