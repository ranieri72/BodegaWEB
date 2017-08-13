package com.ranieri.bodegaweb.model;

import org.parceler.Parcel;

@Parcel(Parcel.Serialization.BEAN)
public class Produtos {

    private long id;
    private String nome;
    private int estoque;
    private int estoqueInicial;
    private int novoEstoque;
    private boolean alterado;
    private boolean apagado;
    private double precoSugerido;
    private double custoInicial;
    private double vendasPorDia;
    private int diasProxEntrega;
    private boolean ativado;
    private Categorias categoria;

    public Produtos() {
        categoria = new Categorias();
    }

    public Produtos(long id) { this.id = id; }

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

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public double getPrecoSugerido() {
        return precoSugerido;
    }

    public void setPrecoSugerido(double precoSugerido) {
        this.precoSugerido = precoSugerido;
    }

    public Categorias getCategoria() {
        return categoria;
    }

    public void setCategoria(Categorias categoria) {
        this.categoria = categoria;
    }

    public int getNovoEstoque() {
        return novoEstoque;
    }

    public void setNovoEstoque(int novoEstoque) {
        this.novoEstoque = novoEstoque;
    }

    public boolean isAlterado() {
        return alterado;
    }

    public void setAlterado(boolean alterado) {
        this.alterado = alterado;
    }

    public int getEstoqueInicial() {
        return estoqueInicial;
    }

    public void setEstoqueInicial(int estoqueInicial) {
        this.estoqueInicial = estoqueInicial;
    }

    public double getCustoInicial() {
        return custoInicial;
    }

    public void setCustoInicial(double custoInicial) {
        this.custoInicial = custoInicial;
    }

    public double getVendasPorDia() {
        return vendasPorDia;
    }

    public void setVendasPorDia(double vendasPorDia) {
        this.vendasPorDia = vendasPorDia;
    }

    public int getDiasProxEntrega() {
        return diasProxEntrega;
    }

    public void setDiasProxEntrega(int diasProxEntrega) {
        this.diasProxEntrega = diasProxEntrega;
    }

    public boolean isAtivado() {
        return ativado;
    }

    public void setAtivado(boolean ativado) {
        this.ativado = ativado;
    }

    public boolean isApagado() {
        return apagado;
    }

    public void setApagado(boolean apagado) {
        this.apagado = apagado;
    }
}
