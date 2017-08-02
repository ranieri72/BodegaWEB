package com.ranieri.bodegaweb.dao.contract;

/**
 * Created by ranie on 27 de abr.
 */

public interface OrderItemsContract {
    String TABLE_NAME = "orderitems";

    String COLUMN_QTD = TABLE_NAME + "_qtd";
    String COLUMN_UNITVALUE = TABLE_NAME + "_unitvalue";
    String COLUMN_UNITMEASUREMENT = TABLE_NAME + "_id_unitmeasurement";
    String COLUMN_ORDER = TABLE_NAME + "_id_order";
    String COLUMN_PRODUCT = TABLE_NAME + "_id_product";

    String QTD = TABLE_NAME + "." + COLUMN_QTD;
    String UNITVALUE = TABLE_NAME + "." + COLUMN_UNITVALUE;
    String UNITMEASUREMENT = TABLE_NAME + "." + COLUMN_UNITMEASUREMENT;
    String ORDER = TABLE_NAME + "." + COLUMN_ORDER;
    String PRODUCT = TABLE_NAME + "." + COLUMN_PRODUCT;
}
