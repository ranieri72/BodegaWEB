package com.ranieri.bodegaweb.asyncTask;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.ranieri.bodegaweb.connection.ConnectionConstants;
import com.ranieri.bodegaweb.connection.GetRequester;
import com.ranieri.bodegaweb.contract.OrderContract;
import com.ranieri.bodegaweb.contract.OrderItemsContract;
import com.ranieri.bodegaweb.dao.OrderItemsDAO;
import com.ranieri.bodegaweb.dao.OrdersDAO;
import com.ranieri.bodegaweb.dao.ProviderDAO;
import com.ranieri.bodegaweb.dao.UnidadeMedidaDAO;
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
        Request request;
        Response response;
        String jsonString;
        Gson gson = new Gson();
        ListJson listJson;
        try {
            listJson = new GetRequester(client, gson, ConnectionConstants.urlProvider).invoke();
            ProviderDAO providerDAO = new ProviderDAO(params[0]);
            providerDAO.refreshOrders(listJson);

            listJson = new GetRequester(client, gson, ConnectionConstants.urlOrder).invoke();
            OrdersDAO ordersDAO = new OrdersDAO(params[0], OrderContract.TABLE_NAME);
            int qtd = ordersDAO.refreshOrders(listJson);

            listJson = new GetRequester(client, gson, ConnectionConstants.urlUnitMeasurement).invoke();
            UnidadeMedidaDAO unidadeMedidaDAO = new UnidadeMedidaDAO(params[0]);
            unidadeMedidaDAO.refreshOrders(listJson);

            listJson = new GetRequester(client, gson, ConnectionConstants.urlOrderItems).invoke();
            OrderItemsDAO orderItemsDAO = new OrderItemsDAO(params[0], OrderItemsContract.TABLE_NAME);
            orderItemsDAO.refreshOrders(listJson);

            return qtd;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}