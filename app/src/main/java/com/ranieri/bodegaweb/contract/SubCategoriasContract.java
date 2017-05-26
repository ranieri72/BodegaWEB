package com.ranieri.bodegaweb.contract;

/**
 * Created by ranie on 27 de abr.
 */

public interface SubCategoriasContract {
    String TABLE_NAME = "subcategory";

    String TABLE_ID = TABLE_NAME + "_id";
    String TABLE_NOME = TABLE_NAME + "_name";

    String ID = TABLE_NAME + "." + TABLE_NAME + "_id";
    String NOME = TABLE_NAME + "." + TABLE_NAME + "_name";
}
