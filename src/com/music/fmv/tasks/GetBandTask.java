package com.music.fmv.tasks;

import android.content.Context;
import com.music.fmv.models.BandInfoModel;
import com.music.fmv.models.SearchBandModel;

/**
 * User: vitaliylebedinskiy
 * Date: 8/6/13
 * Time: 10:08 AM
 */
public abstract class GetBandTask extends BaseAsyncTask<BandInfoModel> {
    protected GetBandTask(Context context, SearchBandModel model) {
        super(context);
    }

    @Override
    protected BandInfoModel doInBackground(Object... params) {
        try {
            return api.getBandInfo();
        }catch (Exception e){
            e.printStackTrace();
           isError = true;
        }
        return null;
    }
}
