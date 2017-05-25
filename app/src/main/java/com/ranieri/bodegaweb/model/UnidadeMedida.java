package com.ranieri.bodegaweb.model;

import org.parceler.Parcel;

/**
 * Created by ranie on 16 de mai.
 */

@Parcel(Parcel.Serialization.BEAN)
public class UnidadeMedida {

    private long id;
    private String nome;
    private String nomeXML;
    private int ordem;
    private int multiplicador;

    public UnidadeMedida() {
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

    public String getNomeXML() {
        return nomeXML;
    }

    public void setNomeXML(String nomeXML) {
        this.nomeXML = nomeXML;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public int getMultiplicador() {
        return multiplicador;
    }

    public void setMultiplicador(int multiplicador) {
        this.multiplicador = multiplicador;
    }
}
