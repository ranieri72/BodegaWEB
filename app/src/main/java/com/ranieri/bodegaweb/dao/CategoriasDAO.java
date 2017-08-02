package com.ranieri.bodegaweb.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ranieri.bodegaweb.dao.contract.CategoriasContract;
import com.ranieri.bodegaweb.dao.database.BodegaHelper;
import com.ranieri.bodegaweb.model.Categorias;
import com.ranieri.bodegaweb.model.ListJson;
import com.ranieri.bodegaweb.model.SubCategorias;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ranie on 4 de mai.
 */

public class CategoriasDAO extends GenericDAO<Categorias> {

    private int indexId;
    private int indexName;
    private int indexOrder;
    private int indexSubCatId;

    public CategoriasDAO(Context mContext) {
        super(mContext);
        tableName = CategoriasContract.TABLE_NAME;
    }

    public int atualizar(Categorias categoria) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] categoriaID = new String[]{String.valueOf(categoria.getId())};

        ContentValues values = valuesFromObject(categoria);
        int rowsAffected = db.update(tableName, values, CategoriasContract.COLUMN_ID + " = ?", categoriaID);

        db.close();
        return rowsAffected;
    }

    public Categorias selecionarPrimeira(SubCategorias subCategoria) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] subCategoriaID = new String[]{String.valueOf(subCategoria.getId())};

        String sql = "SELECT * FROM " +
                CategoriasContract.TABLE_NAME +
                " WHERE " +
                CategoriasContract.SUBCATEGORY + " = ?" +
                " ORDER BY " +
                CategoriasContract.ORDER + " ASC" +
                " LIMIT 1;";

        Cursor cursor = db.rawQuery(sql, subCategoriaID);

        Categorias categorias = null;

        if (cursor.moveToNext()) {
            getColumnIndex(cursor);
            categorias = valuesFromCursor(cursor);
        }
        cursor.close();
        db.close();
        return categorias;
    }

    public List<Categorias> listar(SubCategorias subCategoria) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] subCategoriaID = new String[]{String.valueOf(subCategoria.getId())};

        String sql = "SELECT * FROM " +
                CategoriasContract.TABLE_NAME +
                " WHERE " +
                CategoriasContract.SUBCATEGORY + " = ?" +
                " ORDER BY " +
                CategoriasContract.ORDER + " ASC;";

        Cursor cursor = db.rawQuery(sql, subCategoriaID);

        List<Categorias> lista = new ArrayList<>();
        Categorias categorias;
        getColumnIndex(cursor);

        while (cursor.moveToNext()) {
            categorias = valuesFromCursor(cursor);
            lista.add(categorias);
        }
        cursor.close();
        db.close();
        return lista;
    }

    public List<Categorias> listar() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + CategoriasContract.TABLE_NAME, null);

        List<Categorias> lista = new ArrayList<>();
        Categorias categorias;
        getColumnIndex(cursor);

        while (cursor.moveToNext()) {
            categorias = valuesFromCursor(cursor);
            lista.add(categorias);
        }
        cursor.close();
        db.close();
        return lista;
    }

    private Categorias valuesFromCursor(Cursor cursor) {
        Categorias categoria = new Categorias();

        categoria.setId(cursor.getLong(indexId));
        categoria.setNome(cursor.getString(indexName));
        categoria.setOrdem(cursor.getInt(indexOrder));
        categoria.getSubCategoriaProd().setId(cursor.getLong(indexSubCatId));
        return categoria;
    }

    private void getColumnIndex(Cursor cursor) {
        indexId = cursor.getColumnIndex(CategoriasContract.COLUMN_ID);
        indexName = cursor.getColumnIndex(CategoriasContract.COLUMN_NAME);
        indexOrder = cursor.getColumnIndex(CategoriasContract.COLUMN_ORDER);
        indexSubCatId = cursor.getColumnIndex(CategoriasContract.COLUMN_SUBCATEGORY);
    }

    @Override
    protected ContentValues valuesFromObject(Categorias categoria) {
        ContentValues values = new ContentValues();
        values.put(CategoriasContract.COLUMN_ID, categoria.getId());
        values.put(CategoriasContract.COLUMN_NAME, categoria.getNome());
        values.put(CategoriasContract.COLUMN_ORDER, categoria.getOrdem());
        values.put(CategoriasContract.COLUMN_SUBCATEGORY, categoria.getSubCategoriaProd().getId());
        return values;
    }

    public int refreshStock(ListJson lista) {
        excluir();
        return inserir(lista.getListaCategorias());
    }
}