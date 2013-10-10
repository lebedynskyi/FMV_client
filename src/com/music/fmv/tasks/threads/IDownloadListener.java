package com.music.fmv.tasks.threads;

/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 8/18/13
 * Time: 1:51 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IDownloadListener {
    public void onDownload(String name, int cur, int max);

    public void onDownloadFinished();

    public void onError(String name);
}