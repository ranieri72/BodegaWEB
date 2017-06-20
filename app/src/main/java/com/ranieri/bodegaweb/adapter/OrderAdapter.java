package com.ranieri.bodegaweb.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ranieri.bodegaweb.model.Order;

import java.util.List;

/**
 * Created by ranie on 28 de abr.
 */

public class OrderAdapter extends ArrayAdapter<Order> {

    public OrderAdapter(@NonNull Context context, @NonNull List<Order> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //1)
        Order order = getItem(position);

        //2)
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, null);
            vh = new ViewHolder();

            //3)
            vh.txtEmpresa = (TextView) convertView.findViewById(android.R.id.text1);
            vh.txtData = (TextView) convertView.findViewById(android.R.id.text2);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.txtEmpresa.setText(order.getFornecedor().getEmpresa());
        String data = String.valueOf(order.getDataEntrega());
        float total = order.getTotalPedido();
        vh.txtData.setText(data + " R$ " + total);

        //4)
        return convertView;
    }

    private static class ViewHolder {
        TextView txtEmpresa;
        TextView txtData;
    }
}
