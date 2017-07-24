package com.ranieri.bodegaweb.asyncTask;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.ranieri.bodegaweb.connection.ConnectionConstants;
import com.ranieri.bodegaweb.connection.ConnectionRequester;
import com.ranieri.bodegaweb.dao.CategoriasDAO;
import com.ranieri.bodegaweb.dao.ProdutosDAO;
import com.ranieri.bodegaweb.dao.SubCategoriasDAO;
import com.ranieri.bodegaweb.model.ListJson;

import okhttp3.Response;

/**
 * Created by ranie on 13 de mai.
 */

public class RefreshProductsTask extends AsyncTask<Context, Void, Integer> {

    @Override
    protected Integer doInBackground(Context... params) {
        String jsonString;
        Gson gson = new Gson();
        ListJson listJson = new ListJson();
        try {
            ProdutosDAO produtosDAO = new ProdutosDAO(params[0]);
            listJson.setListaProdutos(produtosDAO.listar(true));
            jsonString = gson.toJson(listJson, ListJson.class);

            Response response = new ConnectionRequester(ConnectionConstants.urlPostProducts, jsonString).postRequester();
            if (!response.isSuccessful()) return null;

            listJson = new ConnectionRequester(ConnectionConstants.urlSubCategory).getRequester();
            new SubCategoriasDAO(params[0]).refreshStock(listJson);

            listJson = new ConnectionRequester(ConnectionConstants.urlCategory).getRequester();
            new CategoriasDAO(params[0]).refreshStock(listJson);

            listJson = new ConnectionRequester(ConnectionConstants.urlProducts).getRequester();
            return produtosDAO.refreshStock(listJson);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}