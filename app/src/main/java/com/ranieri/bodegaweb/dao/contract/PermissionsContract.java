package com.ranieri.bodegaweb.dao.contract;

/**
 * Created by ranie on 27 de abr.
 */

public interface PermissionsContract {
    String TABLE_NAME = "permissions";

    String COLUMN_ID = TABLE_NAME + "_id";
    String COLUMN_PERMITIDO = TABLE_NAME + "_permitido";
    String COLUMN_USER = TABLE_NAME + "_user";
    String COLUMN_NOME = TABLE_NAME + "_nome";

    String ID = TABLE_NAME + "." + COLUMN_ID;
    String USER = TABLE_NAME + "." + COLUMN_USER;
    String NOME = TABLE_NAME + "." + COLUMN_NOME;
    String PERMITIDO = TABLE_NAME + "." + COLUMN_PERMITIDO;
}
