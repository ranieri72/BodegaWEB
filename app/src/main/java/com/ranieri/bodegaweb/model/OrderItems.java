package com.ranieri.bodegaweb.model;

import org.parceler.Parcel;

/**
 * Created by ranie on 15 de mai.
 */

@Parcel
public class OrderItems {

    private OrderItemsPK chaveComposta;
    private double qtd;
    private double precoUnit;
    private UnidadeMedida unidadeMedida;

    public OrderItems() {
        chaveComposta = new OrderItemsPK();
        unidadeMedida = new UnidadeMedida();
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

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }
}
