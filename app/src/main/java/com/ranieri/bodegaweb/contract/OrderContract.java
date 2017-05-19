package com.ranieri.bodegaweb.contract;

import android.provider.BaseColumns;

/**
 * Created by ranie on 27 de abr.
 */

public interface OrderContract extends BaseColumns {
    String TABLE_NAME = "ordered";

    String ORDERDATE = "orderdate";
    String TOTALORDER = "totalorder";
    String PROVIDER = "idprovider";
}
