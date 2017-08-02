package com.ranieri.bodegaweb.dao.contract;

/**
 * Created by ranie on 27 de abr.
 */

public interface UserContract {
    String TABLE_NAME = "user";

    String COLUMN_ID = TABLE_NAME + "_id";
    String COLUMN_LOGIN = TABLE_NAME + "_login";
    String COLUMN_PASSWORD = TABLE_NAME + "_password";
    String COLUMN_AUTOLOGIN = TABLE_NAME + "_autologin";

    String ID = TABLE_NAME + "." + COLUMN_ID;
    String LOGIN = TABLE_NAME + "." + COLUMN_LOGIN;
    String PASSWORD = TABLE_NAME + "." + COLUMN_PASSWORD;
    String AUTOLOGIN = TABLE_NAME + "." + COLUMN_AUTOLOGIN;
}
