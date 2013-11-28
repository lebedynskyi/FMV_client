package com.music.fmv.core;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.music.fmv.utils.ActivityMediator;
import com.music.fmv.utils.ViewUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Vitalii Lebedynskyi
 * Date: 7/14/13
 * Time: 9:03 AM
 * To change this template use File | Settings | File Templates.
 */

public abstract class BaseFragment extends Fragment {
    protected Core core;
    protected ActivityMediator mMediator;
    protected BaseActivity baseActivity;

    private LayoutInflater inflater;
    private View mainView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseActivity = (BaseActivity) getActivity();
        core = Core.getInstance(baseActivity);
        mMediator = new ActivityMediator(baseActivity);
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater = inflater;
        mainView = createView(savedInstanceState);
        ViewUtils.setUpKeyBoardHider(mainView, baseActivity);
        return mainView;
    }

    protected boolean runTask(AsyncTask task) {
        return baseActivity.runTask(task);
    }

    protected View inflateView(int id) {
        return inflater.inflate(id, null, false);
    }


    protected abstract View createView(Bundle savedInstanceState);
}
