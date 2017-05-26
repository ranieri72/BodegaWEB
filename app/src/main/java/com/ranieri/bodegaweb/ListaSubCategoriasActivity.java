package com.ranieri.bodegaweb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ranieri.bodegaweb.adapter.SubCategoriasAdapter;
import com.ranieri.bodegaweb.dao.SubCategoriasDAO;
import com.ranieri.bodegaweb.model.Produtos;
import com.ranieri.bodegaweb.model.SubCategorias;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListaSubCategoriasActivity extends AppCompatActivity {

//    private static final int REQUEST_EXTERNAL_STORAGE = 1;
//    private static String[] PERMISSIONS_STORAGE = {
//            Manifest.permission.WRITE_EXTERNAL_STORAGE
//    };

    @BindView(R.id.listSubCategorias)
    ListView mListView;

    Produtos produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categorias);
        ButterKnife.bind(this);

        Log.v("Listar SubCategorias", "onCreate");

        produto = Parcels.unwrap(getIntent().getParcelableExtra("produto"));

        if (produto != null) {
            setTitle(getResources().getString(R.string.categoria));
        } else {
            setTitle(getResources().getString(R.string.produtos));
        }
//        Bundle extras = getIntent().getExtras();
//
//        if (extras != null) {
//            produto = (Produtos) extras.get("produto");
//            setTitle(getResources().getString(R.string.categoria));
//        } else {
//            setTitle(getResources().getString(R.string.produtos));
//        }

        SubCategoriasDAO mDAO = new SubCategoriasDAO(this);
        List<SubCategorias> mCategorias = mDAO.listar();

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
                it.putExtra("subCategoria", Parcels.wrap(produto));
                startActivity(it);
            } else {

                Log.v("Listar SubCategorias", "OnItemClickListener - Produto recebido");

                it = new Intent();
                it.putExtra("subCategoria", Parcels.wrap(produto));
                setResult(RESULT_OK, it);
                finish();
            }
        }
    };
}
