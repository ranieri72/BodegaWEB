package com.ranieri.bodegaweb.view.adapter;

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

import static com.ranieri.bodegaweb.util.Util.formatoData;

/**
 * Created by ranie on 28 de abr.
 */

public class OrderItemsAdapter extends ArrayAdapter<OrderItems> {

    private int cod;

    public OrderItemsAdapter(@NonNull Context context, @NonNull List<OrderItems> objects, int cod) {
        super(context, 0, objects);
        this.cod = cod;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //1)
        OrderItems orderItems = getItem(position);
        double price;
        int multi;
        double total;

        switch (cod) {
            case 1:
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
                price = orderItems.getPrecoUnit();
                String unidMedida = orderItems.getUnidMedida().getNome();
                total = qtd * price;
                vh.txtQtd.setText("R$" + price + " - QTD:" + qtd + " " + unidMedida + " Total R$" + total);
                break;
            case 2:
                //2)
                ViewHolder2 vh2;
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, null);
                    vh2 = new ViewHolder2();

                    //3)
                    vh2.txtPrice = (TextView) convertView.findViewById(android.R.id.text1);
                    vh2.txtDate = (TextView) convertView.findViewById(android.R.id.text2);

                    convertView.setTag(vh2);
                } else {
                    vh2 = (ViewHolder2) convertView.getTag();
                }

                String data = formatoData.format(orderItems.getChaveComposta().getOrder().getDataPedido());
                price = orderItems.getPrecoUnit();
                multi = orderItems.getUnidMedida().getMultiplicador();
                total = price / multi;

                vh2.txtPrice.setText("R$ " + total);
                vh2.txtDate.setText(data);
                break;
            default:
                break;
        }
        //4)
        return convertView;
    }

    private static class ViewHolder {
        TextView txtProduct;
        TextView txtQtd;
    }

    private static class ViewHolder2 {
        TextView txtPrice;
        TextView txtDate;
    }
}
