package com.music.fmv.core.managers;

import com.music.fmv.R;
import com.music.fmv.core.Core;
import com.music.fmv.models.PlayableSong;
import com.music.fmv.tasks.threads.IDownloadListener;
import com.music.fmv.tasks.threads.SongLoader;
import com.music.fmv.utils.NetworkUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 7/14/13
 * Time: 8:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class DownloadManager extends Manager {
    public DownloadManager(Core coreManager) {
        super(coreManager);
    }

    @Override
    protected void finish() {
        loaderExecutor.getQueue().clear();
        loaderExecutor.shutdownNow();
        if (loaderExecutor.getQueue().isEmpty()){
            core.getNotificationManager().removeDownloading();
        }
    }

    public void download(ArrayList<PlayableSong> album){
        for (PlayableSong song: album){
            download(song, downloadListener);
        }
    }

    public void download(PlayableSong model) {
        download(model, downloadListener);
    }


    public void download(PlayableSong model, IDownloadListener listener) {
        if (!NetworkUtil.isNetworkAvailable(core.getContext())){
            core.showToast(R.string.network_unavailable);
            return;
        }

        String loadFolder = core.getSettingsManager().getDownloadFolder();
        File folder = new File(loadFolder);
        folder.mkdirs();
        File newSongFile = model.getAbsolutheFile(folder);
        if (newSongFile.exists()){
            core.showToast(R.string.file_already_exists);
            return;
        }

        SongLoader loader = new SongLoader(newSongFile, model);
        loader.setDownloadListener(listener);
        if (!loaderExecutor.getQueue().contains(loader)){
            loaderExecutor.execute(loader);
        }else core.showToast(R.string.already_in_queue);
    }

    private IDownloadListener downloadListener = new IDownloadListener() {
        private ArrayList<String> failedSongs = new ArrayList<String>();
        @Override
        public void onDownload(String name, int percent, int max) {
            core.getNotificationManager().notifyDownloading(name, percent, max);
        }

        @Override
        public void onDownloadFinished() {
            if (loaderExecutor.getQueue().isEmpty()){
                if (!failedSongs.isEmpty()){
                    core.getNotificationManager().notifyErrorDownloading(failedSongs.toString());
                    failedSongs.clear();
                }else core.getNotificationManager().notifySuccessDownloading();
            }
            core.callUpdateOnListeners();
        }

        @Override
        public void onError(String name) {
            failedSongs.add(name);
        }
    };

    private ThreadPoolExecutor loaderExecutor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()){
        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            super.beforeExecute(t, r);
            try {
                if (getQueue().size() > 1){
                    Thread.sleep(3500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}
