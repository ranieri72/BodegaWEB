package com.ranieri.bodegaweb.dao.contract;

/**
 * Created by ranie on 27 de abr.
 */

public interface StockMovementContract {
    String TABLE_NAME = "stockmovement";

    String COLUMN_QTD = TABLE_NAME + "_qtd";
    String COLUMN_HORA = TABLE_NAME + "_hora";
    String COLUMN_DATA = TABLE_NAME + "_data";
    String COLUMN_PERDA = TABLE_NAME + "_perda";
    String COLUMN_PRODUCT = TABLE_NAME + "_id_produto";
    String COLUMN_UNITMEASUREMENT = TABLE_NAME + "_id_unidMedida";
    String COLUMN_USER = TABLE_NAME + "_id_user";

    String QTD = TABLE_NAME + "." + COLUMN_QTD;
    String HORA = TABLE_NAME + "." + COLUMN_HORA;
    String DATA = TABLE_NAME + "." + COLUMN_DATA;
    String PERDA = TABLE_NAME + "." + COLUMN_PERDA;
    String PRODUCT = TABLE_NAME + "." + COLUMN_PRODUCT;
    String UNITMEASUREMENT = TABLE_NAME + "." + COLUMN_UNITMEASUREMENT;
    String USER = TABLE_NAME + "." + COLUMN_USER;
}
