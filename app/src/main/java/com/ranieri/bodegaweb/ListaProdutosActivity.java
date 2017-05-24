package com.ranieri.bodegaweb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ranieri.bodegaweb.adapter.ProdutosAdapter;
import com.ranieri.bodegaweb.dao.ProdutosDAO;
import com.ranieri.bodegaweb.model.Produtos;
import com.ranieri.bodegaweb.model.SubCategorias;

import org.parceler.Parcels;

import java.util.List;

public class ListaProdutosActivity extends AppCompatActivity {

    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        Log.v("Listar Produtos", "onCreate");

        SubCategorias subCategoria = Parcels.unwrap(getIntent().getParcelableExtra("subCategoria"));
        setTitle(subCategoria.getNome());

        ProdutosDAO mDAO = new ProdutosDAO(this);
        mListView = (ListView) findViewById(R.id.listProdutos);

        List<Produtos> mLista = mDAO.listar(subCategoria);
        ProdutosAdapter mAdapter = new ProdutosAdapter(this, mLista);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(tratadorDeCliques);
    }

    AdapterView.OnItemClickListener tratadorDeCliques = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Produtos produto = (Produtos) parent.getAdapter().getItem(position);

            Log.v("Listar Produtos", "OnItemClickListener");

            Intent it = new Intent(ListaProdutosActivity.this, DetalProdutosActivity.class);
            it.putExtra("produto", Parcels.wrap(produto));
            startActivity(it);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("Listar Produtos", "onResume");
    }
}
