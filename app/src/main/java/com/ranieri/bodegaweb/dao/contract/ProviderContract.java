package com.ranieri.bodegaweb.dao.contract;

/**
 * Created by ranie on 27 de abr.
 */

public interface ProviderContract {
    String TABLE_NAME = "provider";

    String COLUMN_ID = TABLE_NAME + "_id";
    String COLUMN_COMPANY = TABLE_NAME + "_company";
    String COLUMN_NAME = TABLE_NAME + "_name";
    String COLUMN_PHONE = TABLE_NAME + "_phone";

    String ID = TABLE_NAME + "." + COLUMN_ID;
    String COMPANY = TABLE_NAME + "." + COLUMN_COMPANY;
    String NAME = TABLE_NAME + "." + COLUMN_NAME;
    String PHONE = TABLE_NAME + "." + COLUMN_PHONE;
}
