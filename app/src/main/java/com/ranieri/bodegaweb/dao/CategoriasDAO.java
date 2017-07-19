package com.ranieri.bodegaweb.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ranieri.bodegaweb.contract.CategoriasContract;
import com.ranieri.bodegaweb.database.BodegaHelper;
import com.ranieri.bodegaweb.model.Categorias;
import com.ranieri.bodegaweb.model.ListJson;
import com.ranieri.bodegaweb.model.SubCategorias;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ranie on 4 de mai.
 */

public class CategoriasDAO {

    private Context mContext;
    private int indexId;
    private int indexName;
    private int indexOrder;
    private int indexSubCatId;

    public CategoriasDAO(Context mContext) {
        this.mContext = mContext;
    }

    public Categorias inserir(Categorias categoria) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesFromCategorias(categoria);
        long id = db.insert(CategoriasContract.TABLE_NAME, null, values);
        categoria.setId(id);

        db.close();
        return categoria;
    }

    public int inserir(List<Categorias> lista) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        int contador = 0;

        for (Categorias c : lista) {
            ContentValues values = valuesFromCategorias(c);
            db.insert(CategoriasContract.TABLE_NAME, null, values);
            contador++;
        }
        db.close();
        return contador;
    }

    public int atualizar(Categorias categoria) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] categoriaID = new String[]{String.valueOf(categoria.getId())};

        ContentValues values = valuesFromCategorias(categoria);
        int rowsAffected = db.update(CategoriasContract.TABLE_NAME, values, CategoriasContract.COLUMN_ID + " = ?", categoriaID);

        db.close();
        return rowsAffected;
    }

    public int excluir() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        int rowsAffected = db.delete(CategoriasContract.TABLE_NAME, null, null);

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

    private ContentValues valuesFromCategorias(Categorias categoria) {
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

//    public void refreshStock(ListJson lista) {
//        BodegaHelper helper = new BodegaHelper(mContext);
//        SQLiteDatabase db = helper.getWritableDatabase();
//        List<Categorias> listaBanco = listar();
//        boolean existe;
//
//        for (Categorias cJson : lista.getListaCategorias()) {
//            existe = false;
//            for (Categorias cBanco : listaBanco) {
//                if (cJson.getId() == cBanco.getId()) {
//                    atualizar(cJson);
//                    existe = true;
//                    break;
//                }
//            }
//            if (!existe) {
//                inserir(cJson);
//            }
//        }
//    }
}
