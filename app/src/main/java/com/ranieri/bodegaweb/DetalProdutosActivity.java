package com.ranieri.bodegaweb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ranieri.bodegaweb.dao.ProdutosDAO;
import com.ranieri.bodegaweb.model.Produtos;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetalProdutosActivity extends AppCompatActivity {

    @BindView(R.id.txt_nome)
    TextView mTxtNome;

    @BindView(R.id.txt_estoque)
    TextView mTxtEstoque;

    @BindView(R.id.txt_novo_estoque)
    TextView mTxtNovoEstoque;

    @BindView(R.id.txt_preco)
    TextView mTxtPreco;

    @BindView(R.id.txt_categoria)
    TextView mTxtCategoria;

    @BindView(R.id.txt_subcategoria)
    TextView mTxtSubCategoria;

    Produtos produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detal_produtos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        setTitle(getResources().getString(R.string.detalhes));

        Log.v("Detalhes Produto", "onCreate");

        produto = Parcels.unwrap(getIntent().getParcelableExtra("produto"));
        ProdutosDAO mDAO = new ProdutosDAO(this);
        produto = mDAO.selecionar(produto);

        mTxtNome = (TextView) findViewById(R.id.txt_nome);
        mTxtEstoque = (TextView) findViewById(R.id.txt_estoque);
        mTxtNovoEstoque = (TextView) findViewById(R.id.txt_novo_estoque);
        mTxtPreco = (TextView) findViewById(R.id.txt_preco);
        mTxtCategoria = (TextView) findViewById(R.id.txt_categoria);
        mTxtSubCategoria = (TextView) findViewById(R.id.txt_subcategoria);

        definirTextView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detal_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent it;
        switch (item.getItemId()) {
            case R.id.action_editar:
                Log.v("Detalhes Produto", "onOptionsItemSelected");

                it = new Intent(DetalProdutosActivity.this, EditarProdutosActivity.class);
                it.putExtra("produto", Parcels.wrap(produto));
                startActivityForResult(it, 0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK) {

            Log.v("Detalhes Produto", "onActivityResult");

            produto = Parcels.unwrap(data.getParcelableExtra("produto"));
            definirTextView();

            Log.v("Detalhes Produto", "onActivityResult - Produto " + produto.getNome());
        }
    }

    private void definirTextView() {
        mTxtNome.setText(produto.getNome());
        mTxtEstoque.setText(String.valueOf(produto.getEstoque()));
        mTxtNovoEstoque.setText(String.valueOf(produto.getNovoEstoque()));
        mTxtPreco.setText(String.valueOf(produto.getPrecoSugerido()));
        mTxtCategoria.setText(produto.getCategoria().getNome());
        mTxtSubCategoria.setText(produto.getCategoria().getSubCategoriaProd().getNome());
    }
}
