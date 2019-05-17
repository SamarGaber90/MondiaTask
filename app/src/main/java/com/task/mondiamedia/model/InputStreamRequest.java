package com.task.mondiamedia.model;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by Summer on 5/16/2019.
 */
public class InputStreamRequest {
    private static InputStream inStream;
    private static BufferedReader bReader;

    private InputStreamRequest() {
    }

    public static String getResponse(HttpURLConnection urlConnection) throws IOException {
        inStream = urlConnection.getInputStream();
        bReader = new BufferedReader(new InputStreamReader(inStream));
        String temp;
        StringBuilder response = new StringBuilder();
        while ((temp = bReader.readLine()) != null)
            response.append(temp);
        return response.toString();
    }

    public static void stopStream() {
        if (inStream != null) {
            try {
                bReader.close();
                inStream.close();
            } catch (IOException ignored) {
                Log.e("error", ignored.getMessage());
            }
        }

    }
}
