package com.ranieri.bodegaweb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ranie on 15 de mai.
 */

public class Provider implements Parcelable {

    private long id;
    private String empresa;
    private String empresaXML;
    private String nome;
    private String fone;
    private boolean ativado;

    public Provider(){}

    protected Provider(Parcel in) {
        id = in.readLong();
        empresa = in.readString();
        empresaXML = in.readString();
        nome = in.readString();
        fone = in.readString();
        ativado = in.readByte() != 0;
    }

    public static final Creator<Provider> CREATOR = new Creator<Provider>() {
        @Override
        public Provider createFromParcel(Parcel in) {
            return new Provider(in);
        }

        @Override
        public Provider[] newArray(int size) {
            return new Provider[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(empresa);
        dest.writeString(empresaXML);
        dest.writeString(nome);
        dest.writeString(fone);
        dest.writeByte((byte) (ativado ? 1 : 0));
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
