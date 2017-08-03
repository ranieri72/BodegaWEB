package com.ranieri.bodegaweb.asyncTask;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.ranieri.bodegaweb.connection.ConnectionConstants;
import com.ranieri.bodegaweb.connection.ConnectionRequester;
import com.ranieri.bodegaweb.dao.CategoriasDAO;
import com.ranieri.bodegaweb.dao.ProdutosDAO;
import com.ranieri.bodegaweb.dao.StockMovementDAO;
import com.ranieri.bodegaweb.dao.SubCategoriasDAO;
import com.ranieri.bodegaweb.model.ListJson;

import okhttp3.Response;

/**
 * Created by Ranieri Aguiar on 13 de mai.
 */

public class RefreshProductsTask extends AsyncTask<Context, Void, Integer> {

    @Override
    protected Integer doInBackground(Context... params) {
        String jsonProducts;
        String jsonStockMovementet;
        Gson gson = new Gson();
        Context context = params[0];
        ListJson listJson;
        try {
            listJson = new ListJson();
            listJson.setListaStockMovement(new StockMovementDAO(context).listar());
            jsonStockMovementet = gson.toJson(listJson, ListJson.class);
            Response response = new ConnectionRequester(ConnectionConstants.urlStockMovement, jsonStockMovementet).postRequester();
            if (!response.isSuccessful()) return null;

            ProdutosDAO produtosDAO = new ProdutosDAO(context);
            listJson = new ListJson();
            listJson.setListaProdutos(produtosDAO.listar(true));
            jsonProducts = gson.toJson(listJson, ListJson.class);
            new ConnectionRequester(ConnectionConstants.urlPostProducts, jsonProducts).postRequester();

            listJson = new ConnectionRequester(ConnectionConstants.urlSubCategory).getRequester();
            new SubCategoriasDAO(context).refreshStock(listJson);

            listJson = new ConnectionRequester(ConnectionConstants.urlCategory).getRequester();
            new CategoriasDAO(context).refreshStock(listJson);

            listJson = new ConnectionRequester(ConnectionConstants.urlProducts).getRequester();
            return produtosDAO.refreshStock(listJson);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}