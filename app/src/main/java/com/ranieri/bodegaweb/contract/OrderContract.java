package com.ranieri.bodegaweb.contract;

import android.provider.BaseColumns;

/**
 * Created by ranie on 27 de abr.
 */

public interface OrderContract extends BaseColumns {
    String TABLE_NAME = "order";

    String ORDERDATE = "orderdate";
    String TOTALORDER = "totalorder";
    String PROVIDER = "idprovider";
}
