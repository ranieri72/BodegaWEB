package com.ranieri.bodegaweb.model;

import java.util.List;

/**
 * Created by ranie on 13 de mai.
 */

public class ListJson {

    private List<Produtos> listaProdutos;
    private List<Categorias> listaCategorias;
    private List<SubCategorias> listaSubcategorias;
    private List<Order> listaOrder;
    private List<Provider> listaProvider;
    private List<OrderItems> listaOrderItems;
    private List<UnidadeMedida> listaUnidadeMedida;

    public List<Produtos> getListaProdutos() {
        return listaProdutos;
    }

    public void setListaProdutos(List<Produtos> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    public List<Categorias> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<Categorias> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public List<SubCategorias> getListaSubcategorias() {
        return listaSubcategorias;
    }

    public void setListaSubcategorias(List<SubCategorias> listaSubcategorias) {
        this.listaSubcategorias = listaSubcategorias;
    }

    public List<Order> getListaOrder() {
        return listaOrder;
    }

    public void setListaOrder(List<Order> listaOrder) {
        this.listaOrder = listaOrder;
    }

    public List<Provider> getListaProvider() {
        return listaProvider;
    }

    public void setListaProvider(List<Provider> listaProvider) {
        this.listaProvider = listaProvider;
    }

    public List<OrderItems> getListaOrderItems() {
        return listaOrderItems;
    }

    public void setListaOrderItems(List<OrderItems> listaOrderItems) {
        this.listaOrderItems = listaOrderItems;
    }

    public List<UnidadeMedida> getListaUnidadeMedida() {
        return listaUnidadeMedida;
    }

    public void setListaUnidadeMedida(List<UnidadeMedida> listaUnidadeMedida) {
        this.listaUnidadeMedida = listaUnidadeMedida;
    }
}
