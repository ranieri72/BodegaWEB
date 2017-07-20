package com.ranieri.bodegaweb.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ranieri.bodegaweb.contract.OrderContract;
import com.ranieri.bodegaweb.contract.OrderItemsContract;
import com.ranieri.bodegaweb.contract.ProdutosContract;
import com.ranieri.bodegaweb.contract.UnidadeMedidaContract;
import com.ranieri.bodegaweb.database.BodegaHelper;
import com.ranieri.bodegaweb.model.ListJson;
import com.ranieri.bodegaweb.model.Order;
import com.ranieri.bodegaweb.model.OrderItems;
import com.ranieri.bodegaweb.model.Produtos;
import com.ranieri.bodegaweb.model.Provider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ranie on 16 de mai.
 */

public class OrderItemsDAO extends GenericDAO<OrderItems> {

    public OrderItemsDAO(Context mContext, String tableName) {
        super(mContext, tableName);
    }

    public int atualizar(OrderItems orderItem) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        String orderID = String.valueOf(orderItem.getChaveComposta().getOrder().getId());
        String productID = String.valueOf(orderItem.getChaveComposta().getProdutos().getId());

        ContentValues values = valuesFromObject(orderItem);
        int rowsAffected = db.update(OrderItemsContract.TABLE_NAME, values,
                OrderItemsContract.ORDER + " = ?" + OrderItemsContract.PRODUCT + " = ?",
                new String[]{orderID, productID});

        db.close();
        return rowsAffected;
    }

    public List<OrderItems> listar() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT " +
                OrderItemsContract.TABLE_NAME + ".*, " +
                ProdutosContract.NAME + ", " +
                UnidadeMedidaContract.NAME +
                " FROM " +
                OrderItemsContract.TABLE_NAME + ", " +
                ProdutosContract.TABLE_NAME + ", " +
                UnidadeMedidaContract.TABLE_NAME +
                " WHERE " +
                OrderItemsContract.PRODUCT +
                " = " +
                ProdutosContract.ID +
                " AND " +
                OrderItemsContract.UNITMEASUREMENT +
                " = " +
                UnidadeMedidaContract.ID;

        Cursor cursor = db.rawQuery(sql, null);

        List<OrderItems> lista = valuesFromCursor(cursor);

        cursor.close();
        db.close();
        return lista;
    }

    public List<OrderItems> listar(Order order) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] idOrder = new String[]{String.valueOf(order.getId())};

        String sql = "SELECT " +
                OrderItemsContract.TABLE_NAME + ".*, " +
                ProdutosContract.NAME + ", " +
                UnidadeMedidaContract.NAME + ", " +
                UnidadeMedidaContract.MULTIPLIER +
                " FROM " +
                OrderItemsContract.TABLE_NAME + ", " +
                ProdutosContract.TABLE_NAME + ", " +
                UnidadeMedidaContract.TABLE_NAME +
                " WHERE " +
                OrderItemsContract.PRODUCT +
                " = " +
                ProdutosContract.ID +
                " AND " +
                OrderItemsContract.UNITMEASUREMENT +
                " = " +
                UnidadeMedidaContract.ID +
                " AND " +
                OrderItemsContract.ORDER +
                " = ?";

        Cursor cursor = db.rawQuery(sql, idOrder);

        List<OrderItems> lista = valuesFromCursor(cursor);

        cursor.close();
        db.close();
        return lista;
    }

    public List<OrderItems> listar(Provider provider, Produtos produto) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] idOrder = new String[]{String.valueOf(provider.getId()), String.valueOf(produto.getId())};

        String sql = "SELECT " +
                OrderItemsContract.UNITVALUE + ", " +
                OrderContract.ORDERDATE + ", " +
                UnidadeMedidaContract.MULTIPLIER +
                " FROM " +
                OrderItemsContract.TABLE_NAME + ", " +
                OrderContract.TABLE_NAME + ", " +
                UnidadeMedidaContract.TABLE_NAME +
                " WHERE " +
                OrderItemsContract.ORDER +
                " = " +
                OrderContract.ID +
                " AND " +
                OrderItemsContract.UNITMEASUREMENT +
                " = " +
                UnidadeMedidaContract.ID +
                " AND " +
                OrderContract.PROVIDER +
                " = ?" +
                " AND " +
                OrderItemsContract.PRODUCT +
                " = ?";

        Cursor cursor = db.rawQuery(sql, idOrder);

        List<OrderItems> lista = new ArrayList<>();
        OrderItems o;
        Date dataFormatada;
        final SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        int indexUnitValue = cursor.getColumnIndex(OrderItemsContract.COLUMN_UNITVALUE);
        int indexOrderDate = cursor.getColumnIndex(OrderContract.COLUMN_ORDERDATE);
        int indexUnitMeasuMult = cursor.getColumnIndex(UnidadeMedidaContract.COLUMN_MULTIPLIER);

        try {
            while (cursor.moveToNext()) {
                o = new OrderItems();
                dataFormatada = formato.parse(cursor.getString(indexOrderDate));

                o.setPrecoUnit(cursor.getDouble(indexUnitValue));
                o.getChaveComposta().getOrder().setDataPedido(dataFormatada);
                o.getUnidMedida().setMultiplicador(cursor.getInt(indexUnitMeasuMult));
                lista.add(o);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        cursor.close();
        db.close();
        return lista;
    }

    private List<OrderItems> valuesFromCursor(Cursor cursor) {
        List<OrderItems> lista = new ArrayList<>();
        OrderItems o;

        int indexKeyProdId = cursor.getColumnIndex(OrderItemsContract.COLUMN_PRODUCT);
        int indexKeyProdName = cursor.getColumnIndex(ProdutosContract.COLUMN_NAME);
        int indexKeyOrderId = cursor.getColumnIndex(OrderItemsContract.COLUMN_ORDER);
        int indexUnitValue = cursor.getColumnIndex(OrderItemsContract.COLUMN_UNITVALUE);
        int indexQtd = cursor.getColumnIndex(OrderItemsContract.COLUMN_QTD);
        int indexUnitMeasuId = cursor.getColumnIndex(OrderItemsContract.COLUMN_UNITMEASUREMENT);
        int indexUnitMeasuName = cursor.getColumnIndex(UnidadeMedidaContract.COLUMN_NAME);
        int indexUnitMeasuMult = cursor.getColumnIndex(UnidadeMedidaContract.COLUMN_MULTIPLIER);

        while (cursor.moveToNext()) {
            o = new OrderItems();

            o.getChaveComposta().getProdutos().setId(cursor.getLong(indexKeyProdId));
            o.getChaveComposta().getProdutos().setNome(cursor.getString(indexKeyProdName));
            o.getChaveComposta().getOrder().setId(cursor.getLong(indexKeyOrderId));
            o.setPrecoUnit(cursor.getDouble(indexUnitValue));
            o.setQtd(cursor.getInt(indexQtd));
            o.getUnidMedida().setId(cursor.getLong(indexUnitMeasuId));
            o.getUnidMedida().setNome(cursor.getString(indexUnitMeasuName));
            o.getUnidMedida().setMultiplicador(cursor.getInt(indexUnitMeasuMult));
            lista.add(o);
        }
        return lista;
    }

    @Override
    protected ContentValues valuesFromObject(OrderItems orderItem) {
        ContentValues values = new ContentValues();
        values.put(OrderItemsContract.COLUMN_ORDER, orderItem.getChaveComposta().getOrder().getId());
        values.put(OrderItemsContract.COLUMN_PRODUCT, orderItem.getChaveComposta().getProdutos().getId());
        values.put(OrderItemsContract.COLUMN_UNITVALUE, orderItem.getPrecoUnit());
        values.put(OrderItemsContract.COLUMN_QTD, orderItem.getQtd());
        values.put(OrderItemsContract.COLUMN_UNITMEASUREMENT, orderItem.getUnidMedida().getId());
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
