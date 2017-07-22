package com.ranieri.bodegaweb.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ranieri.bodegaweb.contract.ProviderContract;
import com.ranieri.bodegaweb.database.BodegaHelper;
import com.ranieri.bodegaweb.model.ListJson;
import com.ranieri.bodegaweb.model.Provider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ranie on 16 de mai.
 */

public class ProviderDAO extends GenericDAO<Provider> {

    public ProviderDAO(Context context) {
        super(context);
        tableName = ProviderContract.TABLE_NAME;
    }

    public int atualizar(Provider provider) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] providerId = new String[]{String.valueOf(provider.getId())};

        ContentValues values = valuesFromObject(provider);
        int rowsAffected = db.update(tableName, values, ProviderContract.ID + " = ?", providerId);

        db.close();
        return rowsAffected;
    }

    public List<Provider> listar() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM " +
                ProviderContract.TABLE_NAME +
                " ORDER BY " +
                ProviderContract.NAME;

        Cursor cursor = db.rawQuery(sql, null);

        List<Provider> lista = valuesFromCursor(cursor);
        cursor.close();
        db.close();
        return lista;
    }

    private List<Provider> valuesFromCursor(Cursor cursor) {
        List<Provider> lista = new ArrayList<>();
        Provider p;

        int indexId = cursor.getColumnIndex(ProviderContract.COLUMN_ID);
        int indexName = cursor.getColumnIndex(ProviderContract.COLUMN_NAME);
        int indexPhone = cursor.getColumnIndex(ProviderContract.COLUMN_PHONE);
        int indexCompany = cursor.getColumnIndex(ProviderContract.COLUMN_COMPANY);

        while (cursor.moveToNext()) {
            p = new Provider();
            p.setId(cursor.getLong(indexId));
            p.setNome(cursor.getString(indexName));
            p.setFone(cursor.getString(indexPhone));
            p.setEmpresa(cursor.getString(indexCompany));
            lista.add(p);
        }
        return lista;
    }

    @Override
    protected ContentValues valuesFromObject(Provider provider) {
        ContentValues values = new ContentValues();
        values.put(ProviderContract.COLUMN_ID, provider.getId());
        values.put(ProviderContract.COLUMN_NAME, provider.getNome());
        values.put(ProviderContract.COLUMN_PHONE, provider.getFone());
        values.put(ProviderContract.COLUMN_COMPANY, provider.getEmpresa());
        return values;
    }

    public int refreshOrders(ListJson listJson) {
        excluir();
        return inserir(listJson.getListaProvider());
    }
}