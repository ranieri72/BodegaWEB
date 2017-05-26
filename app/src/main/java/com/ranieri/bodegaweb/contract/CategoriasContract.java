package com.ranieri.bodegaweb.contract;

/**
 * Created by ranie on 27 de abr.
 */

public interface CategoriasContract {
    String TABLE_NAME = "category";

    String TABLE_ID = TABLE_NAME + "_id";
    String TABLE_NOME = TABLE_NAME + "_name";
    String TABLE_ORDEM = TABLE_NAME + "_order";
    String TABLE_SUBCATEGORIA = TABLE_NAME + "_id_subcategory";

    String ID = TABLE_NAME + "." + TABLE_NAME + "_id";
    String NOME = TABLE_NAME + "." + TABLE_NAME + "_name";
    String ORDEM = TABLE_NAME + "." + TABLE_NAME + "_order";
    String SUBCATEGORIA = TABLE_NAME + "." + TABLE_NAME + "_id_subcategory";
}
