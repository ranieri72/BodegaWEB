package com.ranieri.bodegaweb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ranie on 4 de mai.
 */

public class Categorias implements Parcelable {

    private long id;
    private String nome;
    private int ordem;
    private SubCategorias subCategoria;

    public Categorias() {
        subCategoria = new SubCategorias();
    }

    protected Categorias(Parcel in) {
        id = in.readLong();
        nome = in.readString();
        ordem = in.readInt();
        subCategoria = in.readParcelable(SubCategorias.class.getClassLoader());
    }

    public static final Creator<Categorias> CREATOR = new Creator<Categorias>() {
        @Override
        public Categorias createFromParcel(Parcel in) {
            return new Categorias(in);
        }

        @Override
        public Categorias[] newArray(int size) {
            return new Categorias[size];
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
        dest.writeInt(ordem);
        dest.writeParcelable(subCategoria, flags);
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public int getOrdem() { return ordem; }

    public void setOrdem(int ordem) { this.ordem = ordem; }

    public SubCategorias getSubCategoria() { return subCategoria; }

    public void setSubCategoria(SubCategorias subCategoria) { this.subCategoria = subCategoria; }
}
