package com.ranieri.bodegaweb.model;

import org.parceler.Parcel;

/**
 * Created by ranie on 4 de mai.
 */

@Parcel
public class SubCategorias {

    private long id;
    private String nome;

    public SubCategorias() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
