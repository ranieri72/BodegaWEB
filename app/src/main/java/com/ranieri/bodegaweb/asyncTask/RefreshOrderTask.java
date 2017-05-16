package com.ranieri.bodegaweb.asyncTask;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.ranieri.bodegaweb.dao.CategoriasDAO;
import com.ranieri.bodegaweb.dao.OrderItemsDAO;
import com.ranieri.bodegaweb.dao.OrdersDAO;
import com.ranieri.bodegaweb.dao.ProdutosDAO;
import com.ranieri.bodegaweb.dao.ProviderDAO;
import com.ranieri.bodegaweb.dao.SubCategoriasDAO;
import com.ranieri.bodegaweb.model.ListJson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ranie on 13 de mai.
 */

public class RefreshOrderTask extends AsyncTask<Context, Void, Integer> {

    @Override
    protected Integer doInBackground(Context... params) {
        OkHttpClient client = new OkHttpClient();
        final String ipv4 = "http://192.168.25.8";
        final String urlProvider = ipv4 + ":8080/bodegaWEB/rest/produtos/";
        final String urlOrder = ipv4 + ":8080/bodegaWEB/rest/produtos/categorias";
        final String urlOrderItems = ipv4 + ":8080/bodegaWEB/rest/produtos/subcategorias";
        Request request;
        Response response;
        String jsonString;
        Gson gson = new Gson();
        ListJson listJson;
        try {
            request = new Request.Builder().url(urlProvider).build();
            response = client.newCall(request).execute();
            jsonString = response.body().string();
            listJson = gson.fromJson(jsonString, ListJson.class);
            OrderItemsDAO orderItemsDAO = new OrderItemsDAO(params[0]);
            int qtd = orderItemsDAO.refreshStock(listJson);

            request = new Request.Builder().url(urlOrder).build();
            response = client.newCall(request).execute();
            jsonString = response.body().string();
            listJson = gson.fromJson(jsonString, ListJson.class);
            OrdersDAO ordersDAO = new OrdersDAO(params[0]);
            ordersDAO.refreshStock(listJson);

            request = new Request.Builder().url(urlOrderItems).build();
            response = client.newCall(request).execute();
            jsonString = response.body().string();
            listJson = gson.fromJson(jsonString, ListJson.class);
            ProviderDAO providerDAO = new ProviderDAO(params[0]);
            providerDAO.refreshStock(listJson);

            return qtd;
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}