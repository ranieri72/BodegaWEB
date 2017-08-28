package com.ranieri.bodegaweb.asyncTask;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.ranieri.bodegaweb.connection.ConnectionConstants;
import com.ranieri.bodegaweb.connection.ConnectionRequester;
import com.ranieri.bodegaweb.dao.CategoriasDAO;
import com.ranieri.bodegaweb.dao.OrderItemsDAO;
import com.ranieri.bodegaweb.dao.OrdersDAO;
import com.ranieri.bodegaweb.dao.ProdutosDAO;
import com.ranieri.bodegaweb.dao.ProviderDAO;
import com.ranieri.bodegaweb.dao.StockMovementDAO;
import com.ranieri.bodegaweb.dao.SubCategoriasDAO;
import com.ranieri.bodegaweb.dao.UnidadeMedidaDAO;
import com.ranieri.bodegaweb.model.ListJson;

import okhttp3.Response;

/**
 * Created by Ranieri Aguiar on 13 de mai.
 */

public class RefreshDataTask extends AsyncTask<Context, Void, Integer> {

    @Override
    protected Integer doInBackground(Context... params) {
        String jsonString;
        Gson gson = new Gson();
        Context context = params[0];
        ListJson listJson;
        try {
            StockMovementDAO movementDAO = new StockMovementDAO(context);
            listJson = new ListJson();
            listJson.setListaStockMovement(movementDAO.listar());
            jsonString = gson.toJson(listJson, ListJson.class);
            Response response = new ConnectionRequester(ConnectionConstants.urlStockMovement).postRequester(jsonString);
            if (!response.isSuccessful()) return null;
            movementDAO.excluir();

            ProdutosDAO produtosDAO = new ProdutosDAO(context);
            listJson = new ListJson();
            listJson.setListaProdutos(produtosDAO.listar(true));
            jsonString = gson.toJson(listJson, ListJson.class);
            new ConnectionRequester(ConnectionConstants.urlPostProducts).postRequester(jsonString);

            listJson = new ConnectionRequester(ConnectionConstants.urlSubCategory).getRequester();
            new SubCategoriasDAO(context).refreshStock(listJson);

            listJson = new ConnectionRequester(ConnectionConstants.urlCategory).getRequester();
            new CategoriasDAO(context).refreshStock(listJson);

            listJson = new ConnectionRequester(ConnectionConstants.urlProducts).getRequester();
            int qtd = produtosDAO.refreshStock(listJson);

            listJson = new ConnectionRequester(ConnectionConstants.urlProvider).getRequester();
            new ProviderDAO(params[0]).refreshOrders(listJson);

            listJson = new ConnectionRequester(ConnectionConstants.urlOrder).getRequester();
            new OrdersDAO(params[0]).refreshOrders(listJson);

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