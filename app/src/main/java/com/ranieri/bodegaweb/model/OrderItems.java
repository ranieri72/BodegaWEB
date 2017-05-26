package com.ranieri.bodegaweb.model;

import org.parceler.Parcel;

/**
 * Created by ranie on 15 de mai.
 */

@Parcel(Parcel.Serialization.BEAN)
public class OrderItems {

    private OrderItemsPK chaveComposta;
    private double qtd;
    private double precoUnit;
    private UnidadeMedida unidMedida;

    public OrderItems() {
        chaveComposta = new OrderItemsPK();
        setUnidMedida(new UnidadeMedida());
    }

    public OrderItemsPK getChaveComposta() {
        return chaveComposta;
    }

    public void setChaveComposta(OrderItemsPK chaveComposta) {
        this.chaveComposta = chaveComposta;
    }

    public double getQtd() {
        return qtd;
    }

    public void setQtd(double qtd) {
        this.qtd = qtd;
    }

    public double getPrecoUnit() {
        return precoUnit;
    }

    public void setPrecoUnit(double precoUnit) {
        this.precoUnit = precoUnit;
    }

    public UnidadeMedida getUnidMedida() {
        return unidMedida;
    }

    public void setUnidMedida(UnidadeMedida unidMedida) {
        this.unidMedida = unidMedida;
    }
}
