package com.ranieri.bodegaweb.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Produtos implements Parcelable {

    private long id;
    private String nome;
    private int estoque;
    private int estoqueInicial;
    private int novoEstoque;
    private boolean alterado;
    private double precoSugerido;
    private double custoInicial;
    private double vendasPorDia;
    private int diasProxEntrega;
    private boolean ativado;
    private Categorias categoria;

    public Produtos (){
        categoria = new Categorias();
    }

    protected Produtos(Parcel in) {
        id = in.readLong();
        nome = in.readString();
        estoque = in.readInt();
        estoqueInicial = in.readInt();
        novoEstoque = in.readInt();
        alterado = in.readByte() != 0;
        precoSugerido = in.readDouble();
        custoInicial = in.readDouble();
        vendasPorDia = in.readDouble();
        diasProxEntrega = in.readInt();
        ativado = in.readByte() != 0;
        categoria = in.readParcelable(Categorias.class.getClassLoader());
    }

    public static final Creator<Produtos> CREATOR = new Creator<Produtos>() {
        @Override
        public Produtos createFromParcel(Parcel in) {
            return new Produtos(in);
        }

        @Override
        public Produtos[] newArray(int size) {
            return new Produtos[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(nome);
        dest.writeInt(estoque);
        dest.writeInt(estoqueInicial);
        dest.writeInt(novoEstoque);
        dest.writeByte((byte) (alterado ? 1 : 0));
        dest.writeDouble(precoSugerido);
        dest.writeDouble(custoInicial);
        dest.writeDouble(vendasPorDia);
        dest.writeInt(diasProxEntrega);
        dest.writeByte((byte) (ativado ? 1 : 0));
        dest.writeParcelable(categoria, flags);
    }

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

    public double getPrecoSugerido() { return precoSugerido; }

    public void setPrecoSugerido(double precoSugerido) { this.precoSugerido = precoSugerido; }

    public Categorias getCategoria() { return categoria; }

    public void setCategoria(Categorias categoria) { this.categoria = categoria; }

    public int getNovoEstoque() { return novoEstoque; }

    public void setNovoEstoque(int novoEstoque) { this.novoEstoque = novoEstoque; }

    public boolean isAlterado() { return alterado; }

    public void setAlterado(boolean alterado) { this.alterado = alterado; }

    public int getEstoqueInicial() { return estoqueInicial; }

    public void setEstoqueInicial(int estoqueInicial) { this.estoqueInicial = estoqueInicial; }

    public double getCustoInicial() { return custoInicial; }

    public void setCustoInicial(double custoInicial) { this.custoInicial = custoInicial; }

    public double getVendasPorDia() { return vendasPorDia; }

    public void setVendasPorDia(double vendasPorDia) { this.vendasPorDia = vendasPorDia; }

    public int getDiasProxEntrega() { return diasProxEntrega; }

    public void setDiasProxEntrega(int diasProxEntrega) { this.diasProxEntrega = diasProxEntrega; }

    public boolean isAtivado() { return ativado; }

    public void setAtivado(boolean ativado) { this.ativado = ativado; }
}
