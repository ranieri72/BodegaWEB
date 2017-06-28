package com.ranieri.bodegaweb.contract;

/**
 * Created by ranie on 27 de abr.
 */

public interface UnidadeMedidaContract {
    String TABLE_NAME = "unitmeasurement";

    String COLUMN_ID = TABLE_NAME + "_id";
    String COLUMN_NAME = TABLE_NAME + "_name";
    String COLUMN_ORDER = TABLE_NAME + "_order";
    String COLUMN_MULTIPLIER = TABLE_NAME + "_multiplier";

    String ID = TABLE_NAME + "." + COLUMN_ID;
    String NAME = TABLE_NAME + "." + COLUMN_NAME;
    String ORDER = TABLE_NAME + "." + COLUMN_ORDER;
    String MULTIPLIER = TABLE_NAME + "." + COLUMN_MULTIPLIER;
}
