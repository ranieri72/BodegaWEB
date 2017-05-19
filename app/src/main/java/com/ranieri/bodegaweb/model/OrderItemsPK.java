package com.ranieri.bodegaweb.model;

/**
 * Created by ranie on 19 de mai.
 */

public class OrderItemsPK {

    private Order order;
    private Produtos produtos;

    public OrderItemsPK(){
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
