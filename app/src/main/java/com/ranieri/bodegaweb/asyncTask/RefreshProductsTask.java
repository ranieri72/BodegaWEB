package com.ranieri.bodegaweb.asyncTask;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.ranieri.bodegaweb.dao.CategoriasDAO;
import com.ranieri.bodegaweb.dao.ProdutosDAO;
import com.ranieri.bodegaweb.dao.SubCategoriasDAO;
import com.ranieri.bodegaweb.model.ListJson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ranie on 13 de mai.
 */

public class RefreshProductsTask extends AsyncTask<Context, Void, Integer> {

    @Override
    protected Integer doInBackground(Context... params) {
        OkHttpClient client = new OkHttpClient();
        MediaType json = MediaType.parse("application/json; charset=utf-8");
        Request request;
        Response response;
        String jsonString;
        Gson gson = new Gson();
        ListJson listJson = new ListJson();

        String ipv4 = "http://192.168.0.2";
        final String urlTest = ipv4 + ":8080/bodegaWEB/rest/test";

        try {
            request = new Request.Builder().url(urlTest).build();
            client.newCall(request).execute();
        } catch (IOException e) {
            ipv4 = "http://192.168.15.12";
        }

        final String urlProdutos = ipv4 + ":8080/bodegaWEB/rest/products/";
        final String urlPostProdutos = ipv4 + ":8080/bodegaWEB/rest/products/post";
        final String urlCategorias = ipv4 + ":8080/bodegaWEB/rest/products/categorias";
        final String urlSubCategorias = ipv4 + ":8080/bodegaWEB/rest/products/subcategorias";
        try {
            ProdutosDAO produtosDAO = new ProdutosDAO(params[0]);
            listJson.setListaProdutos(produtosDAO.listar(true));
            jsonString = gson.toJson(listJson, ListJson.class);

            RequestBody body = RequestBody.create(json, jsonString);
            request = new Request.Builder().url(urlPostProdutos).post(body).build();
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) return null;

            request = new Request.Builder().url(urlSubCategorias).build();
            response = client.newCall(request).execute();
            jsonString = response.body().string();
            listJson = gson.fromJson(jsonString, ListJson.class);
            SubCategoriasDAO subCategoriasDAO = new SubCategoriasDAO(params[0]);
            subCategoriasDAO.refreshStock(listJson);

            request = new Request.Builder().url(urlCategorias).build();
            response = client.newCall(request).execute();
            jsonString = response.body().string();
            listJson = gson.fromJson(jsonString, ListJson.class);
            CategoriasDAO categoriasDAO = new CategoriasDAO(params[0]);
            categoriasDAO.refreshStock(listJson);

            request = new Request.Builder().url(urlProdutos).build();
            response = client.newCall(request).execute();
            jsonString = response.body().string();
            listJson = gson.fromJson(jsonString, ListJson.class);

            return produtosDAO.refreshStock(listJson);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}