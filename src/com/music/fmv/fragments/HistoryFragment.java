package com.music.fmv.fragments;

import android.os.Bundle;
import android.view.View;
import com.music.fmv.R;
import com.music.fmv.core.BaseFragment;

/**
 * User: vitaliylebedinskiy
 * Date: 7/22/13
 * Time: 12:07 PM
 */
public class HistoryFragment extends BaseFragment {

    @Override
    protected View createView(Bundle savedInstanceState) {
        View view = inflateView(R.layout.history_fragment);
        return view;
    }
}
