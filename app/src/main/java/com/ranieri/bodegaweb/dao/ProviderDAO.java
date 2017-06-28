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

    public Provider inserir(Provider provider) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesFromProvider(provider);
        long id = db.insert(ProviderContract.TABLE_NAME, null, values);
        provider.setId(id);

        db.close();
        return provider;
    }

    public int inserir(List<Provider> lista) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        int contador = 0;

        for (Provider provider : lista) {
            ContentValues values = valuesFromProvider(provider);
            db.insert(ProviderContract.TABLE_NAME, null, values);
            contador++;
        }
        db.close();
        return contador;
    }

    public int atualizar(Provider provider) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] providerId = new String[]{String.valueOf(provider.getId())};

        ContentValues values = valuesFromProvider(provider);
        int rowsAffected = db.update(ProviderContract.TABLE_NAME, values, ProviderContract.ID + " = ?", providerId);

        db.close();
        return rowsAffected;
    }

    public int excluir() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        int rowsAffected = db.delete(ProviderContract.TABLE_NAME, null, null);

        db.close();
        return rowsAffected;
    }

    public List<Provider> listar() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + ProviderContract.TABLE_NAME, null);

        List<Provider> lista = valuesFromCursor(cursor);
        cursor.close();
        db.close();
        return lista;
    }

    private List<Provider> valuesFromCursor(Cursor cursor) {
        List<Provider> lista = new ArrayList<>();
        Provider provider;

        int indexId = cursor.getColumnIndex(ProviderContract.COLUMN_ID);
        int indexName = cursor.getColumnIndex(ProviderContract.COLUMN_NAME);
        int indexPhone = cursor.getColumnIndex(ProviderContract.COLUMN_PHONE);
        int indexCompany = cursor.getColumnIndex(ProviderContract.COLUMN_COMPANY);

        while (cursor.moveToNext()) {
            Provider p = new Provider();
            p.setId(cursor.getLong(indexId));
            p.setNome(cursor.getString(indexName));
            p.setFone(cursor.getString(indexPhone));
            p.setEmpresa(cursor.getString(indexCompany));
            lista.add(p);
        }
        return lista;
    }

    private ContentValues valuesFromProvider(Provider provider) {
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

//    public void refreshOrders(ListJson listJson) {
//        BodegaHelper helper = new BodegaHelper(mContext);
//        SQLiteDatabase db = helper.getWritableDatabase();
//        List<Provider> listaBanco = listar();
//        boolean existe;
//
//        for (Provider pJson : listJson.getListaProvider()) {
//            existe = false;
//            for (Provider pBanco : listaBanco) {
//                if (pJson.getId() == pBanco.getId()) {
//                    atualizar(pJson);
//                    existe = true;
//                    break;
//                }
//            }
//            if (!existe) {
//                inserir(pJson);
//            }
//        }
//    }
}
