package com.ranieri.bodegaweb.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ranieri.bodegaweb.contract.CategoriasContract;
import com.ranieri.bodegaweb.contract.OrderContract;
import com.ranieri.bodegaweb.contract.OrderItemsContract;
import com.ranieri.bodegaweb.contract.ProdutosContract;
import com.ranieri.bodegaweb.contract.ProviderContract;
import com.ranieri.bodegaweb.contract.SubCategoriasContract;
import com.ranieri.bodegaweb.contract.UnidadeMedidaContract;


public class BodegaHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "bodegaOlinda";

    public BodegaHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createProdutos = "CREATE TABLE " + ProdutosContract.TABLE_NAME + " (" +
                ProdutosContract.ID + " INTEGER PRIMARY KEY, " +
                ProdutosContract.NOME + " TEXT NOT NULL, " +
                ProdutosContract.ESTOQUE + " INTEGER NOT NULL, " +
                ProdutosContract.NOVOESTOQUE + " INTEGER NOT NULL, " +
                ProdutosContract.ALTERADO + " INTEGER DEFAULT 0, " +
                ProdutosContract.APAGADO + " INTEGER DEFAULT 0, " +
                ProdutosContract.PRECO + " REAL NOT NULL, " +
                ProdutosContract.CATEGORIA + " INTEGER NOT NULL); ";

        String createCategorias = "CREATE TABLE " + CategoriasContract.TABLE_NAME + " (" +
                CategoriasContract.ID + " INTEGER PRIMARY KEY, " +
                CategoriasContract.NOME + " TEXT NOT NULL, " +
                CategoriasContract.ORDEM + " INTEGER NOT NULL, " +
                CategoriasContract.SUBCATEGORIA + " INTEGER NOT NULL); ";

        String createSubCategorias = "CREATE TABLE " + SubCategoriasContract.TABLE_NAME + " (" +
                SubCategoriasContract.ID + " INTEGER PRIMARY KEY, " +
                SubCategoriasContract.NOME + " TEXT NOT NULL); ";

        String createOrder = "CREATE TABLE " + OrderContract.TABLE_NAME + " (" +
                OrderContract.ID + " INTEGER PRIMARY KEY, " +
                OrderContract.ORDERDATE + " TEXT NOT NULL, " +
                OrderContract.TOTALORDER + " REAL NOT NULL, " +
                OrderContract.PROVIDER + " INTEGER NOT NULL); ";

        String createProvider = "CREATE TABLE " + ProviderContract.TABLE_NAME + " (" +
                ProviderContract.ID + " INTEGER PRIMARY KEY, " +
                ProviderContract.COMPANY + " TEXT NOT NULL, " +
                ProviderContract.NAME + " TEXT NOT NULL, " +
                ProviderContract.PHONE + " TEXT NOT NULL); ";

        String createOrderItems = "CREATE TABLE " + OrderItemsContract.TABLE_NAME + " (" +
                OrderItemsContract.QTD + " INTEGER NOT NULL, " +
                OrderItemsContract.PRECOUNIT + " REAL NOT NULL, " +
                OrderItemsContract.UNIDADEMEDIDA + " INTEGER NOT NULL, " +
                OrderItemsContract.ORDER + " INTEGER NOT NULL, " +
                OrderItemsContract.PRODUTO + " INTEGER NOT NULL, " +
                "PRIMARY KEY (" + OrderItemsContract.ORDER + ", " + OrderItemsContract.PRODUTO + "));";

        String createUnidadeMedida = "CREATE TABLE " + UnidadeMedidaContract.TABLE_NAME + " (" +
                UnidadeMedidaContract.ID + " INTEGER PRIMARY KEY, " +
                UnidadeMedidaContract.NOME + " TEXT NOT NULL, " +
                UnidadeMedidaContract.MULTIPLICADOR + " INTEGER NOT NULL, " +
                UnidadeMedidaContract.ORDEM + " INTEGER NOT NULL); ";

        db.execSQL(createSubCategorias);
        db.execSQL(createCategorias);
        db.execSQL(createProdutos);
        db.execSQL(createProvider);
        db.execSQL(createUnidadeMedida);
        db.execSQL(createOrderItems);
        db.execSQL(createOrder);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
