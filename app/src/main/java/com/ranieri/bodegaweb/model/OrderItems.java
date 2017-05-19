package com.ranieri.bodegaweb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ranie on 15 de mai.
 */

public class OrderItems implements Parcelable {

    private OrderItemsPK chaveComposta;
    private double qtd;
    private double precoUnit;
    private UnidadeMedida unidadeMedida;

    public OrderItems(){
        chaveComposta = new OrderItemsPK();
        unidadeMedida = new UnidadeMedida();
    }

    protected OrderItems(Parcel in) {
        qtd = in.readDouble();
        precoUnit = in.readDouble();
        unidadeMedida = in.readParcelable(UnidadeMedida.class.getClassLoader());
    }

    public static final Creator<OrderItems> CREATOR = new Creator<OrderItems>() {
        @Override
        public OrderItems createFromParcel(Parcel in) {
            return new OrderItems(in);
        }

        @Override
        public OrderItems[] newArray(int size) {
            return new OrderItems[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(qtd);
        dest.writeDouble(precoUnit);
        dest.writeParcelable(unidadeMedida, flags);
    }

    public OrderItemsPK getChaveComposta() { return chaveComposta; }

    public void setChaveComposta(OrderItemsPK chaveComposta) { this.chaveComposta = chaveComposta; }

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
