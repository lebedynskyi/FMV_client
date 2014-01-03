package com.music.fms.fragments;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.music.fms.R;
import com.music.fms.activities.FileChooserActivity;
import com.music.fms.adapters.FileListAdapter;
import com.music.fms.utils.FileLoader;

import java.io.File;
import java.util.List;

public class FileListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<File>> {

    private static final int LOADER_ID = 0;

    private FileListAdapter mAdapter;
    private String mPath;

    /**
     * Create a new instance with the given file path.
     *
     * @param path The absolute path of the file (directory) to display.
     * @return A new Fragment with the given file path.
     */
    public static FileListFragment newInstance(String path) {
        FileListFragment fragment = new FileListFragment();
        Bundle args = new Bundle();
        args.putString(FileChooserActivity.PATH, path);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new FileListAdapter(getActivity());
        mPath = getArguments() != null ? getArguments().getString(
                FileChooserActivity.PATH) : Environment
                .getExternalStorageDirectory().getAbsolutePath();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup layout = (ViewGroup) super.onCreateView(inflater, container, savedInstanceState);

        ViewGroup progressContainer = (ViewGroup) layout.getChildAt(0);
        ((ProgressBar) progressContainer.getChildAt(0)).setIndeterminateDrawable(getResources().getDrawable(R.drawable.blue_rotate));

        ListView lv = (ListView) layout.findViewById(android.R.id.list);

        View chooseHeader = inflater.inflate(R.layout.file_list_header, null, false);
        lv.addHeaderView(chooseHeader);
        lv.setAdapter(mAdapter);

        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        setListShown(false);
        getLoaderManager().initLoader(LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (position == 0) {
            ((FileChooserActivity) getActivity()).onFolderPicked(new File(mPath));
            return;
        }

        HeaderViewListAdapter adapter = (HeaderViewListAdapter) l.getAdapter();
        if (adapter != null) {
            File file = (File) adapter.getItem(position);
            mPath = file.getAbsolutePath();
            ((FileChooserActivity) getActivity()).onFolderClicked(file);
        }
    }

    @Override
    public Loader<List<File>> onCreateLoader(int id, Bundle args) {
        return new FileLoader(getActivity(), mPath);
    }

    @Override
    public void onLoadFinished(Loader<List<File>> loader, List<File> data) {
        mAdapter.setListItems(data);

        if (isResumed())
            setListShown(true);
        else
            setListShownNoAnimation(true);
    }

    @Override
    public void onLoaderReset(Loader<List<File>> loader) {
        mAdapter.clear();
    }
}