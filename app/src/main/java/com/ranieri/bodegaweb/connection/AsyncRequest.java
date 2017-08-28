package com.ranieri.bodegaweb.connection;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.ranieri.bodegaweb.dao.ProdutosDAO;
import com.ranieri.bodegaweb.model.ListJson;
import com.ranieri.bodegaweb.model.Produtos;

import java.util.List;

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
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject jsonObject) {

                        ListJson listJson = new Gson().fromJson(jsonObject.getAsJsonObject(), ListJson.class);
                        int i = new ProdutosDAO(context).refreshStock(listJson);
                        Toast.makeText(context, "Produtos recebidos: " + i, Toast.LENGTH_LONG).show();
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
