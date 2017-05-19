package com.ranieri.bodegaweb.asyncTask;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.ranieri.bodegaweb.dao.CategoriasDAO;
import com.ranieri.bodegaweb.dao.ProdutosDAO;
import com.ranieri.bodegaweb.dao.SubCategoriasDAO;
import com.ranieri.bodegaweb.model.ListJson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ranie on 13 de mai.
 */

public class RefreshProductsTask extends AsyncTask<Context, Void, Integer> {

    @Override
    protected Integer doInBackground(Context... params) {
        OkHttpClient client = new OkHttpClient();
        final String ipv4 = "http://192.168.15.7";
        final String urlProdutos = ipv4 + ":8080/bodegaWEB/rest/products/";
        final String urlCategorias = ipv4 + ":8080/bodegaWEB/rest/products/categorias";
        final String urlSubCategorias = ipv4 + ":8080/bodegaWEB/rest/products/subcategorias";
        Request request;
        Response response;
        String jsonString;
        Gson gson;
        ListJson listJson;
        try {
            request = new Request.Builder().url(urlSubCategorias).build();
            response = client.newCall(request).execute();
            jsonString = response.body().string();
            gson = new Gson();
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
            ProdutosDAO produtosDAO = new ProdutosDAO(params[0]);
            produtosDAO.refreshStock(listJson);

            return listJson.getListaProdutos().size();
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}