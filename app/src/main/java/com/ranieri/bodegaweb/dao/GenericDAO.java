package com.ranieri.bodegaweb.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ranieri.bodegaweb.database.BodegaHelper;

import java.util.List;

/**
 * Created by ranie on 20 de jul.
 */

abstract class GenericDAO<T> {

    protected Context mContext;
    protected String tableName;

    public GenericDAO(Context mContext, String tableName) {
        this.mContext = mContext;
        this.tableName = tableName;
    }

    public T inserir(T object) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesFromObject(object);
        db.insert(tableName, null, values);

        db.close();
        return object;
    }

    public int inserir(List<T> lista) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        int contador = 0;

        for (T o : lista) {
            ContentValues values = valuesFromObject(o);
            db.insert(tableName, null, values);
            contador++;
        }
        db.close();
        return contador;
    }

    public int excluir() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        int rowsAffected = db.delete(tableName, null, null);

        db.close();
        return rowsAffected;
    }

    protected abstract ContentValues valuesFromObject(T object);
}
