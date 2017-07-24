package com.ranieri.bodegaweb.asyncTask;

import android.content.Context;
import android.os.AsyncTask;

import com.ranieri.bodegaweb.connection.ConnectionConstants;
import com.ranieri.bodegaweb.connection.ConnectionRequester;
import com.ranieri.bodegaweb.dao.OrderItemsDAO;
import com.ranieri.bodegaweb.dao.OrdersDAO;
import com.ranieri.bodegaweb.dao.ProviderDAO;
import com.ranieri.bodegaweb.dao.UnidadeMedidaDAO;
import com.ranieri.bodegaweb.model.ListJson;

/**
 * Created by ranie on 13 de mai.
 */

public class RefreshOrderTask extends AsyncTask<Context, Void, Integer> {

    @Override
    protected Integer doInBackground(Context... params) {
        ListJson listJson;
        try {
            listJson = new ConnectionRequester(ConnectionConstants.urlProvider).getRequester();
            new ProviderDAO(params[0]).refreshOrders(listJson);

            listJson = new ConnectionRequester(ConnectionConstants.urlOrder).getRequester();
            int qtd = new OrdersDAO(params[0]).refreshOrders(listJson);

            listJson = new ConnectionRequester(ConnectionConstants.urlUnitMeasurement).getRequester();
            new UnidadeMedidaDAO(params[0]).refreshOrders(listJson);

            listJson = new ConnectionRequester(ConnectionConstants.urlOrderItems).getRequester();
            new OrderItemsDAO(params[0]).refreshOrders(listJson);

            return qtd;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}