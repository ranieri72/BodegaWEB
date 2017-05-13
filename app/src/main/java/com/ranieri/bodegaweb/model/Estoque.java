package com.ranieri.bodegaweb.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ranie on 13 de mai.
 */

public class Estoque {

    private List<Produtos> listaProdutos;

    public Estoque() {
        listaProdutos = new ArrayList<>();
    }

    public Estoque(List<Produtos> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    public List<Produtos> getListaProdutos() {
        return listaProdutos;
    }

    public void setListaProdutos(List<Produtos> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }
}
