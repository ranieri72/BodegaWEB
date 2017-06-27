package com.ranieri.bodegaweb.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ranieri.bodegaweb.model.OrderItems;

import java.util.List;

/**
 * Created by ranie on 28 de abr.
 */

public class OrderItemsAdapter extends ArrayAdapter<OrderItems> {

    public OrderItemsAdapter(@NonNull Context context, @NonNull List<OrderItems> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //1)
        OrderItems orderItems = getItem(position);

        //2)
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, null);
            vh = new ViewHolder();

            //3)
            vh.txtProduct = (TextView) convertView.findViewById(android.R.id.text1);
            vh.txtQtd = (TextView) convertView.findViewById(android.R.id.text2);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.txtProduct.setText(orderItems.getChaveComposta().getProdutos().getNome());
        double qtd = orderItems.getQtd();
        double price = orderItems.getPrecoUnit();
        String unidMedida = orderItems.getUnidMedida().getNome();
        int multi = orderItems.getUnidMedida().getMultiplicador();
        double total = (qtd * multi) * price;
        vh.txtQtd.setText("R$" + price + " " + qtd + " " + unidMedida + " Total R$" + total);

        //4)
        return convertView;
    }

    private static class ViewHolder {
        TextView txtProduct;
        TextView txtQtd;
    }
}
