package com.ranieri.bodegaweb.asyncTask;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.ranieri.bodegaweb.dao.SubCategoriasDAO;
import com.ranieri.bodegaweb.model.ListJson;
import com.ranieri.bodegaweb.model.Produtos;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ranie on 13 de mai.
 */

public class PostProductsTask extends AsyncTask<Context, Void, Integer> {

    @Override
    protected Integer doInBackground(Context... params) {
        OkHttpClient client = new OkHttpClient();
        Request request;
        Response response;
        String jsonString;
        Gson gson;
        ListJson listJson;
        RequestBody body;
        Produtos produto;
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        String ipv4 = "http://192.168.0.2";
        final String urlTest = ipv4 + ":8080/bodegaWEB/rest/test";

        try {
            request = new Request.Builder().url(urlTest).build();
            client.newCall(request).execute();
        } catch (IOException e) {
            ipv4 = "http://192.168.15.12";
        }

        final String urlProdutos = ipv4 + ":8080/bodegaWEB/rest/products/";
        try {
            gson = new Gson();
            produto = new Produtos();
            jsonString = gson.toJson(produto, Produtos.class);

            body = RequestBody.create(JSON, jsonString);
            request = new Request.Builder().url(urlProdutos).post(body).build();
            response = client.newCall(request).execute();

            jsonString = response.body().string();
            listJson = gson.fromJson(jsonString, ListJson.class);
            SubCategoriasDAO subCategoriasDAO = new SubCategoriasDAO(params[0]);
            return subCategoriasDAO.refreshStock(listJson);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}