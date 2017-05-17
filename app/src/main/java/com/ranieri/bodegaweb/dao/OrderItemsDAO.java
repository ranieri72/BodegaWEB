package com.ranieri.bodegaweb.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ranieri.bodegaweb.contract.OrderItemsContract;
import com.ranieri.bodegaweb.database.BodegaHelper;
import com.ranieri.bodegaweb.model.ListJson;
import com.ranieri.bodegaweb.model.OrderItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ranie on 16 de mai.
 */

public class OrderItemsDAO {

    private Context mContext;

    public OrderItemsDAO(Context context) {
        mContext = context;
    }

    private void inserir(OrderItems orderItem) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesFromOrder(orderItem);
        long id = db.insert(OrderItemsContract.TABLE_NAME, null, values);

        db.close();
    }

    private int atualizar(OrderItems orderItem) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesFromOrder(orderItem);
        int rowsAffected = db.update(OrderItemsContract.TABLE_NAME, values,
                OrderItemsContract.ORDER + " = ?" + OrderItemsContract.PRODUTO + " = ?",
                new String[]{String.valueOf(orderItem.getOrder().getId()), String.valueOf(orderItem.getProdutos().getId())});

        db.close();
        return rowsAffected;
    }

    private List<OrderItems> listar() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM orderItem", null);

        List<OrderItems> lista = new ArrayList<>();
        OrderItems orderItem;

        while (cursor.moveToNext()){
            orderItem = valuesFromCursor(cursor);
            lista.add(orderItem);
        }

        cursor.close();
        db.close();
        return lista;
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

    private OrderItems valuesFromCursor(Cursor cursor) {
        OrderItems o = new OrderItems();
        o.getProdutos().setId(cursor.getLong(cursor.getColumnIndex(OrderItemsContract.PRODUTO)));
        o.getOrder().setId(cursor.getLong(cursor.getColumnIndex(OrderItemsContract.ORDER)));
        o.setPrecoUnit(cursor.getDouble(cursor.getColumnIndex(OrderItemsContract.PRECOUNIT)));
        o.setQtd(cursor.getInt(cursor.getColumnIndex(OrderItemsContract.QTD)));
        o.getUnidadeMedida().setId(cursor.getLong(cursor.getColumnIndex(OrderItemsContract.UNIDADEMEDIDA)));
        return o;
    }

    private ContentValues valuesFromOrder(OrderItems orderItem) {
        ContentValues values = new ContentValues();
        values.put(OrderItemsContract.ORDER, orderItem.getOrder().getId());
        values.put(OrderItemsContract.PRODUTO, orderItem.getProdutos().getId());
        values.put(OrderItemsContract.PRECOUNIT, orderItem.getPrecoUnit());
        values.put(OrderItemsContract.QTD, orderItem.getQtd());
        values.put(OrderItemsContract.UNIDADEMEDIDA, orderItem.getUnidadeMedida().getId());

        return values;
    }
}
