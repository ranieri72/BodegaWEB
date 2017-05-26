package com.ranieri.bodegaweb.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by ranie on 19 de mai.
 */

@Parcel(Parcel.Serialization.BEAN)
public class OrderItemsPK {

    @SerializedName("pedido")
    private Order order;

    @SerializedName("produto")
    private Produtos produtos;

    public OrderItemsPK() {
        order = new Order();
        produtos = new Produtos();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Produtos getProdutos() {
        return produtos;
    }

    public void setProdutos(Produtos produtos) {
        this.produtos = produtos;
    }
}
