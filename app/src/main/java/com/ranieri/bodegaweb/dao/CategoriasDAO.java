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
    private Categorias categoria;

    public CategoriasDAO(Context mContext) { this.mContext = mContext; }

    public Categorias inserir(Categorias categoria){
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesFromCategorias(categoria);
        long id = db.insert(CategoriasContract.TABLE_NAME, null, values);
        categoria.setId(id);

        db.close();
        return categoria;
    }

    public void inserirLista(List<Categorias> lista){
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        for (Categorias c : lista) {
            ContentValues values = valuesFromCategorias(c);
            db.insert(CategoriasContract.TABLE_NAME, null, values);
        }
        db.close();
    }

    public int atualizar(Categorias categoria){
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesFromCategorias(categoria);
        int rowsAffected = db.update(CategoriasContract.TABLE_NAME, values, CategoriasContract._ID + " = ?", new String[]{String.valueOf(categoria.getId())});

        db.close();
        return rowsAffected;
    }

    public List<Categorias> listarPorSubCategoria(SubCategorias subCategoria){
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + CategoriasContract.TABLE_NAME +
                " WHERE " + CategoriasContract.TABLE_NAME + "." + CategoriasContract.SUBCATEGORIA + " = ?", new String[]{String.valueOf(subCategoria.getId())});

        List<Categorias> lista = valuesFromCursor(cursor);

        cursor.close();
        db.close();
        return lista;
    }

    public List<Categorias> listar(){
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

        while (cursor.moveToNext()){
            categoria = new Categorias();

            categoria.setId(cursor.getLong(cursor.getColumnIndex(CategoriasContract._ID)));
            categoria.setNome(cursor.getString(cursor.getColumnIndex(CategoriasContract.NOME)));
            categoria.setOrdem(cursor.getInt(cursor.getColumnIndex(CategoriasContract.ORDEM)));

            categoria.getSubCategoria().setId(cursor.getLong(cursor.getColumnIndex(SubCategoriasContract._ID)));
            categoria.getSubCategoria().setNome(cursor.getString(cursor.getColumnIndex(SubCategoriasContract.NOME)));

            lista.add(categoria);
        }
        return lista;
    }

    private ContentValues valuesFromCategorias(Categorias categoria) {
        ContentValues values = new ContentValues();
        values.put(CategoriasContract._ID, categoria.getId());
        values.put(CategoriasContract.NOME, categoria.getNome());
        values.put(CategoriasContract.ORDEM, categoria.getOrdem());
        values.put(CategoriasContract.SUBCATEGORIA, categoria.getSubCategoria().getId());

        return values;
    }

    public void refreshStock(ListJson lista) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        List<Categorias> listaBanco = listar();
        boolean existe;

        for (Categorias cJson : lista.getListaCategorias()) {
            existe = false;
            for (Categorias cBanco : listaBanco) {
                if (cJson.getId() == cBanco.getId()) {
                    atualizar(cJson);
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                inserir(cJson);
            }
        }
    }
}
