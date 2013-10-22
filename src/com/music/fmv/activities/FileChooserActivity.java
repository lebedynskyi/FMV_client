package com.music.fmv.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.music.fmv.R;
import com.music.fmv.core.BaseActivity;
import com.music.fmv.fragments.FileListFragment;

import java.io.File;

/**
 * Main Activity that handles the FileListFragments
 *
 * @author paulburke (ipaulpro)
 * @version 2013-06-25
 */
public class FileChooserActivity extends BaseActivity implements OnBackStackChangedListener {
    public static final String PATH = "path";
    public static final String EXTERNAL_BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String RESULT_FILE = "RESULT_FILE";


    private FragmentManager mFragmentManager;
    private BroadcastReceiver mStorageListener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, R.string.storage_removed, Toast.LENGTH_LONG).show();
            finishWithResult(null);
        }
    };

    private String mPath;


    //TODO implemented logic for open folder in the middle of the filesystem tree.
    @Override
    protected void onCreated(Bundle savedInstanceState) {
        setContentView(R.layout.chooser);
        showActionBar();

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.addOnBackStackChangedListener(this);

        if (savedInstanceState == null) {
            mPath = EXTERNAL_BASE_PATH;
            addFragment();
        } else {
            mPath = savedInstanceState.getString(PATH);
        }

        setTitle(mPath);
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterStorageListener();
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerStorageListener();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(PATH, mPath);
    }

    @Override
    public void onBackStackChanged() {

        int count = mFragmentManager.getBackStackEntryCount();
        if (count > 0) {
            BackStackEntry fragment = mFragmentManager.getBackStackEntryAt(count - 1);
            mPath = fragment.getName();
        } else {
            mPath = EXTERNAL_BASE_PATH;
        }

        setTitle(mPath);
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean hasBackStack = mFragmentManager.getBackStackEntryCount() > 0;

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(hasBackStack);
        actionBar.setHomeButtonEnabled(hasBackStack);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mFragmentManager.popBackStack();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Add the initial Fragment with given path.
     */
    private void addFragment() {
        FileListFragment fragment = FileListFragment.newInstance(mPath);
        mFragmentManager.beginTransaction()
                .add(R.id.explorer_fragment, fragment).commit();
    }

    /**
     * "Replace" the existing Fragment with a new one using given path.
     * We're really adding a Fragment to the back stack.
     *
     * @param file The file (directory) to display.
     */
    private void replaceFragment(File file) {
        mPath = file.getAbsolutePath();

        FileListFragment fragment = FileListFragment.newInstance(mPath);
        mFragmentManager.beginTransaction()
                .replace(R.id.explorer_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(mPath).commit();
    }

    /**
     * Finish this Activity with a result code and URI of the selected file.
     *
     * @param file The file selected.
     */
    private void finishWithResult(File file) {
        if (file != null) {
            Uri uri = Uri.fromFile(file);
            Intent resultIntent = new Intent();
            resultIntent.setData(uri);
            resultIntent.putExtra(RESULT_FILE, file);
            setResult(RESULT_OK, resultIntent);
            finish();
        } else {
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    /**
     * Called when the user selects a File
     *
     * @param file The file that was selected
     */
    public void onFolderClicked(File file) {
        if (file != null) {
            if (file.isDirectory()) {
                replaceFragment(file);
            } else {
                finishWithResult(file);
            }
        } else {
            Toast.makeText(FileChooserActivity.this, R.string.error_selecting_file, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Register the external storage BroadcastReceiver.
     */
    private void registerStorageListener() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_MEDIA_REMOVED);
        registerReceiver(mStorageListener, filter);
    }

    /**
     * Unregister the external storage BroadcastReceiver.
     */
    private void unregisterStorageListener() {
        unregisterReceiver(mStorageListener);
    }

    public void onFolderPicked(File file) {
        finishWithResult(file);
    }
}
