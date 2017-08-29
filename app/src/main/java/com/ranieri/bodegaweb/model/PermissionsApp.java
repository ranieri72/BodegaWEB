package com.ranieri.bodegaweb.model;

import org.parceler.Parcel;

/**
 * Created by ranie on 2 de jul.
 */

@Parcel(Parcel.Serialization.BEAN)
public class PermissionsApp {

    private long id;
    private boolean verEstoque;
    private boolean verFornecedores;
    private boolean verPedidos;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public boolean isVerEstoque() { return verEstoque; }

    public void setVerEstoque(boolean verEstoque) { this.verEstoque = verEstoque; }

    public boolean isVerFornecedores() { return verFornecedores; }

    public void setVerFornecedores(boolean verFornecedores) { this.verFornecedores = verFornecedores; }

    public boolean isVerPedidos() { return verPedidos; }

    public void setVerPedidos(boolean verPedidos) { this.verPedidos = verPedidos; }
}
