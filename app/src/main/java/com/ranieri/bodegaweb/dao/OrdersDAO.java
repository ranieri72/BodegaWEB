package com.ranieri.bodegaweb.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ranieri.bodegaweb.database.BodegaHelper;
import com.ranieri.bodegaweb.model.ListJson;
import com.ranieri.bodegaweb.model.Order;

import java.util.List;

/**
 * Created by ranie on 16 de mai.
 */

public class OrdersDAO {

    private Context mContext;

    public OrdersDAO(Context context) {
        mContext = context;
    }

    public void refreshStock(ListJson listJson) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        List<Order> listaBanco = listar();
        boolean existe;

        for (Order oJson : listJson.getListaOrder()) {
            existe = false;
            for (Order oBanco : listaBanco) {
                if (oJson.getId() == oBanco.getId()) {
                    atualizar(oJson);
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                inserir(oJson);
            }
        }
    }

    private void inserir(Order order) {
    }

    private void atualizar(Order order) {
    }

    private List<Order> listar() {
        return null;
    }
}
