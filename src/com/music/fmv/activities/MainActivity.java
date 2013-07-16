package com.music.fmv.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.music.fmv.R;
import com.music.fmv.adapters.FragmentAdapter;
import com.music.fmv.core.BaseActivity;
import com.music.fmv.core.BaseFragment;
import com.music.fmv.fragments.SearchFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    public static final int SEARCH_TAB = 0;
    public static final int FAVORITE_TAB = 1;
    public static final int MUSIC_TAB = 2;
    public static final int SETTINGS_TAB = 3;

    private ViewPager pager;


    private List<BaseFragment> fragments;

    @Override
    protected void onCreated(Bundle state) {
        setContentView(R.layout.main_activity);
        pager = (ViewPager) findViewById(R.id.pager);

        fragments = new ArrayList<BaseFragment>();
        fragments.add(SEARCH_TAB, createSearchTab());
        fragments.add(FAVORITE_TAB, createSearchTab());
        fragments.add(MUSIC_TAB, createSearchTab());
        fragments.add(SETTINGS_TAB, createSearchTab());

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(adapter);
    }

    private BaseFragment createSearchTab() {
        return new SearchFragment();
    }

    private View.OnClickListener tabListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){

            }
        }
    };
}
