package com.ranieri.bodegaweb.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ranieri.bodegaweb.contract.SubCategoriasContract;
import com.ranieri.bodegaweb.database.BodegaHelper;
import com.ranieri.bodegaweb.model.ListJson;
import com.ranieri.bodegaweb.model.SubCategorias;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ranie on 4 de mai.
 */

public class SubCategoriasDAO extends GenericDAO<SubCategorias> {

    public SubCategoriasDAO(Context mContext) {
        super(mContext);
        tableName = SubCategoriasContract.TABLE_NAME;
    }

    public int atualizar(SubCategorias subCategoria) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] subCategoriaID = new String[]{String.valueOf(subCategoria.getId())};

        ContentValues values = valuesFromObject(subCategoria);
        int rowsAffected = db.update(SubCategoriasContract.TABLE_NAME, values, SubCategoriasContract.COLUMN_ID + " = ?", subCategoriaID);

        db.close();
        return rowsAffected;
    }

    public SubCategorias selecionarPrimeira() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM " +
                SubCategoriasContract.TABLE_NAME +
                " ORDER BY " +
                SubCategoriasContract.ID +
                " LIMIT 1;";

        Cursor cursor = db.rawQuery(sql, null);

        SubCategorias subCategoria = null;
        if (cursor.moveToNext()) {
            int indexId = cursor.getColumnIndex(SubCategoriasContract.COLUMN_ID);
            int indexName = cursor.getColumnIndex(SubCategoriasContract.COLUMN_NAME);
            subCategoria = new SubCategorias();

            subCategoria.setId(cursor.getLong(indexId));
            subCategoria.setNome(cursor.getString(indexName));
        }

        cursor.close();
        db.close();
        return subCategoria;
    }

    public List<SubCategorias> listar() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + SubCategoriasContract.TABLE_NAME, null);

        List<SubCategorias> lista = valuesFromCursor(cursor);

        cursor.close();
        db.close();
        return lista;
    }

    private List<SubCategorias> valuesFromCursor(Cursor cursor) {
        List<SubCategorias> lista = new ArrayList<>();
        SubCategorias subCategoria;

        int indexId = cursor.getColumnIndex(SubCategoriasContract.COLUMN_ID);
        int indexName = cursor.getColumnIndex(SubCategoriasContract.COLUMN_NAME);

        while (cursor.moveToNext()) {
            subCategoria = new SubCategorias();

            subCategoria.setId(cursor.getLong(indexId));
            subCategoria.setNome(cursor.getString(indexName));
            lista.add(subCategoria);
        }
        return lista;
    }

    @Override
    protected ContentValues valuesFromObject(SubCategorias subCategoria) {
        ContentValues values = new ContentValues();
        values.put(SubCategoriasContract.COLUMN_ID, subCategoria.getId());
        values.put(SubCategoriasContract.COLUMN_NAME, subCategoria.getNome());
        return values;
    }

    public int refreshStock(ListJson lista) {
        excluir();
        return inserir(lista.getListaSubcategorias());
    }
}