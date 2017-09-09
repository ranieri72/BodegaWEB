package com.ranieri.bodegaweb.connection;

/**
 * Created by ranie on 19 de jul.
 */

public class ConnectionConstants {

    //private static String ipv4 = "192.168.0.4:8080";
    //private static String ipv4 = "10.0.1.36:8080";
    private static String ipv4 = "192.168.15.10:8080";

    private static final String http = "http://";
    private static final String bodegaWEB = "/bodegaWEB/rest/";
    private static final String urlREST = http + ipv4 + bodegaWEB;

    // Test
    public static final String urlTest = urlREST + "test";

    // Login
    public static final String urlLogin = urlREST + "login/";
    public static final String urlCreateUser = urlLogin + "createuser";
    public static final String urlUpdateUser = urlLogin + "updateuser";
    public static final String urlDeleteUser = urlLogin + "deleteuser";

    // Order
    public static final String urlOrder = urlREST + "order/";
    public static final String urlProvider = urlOrder + "provider";
    public static final String urlOrderItems = urlOrder + "orderitems";
    public static final String urlUnitMeasurement = urlOrder + "unitmeasurement";

    // Products
    public static final String urlProducts = urlREST + "products/";
    public static final String urlPostProducts = urlProducts + "post";
    public static final String urlStockMovement = urlProducts + "postStockMovement";
    public static final String urlCategory = urlProducts + "categorias";
    public static final String urlSubCategory = urlProducts + "subcategorias";
}
