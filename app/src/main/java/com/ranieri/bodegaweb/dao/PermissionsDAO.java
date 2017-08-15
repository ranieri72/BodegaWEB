package com.ranieri.bodegaweb.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ranieri.bodegaweb.dao.contract.PermissionsContract;
import com.ranieri.bodegaweb.dao.database.BodegaHelper;
import com.ranieri.bodegaweb.model.Permissions;
import com.ranieri.bodegaweb.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ranie on 15 de ago.
 */

public class PermissionsDAO extends GenericDAO<Permissions> {

    private int indexId;
    private int indexPermitido;
    private int indexUser;
    private int indexName;

    public PermissionsDAO(Context mContext) {
        super(mContext);
        tableName = PermissionsContract.TABLE_NAME;
    }

    public List<Permissions> listar() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM " + tableName;

        Cursor cursor = db.rawQuery(sql, null);

        List<Permissions> lista = new ArrayList<>();
        Permissions permissions;
        getColumnIndex(cursor);

        while (cursor.moveToNext()) {
            permissions = valuesFromCursor(cursor);
            lista.add(permissions);
        }
        cursor.close();
        db.close();
        return lista;
    }

    private void getColumnIndex(Cursor cursor) {
        indexId = cursor.getColumnIndex(PermissionsContract.COLUMN_ID);
        indexPermitido = cursor.getColumnIndex(PermissionsContract.COLUMN_PERMITIDO);
        indexUser = cursor.getColumnIndex(PermissionsContract.COLUMN_USER);
        indexName = cursor.getColumnIndex(PermissionsContract.COLUMN_NOME);
    }

    private Permissions valuesFromCursor(Cursor cursor) {
        Permissions permissions = new Permissions();

        permissions.setId(cursor.getInt(indexId));
        permissions.setName(cursor.getString(indexName));
        permissions.setUser(new User(cursor.getInt(indexUser)));
        permissions.setPermitido((cursor.getInt(indexPermitido)) == 1);
        return permissions;
    }

    @Override
    protected ContentValues valuesFromObject(Permissions permissions) {
        ContentValues values = new ContentValues();
        values.put(PermissionsContract.COLUMN_ID, permissions.getId());
        values.put(PermissionsContract.COLUMN_NOME, permissions.getName());
        values.put(PermissionsContract.COLUMN_PERMITIDO, (permissions.isPermitido()) ? 1 : 0);
        values.put(PermissionsContract.COLUMN_USER, permissions.getUser().getId());
        return values;
    }
}
