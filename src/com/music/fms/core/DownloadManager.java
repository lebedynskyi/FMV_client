package com.music.fms.core;

import com.music.fms.models.InternetSong;
import com.music.fms.models.SearchAlbumModel;
import com.music.fms.tasks.SongsDownloader;

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
        SongsDownloader downloader = new SongsDownloader(core, listener, song);
        if (loader.getQueue().contains(downloader)){
            return false;
        }else {
            loader.execute(downloader);
            return true;
        }
    }

    public boolean download(SearchAlbumModel song, IDownloadListener listener) {
        return false;
    }

    private ThreadPoolExecutor loader = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()) {
        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            super.afterExecute(r, t);
        }
    };

    public interface IDownloadListener {
        public void onDownloadStarted(String name);
        public void onDownload(String name, int cur, int max);
        public void onDownloadFinished(String name);
        public void onError(String name, ERRORS error);
    }

    public static abstract class IDownloader implements Runnable{
        protected final Core core;
        private IDownloadListener listener;

        public IDownloader(Core handler, IDownloadListener listener){
            this.core = handler;
            this.listener = listener;
        }

        public void notifyStart(final String text){
            if (listener == null) return;
            core.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    listener.onDownloadStarted(text);
                }
            });
        }

        public void notifyOnDownload(final String name, final int cur, final int max){
            if (listener == null) return;
            core.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    listener.onDownload(name, cur, max);
                }
            });
        }

        public void notifyFinish(final String text){
            if (listener == null) return;
            core.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    listener.onDownloadFinished(text);
                }
            });
        }

        public void notifyError(final String text, final ERRORS errors){
            if (listener == null) return;
            core.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    listener.onError(text, errors);
                }
            });
        }

        public abstract void setDownloadListener(IDownloadListener listener);
        public abstract int hashCode();
    }
}
