package com.music.fmv.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.music.fmv.R;
import com.music.fmv.core.BaseFragment;

/**
 * User: vitaliylebedinskiy
 * Date: 7/22/13
 * Time: 12:08 PM
 */
public class MusicFragment extends BaseFragment {
    @Override
    protected void createView(Bundle savedInstanceState) {
        mainView = inflateView(R.layout.music_fragment);
    }
}
