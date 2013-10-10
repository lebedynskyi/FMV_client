package com.music.fmv.tasks.threads;

import com.music.fmv.api.Api;
import com.music.fmv.models.PlayableSong;
import com.music.fmv.utils.NetworkUtil;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 8/18/13
 * Time: 12:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class SongLoader implements IDownlaoder {
    private final File file;
    private final PlayableSong song;
    private IDownloadListener listener;
    private Api api;

    public SongLoader(File f, PlayableSong songs) {
        this.song = songs;
        this.file = f;
        api = new Api();
    }

    @Override
    public void run() {
        if (song == null || file == null) return;
        try {
            String songUrl = api.getUrlOfSong(song.getId());
            if (songUrl == null) return;
            NetworkUtil.downloadFile(file, songUrl, listener);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onError(song.getName());
        }
        if (listener != null) listener.onDownloadFinished();
    }

    @Override
    public void setDownloadListener(IDownloadListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SongLoader that = (SongLoader) o;
        return !(song != null ? !song.equals(that.song) : that.song != null);
    }

    @Override
    public int hashCode() {
        return song != null ? song.hashCode() : 0;
    }
}
