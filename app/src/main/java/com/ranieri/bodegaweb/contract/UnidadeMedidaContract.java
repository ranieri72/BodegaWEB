package com.ranieri.bodegaweb.contract;

import android.provider.BaseColumns;

/**
 * Created by ranie on 27 de abr.
 */

public interface UnidadeMedidaContract extends BaseColumns {
    String TABLE_NAME = "order";

    String NOME = "nome";
    String ORDEM = "ordem";
    String MULTIPLICADOR = "multiplicador";
}
