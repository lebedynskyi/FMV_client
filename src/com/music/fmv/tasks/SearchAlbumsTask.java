package com.music.fmv.tasks;

import android.content.Context;
import com.music.fmv.models.SearchAlbumModel;
import com.music.fmv.models.SearchBandModel;

import java.util.List;

/**
 * User: vitaliylebedinskiy
 * Date: 7/22/13
 * Time: 3:56 PM
 */
public class SearchAlbumsTask extends BaseAsyncTask<List<SearchAlbumModel>>{
    private String searchQuery;
    private Integer page;

    public SearchAlbumsTask(Context context, String searchQuery, Integer page) {
        super(context);
        this.searchQuery = searchQuery;
        this.page = page;
    }

    @Override
    protected final List<SearchAlbumModel> doInBackground(Object... params) {
        return null;
    }
}
