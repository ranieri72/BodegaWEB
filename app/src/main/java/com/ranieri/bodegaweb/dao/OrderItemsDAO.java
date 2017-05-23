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

    public void inserir(OrderItems orderItem) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesFromOrder(orderItem);
        db.insert(OrderItemsContract.TABLE_NAME, null, values);

        db.close();
    }

    public int inserir(List<OrderItems> lista){
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        int contador = 0;

        for (OrderItems orderItems : lista){
            ContentValues values = valuesFromOrder(orderItems);
            db.insert(OrderItemsContract.TABLE_NAME, null, values);
            contador++;
        }
        db.close();
        return contador;
    }

    public int atualizar(OrderItems orderItem) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesFromOrder(orderItem);
        int rowsAffected = db.update(OrderItemsContract.TABLE_NAME, values,
                OrderItemsContract.ORDER + " = ?" + OrderItemsContract.PRODUTO + " = ?",
                new String[]{String.valueOf(orderItem.getChaveComposta().getOrder().getId()), String.valueOf(orderItem.getChaveComposta().getProdutos().getId())});

        db.close();
        return rowsAffected;
    }

    public int excluir() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        int rowsAffected = db.delete(OrderItemsContract.TABLE_NAME, null, null);

        db.close();
        return rowsAffected;
    }

    public List<OrderItems> listar() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + OrderItemsContract.TABLE_NAME, null);

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

    private OrderItems valuesFromCursor(Cursor cursor) {
        OrderItems o = new OrderItems();
        o.getChaveComposta().getProdutos().setId(cursor.getLong(cursor.getColumnIndex(OrderItemsContract.PRODUTO)));
        o.getChaveComposta().getOrder().setId(cursor.getLong(cursor.getColumnIndex(OrderItemsContract.ORDER)));
        o.setPrecoUnit(cursor.getDouble(cursor.getColumnIndex(OrderItemsContract.PRECOUNIT)));
        o.setQtd(cursor.getInt(cursor.getColumnIndex(OrderItemsContract.QTD)));
        o.getUnidadeMedida().setId(cursor.getLong(cursor.getColumnIndex(OrderItemsContract.UNIDADEMEDIDA)));
        return o;
    }

    private ContentValues valuesFromOrder(OrderItems orderItem) {
        ContentValues values = new ContentValues();
        values.put(OrderItemsContract.ORDER, orderItem.getChaveComposta().getOrder().getId());
        values.put(OrderItemsContract.PRODUTO, orderItem.getChaveComposta().getProdutos().getId());
        values.put(OrderItemsContract.PRECOUNIT, orderItem.getPrecoUnit());
        values.put(OrderItemsContract.QTD, orderItem.getQtd());
        values.put(OrderItemsContract.UNIDADEMEDIDA, orderItem.getUnidadeMedida().getId());

        return values;
    }

    public int refreshOrders(ListJson listJson) {
        excluir();
        return inserir(listJson.getListaOrderItems());
    }

//    public int refreshOrders(ListJson listJson) {
//        BodegaHelper helper = new BodegaHelper(mContext);
//        SQLiteDatabase db = helper.getWritableDatabase();
//        List<OrderItems> listaBanco = listar();
//        boolean existe;
//
//        for (OrderItems oJson : listJson.getListaOrderItems()) {
//            existe = false;
//            for (OrderItems oBanco : listaBanco) {
//                if (oJson.getChaveComposta().getOrder().getId() == oBanco.getChaveComposta().getOrder().getId() &&
//                        oJson.getChaveComposta().getProdutos().getId() == oBanco.getChaveComposta().getProdutos().getId()) {
//                    atualizar(oJson);
//                    existe = true;
//                    break;
//                }
//            }
//            if (!existe) {
//                inserir(oJson);
//            }
//        }
//        return 0;
//    }
}