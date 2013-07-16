package com.music.fmv.api;

import android.text.TextUtils;
import com.music.fmv.models.SearchBandModel;
import com.music.fmv.utils.NetworkUtil;
import org.json.JSONException;
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
    public static final String API_URL = "http://vetal.romcheg.me/";
    public static final String SEARCH_BAND_COMMAND = "artist.search";


    public List<SearchBandModel> searchBand(String searchQuery, String language, Integer page) throws Exception {
        if (TextUtils.isEmpty(searchQuery)) throw new IllegalArgumentException("searchQuery cannot be null");
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("artist", searchQuery);
        params.put("lan", language);

        if (page != null){
            params.put("page", page.toString());
        }

        String url = API_URL + SEARCH_BAND_COMMAND + generateUrl(params);
        String jsonResponse = NetworkUtil.doRequest(url);

        if (!TextUtils.isEmpty(jsonResponse)){
            JSONObject response = new JSONObject(jsonResponse);
            checkError(response);
            return ApiUtils.parseSearchBand(response);
        }

        return null;
    }

    private void checkError(JSONObject response) {
        if (response.has("error")){
            throw new ApiErrorException();
        }
    }

    private String generateUrl(Map<String, String> params){
        if (params.size() == 0) return "";

        Iterator<String> keys = params.keySet().iterator();
        StringBuilder urlBuilder = new StringBuilder();
        int counter = 0;

        while (keys.hasNext()){
            String key = keys.next();
            urlBuilder.append(counter++ == 0 ? "?" : "&").append(key).append("=").append(params.get(key));
        }

        return urlBuilder.toString();
    }

    private String encodePhrase(String phrase){
        if (TextUtils.isEmpty(phrase)) return "";

        try {
            return URLEncoder.encode(phrase, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return phrase;
    }
}
