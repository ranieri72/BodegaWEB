package com.ranieri.bodegaweb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ranie on 16 de mai.
 */

public class UnidadeMedida implements Parcelable{

    private long id;
    private String nome;
    private String nomeXML;
    private int ordem;
    private int multiplicador;

    public UnidadeMedida(){}

    protected UnidadeMedida(Parcel in) {
        id = in.readLong();
        nome = in.readString();
        nomeXML = in.readString();
        ordem = in.readInt();
        multiplicador = in.readInt();
    }

    public static final Creator<UnidadeMedida> CREATOR = new Creator<UnidadeMedida>() {
        @Override
        public UnidadeMedida createFromParcel(Parcel in) {
            return new UnidadeMedida(in);
        }

        @Override
        public UnidadeMedida[] newArray(int size) {
            return new UnidadeMedida[size];
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
        dest.writeString(nomeXML);
        dest.writeInt(ordem);
        dest.writeInt(multiplicador);
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
