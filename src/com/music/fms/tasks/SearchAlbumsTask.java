package com.music.fms.tasks;

import android.content.Context;
import com.music.fms.api.Api;
import com.music.fms.models.ModelType;
import com.music.fms.models.SearchAlbumModel;

import java.util.List;

/**
 * User: vitaliylebedinskiy
 * Date: 7/22/13
 * Time: 3:56 PM
 */
public class SearchAlbumsTask extends BaseAsyncTask<List<SearchAlbumModel>> {
    private static final ModelType  queryType = ModelType .ALBUM;

    private String searchQuery;
    private Integer page;

    public SearchAlbumsTask(Context context, String searchQuery, Integer page, boolean isShowLoader) {
        super(context, isShowLoader);
        this.searchQuery = searchQuery;
        this.page = page;
    }

    @Override
    protected final List<SearchAlbumModel> doInBackground(Object... params) {
        try {
            addQueryToDB(searchQuery, queryType);
            String language = core.getSettingsManager().getResultLanguage();
            return new Api().searchAlbum(searchQuery, language, page);
        } catch (Exception e) {
            e.printStackTrace();
            isError = true;
        }
        return null;
    }
}
