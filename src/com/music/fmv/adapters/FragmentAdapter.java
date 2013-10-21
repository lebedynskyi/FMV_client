package com.music.fmv.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.music.fmv.core.BaseFragment;
import com.music.fmv.core.Refreshable;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 7/14/13
 * Time: 9:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    List<BaseFragment> fragments;

    public FragmentAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fr = fragments.get(i);
        if (fr instanceof Refreshable){
            ((Refreshable) fr).refresh();
        }
        return fr;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
