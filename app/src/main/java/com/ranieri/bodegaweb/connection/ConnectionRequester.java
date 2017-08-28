package com.ranieri.bodegaweb.connection;

import com.google.gson.Gson;
import com.ranieri.bodegaweb.model.ListJson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ranie on 20 de jul.
 */

public class ConnectionRequester {
    private MediaType json = MediaType.parse("application/json; charset=utf-8");
    private String url;

    public ConnectionRequester(String url) {
        this.url = url;
    }

    public Response postRequester(String jsonString) throws IOException {
        RequestBody body = RequestBody.create(json, jsonString);
        Request request = new Request.Builder().url(url).post(body).build();
        return new OkHttpClient().newCall(request).execute();
    }

    public ListJson getRequester() throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = new OkHttpClient().newCall(request).execute();
        String jsonString = response.body().string();
        return new Gson().fromJson(jsonString, ListJson.class);
    }
}