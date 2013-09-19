package com.music.fmv.tasks;

import android.content.Context;
import com.music.fmv.core.Core;
import com.music.fmv.models.SearchAlbumModel;

import java.util.List;

/**
 * User: vitaliylebedinskiy
 * Date: 7/22/13
 * Time: 3:56 PM
 */
public abstract class SearchAlbumsTask extends BaseAsyncTask<List<SearchAlbumModel>>{
    private String searchQuery;
    private Integer page;

    public SearchAlbumsTask(Context context, String searchQuery, Integer page, boolean isShowLoader) {
        super(context, isShowLoader);
        this.searchQuery = searchQuery;
        this.page = page;
    }

    @Override
    protected final List<SearchAlbumModel> doInBackground(Object... params) {
        String language = Core.getInstance(context).getSettingsManager().getResultLanguage();
        try {
            return api.searchAlbum(searchQuery, language, page);
        } catch (Exception e) {
            e.printStackTrace();
            isError = true;
        }
        return null;
    }
}
