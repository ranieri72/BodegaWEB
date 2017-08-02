package com.ranieri.bodegaweb.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ranieri.bodegaweb.R;
import com.ranieri.bodegaweb.connection.AppSession;
import com.ranieri.bodegaweb.model.Produtos;
import com.ranieri.bodegaweb.util.Util;

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
        String inf = "";

        if (produto.isAlterado()) {
            //convertView.setBackgroundColor(Color.RED);
            vh.txtEstoque.setTextColor(Color.RED);
            inf += String.valueOf(convertView.getResources().getString(R.string.novoestoque) + " " + produto.getNovoEstoque() + " ");
        } else {
            if (AppSession.user.getId() != 3) {
                //convertView.setBackgroundColor(Color.WHITE);
                vh.txtEstoque.setTextColor(Color.BLACK);
                inf += String.valueOf(convertView.getResources().getString(R.string.estoque) + " " + produto.getEstoque() + " ");
            }
        }
        inf += Util.moneyFormatter(produto.getPrecoSugerido());
        if (Util.isPhone) {
            inf += " " + produto.getCategoria().getNome();
        }

        vh.txtEstoque.setText(inf);

        //4)
        return convertView;
    }

    static class ViewHolder {
        TextView txtNome;
        TextView txtEstoque;
    }
}
