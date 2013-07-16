package com.music.fmv.core;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 7/14/13
 * Time: 9:03 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseFragment extends Fragment {
    protected Core core = Core.getInstance();
    protected View mainView;

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        createView(inflater, container, savedInstanceState);
        return mainView;
    }

    protected abstract void createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
}
