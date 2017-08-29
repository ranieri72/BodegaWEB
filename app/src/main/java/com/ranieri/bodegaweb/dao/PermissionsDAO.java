package com.ranieri.bodegaweb.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ranieri.bodegaweb.dao.contract.PermissionsContract;
import com.ranieri.bodegaweb.dao.database.BodegaHelper;
import com.ranieri.bodegaweb.model.PermissionsApp;
import com.ranieri.bodegaweb.model.User;

/**
 * Created by ranie on 15 de ago.
 */

public class PermissionsDAO extends GenericDAO<PermissionsApp> {

    public PermissionsDAO(Context mContext) {
        super(mContext);
        tableName = PermissionsContract.TABLE_NAME;
    }

    public int excluir(User user) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        String where = PermissionsContract.COLUMN_ID + " = ?";
        String[] userId = new String[]{String.valueOf(user.getId())};

        int rowsAffected = db.delete(tableName, where, userId);

        db.close();
        return rowsAffected;
    }

    public int update(PermissionsApp permissionsApp) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] idUser = new String[]{String.valueOf(permissionsApp.getId())};

        ContentValues values = valuesFromObject(permissionsApp);
        int rowsAffected = db.update(PermissionsContract.TABLE_NAME, values, PermissionsContract.COLUMN_ID + " = ?", idUser);

        db.close();
        return rowsAffected;
    }

    @Override
    protected ContentValues valuesFromObject(PermissionsApp permissions) {
        ContentValues values = new ContentValues();
        values.put(PermissionsContract.COLUMN_ID, permissions.getId());
        values.put(PermissionsContract.COLUMN_VER_ESTOQUE, (permissions.isVerEstoque()) ? 1 : 0);
        values.put(PermissionsContract.COLUMN_VER_FORNECEDORES, (permissions.isVerFornecedores()) ? 1 : 0);
        values.put(PermissionsContract.COLUMN_VER_PEDIDOS, (permissions.isVerPedidos()) ? 1 : 0);
        return values;
    }
}