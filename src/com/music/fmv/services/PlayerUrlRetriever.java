package com.music.fmv.services;

import android.content.Context;
import com.music.fmv.api.Api;
import com.music.fmv.models.PlayAbleSong;
import com.music.fmv.tasks.BaseAsyncTask;

/**
 * Created with IntelliJ IDEA.
 * User: Vitalii Lebedynskyi
 * Date: 12/19/13
 * Time: 6:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlayerUrlRetriever extends BaseAsyncTask<PlayAbleSong> {
    private final PlayAbleSong song;

    protected PlayerUrlRetriever(Context context, PlayAbleSong song) {
        super(context, false);
        this.song = song;
    }

    @Override
    protected PlayAbleSong doInBackground(Object... params) {
        Api api = new Api();
        try {
            String url = api.getUrlOfSong(song);
            song.setUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
            isError = true;
        }
        return song;
    }
}
