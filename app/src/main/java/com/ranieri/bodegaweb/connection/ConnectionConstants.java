package com.ranieri.bodegaweb.connection;

/**
 * Created by ranie on 19 de jul.
 */

public class ConnectionConstants {

    //public static final String ipv4 = "http://192.168.0.2:8080/bodegaWEB/rest/";
    public static final String ipv4 = "http://192.168.15.13:8080/bodegaWEB/rest/";
    public static final String urlTest = ipv4 + "test";

    // Login
    public static final String urlLogin = ipv4 + "login/";
    public static final String urlCreateUser = urlLogin + "createuser";
    public static final String urlUpdateUser = urlLogin + "updateuser";
    public static final String urlDeleteUser = urlLogin + "deleteuser";

    // Order
    public static final String urlOrder = ipv4 + "order/";
    public static final String urlProvider = urlOrder + "provider";
    public static final String urlOrderItems = urlOrder + "orderitems";
    public static final String urlUnitMeasurement = urlOrder + "unitmeasurement";

    // Products
    public static final String urlProducts = ipv4 + "products/";
    public static final String urlPostProducts = urlProducts + "post";
    public static final String urlStockMovement = urlProducts + "postStockMovement";
    public static final String urlCategory = urlProducts + "categorias";
    public static final String urlSubCategory = urlProducts + "subcategorias";
}
