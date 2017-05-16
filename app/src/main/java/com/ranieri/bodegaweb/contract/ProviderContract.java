package com.ranieri.bodegaweb.contract;

import android.provider.BaseColumns;

/**
 * Created by ranie on 27 de abr.
 */

public interface ProviderContract extends BaseColumns {
    String TABLE_NAME = "provider";

    String COMPANY = "company";
    String NAME = "name";
    String PHONE = "phone";
}
