package com.task.mondiamedia.view.presenter;

import android.os.AsyncTask;

import com.task.mondiamedia.model.SongModel;
import com.task.mondiamedia.network.ApiRequestAsynTask;
import com.task.mondiamedia.view.ApiContract;

import java.util.List;

/**
 * Created by Summer on 5/16/2019.
 */
public class SongsPresenter implements ApiContract.ApiListner, ApiContract.PresenterListner {
    private ApiRequestAsynTask apiRequestAsynTask;
    private ApiContract.SongsView songsView;
    private String accessToken;

    public SongsPresenter(ApiContract.SongsView songsView) {
        this.songsView = songsView;
    }

    @Override
    public void fetch(String searchKey) {
        if (apiRequestAsynTask != null) {
            apiRequestAsynTask.execute(accessToken, searchKey);
        }
    }

    @Override
    public void initialize() {
        apiRequestAsynTask = new ApiRequestAsynTask(this);
    }

    @Override
    public void stop() {
        if (apiRequestAsynTask != null && apiRequestAsynTask.getStatus() == AsyncTask.Status.RUNNING) {
            apiRequestAsynTask.cancel(true);
            apiRequestAsynTask = null;
        }
    }


    @Override
    public void onFetchSuccess(List<SongModel> songModels) {
        if (songModels.isEmpty()) {
            songsView.showError("empty List");
        } else {
            songsView.showSongsList(songModels);
        }
        apiRequestAsynTask = null;
    }

    @Override
    public void onFetchFailed(String errorMsg) {
        songsView.showError(errorMsg);
        apiRequestAsynTask = null;
    }

    @Override
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
