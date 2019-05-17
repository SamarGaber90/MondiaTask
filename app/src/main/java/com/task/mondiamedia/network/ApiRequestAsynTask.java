package com.task.mondiamedia.network;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.task.mondiamedia.model.InputStreamRequest;
import com.task.mondiamedia.model.SongModel;
import com.task.mondiamedia.view.ApiContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Summer on 5/15/2019.
 */
public class ApiRequestAsynTask extends AsyncTask<String, Void, List<SongModel>> {
    private static final String BASE_URL = "http://staging-gateway.mondiamedia.com/";

    private static final String TOKEN_API_METHOD = "v0/api/gateway/token/client";
    private static final String ALL_LIST_API_METHOD = "v2/api/sayt/flat?query=%s&limit=20";


    private ApiContract.ApiListner apiListner;
    private Exception exception = null;

    public ApiRequestAsynTask(ApiContract.ApiListner apiListner) {
        this.apiListner = apiListner;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.exception = null;
    }

    @Override
    protected List<SongModel> doInBackground(String... strings) {
        try {
            String accessToken = strings[0];
            //check if access token exist
            if (TextUtils.isEmpty(accessToken)) {
                accessToken = getAccessTokenRequest();

                apiListner.setAccessToken(accessToken);
            }
            //then get songs list
            return getSongsListRequest(accessToken, strings[1]);

        } catch (Exception e) {
            this.exception = e;
        }
        return Collections.emptyList();
    }

    @Override
    protected void onPostExecute(List<SongModel> songModelList) {
        super.onPostExecute(songModelList);
        if (this.exception != null) {
            apiListner.onFetchFailed(this.exception.getMessage());
        } else {
            //set view with list
            apiListner.onFetchSuccess(songModelList);
        }
    }

    private String getAccessTokenRequest() throws IOException, JSONException {
        String requestUrl = getGetAccessTokenApiUrl();

        HttpURLConnection urlConnection = HttpUrlConnectionRequest.getHttpConnection(requestUrl, "POST", "");
        String response = InputStreamRequest.getResponse(urlConnection);

        JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();
        return jsonObject.getString("accessToken");
    }

    private List<SongModel> getSongsListRequest(String accessToken, String searchKey) throws IOException, JSONException {
        String requestUrl = getListApiUrl(searchKey);

        HttpURLConnection urlConnection = HttpUrlConnectionRequest.getHttpConnection(requestUrl, "GET", accessToken);
        String response = InputStreamRequest.getResponse(urlConnection);

        JSONArray jsonSongsArray = (JSONArray) new JSONTokener(response).nextValue();

        closeAllRequests();

        return SongModel.getSongsListFromJsonArray(jsonSongsArray);
    }


    private String getGetAccessTokenApiUrl() {
        return BASE_URL.concat(TOKEN_API_METHOD);
    }

    private String getListApiUrl(String searchKey) {
        String url = String.format(ALL_LIST_API_METHOD, searchKey);
        return BASE_URL.concat(url);
    }

    private void closeAllRequests() {
        InputStreamRequest.stopStream();
        HttpUrlConnectionRequest.stopConnection();
    }
}

