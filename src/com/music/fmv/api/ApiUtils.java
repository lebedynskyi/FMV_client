package com.music.fmv.api;

import com.music.fmv.models.BandInfoModel;
import com.music.fmv.models.SearchBandModel;
import com.music.fmv.models.SimilarBandModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 7/16/13
 * Time: 8:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class ApiUtils {
    public static final String RESPONSE_TAG = "response";

    public static List<SearchBandModel> parseSearchBand(JSONObject response){
        ArrayList<SearchBandModel> result = new ArrayList<SearchBandModel>();
        JSONArray artists = response.optJSONArray(RESPONSE_TAG);
        if (artists!= null){
            for (int i = 0; i < artists.length(); i++) {
                try {
                    JSONObject artistData = artists.getJSONObject(i);
                    SearchBandModel model = new SearchBandModel();
                    model.setName(artistData.optString("name"));
                    model.setDescr(artistData.optString("descr"));
                    model.setUrl(artistData.optString("url"));
                    JSONArray genres = artistData.optJSONArray("genres");
                    if (genres != null){
                        ArrayList<String> modelGenres = new ArrayList<String>();
                        for (int j = 0; j < genres.length(); j++) {
                            modelGenres.add(genres.optString(i));
                        }
                        model.setGenres(modelGenres);
                    }
                    result.add(model);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static BandInfoModel parseBandInfo(JSONObject response){
        response = response.optJSONObject(RESPONSE_TAG);
        BandInfoModel model = new BandInfoModel();
        JSONArray images = response.optJSONArray("images");
        JSONArray similar = response.optJSONArray("similar");
        model.setDescr(response.optString("descr"));
        if (images !=null){
            ArrayList<String> imagesUrls = new ArrayList<String>();
            for (int i = 0; i < images.length(); i++) {
                imagesUrls.add(images.optString(i));
            }
            model.setImages(imagesUrls);
        }

        if (similar != null){
            ArrayList<SimilarBandModel> similarsModels = new ArrayList<SimilarBandModel>();
            for (int i = 0; i < similar.length(); i++) {
                JSONObject tempSimilarJson = similar.optJSONObject(i);
                SimilarBandModel similarBandModel = new SimilarBandModel() ;
                similarBandModel.setUrl(tempSimilarJson.optString("url"));
                similarBandModel.setImage(tempSimilarJson.optString("image"));
                similarBandModel.setName(tempSimilarJson.optString("name"));
                similarsModels.add(similarBandModel);
            }
            model.setSimilars(similarsModels);
        }

        return model;
    }
}


