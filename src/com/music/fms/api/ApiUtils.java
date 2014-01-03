package com.music.fms.api;

import com.music.fms.models.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Vitalii Lebednskyi
 * Date: 7/16/13
 * Time: 8:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class ApiUtils {
    public static final String RESPONSE_TAG = "response";

    public static List<SearchBandModel> parseSearchBand(JSONObject response) throws Exception {
        ArrayList<SearchBandModel> result = new ArrayList<SearchBandModel>();
        JSONArray bands = response.optJSONArray(RESPONSE_TAG);
        if (bands == null || bands.length() == 0){
            SearchBandModel.AVAILABLE_PAGES = -1;
            return result;
        }

        final int bandsLegth = bands.length();
        for (int i = 0; i < bandsLegth; i++) {
            try {
                JSONObject artistData = bands.getJSONObject(i);
                SearchBandModel model = new SearchBandModel();
                model.setName(artistData.optString("name"));
                model.setDescr(artistData.optString("descr"));
                model.setUrl(artistData.optString("url"));
                model.setImage(artistData.optString("image"));
                result.add(model);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        SearchBandModel.AVAILABLE_PAGES = response.optInt("pages", -1);

        return result;
    }

    public static BandInfoModel parseBandInfo(JSONObject response) {

        return null;
    }

    public static List<SearchAlbumModel> parseSearchAlbum(JSONObject response) throws Exception {
        ArrayList<SearchAlbumModel> result = new ArrayList<SearchAlbumModel>();
        JSONArray albums = response.optJSONArray(RESPONSE_TAG);
        if (albums == null || albums.length() == 0){
            SearchAlbumModel.AVAILABLE_PAGES = -1;
            return result;
        }

        final int albumsLength = albums.length();
        for (int i = 0; i < albumsLength; i++) {
            try {
                SearchAlbumModel model = new SearchAlbumModel();
                JSONObject albumData = albums.getJSONObject(i);
                model.setAlbumName(albumData.optString("name"));
                model.setAlbumUrl(albumData.optString("url"));
                model.setArtistName(albumData.optString("artist"));
                model.setArtistUrl(albumData.optString("artist_url"));
                model.setBriefDescr(albumData.optString("brief"));
                model.setImage(albumData.optString("image"));
                result.add(model);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        SearchAlbumModel.AVAILABLE_PAGES = response.optInt("pages", -1);
        return result;
    }

    public static ArrayList<InternetSong> parseSearchSongs(JSONObject response) throws Exception {
        Set<InternetSong> result = new HashSet<InternetSong>();
        JSONArray songs = response.optJSONArray(RESPONSE_TAG);
        if (songs == null || songs.length() == 0) {
            InternetSong.PAGE_AVAILABLE = -1;
            return new ArrayList<InternetSong>(result);
        }

        final int songsLength = songs.length();
        for (int i = 0; i < songsLength; i++) {
            InternetSong song = new InternetSong();
            JSONObject songData = songs.getJSONObject(i);
            try {
                song.setUrlForUrl(songData.optString("url_for"));
                song.setName(songData.optString("name"));
                song.setArtist(songData.optString("artist"));
                song.setUrl(songData.optString("url"));
                song.setRate(songData.optString("bitrate"));
                song.setId(songData.optString("id"));
                song.setDuration(songData.optInt("length") * 1000);
                song.setSize(songData.optLong("size"));
                result.add(song);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        InternetSong.PAGE_AVAILABLE = response.optInt("pages", -1);
        return new ArrayList<InternetSong>(result);
    }
}


