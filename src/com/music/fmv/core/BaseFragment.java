package com.music.fmv.core;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.music.fmv.R;
import com.music.fmv.utils.ActivityMediator;
import com.music.fmv.utils.NetworkUtil;
import com.music.fmv.utils.ViewUtils;

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
    protected ActivityMediator mMediator;
    protected BaseActivity baseActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMediator = new ActivityMediator(getActivity());
        baseActivity = (BaseActivity) getActivity();
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        createView(inflater, container, savedInstanceState);
        ViewUtils.setUpKeyBoardHider(mainView, getActivity());
        return mainView;
    }

    protected boolean runTask(AsyncTask task){
        if (NetworkUtil.isOnline(getActivity())){
            task.execute();
            return true;
        }
        Toast.makeText(getActivity(), getString(R.string.network_unavailable), Toast.LENGTH_SHORT).show();
        return false;
    }

    protected abstract void createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
}
