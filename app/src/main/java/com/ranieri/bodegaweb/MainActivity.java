package com.ranieri.bodegaweb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ranieri.bodegaweb.fragments.ListSubCategoryFragment;
import com.ranieri.bodegaweb.model.Produtos;
import com.ranieri.bodegaweb.model.SubCategorias;

import org.parceler.Parcels;

public class MainActivity extends AppCompatActivity implements ListSubCategoryFragment.CliqueNaSubCategoriaListener {

    Produtos produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("MainActivity", "onCreate");
        produto = Parcels.unwrap(getIntent().getParcelableExtra("produto"));
        if (produto != null) {
            setTitle(getResources().getString(R.string.categoria));
        }
    }

    @Override
    public void subcategoriaFoiClicada(SubCategorias subCategoria) {
        Intent it;

        if (produto == null) {
            Log.v("MainActivity", "OnItemClickListener - Produto nulo");

            it = new Intent(this, ListaProdutosActivity.class);
            it.putExtra("subCategoria", Parcels.wrap(subCategoria));
            startActivity(it);
        } else {
            Log.v("MainActivity", "OnItemClickListener - Produto recebido");

            it = new Intent();
            it.putExtra("subCategoria", Parcels.wrap(subCategoria));
            setResult(RESULT_OK, it);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent it;
        switch (item.getItemId()) {
            case R.id.action_configuracoes:
                it = new Intent(this, ConfiguracoesActivity.class);
                startActivity(it);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
