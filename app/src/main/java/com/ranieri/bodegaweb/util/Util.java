package com.ranieri.bodegaweb.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Bernadete on 26/07/2017.
 */

public class Util {

    private static Locale ptBr = new Locale("pt", "BR");
    public static final SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy", ptBr);
    public static final SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm", ptBr);

    public static final String tabletViewPreference = "tabletView";
    public static boolean isTablet;

    public static String moneyFormatter(double money) {
        return NumberFormat.getCurrencyInstance(ptBr).format(money);
    }
}