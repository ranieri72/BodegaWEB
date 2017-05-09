package com.ranieri.bodegaweb.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ranieri.bodegaweb.contract.CategoriasContract;
import com.ranieri.bodegaweb.contract.SubCategoriasContract;
import com.ranieri.bodegaweb.database.BodegaHelper;
import com.ranieri.bodegaweb.model.SubCategorias;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ranie on 4 de mai.
 */

public class SubCategoriasDAO {

    private Context mContext;
    private SubCategorias subCategoria;

    public SubCategoriasDAO(Context mContext) { this.mContext = mContext; }

    public SubCategorias inserir(SubCategorias subCategoria){
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = valuesFromSubCategorias(subCategoria);
        long id = db.insert(SubCategoriasContract.TABLE_NAME, null, values);
        subCategoria.setId(id);

        db.close();
        return subCategoria;
    }

    public List<SubCategorias> listar(){
        BodegaHelper helper = new BodegaHelper(mContext);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + SubCategoriasContract.TABLE_NAME, null);

        List<SubCategorias> lista = valuesFromCursor(cursor);

        cursor.close();
        db.close();
        return lista;
    }

    private List<SubCategorias> valuesFromCursor(Cursor cursor) {
        List<SubCategorias> lista = new ArrayList<SubCategorias>();
        SubCategorias subCategoria;

        while (cursor.moveToNext()){
            subCategoria = new SubCategorias();

            subCategoria.setId(cursor.getLong(cursor.getColumnIndex(SubCategoriasContract._ID)));
            subCategoria.setNome(cursor.getString(cursor.getColumnIndex(SubCategoriasContract.NOME)));
            lista.add(subCategoria);
        }
        return lista;
    }

    private ContentValues valuesFromSubCategorias(SubCategorias subCategoria) {
        ContentValues values = new ContentValues();
        values.put(CategoriasContract._ID, subCategoria.getId());
        values.put(CategoriasContract.NOME, subCategoria.getNome());

        return values;
    }
}
