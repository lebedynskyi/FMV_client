package com.music.fmv.tasks;

import android.content.Context;
import com.music.fmv.api.Api;
import com.music.fmv.models.notdbmodels.BandInfoModel;
import com.music.fmv.models.notdbmodels.SearchBandModel;

/**
 * User: vitaliylebedinskiy
 * Date: 8/6/13
 * Time: 10:08 AM
 */
public class GetBandTask extends BaseAsyncTask<BandInfoModel> {
    protected GetBandTask(Context context, SearchBandModel model, boolean isShowLoader) {
        super(context, isShowLoader);
    }

    @Override
    protected BandInfoModel doInBackground(Object... params) {
        try {
            return new Api().getBandInfo();
        } catch (Exception e) {
            e.printStackTrace();
            isError = true;
        }
        return null;
    }
}
