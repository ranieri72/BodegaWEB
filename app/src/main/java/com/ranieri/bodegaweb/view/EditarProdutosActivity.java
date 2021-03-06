package com.ranieri.bodegaweb.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ranieri.bodegaweb.R;
import com.ranieri.bodegaweb.dao.ProdutosDAO;
import com.ranieri.bodegaweb.model.Categorias;
import com.ranieri.bodegaweb.model.Produtos;
import com.ranieri.bodegaweb.model.SubCategorias;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditarProdutosActivity extends AppCompatActivity {

    @BindView(R.id.edt_nome)
    EditText mEdtNome;

    @BindView(R.id.edt_preco)
    EditText mEdtPreco;

    @BindView(R.id.btnCategoria)
    Button mBtnCategoria;

    @BindView(R.id.btnSubCategoria)
    Button mBtnSubCategoria;

    Produtos produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_produtos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        setTitle(getResources().getString(R.string.editar));

        Log.v("Editar Produto", "onCreate");

        produto = Parcels.unwrap(getIntent().getParcelableExtra("produto"));

        mEdtNome.setText(produto.getNome());
        mEdtPreco.setText(String.valueOf(produto.getPrecoSugerido()));
        mBtnCategoria.setText(produto.getCategoria().getNome());
        mBtnSubCategoria.setText(produto.getCategoria().getSubCategoriaProd().getNome());
    }

    @OnClick({R.id.btnSubCategoria, R.id.btnCategoria})
    void onItemClicked(View view) {
        Intent it;

        switch (view.getId()) {
            case R.id.btnCategoria:

                Log.v("Editar Produto", "onClick - Categoria");

                it = new Intent(EditarProdutosActivity.this, ListaCategoriasActivity.class);
                it.putExtra("categoria", Parcels.wrap(produto.getCategoria()));
                startActivityForResult(it, 2);
                break;
            case R.id.btnSubCategoria:

                Log.v("Editar Produto", "onClick - Subcategoria");

                it = new Intent(EditarProdutosActivity.this, MainPhoneActivity.class);
                it.putExtra("produto", Parcels.wrap(produto));
                startActivityForResult(it, 1);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent it;
        ProdutosDAO mDAO;
        switch (item.getItemId()) {
            case R.id.action_salvar:
                Log.v("Editar Produto", "onClick - Salvar");

                produto.setNome(mEdtNome.getText().toString());
                produto.setPrecoSugerido(Double.parseDouble(mEdtPreco.getText().toString()));

                mDAO = new ProdutosDAO(EditarProdutosActivity.this);
                mDAO.atualizar(produto);

                it = new Intent();
                it.putExtra("produto", Parcels.wrap(produto));
                setResult(RESULT_OK, it);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {

            Log.v("Editar Produto", "onActivityResult");

            SubCategorias subCategoria = Parcels.unwrap(data.getParcelableExtra("subCategoria"));

            mBtnSubCategoria.setText(subCategoria.getNome());
            mBtnCategoria.setText("");

            produto.getCategoria().setSubCategoriaProd(subCategoria);
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {

            Log.v("Editar Produto", "onActivityResult");

            Categorias categoria = Parcels.unwrap(data.getParcelableExtra("categoria"));

            mBtnCategoria.setText(categoria.getNome());
            produto.setCategoria(categoria);
        }
    }
}
