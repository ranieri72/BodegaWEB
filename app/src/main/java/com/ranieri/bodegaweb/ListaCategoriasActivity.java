package com.ranieri.bodegaweb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ranieri.bodegaweb.adapter.CategoriasAdapter;
import com.ranieri.bodegaweb.dao.CategoriasDAO;
import com.ranieri.bodegaweb.model.Categorias;
import com.ranieri.bodegaweb.model.Produtos;
import com.ranieri.bodegaweb.model.SubCategorias;

import java.util.List;

public class ListaCategoriasActivity extends AppCompatActivity {

    CategoriasDAO mDAO;
    Produtos produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);
        setTitle(getResources().getString(R.string.subcategoria));

        Log.v("Listar Categorias", "onCreate");

        produto = (Produtos) getIntent().getExtras().get("produto");

        mDAO = new CategoriasDAO(this);
        ListView mListView = (ListView)findViewById(R.id.listCategorias);
        List<Categorias> mCategorias = mDAO.listar(produto.getCategoria().getSubCategoria());

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
            it.putExtra("categoria", categoria);
            setResult(RESULT_OK, it);
            finish();

        }
    };
}
