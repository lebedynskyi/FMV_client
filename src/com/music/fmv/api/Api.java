package com.music.fmv.api;

import android.text.TextUtils;
import com.music.fmv.models.SearchBandModel;
import com.music.fmv.utils.NetworkUtil;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
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
    public static final String API_URL = "http://vetal.romcheg.me";

    public List<SearchBandModel> searchBand(String searchQuery, String language, Integer page){
        if (TextUtils.isEmpty(searchQuery)) throw new IllegalArgumentException("searchQuery cannot be null");
        String apiCommand = "artist.search";
        Map<String, String> params = new HashMap<String, String>();
        params.put("artist", searchQuery);
        params.put("lan", language);
        if (page != null){
            params.put("page", page.toString());
        }
        JSONObject response = new JSONObject(NetworkUtil.doRequest());
        return null;
    }

    public static String generateUrl(HashMap<String, String> params){

    }

    public static String encodePhrase(String phrase){
        try {
            return URLEncoder.encode(phrase, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return phrase;
    }
}
