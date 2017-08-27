package com.ranieri.bodegaweb.dao.contract;

/**
 * Created by ranie on 27 de abr.
 */

public interface PermissionsContract {
    String TABLE_NAME = "permissions";

    String COLUMN_ID = TABLE_NAME + "_id";
    String COLUMN_VER_ESTOQUE = TABLE_NAME + "_ver_estoque";

    String ID = TABLE_NAME + "." + COLUMN_ID;
    String VER_ESTOQUE = TABLE_NAME + "." + COLUMN_VER_ESTOQUE;
}
