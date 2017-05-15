package com.ranieri.bodegaweb.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ranie on 13 de mai.
 */

public class ListJson {

    private List<Produtos> listaProdutos;
    private List<Categorias> listaCategorias;
    private List<SubCategorias> listaSubcategorias;

    public ListJson() {
        listaProdutos = new ArrayList<>();
        listaCategorias = new ArrayList<>();
        listaSubcategorias = new ArrayList<>();
    }

    public ListJson(List<Produtos> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

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
}
