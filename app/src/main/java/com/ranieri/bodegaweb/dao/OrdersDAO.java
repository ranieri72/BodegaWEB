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

    public int inserir(List<Order> lista) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        int contador = 0;

        for (Order order : lista) {
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
        String[] orderID = new String[]{String.valueOf(order.getId())};

        ContentValues values = valuesFromOrder(order);
        int rowsAffected = db.update(OrderContract.TABLE_NAME, values, OrderContract.ID + " = ?", orderID);

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

        List<Order> lista = valuesFromCursor(cursor);

        cursor.close();
        db.close();
        return lista;
    }

    private List<Order> valuesFromCursor(Cursor cursor) {
        List<Order> lista = new ArrayList<>();
        Order order;

        int indexId = cursor.getColumnIndex(OrderContract.ID);
        //int indexOrderDate = cursor.getColumnIndex(OrderContract.ORDERDATE);
        int indexTotalOrder = cursor.getColumnIndex(OrderContract.TOTALORDER);
        int indexProviderId = cursor.getColumnIndex(OrderContract.PROVIDER);

        while (cursor.moveToNext()) {
            Order o = new Order();
            o.setId(cursor.getLong(indexId));
            //o.setDataPedido(cursor.getString(indexOrderDate));
            o.setTotalPedido(cursor.getFloat(indexTotalOrder));
            o.getFornecedor().setId(cursor.getLong(indexProviderId));
            lista.add(o);
        }
        return lista;
    }

    private ContentValues valuesFromOrder(Order order) {
        ContentValues values = new ContentValues();
        values.put(OrderContract.ID, order.getId());
        values.put(OrderContract.ORDERDATE, order.getDataPedido().toString());
        values.put(OrderContract.TOTALORDER, order.getTotalPedido());
        values.put(OrderContract.PROVIDER, order.getFornecedor().getId());

        return values;
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
