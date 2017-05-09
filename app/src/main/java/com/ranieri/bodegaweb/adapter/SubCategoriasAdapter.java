package com.ranieri.bodegaweb.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ranieri.bodegaweb.model.SubCategorias;

import java.util.List;

/**
 * Created by ranie on 28 de abr.
 */

public class SubCategoriasAdapter extends ArrayAdapter<SubCategorias> {

    public SubCategoriasAdapter(@NonNull Context context, @NonNull List<SubCategorias> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //1)
        SubCategorias subCategoria = getItem(position);

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


        vh.txtNome.setText(subCategoria.getNome());

        //4)
        return convertView;
    }

    static class ViewHolder {
        TextView txtNome;
    }
}
