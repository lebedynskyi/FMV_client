package com.music.fmv.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.music.fmv.R;
import com.music.fmv.core.BaseFragment;

/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 7/14/13
 * Time: 9:03 AM
 * To change this template use File | Settings | File Templates.
 */

public class SearchFragment  extends BaseFragment{

    @Override
    protected void createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.search_fragment, container, false);
        initUI();
    }

    private void initUI() {

    }
}
