package com.ranieri.bodegaweb.contract;

/**
 * Created by ranie on 27 de abr.
 */

public interface ProdutosContract {
    String TABLE_NAME = "products";

    String ID = "products_id";
    String NOME = "products_name";
    String ESTOQUE = "products_stock";
    String NOVOESTOQUE = "products_newstock";
    String ALTERADO = "products_altered";
    String APAGADO = "products_deleted";
    String PRECO = "products_price";
    String CATEGORIA = "products_id_category";
}
