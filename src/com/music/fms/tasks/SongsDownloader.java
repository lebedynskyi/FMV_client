package com.music.fms.tasks;

import android.text.TextUtils;
import com.music.fms.api.Api;
import com.music.fms.core.Core;
import com.music.fms.core.DownloadManager;
import com.music.fms.models.InternetSong;
import com.music.fms.network.Network;
import com.music.fms.network.NetworkRequest;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: Vitalii Lebedynskyi
 * Date: 12/24/13
 * Time: 4:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class SongsDownloader extends DownloadManager.IDownloader {
    private final InternetSong song;
    private DownloadManager.IDownloadListener listener;

    public SongsDownloader(Core core, DownloadManager.IDownloadListener listener, InternetSong song) {
        super(core, listener);
        this.song = song;
    }

    @Override
    public void setDownloadListener(DownloadManager.IDownloadListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        if (!Network.isNetworkAvailable(core.getContext())){
            notifyError(song.toString(), DownloadManager.ERRORS.NET_UNAVAILABE);
            return;
        }

        notifyStart(song.toString());
        try {
            if (TextUtils.isEmpty(song.getUrl())){
                String downloadUrl = new Api().getUrlOfSong(song);
                song.setUrl(downloadUrl);
            }

            if(TextUtils.isEmpty(song.getUrl())){
                throw new RuntimeException("Cannot download song without a url");
            }

            downloadSong(song);
            notifyFinish(song.toString());
        }catch (Exception e){
            e.printStackTrace();
            notifyError(song.toString(), DownloadManager.ERRORS.UNKNOWN);
        }
    }

    private void downloadSong(InternetSong song) throws IOException {
        String futureFileName = song.getFutureFileName();
        String folder = core.getSettingsManager().getSongsFolder();
        File futureFile = new File(folder, futureFileName);

        if (futureFile.exists()){
            notifyError(song.toString(), DownloadManager.ERRORS.FILE_EXIST);
            return;
        }else {
            futureFile.getParentFile().mkdirs();
        }

        try {
            NetworkRequest request = new NetworkRequest(song.getUrl());
            BufferedInputStream in = new BufferedInputStream(Network.doRequest(request).getStream());
            FileOutputStream fout = new FileOutputStream(futureFile);

            byte data[] = new byte[2048];
            int count;
            while ((count = in.read(data, 0, 2048)) != -1){
                fout.write(data, 0, count);
            }
        }catch (Exception e){
            e.printStackTrace();
            futureFile.delete();
            throw new RuntimeException("Cannot download song " + song.getFutureFileName());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongsDownloader that = (SongsDownloader) o;
        return song.equals(that.song);
    }

    @Override
    public int hashCode() {
        return song.hashCode();
    }
}
