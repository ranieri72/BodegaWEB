package com.ranieri.bodegaweb.model;

import org.parceler.Parcel;

/**
 * Created by ranie on 15 de mai.
 */

@Parcel(Parcel.Serialization.BEAN)
public class Provider {

    private long id;
    private String empresa;
    private String empresaXML;
    private String nome;
    private String fone;
    private boolean ativado;

    public Provider() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getEmpresaXML() {
        return empresaXML;
    }

    public void setEmpresaXML(String empresaXML) {
        this.empresaXML = empresaXML;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public boolean isAtivado() {
        return ativado;
    }

    public void setAtivado(boolean ativado) {
        this.ativado = ativado;
    }
}
