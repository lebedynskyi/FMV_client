package com.music.fmv.tasks.threads;

/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 8/18/13
 * Time: 1:50 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IDownlaoder extends Runnable {
    public void setDownloadListener(IDownloadListener listener);
}

