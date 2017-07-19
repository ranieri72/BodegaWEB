package com.ranieri.bodegaweb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.ranieri.bodegaweb.fragments.ListProductsFragment;
import com.ranieri.bodegaweb.fragments.ListProductsFragment.ClickOnProductListener;
import com.ranieri.bodegaweb.model.Produtos;
import com.ranieri.bodegaweb.model.SubCategorias;

import org.parceler.Parcels;

import butterknife.ButterKnife;

public class ListaProdutosActivity extends AppCompatActivity implements ClickOnProductListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.v("ListaProdutosActivity", "onCreate");
        ButterKnife.bind(this);

        SubCategorias subCategoria = Parcels.unwrap(getIntent().getParcelableExtra("subCategoria"));
        setTitle(subCategoria.getNome());

        ListProductsFragment fragment = ListProductsFragment.novaInstancia(subCategoria);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_list_products, fragment, "detalhe").commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("ListaProdutosActivity", "onResume");
    }

    @Override
    public void productClicked(Produtos produto) {
        Log.v("ListaProdutosActivity", "OnItemClickListener");

        Intent it = new Intent(ListaProdutosActivity.this, DetalProdutosActivity.class);
        it.putExtra("produto", Parcels.wrap(produto));
        startActivity(it);
    }
}
