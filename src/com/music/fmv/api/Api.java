package com.music.fmv.api;

import android.text.TextUtils;
import com.music.fmv.models.BandInfoModel;
import com.music.fmv.models.SearchAlbumModel;
import com.music.fmv.models.SearchBandModel;
import com.music.fmv.utils.NetworkUtil;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import static com.music.fmv.api.ApiUtils.generateUrl;

/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 7/14/13
 * Time: 8:54 AM
 * To change this template use File | Settings | File Templates.
 */

public class Api {
    //test and real urls
    private static final String TEST_URL = "http://vetal.romcheg.me/";
    private static final String REAL_URL = "http://vetal.romcheg.me/";

    //this url will used for requests
    public static final String API_URL = TEST_URL;

    //commands for api
    public static final String SEARCH_BAND_COMMAND = "artist.search";
    public static final String GET_BAND_COMMAND = "artist.get";
    public static final String SEARCH_ALBUMS_COMMAND = "albums.search";


    //returns List<SearchBandModel>
    public List<SearchBandModel> searchBand(String searchQuery, String language, Integer page) throws Exception {
        if (TextUtils.isEmpty(searchQuery)) throw new IllegalArgumentException("searchQuery cannot be empty");
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("artist", searchQuery);
        params.put("lan", language);

        if (page != null){
            params.put("page", page.toString());
        }

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(API_URL).append(SEARCH_BAND_COMMAND).append(generateUrl(params));
        String jsonResponse = NetworkUtil.doRequest(urlBuilder.toString());

        if (!TextUtils.isEmpty(jsonResponse)){
            JSONObject response = new JSONObject(jsonResponse);
            checkError(response);
            return ApiUtils.parseSearchBand(response);
        }

        return null;
    }


    //returns List<SearchAlbumModel>
    public List<SearchAlbumModel> searchAlbum(String searchQuery, String language, Integer page)throws Exception{
        if (TextUtils.isEmpty(searchQuery)) throw new IllegalArgumentException("searchQuery cannot be empty");
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("album", searchQuery);
        params.put("lan", language);
        if(page != null){
            params.put("page", page.toString());
        }

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(API_URL).append(SEARCH_ALBUMS_COMMAND).append(generateUrl(params));

        String jsonResponse = NetworkUtil.doRequest(urlBuilder.toString());

        if (!TextUtils.isEmpty(jsonResponse)){
            JSONObject response =new JSONObject(jsonResponse);
            checkError(response);
            return ApiUtils.parseSearchAlbum(response);
        }

        return null;
    }

    public BandInfoModel getBandInfo() {
        return null;
    }

    //throws exception if Json response has error
    private void checkError(JSONObject response) {
        if (response.has("error")){
            throw new RuntimeException("Error in api response");
        }
    }
}
