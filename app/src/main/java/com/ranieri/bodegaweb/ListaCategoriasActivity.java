package com.ranieri.bodegaweb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ranieri.bodegaweb.adapter.CategoriasAdapter;
import com.ranieri.bodegaweb.dao.CategoriasDAO;
import com.ranieri.bodegaweb.model.Categorias;
import com.ranieri.bodegaweb.model.Produtos;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListaCategoriasActivity extends AppCompatActivity {

    @BindView(R.id.listCategorias)
    ListView mListView;
    Produtos produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);
        ButterKnife.bind(this);
        setTitle(getResources().getString(R.string.subcategoria));

        Log.v("Listar Categorias", "onCreate");

        produto = Parcels.unwrap(getIntent().getParcelableExtra("produto"));

        CategoriasDAO mDAO = new CategoriasDAO(this);
        List<Categorias> mCategorias = mDAO.listar(produto.getCategoria().getSubCategoriaProd());

        CategoriasAdapter adapter = new CategoriasAdapter(this, mCategorias);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(tratadorDeCliques);
    }

    AdapterView.OnItemClickListener tratadorDeCliques = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Categorias categoria = (Categorias) parent.getAdapter().getItem(position);
            Intent it;

            Log.v("Listar Categorias", "OnItemClickListener - Produto recebido");

            it = new Intent();
            it.putExtra("categoria", Parcels.wrap(produto));
            setResult(RESULT_OK, it);
            finish();

        }
    };
}
