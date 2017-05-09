package com.ranieri.bodegaweb.contract;

import android.provider.BaseColumns;

/**
 * Created by ranie on 27 de abr.
 */

public interface CategoriasContract extends BaseColumns {
    String TABLE_NAME = "categorias";

    String NOME = "nome";
    String ORDEM = "ordem";
    String SUBCATEGORIA = "idSubCategoria";
}
