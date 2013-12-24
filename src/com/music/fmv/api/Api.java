package com.music.fmv.api;

import android.text.TextUtils;
import com.music.fmv.models.*;
import com.music.fmv.network.Network;
import com.music.fmv.network.NetworkRequest;
import com.music.fmv.network.NetworkResponse;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vitalii Lebedynskyi
 * Date: 7/14/13
 * Time: 8:54 AM
 * To change this template use File | Settings | File Templates.
 */

public class Api {
    public static final int API_VERSION = 1;
    //test and real urls
    private static final String TEST_URL = "http://95.85.8.199/";
    private static final String REAL_URL = "http://vetal.romcheg.me/";

    //this url will used for requests
    public static final String API_URL = TEST_URL;

    //commands for api
    public static final String SEARCH_BAND_COMMAND = "artists.search";
    public static final String GET_BAND_COMMAND = "artists.get";
    public static final String SEARCH_ALBUMS_COMMAND = "albums.search";
    public static final String SEARCH_SONGS_COMMAND = "songs.search";

    public List<SearchBandModel> searchBand(String searchQuery, String language, Integer page) throws Exception {
        if (TextUtils.isEmpty(searchQuery)) throw new IllegalArgumentException("searchQuery cannot be empty");

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("q", searchQuery);
        params.put("lan", language);
        params.put("version", String.valueOf(API_VERSION));

        if (page != null) params.put("page", page.toString());

        NetworkResponse httpResponse = Network.doRequest(new NetworkRequest(API_URL + SEARCH_BAND_COMMAND, params));
        JSONObject jsonResponse = new JSONObject(httpResponse.readResponse());
        checkError(jsonResponse);
        return ApiUtils.parseSearchBand(jsonResponse);
    }


    public List<SearchAlbumModel> searchAlbum(String searchQuery, String language, Integer page) throws Exception {
        if (TextUtils.isEmpty(searchQuery)) throw new IllegalArgumentException("searchQuery cannot be empty");

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("q", searchQuery);
        params.put("lan", language);
        params.put("version", String.valueOf(API_VERSION));

        if (page != null) params.put("page", page.toString());


        NetworkResponse httpResponse = Network.doRequest(new NetworkRequest(API_URL + SEARCH_ALBUMS_COMMAND, params));
        JSONObject jsonResponse = new JSONObject(httpResponse.readResponse());
        checkError(jsonResponse);
        return ApiUtils.parseSearchAlbum(jsonResponse);
    }

    public ArrayList<InternetSong> searchSongs(String query, Integer page) throws Exception {
        if (TextUtils.isEmpty(query)) throw new IllegalArgumentException("searchQuery cannot be empty");

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("version", String.valueOf(API_VERSION));

        if (page != null) params.put("page", page.toString());

        NetworkResponse httpResponse = Network.doRequest(new NetworkRequest(API_URL + SEARCH_SONGS_COMMAND, params));
        JSONObject jsonResponse = new JSONObject(httpResponse.readResponse());
        checkError(jsonResponse);
        return ApiUtils.parseSearchSongs(jsonResponse);
    }

    public String getUrlOfSong(PlayAbleSong song) throws Exception {
        //TODO we need to implemented sending of version
        NetworkResponse httpResponse = Network.doRequest(new NetworkRequest(song.getUrlForUrl()));
        JSONObject jsonResponse = new JSONObject(httpResponse.readResponse());
        return jsonResponse.getString(song.getUrlKey());
    }

    public BandInfoModel getBandInfo() {
        return null;
    }

    //throws exception if Json response has error
    private void checkError(JSONObject response) throws Exception{
        if (response.has("error")) {
            throw new Exception(response.get("error").toString());
        }
    }
}
