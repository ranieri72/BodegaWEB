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

public class ProviderDAO {

    private Context mContext;

    public ProviderDAO(Context context) {
        mContext = context;
    }

    private void inserir(Provider provider) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesFromProvider(provider);
        long id = db.insert(ProviderContract.TABLE_NAME, null, values);

        db.close();
    }

    private int atualizar(Provider provider) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesFromProvider(provider);
        int rowsAffected = db.update(ProviderContract.TABLE_NAME, values, ProviderContract._ID + " = ?", new String[]{String.valueOf(provider.getId())});

        db.close();
        return rowsAffected;
    }

    private List<Provider> listar() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM provider", null);

        List<Provider> lista = new ArrayList<>();
        Provider provider;

        while (cursor.moveToNext()){
            provider = valuesFromCursor(cursor);
            lista.add(provider);
        }

        cursor.close();
        db.close();
        return lista;
    }

    public void refreshStock(ListJson listJson) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        List<Provider> listaBanco = listar();
        boolean existe;

        for (Provider pJson : listJson.getListaProvider()) {
            existe = false;
            for (Provider pBanco : listaBanco) {
                if (pJson.getId() == pBanco.getId()) {
                    atualizar(pJson);
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                inserir(pJson);
            }
        }
    }

    private ContentValues valuesFromProvider(Provider provider) {
        ContentValues values = new ContentValues();
        values.put(ProviderContract._ID, provider.getId());
        values.put(ProviderContract.NAME, provider.getNome());
        values.put(ProviderContract.PHONE, provider.getFone());

        return values;
    }

    private Provider valuesFromCursor(Cursor cursor) {
        Provider p = new Provider();
        p.setId(cursor.getLong(cursor.getColumnIndex(ProviderContract._ID)));
        p.setNome(cursor.getString(cursor.getColumnIndex(ProviderContract.NAME)));
        p.setFone(cursor.getString(cursor.getColumnIndex(ProviderContract.PHONE)));
        return p;
    }
}
