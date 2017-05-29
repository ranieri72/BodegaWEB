package com.ranieri.bodegaweb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.ranieri.bodegaweb.adapter.CategoriasAdapter;
import com.ranieri.bodegaweb.dao.CategoriasDAO;
import com.ranieri.bodegaweb.model.Categorias;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class ListaCategoriasActivity extends AppCompatActivity {

    @BindView(R.id.listCategorias)
    ListView mListView;
    CategoriasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);
        ButterKnife.bind(this);
        setTitle(getResources().getString(R.string.subcategoria));

        Log.v("Listar Categorias", "onCreate");

        Categorias categoria = Parcels.unwrap(getIntent().getParcelableExtra("categoria"));

        CategoriasDAO mDAO = new CategoriasDAO(this);
        List<Categorias> mCategorias = mDAO.listar(categoria.getSubCategoriaProd());

        adapter = new CategoriasAdapter(this, mCategorias);
        mListView.setAdapter(adapter);
    }

    @OnItemClick(R.id.listCategorias)
    void onItemClicked(int position) {
        Categorias categoria = (Categorias) adapter.getItem(position);
        Intent it;

        Log.v("Listar Categorias", "OnItemClickListener - Produto recebido");

        it = new Intent();
        it.putExtra("categoria", Parcels.wrap(categoria));
        setResult(RESULT_OK, it);
        finish();
    }
}
