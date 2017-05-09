package com.ranieri.bodegaweb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ranie on 4 de mai.
 */

public class SubCategorias implements Parcelable {

    private long id;
    private String nome;

    public SubCategorias() {
    }

    protected SubCategorias(Parcel in) {
        id = in.readLong();
        nome = in.readString();
    }

    public static final Creator<SubCategorias> CREATOR = new Creator<SubCategorias>() {
        @Override
        public SubCategorias createFromParcel(Parcel in) {
            return new SubCategorias(in);
        }

        @Override
        public SubCategorias[] newArray(int size) {
            return new SubCategorias[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(nome);
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }
}
