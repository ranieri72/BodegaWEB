package com.ranieri.bodegaweb.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ranieri.bodegaweb.dao.contract.StockMovementContract;
import com.ranieri.bodegaweb.dao.database.BodegaHelper;
import com.ranieri.bodegaweb.model.ListJson;
import com.ranieri.bodegaweb.model.StockMovement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ranie on 2 de ago.
 */

public class StockMovementDAO extends GenericDAO<StockMovement> {

    private final SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
    private final SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

    public StockMovementDAO(Context mContext) {
        super(mContext);
    }

    public List<StockMovement> listar() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM " + StockMovementContract.TABLE_NAME;

        Cursor cursor = db.rawQuery(sql, null);

        List<StockMovement> lista = new ArrayList<>();
        StockMovement stockMovement;
        getColumnIndex(cursor);

        while (cursor.moveToNext()) {
            stockMovement = valuesFromCursor(cursor);
            lista.add(stockMovement);
        }
        cursor.close();
        db.close();
        return lista;
    }

    private StockMovement valuesFromCursor(Cursor cursor) {
        return null;
    }

    private void getColumnIndex(Cursor cursor) {
    }

    @Override
    protected ContentValues valuesFromObject(StockMovement stockMovement) {
        ContentValues values = new ContentValues();
        values.put(StockMovementContract.COLUMN_ID, stockMovement.getId());
        values.put(StockMovementContract.COLUMN_QTD, stockMovement.getQtd());
        values.put(StockMovementContract.COLUMN_HORA, formatoHora.format(stockMovement.getHora()));
        values.put(StockMovementContract.COLUMN_DATA, formatoData.format(stockMovement.getData()));
        values.put(StockMovementContract.COLUMN_PERDA, (stockMovement.isPerda()) ? 1 : 0);
        values.put(StockMovementContract.COLUMN_PRODUCT, stockMovement.getProduto().getId());
        values.put(StockMovementContract.COLUMN_UNITMEASUREMENT, stockMovement.getUnidMedida().getId());
        return values;
    }

    public int refreshOrders(ListJson listJson) {
        excluir();
        return inserir(listJson.getListaStockMovement());
    }
}
