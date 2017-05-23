package com.ranieri.bodegaweb.model;

import org.parceler.Parcel;

/**
 * Created by ranie on 4 de mai.
 */

@Parcel
public class Categorias {

    private long id;
    private String nome;
    private int ordem;
    private SubCategorias subCategoria;

    public Categorias() {
        subCategoria = new SubCategorias();
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

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public SubCategorias getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(SubCategorias subCategoria) {
        this.subCategoria = subCategoria;
    }
}
