package com.ranieri.bodegaweb.connection;

import com.google.gson.Gson;
import com.ranieri.bodegaweb.model.ListJson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ranie on 20 de jul.
 */

public class GetRequester {
    private OkHttpClient client;
    private Gson gson;
    private String url;

    public GetRequester(OkHttpClient client, Gson gson, String url) {
        this.client = client;
        this.gson = gson;
        this.url = url;
    }

    public ListJson invoke() throws IOException {
        Request request;
        Response response;
        String jsonString;
        ListJson listJson;
        request = new Request.Builder().url(url).build();
        response = client.newCall(request).execute();
        jsonString = response.body().string();
        listJson = gson.fromJson(jsonString, ListJson.class);
        return listJson;
    }
}