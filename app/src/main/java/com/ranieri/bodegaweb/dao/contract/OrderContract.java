package com.ranieri.bodegaweb.dao.contract;

/**
 * Created by ranie on 27 de abr.
 */

public interface OrderContract {
    String TABLE_NAME = "ordered";

    String COLUMN_ID = TABLE_NAME + "_id";
    String COLUMN_ORDERDATE = TABLE_NAME + "_orderdate";
    String COLUMN_TOTALORDER = TABLE_NAME + "_totalorder";
    String COLUMN_PROVIDER = TABLE_NAME + "_id_provider";

    String ID = TABLE_NAME + "." + COLUMN_ID;
    String ORDERDATE = TABLE_NAME + "." + COLUMN_ORDERDATE;
    String TOTALORDER = TABLE_NAME + "." + COLUMN_TOTALORDER;
    String PROVIDER = TABLE_NAME + "." + COLUMN_PROVIDER;
}
