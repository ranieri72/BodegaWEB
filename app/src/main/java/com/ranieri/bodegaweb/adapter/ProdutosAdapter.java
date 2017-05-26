package com.ranieri.bodegaweb.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ranieri.bodegaweb.R;
import com.ranieri.bodegaweb.model.Produtos;

import java.util.List;

/**
 * Created by ranie on 28 de abr.
 */

public class ProdutosAdapter extends ArrayAdapter<Produtos> {

    public ProdutosAdapter(@NonNull Context context, @NonNull List<Produtos> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //1)
        Produtos produto = getItem(position);

        //2)
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, null);
            vh = new ViewHolder();

            //3)
            vh.txtNome = (TextView) convertView.findViewById(android.R.id.text1);
            vh.txtEstoque = (TextView) convertView.findViewById(android.R.id.text2);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.txtNome.setText(String.valueOf(produto.getNome()));
        String estoque = String.valueOf(convertView.getResources().getString(R.string.estoque) + " " + produto.getEstoque());
        String preco = String.valueOf(convertView.getResources().getString(R.string.preco) + " " + produto.getPrecoSugerido());
        vh.txtEstoque.setText(estoque + " " + preco + " " + produto.getCategoria().getNome());

        //4)
        return convertView;
    }

    static class ViewHolder {
        TextView txtNome;
        TextView txtEstoque;
    }
}
