package com.kirby510.covid19tracker.domain.repository;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Covid19APIRequest {
    private Context mContext;

    ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    OnExecuteListener listener = null;

    public static final String DIRECTORY = "DIRECTORY";
    public static final String METHOD = "METHOD";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";
    public static final String METHOD_PATCH = "PATCH";
    public static final String PARAM_BODY = "PARAM_BODY";

    public Covid19APIRequest(Context context) {
        this.mContext = context;
    }

    public Covid19APIRequest(Context context, OnExecuteListener listener) {
        this.mContext = context;
        this.listener = listener;
    }

    public void execute(Bundle requestParams) {
        mExecutor.execute(() -> {
            HttpURLConnection urlConnection = null;

            try {
                URL url = new URL("https://api.covid19api.com/" + requestParams.getString(DIRECTORY, ""));
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(10 * 1000);
                urlConnection.setReadTimeout(10 * 1000);

                if (!requestParams.getString(METHOD, METHOD_GET).equals(METHOD_GET) && !requestParams.getString(METHOD, METHOD_GET).equals(METHOD_DELETE)) {
                    JsonObject bodyJson = new JsonObject();

                    try {
                        JSONObject tempBodyJson = new JSONObject(requestParams.getString(PARAM_BODY, "{}"));

                        Iterator<String> keys = tempBodyJson.keys();

                        while (keys.hasNext()) {
                            String key = keys.next();

                            bodyJson.addProperty(key, tempBodyJson.getString(key));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestMethod(requestParams.getString(METHOD, METHOD_GET));
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);
                    urlConnection.setChunkedStreamingMode(0);

                    OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                    writer.write(bodyJson.toString());
                    writer.flush();
                } else {
                    urlConnection.setRequestMethod(requestParams.getString(METHOD, METHOD_GET));
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(false);
                    urlConnection.setChunkedStreamingMode(-1);
                }

                Log.i("Covid19Tracker", requestParams.getString(DIRECTORY, ""));
                Log.i("Covid19Tracker", "Code: " + urlConnection.getResponseCode());
                Log.i("Covid19Tracker", "Message: " + urlConnection.getResponseMessage());

                BufferedReader bufferedReader;

                if ((urlConnection.getResponseCode() >= 100) && (urlConnection.getResponseCode() <= 399)) {
                    bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                } else {
                    bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
                }

                String line;
                String body = "";

                while ((line = bufferedReader.readLine()) != null) {
                    body += line + "\n";
                }

                if (listener != null) {
                    if ((urlConnection.getResponseCode() >= 100) && (urlConnection.getResponseCode() <= 399)) {
                        listener.onSuccess(body.trim());
                    } else {
                        listener.onError(body.trim());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

                if (listener != null) {
                    listener.onError(e.getMessage());
                }
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        });
    }

    public interface OnExecuteListener {
        void onSuccess(String message);

        void onError(String errorMessage);
    }
}
