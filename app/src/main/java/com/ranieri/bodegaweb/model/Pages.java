package com.ranieri.bodegaweb.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by ranie on 2 de jul.
 */

@Parcel(Parcel.Serialization.BEAN)
public class Pages {

    private long id;

    @SerializedName("nome")
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}