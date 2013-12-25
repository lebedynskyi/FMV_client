package com.music.fmv.core;

import android.os.Handler;
import com.music.fmv.models.InternetSong;
import com.music.fmv.models.SearchAlbumModel;
import com.music.fmv.tasks.SongsDownloader;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Vitalii Lebedynskyi
 * Date: 7/14/13
 * Time: 8:29 AM
 * To change this template use File | Settings | File Templates.
 */

public class DownloadManager extends Manager {
    public enum ERRORS{
        NET_UNAVAILABE, UNKNOWN, FILE_EXIST, FOLDER_EXIST
    }

    DownloadManager(Core core) {
        super(core);
    }

    @Override
    protected void finish() {
        loader.getQueue().clear();
        loader.shutdownNow();
        core.getNotificationManager().removeDownloading();
    }

    public boolean download(InternetSong song, IDownloadListener listener) {
        SongsDownloader downloader = new SongsDownloader(core.getHandler(), song);
        downloader.setDownloadListener(listener);
        if (loader.getQueue().contains(downloader)){
            return false;
        }
        loader.execute(downloader);
        return true;
    }

    public boolean download(SearchAlbumModel song, IDownloadListener listener) {
        return false;
    }

    private ThreadPoolExecutor loader = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()) {
        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            try {
                if (getQueue().size() > 1) {
                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            super.beforeExecute(t, r);
        }
    };

    public interface IDownloadListener {
        public void onDownloadStarted(String name);
        public void onDownload(String name, int cur, int max, int percent);
        public void onDownloadFinished(String name);
        public void onError(String name);
    }

    public static abstract class IDownloader implements Runnable{
        private final Handler handler;

        public IDownloader(Handler handler){
            this.handler = handler;
        }

        public Handler getHandler() {
            return handler;
        }

        public abstract void setDownloadListener(IDownloadListener listener);
        public abstract int hashCode();
    }
}
