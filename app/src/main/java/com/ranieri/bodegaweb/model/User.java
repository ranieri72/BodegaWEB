package com.ranieri.bodegaweb.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by ranie on 1 de jul.
 */

@Parcel(Parcel.Serialization.BEAN)
public class User {

    // Status code retornado do servidor
    public static final int loginOk = 1;
    public static final int userAndPasswordDoesntMatch = 2;
    public static final int userDoesntExist = 3;
    public static final int userAlreadyExists = 4;
    public static final int serverError = 5;
    public static final int serverNotFound = 6;
    public static final int updateOk = 7;
    public static final int deleteOk = 8;

    // Códigos para AsyncTask
    public static final int loginAccount = 1;
    public static final int createAccount = 2;
    public static final int updateAccount = 3;
    public static final int deleteAccount = 4;

    private long id;
    private String login;

    @SerializedName("senha")
    private String password;

    @SerializedName("permissoesApp")
    private PermissionsApp permissionsApp;

    private boolean autoLogin;
    private int statusCode;

    public User() {
    }

    public User(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAutoLogin() {
        return autoLogin;
    }

    public void setAutoLogin(boolean autoLogin) {
        this.autoLogin = autoLogin;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public PermissionsApp getPermissionsApp() {
        return permissionsApp;
    }

    public void setPermissionsApp(PermissionsApp permissionsApp) {
        this.permissionsApp = permissionsApp;
    }
}
