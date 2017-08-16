package com.ranieri.bodegaweb.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

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
    public static final int updateOk = 6;
    public static final int deleteOk = 7;

    // Códigos para AsyncTask
    public static final int loginAccount = 1;
    public static final int createAccount = 2;
    public static final int updateAccount = 3;
    public static final int deleteAccount = 4;

    private long id;
    private String login;

    @SerializedName("senha")
    private String password;

    @SerializedName("listaPermissoesApp")
    private List<Permissions> listPermissions;

    private boolean autoLogin;
    private int statusCode;
    private boolean permiPreco = false;

    public User() { }

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

    public List<Permissions> getListPermissions() {
        return listPermissions;
    }

    public void setListPermissions(List<Permissions> listPermissions) {
        this.listPermissions = listPermissions;
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

    public boolean isPermiPreco() { return permiPreco; }

    public void setPermiPreco(boolean permiPreco) { this.permiPreco = permiPreco; }
}
