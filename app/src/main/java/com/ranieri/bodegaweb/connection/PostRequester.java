package com.ranieri.bodegaweb.connection;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ranie on 20 de jul.
 */

public class PostRequester {
    private OkHttpClient client;
    private MediaType json;
    private String url;
    private String jsonString;

    public PostRequester(OkHttpClient client, MediaType json, String url, String jsonString) {
        this.client = client;
        this.json = json;
        this.url = url;
        this.jsonString = jsonString;
    }

    public Response invoke() throws IOException {
        Request request;
        RequestBody body = RequestBody.create(json, jsonString);
        request = new Request.Builder().url(url).post(body).build();
        return client.newCall(request).execute();
    }
}