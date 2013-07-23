package com.music.fmv.api;

import android.text.TextUtils;
import com.music.fmv.models.SearchBandModel;
import com.music.fmv.utils.NetworkUtil;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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


    //returns of List<SearchBandModel>
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

    //throws exception if Json response has error
    private void checkError(JSONObject response) {
        if (response.has("error")){
            throw new RuntimeException();
        }
    }

    //returns a valid url from Map
    private String generateUrl(Map<String, String> params){
        if (params.size() == 0) return "";

        Iterator<String> keys = params.keySet().iterator();
        StringBuilder urlBuilder = new StringBuilder();
        int counter = 0;

        while (keys.hasNext()){
            String key = keys.next();
            urlBuilder.append(counter++ == 0 ? "?" : "&").append(key).append("=").append(encodeString(params.get(key)));
        }

        return urlBuilder.toString();
    }

    public static String encodeString(String s){
        if (TextUtils.isEmpty(s)) return "";
        try {
            return URLEncoder.encode(s, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }
}
