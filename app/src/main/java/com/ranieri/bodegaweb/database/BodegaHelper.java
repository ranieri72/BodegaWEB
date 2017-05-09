package com.ranieri.bodegaweb.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ranieri.bodegaweb.contract.CategoriasContract;
import com.ranieri.bodegaweb.contract.ProdutosContract;
import com.ranieri.bodegaweb.contract.SubCategoriasContract;


public class BodegaHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "bodegaOlinda";

    public BodegaHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createProdutos = "CREATE TABLE " + ProdutosContract.TABLE_NAME + " (" +
                ProdutosContract._ID + " INTEGER PRIMARY KEY, " +
                ProdutosContract.NOME + " TEXT NOT NULL, " +
                ProdutosContract.ESTOQUE + " INTEGER NOT NULL, " +
                ProdutosContract.PRECO + " REAL NOT NULL, " +
                ProdutosContract.CATEGORIA + " INTEGER NOT NULL); ";

        String createCategorias = "CREATE TABLE " + CategoriasContract.TABLE_NAME + " (" +
                CategoriasContract._ID + " INTEGER PRIMARY KEY, " +
                CategoriasContract.NOME + " TEXT NOT NULL, " +
                CategoriasContract.ORDEM + " INTEGER NOT NULL, " +
                CategoriasContract.SUBCATEGORIA + " INTEGER NOT NULL); ";

        String createSubCategorias = "CREATE TABLE " + SubCategoriasContract.TABLE_NAME + " (" +
                SubCategoriasContract._ID + " INTEGER PRIMARY KEY, " +
                SubCategoriasContract.NOME + " TEXT NOT NULL); ";

        db.execSQL(createSubCategorias);
        db.execSQL(createCategorias);
        db.execSQL(createProdutos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
