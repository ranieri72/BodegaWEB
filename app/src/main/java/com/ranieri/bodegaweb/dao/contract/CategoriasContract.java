package com.ranieri.bodegaweb.dao.contract;

/**
 * Created by ranie on 27 de abr.
 */

public interface CategoriasContract {
    String TABLE_NAME = "category";

    String COLUMN_ID = TABLE_NAME + "_id";
    String COLUMN_NAME = TABLE_NAME + "_name";
    String COLUMN_ORDER = TABLE_NAME + "_order";
    String COLUMN_SUBCATEGORY = TABLE_NAME + "_id_subcategory";

    String ID = TABLE_NAME + "." + COLUMN_ID;
    String NAME = TABLE_NAME + "." + COLUMN_NAME;
    String ORDER = TABLE_NAME + "." + COLUMN_ORDER;
    String SUBCATEGORY = TABLE_NAME + "." + COLUMN_SUBCATEGORY;
}
