package com.ranieri.bodegaweb;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ranieri.bodegaweb.dao.ProdutosDAO;
import com.ranieri.bodegaweb.model.Produtos;

import org.parceler.Parcels;

public class DetalProdutosActivity extends AppCompatActivity {

    TextView mTxtNome;
    TextView mTxtEstoque;
    TextView mTxtNovoEstoque;
    TextView mTxtPreco;
    TextView mTxtCategoria;
    TextView mTxtSubCategoria;
    Produtos produto;
    ProdutosDAO mDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detal_produtos);
        setTitle(getResources().getString(R.string.detalhes));

        Log.v("Detalhes Produto", "onCreate");

        produto = (Produtos) getIntent().getExtras().get("produto");
        mDAO = new ProdutosDAO(this);
        produto = mDAO.selecionar(produto);

        mTxtNome = (TextView)findViewById(R.id.txt_nome);
        mTxtEstoque = (TextView)findViewById(R.id.txt_estoque);
        mTxtNovoEstoque = (TextView)findViewById(R.id.txt_novo_estoque);
        mTxtPreco = (TextView)findViewById(R.id.txt_preco);
        mTxtCategoria = (TextView)findViewById(R.id.txt_categoria);
        mTxtSubCategoria = (TextView)findViewById(R.id.txt_subcategoria);

        definirTextView();
        findViewById(R.id.btnEditar).setOnClickListener(tratadorDeEventos);
    }

    View.OnClickListener tratadorDeEventos = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Log.v("Detalhes Produto", "onClick");

            Intent it = new Intent(DetalProdutosActivity.this, EditarProdutosActivity.class);
            Parcelable parcelable = Parcels.wrap(produto);
            it.putExtra("produto", parcelable);
            startActivityForResult(it, 0);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK){

            Log.v("Detalhes Produto", "onActivityResult");

            produto = data.getParcelableExtra("produto");
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
        mTxtSubCategoria.setText(produto.getCategoria().getSubCategoria().getNome());
    }
}
