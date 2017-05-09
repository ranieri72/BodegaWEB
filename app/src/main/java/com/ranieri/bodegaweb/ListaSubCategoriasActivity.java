package com.ranieri.bodegaweb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ranieri.bodegaweb.adapter.SubCategoriasAdapter;
import com.ranieri.bodegaweb.dao.CategoriasDAO;
import com.ranieri.bodegaweb.dao.ProdutosDAO;
import com.ranieri.bodegaweb.dao.SubCategoriasDAO;
import com.ranieri.bodegaweb.database.CategoriasTxt;
import com.ranieri.bodegaweb.database.ProdutosTxt;
import com.ranieri.bodegaweb.database.SubCategoriasTxt;
import com.ranieri.bodegaweb.model.Categorias;
import com.ranieri.bodegaweb.model.Produtos;
import com.ranieri.bodegaweb.model.SubCategorias;

import java.util.List;

public class ListaSubCategoriasActivity extends AppCompatActivity {

    SubCategoriasDAO mDAO;
    Produtos produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categorias);

        Log.v("Listar SubCategorias", "onCreate");

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            produto = (Produtos) extras.get("produto");
            setTitle(getResources().getString(R.string.produtos));
        } else {
            setTitle(getResources().getString(R.string.categoria));
        }

        mDAO = new SubCategoriasDAO(this);
        ListView mListView = (ListView)findViewById(R.id.listSubCategorias);
        List<SubCategorias> mCategorias = mDAO.listar();

        if (mCategorias.isEmpty()){
            preencherBanco();
            mCategorias = mDAO.listar();
        }

        SubCategoriasAdapter adapter = new SubCategoriasAdapter(this, mCategorias);

        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(tratadorDeCliques);
    }

    AdapterView.OnItemClickListener tratadorDeCliques = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SubCategorias subCategoria = (SubCategorias) parent.getAdapter().getItem(position);
            Intent it;

            if (produto == null) {

                Log.v("Listar SubCategorias", "OnItemClickListener - Produto nulo");

                it = new Intent(ListaSubCategoriasActivity.this, ListaProdutosActivity.class);
                it.putExtra("subCategoria", subCategoria);
                startActivity(it);
            } else {

                Log.v("Listar SubCategorias", "OnItemClickListener - Produto recebido");

                it = new Intent();
                it.putExtra("subCategoria", subCategoria);
                setResult(RESULT_OK, it);
                finish();
            }
        }
    };

    private void preencherBanco() {
        ProdutosTxt produtosTxt = new ProdutosTxt();
        CategoriasTxt categoriasTxt = new CategoriasTxt();
        SubCategoriasTxt subCategoriasTxt = new SubCategoriasTxt();

        ProdutosDAO produtosDAO = new ProdutosDAO(this);
        CategoriasDAO categoriasDAO = new CategoriasDAO(this);

        List<Produtos> listaProdutos = produtosTxt.readRawTextFile(this);
        List<Categorias> listaCategorias = categoriasTxt.readRawTextFile(this);
        List<SubCategorias> listaSubCategorias = subCategoriasTxt.readRawTextFile(this);

        for (SubCategorias s: listaSubCategorias) {
            mDAO.inserir(s);
        }

        for (Categorias c: listaCategorias) {
            categoriasDAO.inserir(c);
        }

        for (Produtos p: listaProdutos) {
            p.setNovoEstoque(p.getEstoque());
            p.setAlterado(false);
            produtosDAO.inserir(p);
        }
    }
}
