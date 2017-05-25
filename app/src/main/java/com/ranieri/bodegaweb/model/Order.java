package com.ranieri.bodegaweb.model;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ranie on 15 de mai.
 */

@Parcel(Parcel.Serialization.BEAN)
public class Order {

    private long id;
    private Date dataPedido;
    private Date dataEntrega;
    private Date dataVencimento;
    private boolean aberto;
    private boolean notaXML;
    private float porcentagem;
    private float totalPedido;
    private Provider fornecedor;
    private List<OrderItems> listaItensPedido;

    public Order() {
        fornecedor = new Provider();
        listaItensPedido = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public boolean isAberto() {
        return aberto;
    }

    public void setAberto(boolean aberto) {
        this.aberto = aberto;
    }

    public boolean isNotaXML() {
        return notaXML;
    }

    public void setNotaXML(boolean notaXML) {
        this.notaXML = notaXML;
    }

    public float getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(float porcentagem) {
        this.porcentagem = porcentagem;
    }

    public float getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(float totalPedido) {
        this.totalPedido = totalPedido;
    }

    public Provider getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Provider fornecedor) {
        this.fornecedor = fornecedor;
    }

    public List<OrderItems> getListaItensPedido() {
        return listaItensPedido;
    }

    public void setListaItensPedido(List<OrderItems> listaItensPedido) {
        this.listaItensPedido = listaItensPedido;
    }
}
