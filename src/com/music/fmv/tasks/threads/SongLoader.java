package com.music.fmv.tasks.threads;

import com.music.fmv.api.Api;
import com.music.fmv.models.PlayAbleSong;
import com.music.fmv.network.Network;
import com.music.fmv.network.NetworkRequest;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 8/18/13
 * Time: 12:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class SongLoader implements IDownlaoder {
    private final File file;
    private final PlayAbleSong song;
    private IDownloadListener listener;
    private Api api;

    public SongLoader(File f, PlayAbleSong songs) {
        this.song = songs;
        this.file = f;
        api = new Api();
    }

    @Override
    public void run() {
        try {
            String songUrl = api.getUrlOfSong(song);
            FileOutputStream outputStream = new FileOutputStream(file);
            Network.doRequest(new NetworkRequest(songUrl)).readData(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onError(song.getName());
            return;
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
