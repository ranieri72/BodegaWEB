package com.ranieri.bodegaweb.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ranieri.bodegaweb.contract.CategoriasContract;
import com.ranieri.bodegaweb.contract.SubCategoriasContract;
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
        int rowsAffected = db.update(CategoriasContract.TABLE_NAME, values, CategoriasContract.TABLE_ID + " = ?", categoriaID);

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

    public List<Categorias> listar(SubCategorias subCategoria) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] subCategoriaID = new String[]{String.valueOf(subCategoria.getId())};

        Cursor cursor = db.rawQuery("SELECT * FROM " +
                CategoriasContract.TABLE_NAME +
                " WHERE " +
                CategoriasContract.SUBCATEGORIA + " = ?", subCategoriaID);

        List<Categorias> lista = valuesFromCursor(cursor);

        cursor.close();
        db.close();
        return lista;
    }

    public List<Categorias> listar() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + CategoriasContract.TABLE_NAME, null);

        List<Categorias> lista = valuesFromCursor(cursor);

        cursor.close();
        db.close();
        return lista;
    }

    private List<Categorias> valuesFromCursor(Cursor cursor) {
        List<Categorias> lista = new ArrayList<>();
        Categorias categoria;

        int indexId = cursor.getColumnIndex(CategoriasContract.TABLE_ID);
        int indexName = cursor.getColumnIndex(CategoriasContract.TABLE_NOME);
        int indexOrder = cursor.getColumnIndex(CategoriasContract.TABLE_ORDEM);
        int indexSubCatId = cursor.getColumnIndex(SubCategoriasContract.TABLE_ID);
        int indexSubCatName = cursor.getColumnIndex(SubCategoriasContract.TABLE_NOME);

        while (cursor.moveToNext()) {
            categoria = new Categorias();

            categoria.setId(cursor.getLong(indexId));
            categoria.setNome(cursor.getString(indexName));
            categoria.setOrdem(cursor.getInt(indexOrder));

            categoria.getSubCategoriaProd().setId(cursor.getLong(indexSubCatId));
            categoria.getSubCategoriaProd().setNome(cursor.getString(indexSubCatName));

            lista.add(categoria);
        }
        return lista;
    }

    private ContentValues valuesFromCategorias(Categorias categoria) {
        ContentValues values = new ContentValues();
        values.put(CategoriasContract.TABLE_ID, categoria.getId());
        values.put(CategoriasContract.TABLE_NOME, categoria.getNome());
        values.put(CategoriasContract.TABLE_ORDEM, categoria.getOrdem());
        values.put(CategoriasContract.TABLE_SUBCATEGORIA, categoria.getSubCategoriaProd().getId());

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
