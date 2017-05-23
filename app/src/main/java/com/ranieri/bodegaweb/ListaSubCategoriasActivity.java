package com.ranieri.bodegaweb;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ranieri.bodegaweb.adapter.SubCategoriasAdapter;
import com.ranieri.bodegaweb.dao.ProdutosDAO;
import com.ranieri.bodegaweb.dao.SubCategoriasDAO;
import com.ranieri.bodegaweb.database.ProdutosTxt;
import com.ranieri.bodegaweb.model.Produtos;
import com.ranieri.bodegaweb.model.SubCategorias;

import org.parceler.Parcels;

import java.util.List;

public class ListaSubCategoriasActivity extends AppCompatActivity {

//    private static final int REQUEST_EXTERNAL_STORAGE = 1;
//    private static String[] PERMISSIONS_STORAGE = {
//            Manifest.permission.WRITE_EXTERNAL_STORAGE
//    };

    SubCategoriasDAO mDAO;
    ProdutosDAO produtosDAO;
    Produtos produto;
    ProdutosTxt produtosTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categorias);

        Log.v("Listar SubCategorias", "onCreate");

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            produto = (Produtos) extras.get("produto");
            setTitle(getResources().getString(R.string.categoria));
        } else {
            setTitle(getResources().getString(R.string.produtos));
        }

        mDAO = new SubCategoriasDAO(this);
        ListView mListView = (ListView)findViewById(R.id.listSubCategorias);
        List<SubCategorias> mCategorias = mDAO.listar();

//        if (mCategorias.isEmpty()){
//            preencherBanco();
//            mCategorias = mDAO.listar();
//        }

        SubCategoriasAdapter adapter = new SubCategoriasAdapter(this, mCategorias);

        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(tratadorDeCliques);
    }

    AdapterView.OnItemClickListener tratadorDeCliques = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SubCategorias subCategoria = (SubCategorias) parent.getAdapter().getItem(position);
            Parcelable parcelable = Parcels.wrap(produto);
            Intent it;

            if (produto == null) {

                Log.v("Listar SubCategorias", "OnItemClickListener - Produto nulo");

                it = new Intent(ListaSubCategoriasActivity.this, ListaProdutosActivity.class);
                it.putExtra("subCategoria", parcelable);
                startActivity(it);
            } else {

                Log.v("Listar SubCategorias", "OnItemClickListener - Produto recebido");

                it = new Intent();
                it.putExtra("subCategoria", parcelable);
                setResult(RESULT_OK, it);
                finish();
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent it;
        switch (item.getItemId()){
//            case R.id.action_gerar_sql:
//                verifyStoragePermissions();
//                gerarSQL();
//                Toast.makeText(this, getResources().getString(R.string.sqlgerado), Toast.LENGTH_SHORT).show();
//                break;
            case R.id.action_configuracoes:
                it = new Intent(ListaSubCategoriasActivity.this, ConfiguracoesActivity.class);
                startActivity(it);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

//    private void gerarSQL() {
//        produtosTxt = new ProdutosTxt();
//        produtosDAO = new ProdutosDAO(this);
//        List<Produtos> lista = produtosDAO.listarAlterado();
//        produtosTxt.writeRawTextFile(lista, this);
//    }

//    private void verifyStoragePermissions() {
//        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//        if (permission != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(
//                    this,
//                    PERMISSIONS_STORAGE,
//                    REQUEST_EXTERNAL_STORAGE
//            );
//        }
//    }

//    private void preencherBanco() {
//        produtosTxt = new ProdutosTxt();
//        CategoriasTxt categoriasTxt = new CategoriasTxt();
//        SubCategoriasTxt subCategoriasTxt = new SubCategoriasTxt();
//
//        produtosDAO = new ProdutosDAO(this);
//        CategoriasDAO categoriasDAO = new CategoriasDAO(this);
//
//        Estoque estoque = new Estoque(produtosTxt.readRawTextFile(this));
//        List<Categorias> listaCategorias = categoriasTxt.readRawTextFile(this);
//        List<SubCategorias> listaSubCategorias = subCategoriasTxt.readRawTextFile(this);
//
//        mDAO.inserirLista(listaSubCategorias);
//        categoriasDAO.inserirLista(listaCategorias);
//        produtosDAO.inserirEstoque(estoque);
//    }
}
