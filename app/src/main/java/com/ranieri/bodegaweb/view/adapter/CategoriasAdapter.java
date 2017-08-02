package com.ranieri.bodegaweb.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ranieri.bodegaweb.model.Categorias;

import java.util.List;

/**
 * Created by ranie on 28 de abr.
 */

public class CategoriasAdapter extends ArrayAdapter<Categorias> {

    public CategoriasAdapter(@NonNull Context context, @NonNull List<Categorias> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //1)
        Categorias categoria = getItem(position);

        //2)
        ViewHolder vh;
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, null);
            vh = new ViewHolder();

            //3)
            vh.txtNome = (TextView)convertView.findViewById(android.R.id.text1);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }


        vh.txtNome.setText(categoria.getNome());

        //4)
        return convertView;
    }

    static class ViewHolder {
        TextView txtNome;
    }
}
