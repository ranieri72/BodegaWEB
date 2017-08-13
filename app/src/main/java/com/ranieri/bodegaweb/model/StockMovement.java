package com.ranieri.bodegaweb.model;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by Bernadete on 31/07/2017.
 */

@Parcel(Parcel.Serialization.BEAN)
public class StockMovement {

    private int qtd;
    private Date hora;
    private Date data;
    private boolean perda;
    private Produtos produto;
    private UnidadeMedida unidMedida;

    public StockMovement() {
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public boolean isPerda() {
        return perda;
    }

    public void setPerda(boolean perda) {
        this.perda = perda;
    }

    public Produtos getProduto() {
        return produto;
    }

    public void setProduto(Produtos produto) {
        this.produto = produto;
    }

    public UnidadeMedida getUnidMedida() {
        return unidMedida;
    }

    public void setUnidMedida(UnidadeMedida unidMedida) {
        this.unidMedida = unidMedida;
    }
}
