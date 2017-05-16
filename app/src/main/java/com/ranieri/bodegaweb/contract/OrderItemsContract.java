package com.ranieri.bodegaweb.contract;

import android.provider.BaseColumns;

/**
 * Created by ranie on 27 de abr.
 */

public interface OrderItemsContract extends BaseColumns {
    String TABLE_NAME = "orderitems";

    String QTD = "qtd";
    String PRECOUNIT = "precounit";
    String UNIDADEMEDIDA = "idunidademedida";
    String ORDER = "idorder";
    String PRODUTO = "idproduto";
}
