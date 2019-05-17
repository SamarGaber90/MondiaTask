package com.task.mondiamedia.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Summer on 5/16/2019.
 */
public class SongModel implements Serializable {

    private String type;
    private String title;
    private String artistName;
    private String songImg;
    private String publishingDate;
    private String trackNumber;
    private String duration;

    public String getType() {
        return type;
    }

    private void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public String getArtistName() {
        return artistName;
    }

    private void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getSongImg() {
        return songImg;
    }

    private void setSongImg(String songImg) {
        this.songImg = songImg;
    }

    public String getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(String publishingDate) {
        this.publishingDate = publishingDate;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(String trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public static List<SongModel> getSongsListFromJsonArray(JSONArray jsonArray) {
        List<SongModel> songModelList = null;
        if (jsonArray != null && jsonArray.length() > 0) {
            songModelList = new ArrayList<>();
            for (int iSong = 0; iSong <= jsonArray.length(); iSong++) {
                SongModel songModel = new SongModel();
                try {
                    songModel.setTitle(jsonArray.getJSONObject(iSong).getString("title"));
                    songModel.setType(jsonArray.getJSONObject(iSong).getString("type"));
                    songModel.setPublishingDate(jsonArray.getJSONObject(iSong).getString("publishingDate"));
                    songModel.setDuration(jsonArray.getJSONObject(iSong).getString("duration"));

                    songModel.setTrackNumber(jsonArray.getJSONObject(iSong).getString("trackNumber"));
                    songModel.setArtistName(jsonArray.getJSONObject(iSong).getJSONObject("mainArtist").getString("name"));

                    songModel.setSongImg(editUrl(jsonArray.getJSONObject(iSong).getJSONObject("cover").getString("template")));

                    songModelList.add(songModel);
                } catch (JSONException e) {
                    Log.e("error", e.getMessage());
                }
            }
        }
        return songModelList;
    }

    private static String editUrl(String url) {
        return "http:" + url.replace("{width}", "80").replace("{height}", "80").replace("{suffix}", "jpg");
    }

}
