package com.ranieri.bodegaweb.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ranieri.bodegaweb.contract.OrderContract;
import com.ranieri.bodegaweb.contract.ProviderContract;
import com.ranieri.bodegaweb.database.BodegaHelper;
import com.ranieri.bodegaweb.model.ListJson;
import com.ranieri.bodegaweb.model.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ranie on 16 de mai.
 */

public class OrdersDAO {

    private Context mContext;
    private final SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    private int indexId;
    private int indexOrderDate;
    private int indexTotalOrder;
    private int indexProviderId;

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

        String sql = "SELECT " +
                OrderContract.TABLE_NAME + ".*, " +
                ProviderContract.COMPANY +
                " FROM " +
                OrderContract.TABLE_NAME + ", " +
                ProviderContract.TABLE_NAME +
                " WHERE " +
                OrderContract.PROVIDER +
                " = " +
                ProviderContract.ID +
                " ORDER BY " +
                OrderContract.ORDERDATE + " DESC";

        Cursor cursor = db.rawQuery(sql, null);

        List<Order> lista = new ArrayList<>();
        Order order;
        getColumnIndex(cursor);
        int indexProviderName = cursor.getColumnIndex(ProviderContract.COLUMN_COMPANY);

        while (cursor.moveToNext()) {
            order = valuesFromCursor(cursor);
            order.getFornecedor().setEmpresa(cursor.getString(indexProviderName));
            lista.add(order);
        }
        cursor.close();
        db.close();
        return lista;
    }

    private Order valuesFromCursor(Cursor cursor) {
        Order o = new Order();
        Date dataFormatada;
        try {
            dataFormatada = formato.parse(cursor.getString(indexOrderDate));

            o.setId(cursor.getLong(indexId));
            o.setDataPedido(dataFormatada);
            o.setTotalPedido(cursor.getFloat(indexTotalOrder));
            o.getFornecedor().setId(cursor.getLong(indexProviderId));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return o;
    }

    private void getColumnIndex(Cursor cursor) {
        indexId = cursor.getColumnIndex(OrderContract.COLUMN_ID);
        indexOrderDate = cursor.getColumnIndex(OrderContract.COLUMN_ORDERDATE);
        indexTotalOrder = cursor.getColumnIndex(OrderContract.COLUMN_TOTALORDER);
        indexProviderId = cursor.getColumnIndex(OrderContract.COLUMN_PROVIDER);
    }

    private ContentValues valuesFromOrder(Order order) {
        ContentValues values = new ContentValues();
        values.put(OrderContract.COLUMN_ID, order.getId());
        values.put(OrderContract.COLUMN_ORDERDATE, formato.format(order.getDataPedido()));
        values.put(OrderContract.COLUMN_TOTALORDER, order.getTotalPedido());
        values.put(OrderContract.COLUMN_PROVIDER, order.getFornecedor().getId());

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
