package com.task.mondiamedia.mondiamediaapplication.network;

import android.text.TextUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Summer on 5/16/2019.
 */
public class HttpUrlConnectionRequest {
    private static final String GETWAY_KEY = "X-MM-GATEWAY-KEY";
    private static final String GETWAY_KEY_VALUE = "Ge6c853cf-5593-a196-efdb-e3fd7b881eca";

    private static final String ACCESS_TOKE_KEY = "Authorization";
    private static final String ACCESS_TOKEN_KEY_VALUE = "Bearer ";

    private static HttpURLConnection httpURLConnection;

    private HttpUrlConnectionRequest() {
    }

    public static HttpURLConnection getHttpConnection(String requestUrl,
                                                      String requestMethod, String accessToken) throws IOException {
        StringBuilder urlString = new StringBuilder();
        urlString.append(requestUrl);
        URL url = new URL(urlString.toString());

        httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestProperty(GETWAY_KEY, GETWAY_KEY_VALUE);
        if (!TextUtils.isEmpty(accessToken)) {
            //then its songs list request ---> add new header
            httpURLConnection.setRequestProperty(ACCESS_TOKE_KEY, ACCESS_TOKEN_KEY_VALUE.concat(accessToken));
        }
        httpURLConnection.setRequestMethod(requestMethod);
        httpURLConnection.connect();
        return httpURLConnection;

    }

    public static void stopConnection() {
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }
}
