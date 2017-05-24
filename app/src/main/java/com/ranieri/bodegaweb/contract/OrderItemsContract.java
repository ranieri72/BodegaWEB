package com.ranieri.bodegaweb.contract;

/**
 * Created by ranie on 27 de abr.
 */

public interface OrderItemsContract {
    String TABLE_NAME = "orderitems";

    String QTD = "orderitems_qtd";
    String PRECOUNIT = "orderitems_unitvalue";
    String UNIDADEMEDIDA = "orderitems_id_unitmeasurement";
    String ORDER = "orderitems_id_order";
    String PRODUTO = "orderitems_id_produto";
}
