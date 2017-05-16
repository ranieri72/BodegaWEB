package com.ranieri.bodegaweb.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ranieri.bodegaweb.database.BodegaHelper;
import com.ranieri.bodegaweb.model.ListJson;
import com.ranieri.bodegaweb.model.OrderItems;

import java.util.List;

/**
 * Created by ranie on 16 de mai.
 */

public class OrderItemsDAO {

    private Context mContext;

    public OrderItemsDAO(Context context) {
        mContext = context;
    }

    public int refreshStock(ListJson listJson) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        List<OrderItems> listaBanco = listar();
        boolean existe;

        for (OrderItems oJson : listJson.getListaOrderItems()) {
            existe = false;
            for (OrderItems oBanco : listaBanco) {
                if (oJson.getOrder().getId() == oBanco.getOrder().getId() &&
                        oJson.getProdutos().getId() == oBanco.getProdutos().getId()) {
                    atualizar(oJson);
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                inserir(oJson);
            }
        }
        return 0;
    }

    private void inserir(OrderItems provider) {
    }

    private void atualizar(OrderItems provider) {
    }

    private List<OrderItems> listar() {
        return null;
    }
}
