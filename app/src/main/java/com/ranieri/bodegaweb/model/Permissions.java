package com.ranieri.bodegaweb.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by ranie on 2 de jul.
 */

@Parcel(Parcel.Serialization.BEAN)
public class Permissions {

    private long id;

    private boolean permitido;

    @SerializedName("usuario")
    private User user;

    @SerializedName("nome")
    private String name;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public boolean isPermitido() { return permitido; }

    public void setPermitido(boolean permitido) { this.permitido = permitido; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
