package com.music.fmv.tasks.threads;

import com.music.fmv.models.SearchAlbumModel;

/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 8/18/13
 * Time: 12:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class AlbumLoader implements IDownlaoder {
    private SearchAlbumModel model;
    private IDownloadListener listener;

    private AlbumLoader(SearchAlbumModel model) {
        this.model = model;
    }

    @Override
    public void run() {

    }

    @Override
    public void setDownloadListener(IDownloadListener listener) {
        this.listener = listener;
    }
}