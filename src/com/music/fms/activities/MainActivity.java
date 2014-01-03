package com.music.fms.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;
import com.music.fms.R;
import com.music.fms.adapters.FragmentAdapter;
import com.music.fms.core.BaseActivity;
import com.music.fms.core.BaseFragment;
import com.music.fms.core.ISearchFragment;
import com.music.fms.fragments.HistoryFragment;
import com.music.fms.fragments.MusicFragment;
import com.music.fms.fragments.SearchFragment;
import com.music.fms.fragments.SettingsFragment;
import com.music.fms.models.SearchQueryCache;
import com.music.fms.utils.ViewUtils;
import com.music.fms.views.TabButton;
import com.music.fms.widgets.RefreshableViewPager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends BaseActivity implements HistoryFragment.HistoryFragmentCallback {
    public static final int SEARCH_TAB = 0;
    public static final int HISTORY_TAB = 1;
    public static final int MUSIC_TAB = 2;
    public static final int SETTINGS_TAB = 3;
    public static final int BACK_CLICK_DELAY = 1500;

    private RefreshableViewPager pager;

    private TabButton searchBTN;
    private TabButton historyBTN;
    private TabButton musicBTN;
    private TabButton settingsBTN;

    private long lastBackTime = 0;

    @Override
    protected void onCreated(Bundle state) {
        setContentView(R.layout.main_activity);
        pager = (RefreshableViewPager) findViewById(R.id.pager);

        searchBTN = (TabButton) findViewById(R.id.search_btn);
        historyBTN = (TabButton) findViewById(R.id.history_btn);
        musicBTN = (TabButton) findViewById(R.id.music_btn);
        settingsBTN = (TabButton) findViewById(R.id.settings_tab);

        searchBTN.initUI(getResources().getDrawable(R.drawable.search_tab_selector));
        historyBTN.initUI(getResources().getDrawable(R.drawable.history_tab_selector));
        musicBTN.initUI(getResources().getDrawable(R.drawable.music_tab_selector));
        settingsBTN.initUI(getResources().getDrawable(R.drawable.settings_tab_selector));

        searchBTN.setOnClickListener(tabListener);
        historyBTN.setOnClickListener(tabListener);
        musicBTN.setOnClickListener(tabListener);
        settingsBTN.setOnClickListener(tabListener);

        ArrayList<BaseFragment> fragments = new ArrayList<BaseFragment>(4);
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
        sendScreenStatistic("Search tab");
    }

    public void musicTabClicked() {
        ViewUtils.selectButton(musicBTN, searchBTN, historyBTN, settingsBTN);
        sendScreenStatistic("Music tab");
    }

    public void settingsTabClicked() {
        ViewUtils.selectButton(settingsBTN, searchBTN, musicBTN, historyBTN);
        sendScreenStatistic("Settings tab");
    }

    public void historyClicked() {
        ViewUtils.selectButton(historyBTN, searchBTN, musicBTN, settingsBTN);
        sendScreenStatistic("History tab");
    }

    //Listener for buttons on the bottom of screen (Tabs)
    private View.OnClickListener tabListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.search_btn:
                    pager.setCurrentItem(SEARCH_TAB);
                    searchTabClicked();
                    break;
                case R.id.music_btn:
                    pager.setCurrentItem(MUSIC_TAB);
                    musicTabClicked();
                    break;
                case R.id.settings_tab:
                    pager.setCurrentItem(SETTINGS_TAB);
                    settingsTabClicked();
                    break;
                case R.id.history_btn:
                    pager.setCurrentItem(HISTORY_TAB);
                    historyClicked();
            }
        }
    };


    @Override
    public void onBackPressed() {
        Date d  = Calendar.getInstance().getTime();
        if ((d.getTime() - lastBackTime) < BACK_CLICK_DELAY){
            super.onBackPressed();
        }else {
            lastBackTime = d.getTime();
            Toast.makeText(this, R.string.press_one_more_to_exit, BACK_CLICK_DELAY).show();
        }
    }

    @Override
    public void onItemFromHistoryClicked(SearchQueryCache model) {
        searchTabClicked();
        pager.setCurrentItem(SEARCH_TAB);
        Fragment fr = pager.getFragment(SEARCH_TAB);
        if (fr != null && fr instanceof ISearchFragment){
            ((ISearchFragment) fr).search(model);
        }
    }

    //Listener for viewpager. OnPageSelected() clicked when page was changed by swiping
    private ViewPager.OnPageChangeListener pagerListener = new RefreshableViewPager.BasePageChangeListener() {
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
