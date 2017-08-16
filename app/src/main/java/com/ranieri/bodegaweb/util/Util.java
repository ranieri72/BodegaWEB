package com.ranieri.bodegaweb.util;

import android.content.Context;

import com.ranieri.bodegaweb.connection.AppSession;
import com.ranieri.bodegaweb.dao.PermissionsDAO;
import com.ranieri.bodegaweb.dao.UserDAO;
import com.ranieri.bodegaweb.model.Permissions;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Ranieri Aguiar on 26/07/2017.
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

    public static void updateUser(Context context, boolean autoLogin) {
        if (AppSession.user != null) {
            new UserDAO(context).update(AppSession.user, autoLogin);
            PermissionsDAO permissionsDAO = new PermissionsDAO(context);
            permissionsDAO.excluir(AppSession.user);
            permissionsDAO.inserir(AppSession.user.getListPermissions());
        }
    }

    public static void createUser(Context context, boolean autoLogin) {
        if (AppSession.user != null) {
            new UserDAO(context).insert(AppSession.user, autoLogin);
            new PermissionsDAO(context).inserir(AppSession.user.getListPermissions());
        }
    }

    public static void converterPermissoes() {
        if (AppSession.user != null && AppSession.user.getListPermissions() != null && AppSession.user.getListPermissions().size() > 0) {
            for (Permissions p : AppSession.user.getListPermissions()) {
                switch (p.getName()) {
                    case "preco":
                        AppSession.user.setPermiPreco(true);
                        break;
                }
            }
        }
    }
}