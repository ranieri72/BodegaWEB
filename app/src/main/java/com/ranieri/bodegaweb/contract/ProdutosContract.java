package com.ranieri.bodegaweb.contract;

import android.provider.BaseColumns;

/**
 * Created by ranie on 27 de abr.
 */

public interface ProdutosContract extends BaseColumns {
    String TABLE_NAME = "produtos";

    String NOME = "nome";
    String ESTOQUE = "estoque";
    String NOVOESTOQUE = "novoestoque";
    String ALTERADO = "alterado";
    String PRECO = "preco";
    String CATEGORIA = "idCategoria";
}
