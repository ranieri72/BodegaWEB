package com.ranieri.bodegaweb.contract;

/**
 * Created by ranie on 27 de abr.
 */

public interface ProdutosContract {
    String TABLE_NAME = "products";

    String TABLE_ID = TABLE_NAME + "_id";
    String TABLE_NOME = TABLE_NAME + "_name";
    String TABLE_ESTOQUE = TABLE_NAME + "_stock";
    String TABLE_NOVOESTOQUE = TABLE_NAME + "_newstock";
    String TABLE_ALTERADO = TABLE_NAME + "_altered";
    String TABLE_APAGADO = TABLE_NAME + "_deleted";
    String TABLE_PRECO = TABLE_NAME + "_price";
    String TABLE_CATEGORIA = TABLE_NAME + "_id_category";

    String ID = TABLE_NAME + "." + TABLE_NAME + "_id";
    String NOME = TABLE_NAME + "." + TABLE_NAME + "_name";
    String ESTOQUE = TABLE_NAME + "." + TABLE_NAME + "_stock";
    String NOVOESTOQUE = TABLE_NAME + "." + TABLE_NAME + "_newstock";
    String ALTERADO = TABLE_NAME + "." + TABLE_NAME + "_altered";
    String APAGADO = TABLE_NAME + "." + TABLE_NAME + "_deleted";
    String PRECO = TABLE_NAME + "." + TABLE_NAME + "_price";
    String CATEGORIA = TABLE_NAME + "." + TABLE_NAME + "_id_category";
}
