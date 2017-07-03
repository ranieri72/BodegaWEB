package com.ranieri.bodegaweb.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ranieri.bodegaweb.contract.CategoriasContract;
import com.ranieri.bodegaweb.contract.OrderContract;
import com.ranieri.bodegaweb.contract.OrderItemsContract;
import com.ranieri.bodegaweb.contract.PagesContract;
import com.ranieri.bodegaweb.contract.PermissionsContract;
import com.ranieri.bodegaweb.contract.ProdutosContract;
import com.ranieri.bodegaweb.contract.ProviderContract;
import com.ranieri.bodegaweb.contract.SubCategoriasContract;
import com.ranieri.bodegaweb.contract.UnidadeMedidaContract;
import com.ranieri.bodegaweb.contract.UserContract;


public class BodegaHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "bodegaOlinda";

    public BodegaHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable;

        createTable = "CREATE TABLE " + ProdutosContract.TABLE_NAME + " (" +
                ProdutosContract.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                ProdutosContract.COLUMN_NAME + " TEXT NOT NULL, " +
                ProdutosContract.COLUMN_STOCK + " INTEGER NOT NULL, " +
                ProdutosContract.COLUMN_NEWSTOCK + " INTEGER NOT NULL, " +
                ProdutosContract.COLUMN_ALTERED + " INTEGER DEFAULT 0, " +
                ProdutosContract.COLUMN_DELETED + " INTEGER DEFAULT 0, " +
                ProdutosContract.COLUMN_PRICE + " REAL NOT NULL, " +
                ProdutosContract.COLUMN_CATEGORY + " INTEGER NOT NULL); ";
        db.execSQL(createTable);

        createTable = "CREATE TABLE " + CategoriasContract.TABLE_NAME + " (" +
                CategoriasContract.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                CategoriasContract.COLUMN_NAME + " TEXT NOT NULL, " +
                CategoriasContract.COLUMN_ORDER + " INTEGER NOT NULL, " +
                CategoriasContract.COLUMN_SUBCATEGORY + " INTEGER NOT NULL); ";
        db.execSQL(createTable);

        createTable = "CREATE TABLE " + SubCategoriasContract.TABLE_NAME + " (" +
                SubCategoriasContract.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                SubCategoriasContract.COLUMN_NAME + " TEXT NOT NULL); ";
        db.execSQL(createTable);

        createTable = "CREATE TABLE " + OrderContract.TABLE_NAME + " (" +
                OrderContract.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                OrderContract.COLUMN_ORDERDATE + " TEXT NOT NULL, " +
                OrderContract.COLUMN_TOTALORDER + " REAL NOT NULL, " +
                OrderContract.COLUMN_PROVIDER + " INTEGER NOT NULL); ";
        db.execSQL(createTable);

        createTable = "CREATE TABLE " + ProviderContract.TABLE_NAME + " (" +
                ProviderContract.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                ProviderContract.COLUMN_COMPANY + " TEXT NOT NULL, " +
                ProviderContract.COLUMN_NAME + " TEXT NOT NULL, " +
                ProviderContract.COLUMN_PHONE + " TEXT NOT NULL); ";
        db.execSQL(createTable);

        createTable = "CREATE TABLE " + OrderItemsContract.TABLE_NAME + " (" +
                OrderItemsContract.COLUMN_QTD + " INTEGER NOT NULL, " +
                OrderItemsContract.COLUMN_UNITVALUE + " REAL NOT NULL, " +
                OrderItemsContract.COLUMN_UNITMEASUREMENT + " INTEGER NOT NULL, " +
                OrderItemsContract.COLUMN_ORDER + " INTEGER NOT NULL, " +
                OrderItemsContract.COLUMN_PRODUCT + " INTEGER NOT NULL, " +
                "PRIMARY KEY (" + OrderItemsContract.COLUMN_ORDER + ", " + OrderItemsContract.COLUMN_PRODUCT + ")); ";
        db.execSQL(createTable);

        createTable = "CREATE TABLE " + UnidadeMedidaContract.TABLE_NAME + " (" +
                UnidadeMedidaContract.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                UnidadeMedidaContract.COLUMN_NAME + " TEXT NOT NULL, " +
                UnidadeMedidaContract.COLUMN_MULTIPLIER + " INTEGER NOT NULL, " +
                UnidadeMedidaContract.COLUMN_ORDER + " INTEGER NOT NULL); ";
        db.execSQL(createTable);

        createTable = "CREATE TABLE " + UserContract.TABLE_NAME + " (" +
                UserContract.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                UserContract.COLUMN_LOGIN + " TEXT NOT NULL, " +
                UserContract.COLUMN_PASSWORD + " TEXT NOT NULL, " +
                UserContract.COLUMN_AUTOLOGIN + " INTEGER NOT NULL); ";
        db.execSQL(createTable);

        createTable = "CREATE TABLE " + PermissionsContract.TABLE_NAME + " (" +
                PermissionsContract.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                PermissionsContract.COLUMN_PAGE + " TEXT NOT NULL, " +
                PermissionsContract.COLUMN_USER + " INTEGER NOT NULL); ";
        db.execSQL(createTable);

        createTable = "CREATE TABLE " + PagesContract.TABLE_NAME + " (" +
                PagesContract.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                PagesContract.COLUMN_NAME + " TEXT NOT NULL); ";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
