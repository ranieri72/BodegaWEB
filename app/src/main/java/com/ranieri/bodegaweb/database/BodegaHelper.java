package com.ranieri.bodegaweb.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ranieri.bodegaweb.contract.CategoriasContract;
import com.ranieri.bodegaweb.contract.OrderContract;
import com.ranieri.bodegaweb.contract.ProdutosContract;
import com.ranieri.bodegaweb.contract.ProviderContract;
import com.ranieri.bodegaweb.contract.SubCategoriasContract;
import com.ranieri.bodegaweb.model.Produtos;


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
                ProdutosContract.NOVOESTOQUE + " INTEGER NOT NULL, " +
                ProdutosContract.ALTERADO + " INTEGER NOT NULL, " +
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

        String createOrder = "CREATE TABLE " + OrderContract.TABLE_NAME + " (" +
                OrderContract._ID + " INTEGER PRIMARY KEY, " +
                OrderContract.ORDERDATE + " TEXT NOT NULL, " +
                OrderContract.TOTALORDER + " REAL NOT NULL, " +
                OrderContract.PROVIDER + " INTEGER NOT NULL); ";

        String createProvider = "CREATE TABLE " + ProviderContract.TABLE_NAME + " (" +
                ProviderContract._ID + " INTEGER PRIMARY KEY, " +
                ProviderContract.COMPANY + " TEXT NOT NULL, " +
                ProviderContract.NAME + " TEXT NOT NULL, " +
                ProviderContract.PHONE + " TEXT NOT NULL); ";

        db.execSQL(createSubCategorias);
        db.execSQL(createCategorias);
        db.execSQL(createProdutos);
        db.execSQL(createProvider);
        db.execSQL(createOrder);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
