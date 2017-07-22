package com.ranieri.bodegaweb.asyncTask;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.ranieri.bodegaweb.connection.ConnectionConstants;
import com.ranieri.bodegaweb.connection.GetRequester;
import com.ranieri.bodegaweb.connection.PostRequester;
import com.ranieri.bodegaweb.contract.CategoriasContract;
import com.ranieri.bodegaweb.dao.CategoriasDAO;
import com.ranieri.bodegaweb.dao.ProdutosDAO;
import com.ranieri.bodegaweb.dao.SubCategoriasDAO;
import com.ranieri.bodegaweb.model.ListJson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by ranie on 13 de mai.
 */

public class RefreshProductsTask extends AsyncTask<Context, Void, Integer> {

    @Override
    protected Integer doInBackground(Context... params) {
        OkHttpClient client = new OkHttpClient();
        MediaType json = MediaType.parse("application/json; charset=utf-8");
        String jsonString;
        Gson gson = new Gson();
        ListJson listJson = new ListJson();
        try {
            ProdutosDAO produtosDAO = new ProdutosDAO(params[0]);
            listJson.setListaProdutos(produtosDAO.listar(true));
            jsonString = gson.toJson(listJson, ListJson.class);

            Response response = new PostRequester(client, json, ConnectionConstants.urlPostProducts, jsonString).invoke();
            if (!response.isSuccessful()) return null;

            listJson = new GetRequester(client, gson, ConnectionConstants.urlSubCategory).invoke();
            SubCategoriasDAO subCategoriasDAO = new SubCategoriasDAO(params[0]);
            subCategoriasDAO.refreshStock(listJson);

            listJson = new GetRequester(client, gson, ConnectionConstants.urlCategory).invoke();
            CategoriasDAO categoriasDAO = new CategoriasDAO(params[0]);
            categoriasDAO.refreshStock(listJson);

            listJson = new GetRequester(client, gson, ConnectionConstants.urlProducts).invoke();
            return produtosDAO.refreshStock(listJson);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}