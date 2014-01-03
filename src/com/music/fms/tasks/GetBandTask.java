package com.music.fms.tasks;

import android.content.Context;
import com.music.fms.api.Api;
import com.music.fms.models.BandInfoModel;
import com.music.fms.models.SearchBandModel;

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
