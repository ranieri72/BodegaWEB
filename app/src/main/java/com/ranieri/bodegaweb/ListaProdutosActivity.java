package com.ranieri.bodegaweb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ranieri.bodegaweb.adapter.ProdutosAdapter;
import com.ranieri.bodegaweb.dao.ProdutosDAO;
import com.ranieri.bodegaweb.model.Produtos;
import com.ranieri.bodegaweb.model.SubCategorias;

import java.util.List;

public class ListaProdutosActivity extends AppCompatActivity {

    ListView mListView;
    ProdutosAdapter mAdapter;
    List<Produtos> mLista;
    ProdutosDAO mDAO;
    SubCategorias subCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        Log.v("Listar Produtos", "onCreate");

        subCategoria = (SubCategorias) getIntent().getExtras().get("subCategoria");
        setTitle(subCategoria.getNome());

        mDAO = new ProdutosDAO(this);
        mListView = (ListView)findViewById(R.id.listProdutos);

        mLista = mDAO.listar(subCategoria);
        mAdapter = new ProdutosAdapter(this, mLista);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(tratadorDeCliques);
    }

    AdapterView.OnItemClickListener tratadorDeCliques = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Produtos produto = (Produtos) parent.getAdapter().getItem(position);

            Log.v("Listar Produtos", "OnItemClickListener");

            Intent it = new Intent(ListaProdutosActivity.this, DetalProdutosActivity.class);
            it.putExtra("produto", produto);
            startActivity(it);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("Listar Produtos", "onResume");
    }
}
