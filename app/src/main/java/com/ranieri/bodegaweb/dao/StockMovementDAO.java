package com.ranieri.bodegaweb.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ranieri.bodegaweb.dao.contract.StockMovementContract;
import com.ranieri.bodegaweb.dao.database.BodegaHelper;
import com.ranieri.bodegaweb.model.Produtos;
import com.ranieri.bodegaweb.model.StockMovement;
import com.ranieri.bodegaweb.model.UnidadeMedida;
import com.ranieri.bodegaweb.model.User;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ranieri.bodegaweb.util.Util.formatoData;
import static com.ranieri.bodegaweb.util.Util.formatoHora;

/**
 * Created by ranie on 2 de ago.
 */

public class StockMovementDAO extends GenericDAO<StockMovement> {

    private int indexQtd;
    private int indexHora;
    private int indexData;
    private int indexPerda;
    private int indexProduto;
    private int indexUnidMedida;
    private int indexUser;

    public StockMovementDAO(Context mContext) {
        super(mContext);
        tableName = StockMovementContract.TABLE_NAME;
    }

    public List<StockMovement> listar() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM " + tableName;

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

    public int contar() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT COUNT(*) FROM " + tableName;

        Cursor cursor = db.rawQuery(sql, null);
        int quantidade = 0;

        if (cursor.moveToNext()) {
            quantidade = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return quantidade;
    }

    private void getColumnIndex(Cursor cursor) {
        indexQtd = cursor.getColumnIndex(StockMovementContract.COLUMN_QTD);
        indexHora = cursor.getColumnIndex(StockMovementContract.COLUMN_HORA);
        indexData = cursor.getColumnIndex(StockMovementContract.COLUMN_DATA);
        indexPerda = cursor.getColumnIndex(StockMovementContract.COLUMN_PERDA);
        indexProduto = cursor.getColumnIndex(StockMovementContract.COLUMN_PRODUCT);
        indexUnidMedida = cursor.getColumnIndex(StockMovementContract.COLUMN_UNITMEASUREMENT);
        indexUser = cursor.getColumnIndex(StockMovementContract.COLUMN_USER);
    }

    private StockMovement valuesFromCursor(Cursor cursor) {
        StockMovement s = new StockMovement();
        Date dataFormatada;
        Date horaFormatada;
        try {
            s.setQtd(cursor.getInt(indexQtd));

            horaFormatada = formatoHora.parse(cursor.getString(indexHora));
            s.setHora(horaFormatada);

            dataFormatada = formatoData.parse(cursor.getString(indexData));
            s.setData(dataFormatada);

            s.setPerda((cursor.getInt(indexPerda)) == 1);
            s.setProduto(new Produtos(cursor.getInt(indexProduto)));
            s.setUnidMedida(new UnidadeMedida(cursor.getInt(indexUnidMedida)));
            s.setUser(new User(cursor.getInt(indexUser)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return s;
    }

    @Override
    protected ContentValues valuesFromObject(StockMovement stockMovement) {
        ContentValues values = new ContentValues();
        values.put(StockMovementContract.COLUMN_QTD, stockMovement.getQtd());
        values.put(StockMovementContract.COLUMN_HORA, formatoHora.format(stockMovement.getHora()));
        values.put(StockMovementContract.COLUMN_DATA, formatoData.format(stockMovement.getData()));
        values.put(StockMovementContract.COLUMN_PERDA, (stockMovement.isPerda()) ? 1 : 0);
        values.put(StockMovementContract.COLUMN_PRODUCT, stockMovement.getProduto().getId());
        values.put(StockMovementContract.COLUMN_UNITMEASUREMENT, stockMovement.getUnidMedida().getId());
        values.put(StockMovementContract.COLUMN_USER, stockMovement.getUser().getId());
        return values;
    }
}