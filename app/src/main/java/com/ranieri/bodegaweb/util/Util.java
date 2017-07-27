package com.ranieri.bodegaweb.util;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Bernadete on 26/07/2017.
 */

public class Util {

    private static Locale ptBr = new Locale("pt", "BR");
    public static boolean isPhone;

    public static String moneyFormatter(double money) {
        return NumberFormat.getCurrencyInstance(ptBr).format(money);
    }
}