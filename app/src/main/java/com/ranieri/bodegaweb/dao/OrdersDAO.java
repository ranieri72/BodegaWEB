package com.ranieri.bodegaweb.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ranieri.bodegaweb.contract.OrderContract;
import com.ranieri.bodegaweb.database.BodegaHelper;
import com.ranieri.bodegaweb.model.ListJson;
import com.ranieri.bodegaweb.model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ranie on 16 de mai.
 */

public class OrdersDAO {

    private Context mContext;

    public OrdersDAO(Context context) {
        mContext = context;
    }

    private void inserir(Order order) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesFromOrder(order);
        long id = db.insert(OrderContract.TABLE_NAME, null, values);

        db.close();
    }

    private int atualizar(Order order) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesFromOrder(order);
        int rowsAffected = db.update(OrderContract.TABLE_NAME, values, OrderContract._ID + " = ?", new String[]{String.valueOf(order.getId())});

        db.close();
        return rowsAffected;
    }

    private List<Order> listar() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM order", null);

        List<Order> lista = new ArrayList<>();
        Order order;

        while (cursor.moveToNext()){
            order = valuesFromCursor(cursor);
            lista.add(order);
        }

        cursor.close();
        db.close();
        return lista;
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

    private ContentValues valuesFromOrder(Order order) {
        ContentValues values = new ContentValues();
        values.put(OrderContract._ID, order.getId());
        values.put(OrderContract.ORDERDATE, order.getDataPedido().toString());
        values.put(OrderContract.TOTALORDER, order.getTotalPedido());

        return values;
    }

    private Order valuesFromCursor(Cursor cursor) {
        Order o = new Order();
        o.setId(cursor.getLong(cursor.getColumnIndex(OrderContract._ID)));
        //o.setDataPedido(cursor.getString(cursor.getColumnIndex(OrderContract.ORDERDATE)));
        o.setTotalPedido(cursor.getFloat(cursor.getColumnIndex(OrderContract.TOTALORDER)));
        return o;
    }
}
