package com.music.fmv.api;

import android.text.TextUtils;
import com.music.fmv.models.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 7/16/13
 * Time: 8:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class ApiUtils {
    public static final String RESPONSE_TAG = "response";


    public static String encodeString(String s){
        if (TextUtils.isEmpty(s)) return "";
        try {
            return URLEncoder.encode(s, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }

    //returns a valid url from Map
    public static String generateUrl(Map<String, String> params){
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

    public static List<SearchBandModel> parseSearchBand(JSONObject response)throws Exception{
        ArrayList<SearchBandModel> result = new ArrayList<SearchBandModel>();
        JSONObject resp = response.optJSONObject(RESPONSE_TAG);
        JSONArray artists = resp.optJSONArray("artists");
        if (artists!= null){
            for (int i = 0; i < artists.length(); i++) {
                try {
                    JSONObject artistData = artists.getJSONObject(i);
                    SearchBandModel model = new SearchBandModel();
                    model.setName(artistData.optString("name"));
                    model.setDescr(artistData.optString("descr"));
                    model.setUrl(artistData.optString("url"));
                    model.setImage(artistData.optString("image"));
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
        SearchBandModel.AVAILABLE_PAGES = resp.optInt("pages", -1);

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

    public static List<SearchAlbumModel> parseSearchAlbum(JSONObject response) throws Exception {
        ArrayList<SearchAlbumModel> albums = new ArrayList<SearchAlbumModel>();
        JSONObject resp = response.getJSONObject(RESPONSE_TAG);
        JSONArray albumsArr = resp.getJSONArray("albums");
        if (albumsArr == null) {
            SearchAlbumModel.AVAILABLE_PAGES = -1;
            return albums;
        }

        for (int i = 0; i < albumsArr.length(); i++) {
            try {
                SearchAlbumModel model = new SearchAlbumModel();
                JSONObject albumData = albumsArr.getJSONObject(i);
                model.setAlbumName(albumData.optString("album_name"));
                model.setAlbumUrl(albumData.optString("album_url"));
                model.setArtistName(albumData.optString("artist_name"));
                model.setArtistUrl(albumData.optString("artist_url"));
                model.setBriefDescr(albumData.optString("biref"));
                model.setImage(albumData.optString("image"));
                albums.add(model);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        SearchAlbumModel.AVAILABLE_PAGES = resp.optInt("pages", -1);
        return albums;
    }

    public static ArrayList<PlayableSong> parseSearchSongs(JSONObject response)throws Exception{
        ArrayList<PlayableSong> songs = new ArrayList<PlayableSong>();
        JSONObject resp = response.getJSONObject(RESPONSE_TAG);
        JSONArray songsArr = resp.getJSONArray("songs");
        if (songsArr == null) {
            PlayableSong.PAGE_AVAILABLE = -1;
            return songs;
        }

        for (int i = 0; i < songsArr.length(); i++) {
            PlayableSong song = new PlayableSong();
            JSONObject songData = songsArr.getJSONObject(i);
            try {
                song.setId(songData.optString("id"));
                song.setArtist(songData.optString("owner"));
                song.setDuration(Integer.parseInt(songData.optString("duration")));
                song.setTitle(songData.optString("name"));
                song.setRate(songData.optString("rate"));
                songs.add(song);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        PlayableSong.PAGE_AVAILABLE =  resp.optInt("pages", -1);
        return songs;
    }
}


