package com.music.fmv.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.music.fmv.R;
import com.music.fmv.adapters.FragmentAdapter;
import com.music.fmv.core.BaseActivity;
import com.music.fmv.core.BaseFragment;
import com.music.fmv.fragments.*;
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

    private int backPressed = 0;

    @Override
    protected void onCreated(Bundle state) {
        setContentView(R.layout.main_activity);
        pager = (ViewPager) findViewById(R.id.pager);

        searchBTN = (TabButton) findViewById(R.id.search_btn);
        historyBTN = (TabButton) findViewById(R.id.history_btn);
        musicBTN = (TabButton) findViewById(R.id.music_btn);
        settingsBTN = (TabButton) findViewById(R.id.settings_tab);

        searchBTN.initUI(getResources().getDrawable(R.drawable.search_tab_selector), tabListener);
        historyBTN.initUI(getResources().getDrawable(R.drawable.history_tab_selector), tabListener);
        musicBTN.initUI(getResources().getDrawable(R.drawable.music_tab_selector), tabListener);
        settingsBTN.initUI(getResources().getDrawable(R.drawable.settings_tab_selector), tabListener);

        List<BaseFragment> fragments = new ArrayList<BaseFragment>(4);
        fragments.add(SEARCH_TAB, createSearchTab());
        fragments.add(HISTORY_TAB, createHistoryTab());
        fragments.add(MUSIC_TAB, createMusicTab());
        fragments.add(SETTINGS_TAB, createSettingsTab());

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(4);
        pager.setOnPageChangeListener(pagerListener);
        searchTabClicked();
    }

    private BaseFragment createHistoryTab() {
        BaseFragment fragment = new HistoryFragment();
        return fragment;
    }

    private BaseFragment createSettingsTab() {
        BaseFragment fragment = new SettingsFragment();
        return fragment;
    }

    private BaseFragment createMusicTab() {
        BaseFragment fragment = new MusicFragment();
        return fragment;
    }

    private BaseFragment createSearchTab() {
        return new SearchFragment();
    }

    public void searchTabClicked() {
        ViewUtils.selectButton(searchBTN, musicBTN, historyBTN, settingsBTN);
        pager.setCurrentItem(SEARCH_TAB);
        sendScreenStatistic("Search tab");
    }

    public void musicTabClicked() {
        ViewUtils.selectButton(musicBTN, searchBTN, historyBTN, settingsBTN);
        pager.setCurrentItem(MUSIC_TAB);
        sendScreenStatistic("Music tab");
    }

    public void settingsTabClicked() {
        ViewUtils.selectButton(settingsBTN, searchBTN, musicBTN, historyBTN);
        pager.setCurrentItem(SETTINGS_TAB);
        sendScreenStatistic("Settings tab");
    }

    public void historyClicked() {
        ViewUtils.selectButton(historyBTN, searchBTN, musicBTN, settingsBTN);
        pager.setCurrentItem(HISTORY_TAB);
        sendScreenStatistic("History tab");
    }

    //Listener for buttons on the bottom of screen (Tabs)
    private TabButton.ClickCallBack tabListener = new TabButton.ClickCallBack() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
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

    //Listener for viewpager. OnPageSelected() clicked when page was changed by swiping
    private ViewPager.OnPageChangeListener pagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i2) {
        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }

        @Override
        public void onPageSelected(int i) {
            switch (i) {
                case SETTINGS_TAB:
                    settingsTabClicked();
                    break;
                case HISTORY_TAB:
                    historyClicked();
                    break;
                case MUSIC_TAB:
                    musicTabClicked();
                    break;
                case SEARCH_TAB:
                    searchTabClicked();
            }
        }
    };
}
