package com.ranieri.bodegaweb.contract;

/**
 * Created by ranie on 27 de abr.
 */

public interface SubCategoriasContract {
    String TABLE_NAME = "subcategory";

    String COLUMN_ID = TABLE_NAME + "_id";
    String COLUMN_NAME = TABLE_NAME + "_name";

    String ID = TABLE_NAME + "." + COLUMN_ID;
    String NAME = TABLE_NAME + "." + COLUMN_NAME;
}
