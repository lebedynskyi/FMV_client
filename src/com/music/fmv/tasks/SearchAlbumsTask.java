package com.music.fmv.tasks;

import android.content.Context;
import com.music.fmv.api.Api;
import com.music.fmv.models.dbmodels.ModelType;
import com.music.fmv.models.notdbmodels.SearchAlbumModel;

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
