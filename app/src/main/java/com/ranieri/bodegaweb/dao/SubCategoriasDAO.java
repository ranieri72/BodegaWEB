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

public class SubCategoriasDAO {

    private Context mContext;

    public SubCategoriasDAO(Context mContext) {
        this.mContext = mContext;
    }

    public SubCategorias inserir(SubCategorias subCategoria) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesFromSubCategorias(subCategoria);
        long id = db.insert(SubCategoriasContract.TABLE_NAME, null, values);
        subCategoria.setId(id);

        db.close();
        return subCategoria;
    }

    public int inserir(List<SubCategorias> lista) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        int contador = 0;

        for (SubCategorias s : lista) {
            ContentValues values = valuesFromSubCategorias(s);
            db.insert(SubCategoriasContract.TABLE_NAME, null, values);
            contador++;
        }
        db.close();
        return contador;
    }

    public int atualizar(SubCategorias subCategoria) {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] subCategoriaID = new String[]{String.valueOf(subCategoria.getId())};

        ContentValues values = valuesFromSubCategorias(subCategoria);
        int rowsAffected = db.update(SubCategoriasContract.TABLE_NAME, values, SubCategoriasContract.TABLE_ID + " = ?", subCategoriaID);

        db.close();
        return rowsAffected;
    }

    public int excluir() {
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        int rowsAffected = db.delete(SubCategoriasContract.TABLE_NAME, null, null);

        db.close();
        return rowsAffected;
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

        int indexId = cursor.getColumnIndex(SubCategoriasContract.TABLE_ID);
        int indexName = cursor.getColumnIndex(SubCategoriasContract.TABLE_NOME);

        while (cursor.moveToNext()) {
            subCategoria = new SubCategorias();

            subCategoria.setId(cursor.getLong(indexId));
            subCategoria.setNome(cursor.getString(indexName));
            lista.add(subCategoria);
        }
        return lista;
    }

    private ContentValues valuesFromSubCategorias(SubCategorias subCategoria) {
        ContentValues values = new ContentValues();
        values.put(SubCategoriasContract.TABLE_ID, subCategoria.getId());
        values.put(SubCategoriasContract.TABLE_NOME, subCategoria.getNome());

        return values;
    }

    public int refreshStock(ListJson lista) {
        excluir();
        return inserir(lista.getListaSubcategorias());
    }

//    public void refreshStock(ListJson lista) {
//        BodegaHelper helper = new BodegaHelper(mContext);
//        SQLiteDatabase db = helper.getWritableDatabase();
//        List<SubCategorias> listaBanco = listar();
//        boolean existe;
//
//        for (SubCategorias cJson : lista.getListaSubcategorias()) {
//            existe = false;
//            for (SubCategorias cBanco : listaBanco) {
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
