package com.task.mondiamedia.mondiamediaapplication.view;

import com.task.mondiamedia.mondiamediaapplication.network.modle.SongModel;

import java.util.List;

/**
 * Created by Summer on 5/16/2019.
 */
public interface ApiContract {
    interface PresenterListner {
        void initialize();

        void stop();

        void fetch(String searchKey);
    }

    interface ApiListner {
        void onFetchSuccess(List<SongModel> songModels);

        void onFetchFailed(String errorMsg);

        void setAccessToken(String accessToken);
    }

    interface SongsView {
        void showSongsList(List<SongModel> songModels);

        void showError(String errorMsg);
    }
}
