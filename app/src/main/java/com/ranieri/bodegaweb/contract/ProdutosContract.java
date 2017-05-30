package com.ranieri.bodegaweb.contract;

/**
 * Created by ranie on 27 de abr.
 */

public interface ProdutosContract {
    String TABLE_NAME = "products";

    String COLUMN_ID = TABLE_NAME + "_id";
    String COLUMN_NAME = TABLE_NAME + "_name";
    String COLUMN_STOCK = TABLE_NAME + "_stock";
    String COLUMN_NEWSTOCK = TABLE_NAME + "_newstock";
    String COLUMN_ALTERED = TABLE_NAME + "_altered";
    String COLUMN_DELETED = TABLE_NAME + "_deleted";
    String COLUMN_PRICE = TABLE_NAME + "_price";
    String COLUMN_CATEGORY = TABLE_NAME + "_id_category";

    String ID = TABLE_NAME + "." + COLUMN_ID;
    String NAME = TABLE_NAME + "." + COLUMN_NAME;
    String STOCK = TABLE_NAME + "." + COLUMN_STOCK;
    String NEWSTOCK = TABLE_NAME + "." + COLUMN_NEWSTOCK;
    String ALTERED = TABLE_NAME + "." + COLUMN_ALTERED;
    String DELETED = TABLE_NAME + "." + COLUMN_DELETED;
    String PRICE = TABLE_NAME + "." + COLUMN_PRICE;
    String CATEGORY = TABLE_NAME + "." + COLUMN_CATEGORY;
}
