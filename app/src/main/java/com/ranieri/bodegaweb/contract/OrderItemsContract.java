package com.ranieri.bodegaweb.contract;

/**
 * Created by ranie on 27 de abr.
 */

public interface OrderItemsContract {
    String TABLE_NAME = "orderitems";

    String QTD = "orderitems_qtd";
    String UNITVALUE = "orderitems_unitvalue";
    String UNITMEASUREMENT = "orderitems_id_unitmeasurement";
    String ORDER = "orderitems_id_order";
    String PRODUCT = "orderitems_id_product";
}
