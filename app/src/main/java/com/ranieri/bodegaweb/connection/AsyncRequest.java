package com.ranieri.bodegaweb.connection;

import android.content.Context;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.ranieri.bodegaweb.dao.ProdutosDAO;
import com.ranieri.bodegaweb.model.ListJson;

/**
 * Created by ranie on 23 de jul.
 */

public class AsyncRequest {

    private Context context;
    private String url;

    public AsyncRequest(Context context, String url) {
        this.context = context;
        this.url = url;

    }

    public void getJson() throws Exception {
        Ion.with(context)
                .load(url)
                .as(new TypeToken<ListJson>() {
                })
                .setCallback(new FutureCallback<ListJson>() {
                    @Override
                    public void onCompleted(Exception e, ListJson produtos) {
                        new ProdutosDAO(context).refreshStock(produtos);
                    }
                });
    }

    public void postJson() {
        JsonObject json = new JsonObject();
        json.addProperty("foo", "bar");

        Ion.with(context)
                .load(url)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                    }
                });
    }
}
