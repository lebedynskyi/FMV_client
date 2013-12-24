package com.music.fmv.tasks;

import android.os.Handler;
import com.music.fmv.core.DownloadManager;
import com.music.fmv.models.InternetSong;

/**
 * Created with IntelliJ IDEA.
 * User: Vitalii Lebedynskyi
 * Date: 12/24/13
 * Time: 4:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class SongsDownloader extends DownloadManager.IDownloader {
    private final Handler handler;
    private final InternetSong song;
    private DownloadManager.IDownloadListener listener;

    public SongsDownloader(Handler handler, InternetSong song) {
        super(handler);
        this.handler = handler;
        this.song = song;
    }

    @Override
    public void setDownloadListener(DownloadManager.IDownloadListener listener) {
        this.listener = listener;
    }

    @Override
    public int hashCode() {
        return song.hashCode();
    }

    @Override
    public void run() {

    }
}
