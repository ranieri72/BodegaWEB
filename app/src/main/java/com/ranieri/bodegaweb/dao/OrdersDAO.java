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

    public Order inserir(Order order) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesFromOrder(order);
        long id = db.insert(OrderContract.TABLE_NAME, null, values);
        order.setId(id);

        db.close();
        return order;
    }

    public int inserir(List<Order> lista){
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        int contador = 0;

        for (Order order : lista){
            ContentValues values = valuesFromOrder(order);
            db.insert(OrderContract.TABLE_NAME, null, values);
            contador++;
        }
        db.close();
        return contador;
    }

    public int atualizar(Order order) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesFromOrder(order);
        int rowsAffected = db.update(OrderContract.TABLE_NAME, values, OrderContract._ID + " = ?", new String[]{String.valueOf(order.getId())});

        db.close();
        return rowsAffected;
    }

    public int excluir() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        int rowsAffected = db.delete(OrderContract.TABLE_NAME, null, null);

        db.close();
        return rowsAffected;
    }

    public List<Order> listar() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + OrderContract.TABLE_NAME, null);

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

    private ContentValues valuesFromOrder(Order order) {
        ContentValues values = new ContentValues();
        values.put(OrderContract._ID, order.getId());
        values.put(OrderContract.ORDERDATE, order.getDataPedido().toString());
        values.put(OrderContract.TOTALORDER, order.getTotalPedido());
        values.put(OrderContract.PROVIDER, order.getFornecedor().getId());

        return values;
    }

    private Order valuesFromCursor(Cursor cursor) {
        Order o = new Order();
        o.setId(cursor.getLong(cursor.getColumnIndex(OrderContract._ID)));
        //o.setDataPedido(cursor.getString(cursor.getColumnIndex(OrderContract.ORDERDATE)));
        o.setTotalPedido(cursor.getFloat(cursor.getColumnIndex(OrderContract.TOTALORDER)));
        o.getFornecedor().setId(cursor.getLong(cursor.getColumnIndex(OrderContract.PROVIDER)));
        return o;
    }

    public int refreshOrders(ListJson listJson) {
        excluir();
        return inserir(listJson.getListaOrder());
    }

//    public void refreshOrders(ListJson listJson) {
//        BodegaHelper helper = new BodegaHelper(mContext);
//        SQLiteDatabase db = helper.getWritableDatabase();
//        List<Order> listaBanco = listar();
//        boolean existe;
//
//        for (Order oJson : listJson.getListaOrder()) {
//            existe = false;
//            for (Order oBanco : listaBanco) {
//                if (oJson.getId() == oBanco.getId()) {
//                    atualizar(oJson);
//                    existe = true;
//                    break;
//                }
//            }
//            if (!existe) {
//                inserir(oJson);
//            }
//        }
//    }
}
