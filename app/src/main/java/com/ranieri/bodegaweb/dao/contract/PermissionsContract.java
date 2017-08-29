package com.ranieri.bodegaweb.dao.contract;

/**
 * Created by ranie on 27 de abr.
 */

public interface PermissionsContract {
    String TABLE_NAME = "permissions";

    String COLUMN_ID = TABLE_NAME + "_id";
    String COLUMN_VER_ESTOQUE = TABLE_NAME + "_ver_estoque";
    String COLUMN_VER_FORNECEDORES = TABLE_NAME + "_ver_fornecedores";
    String COLUMN_VER_PEDIDOS = TABLE_NAME + "_ver_pedidos";

    String ID = TABLE_NAME + "." + COLUMN_ID;
    String VER_ESTOQUE = TABLE_NAME + "." + COLUMN_VER_ESTOQUE;
    String VER_FORNECEDORES = TABLE_NAME + "." + COLUMN_VER_FORNECEDORES;
    String VER_PEDIDOS = TABLE_NAME + "." + COLUMN_VER_PEDIDOS;
}
