package com.ranieri.bodegaweb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ranieri.bodegaweb.dao.ProdutosDAO;
import com.ranieri.bodegaweb.model.Categorias;
import com.ranieri.bodegaweb.model.Produtos;
import com.ranieri.bodegaweb.model.SubCategorias;

public class EditarProdutosActivity extends AppCompatActivity {

    EditText mEdtNome;
    EditText mEdtEstoque;
    EditText mEdtPreco;
    Button mBtnCategoria;
    Button mBtnSubCategoria;
    Produtos produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_produtos);
        setTitle(getResources().getString(R.string.editar));

        Log.v("Editar Produto", "onCreate");

        produto = (Produtos) getIntent().getExtras().get("produto");

        mEdtNome = (EditText)findViewById(R.id.edt_nome);
        mEdtEstoque = (EditText)findViewById(R.id.edt_estoque);
        mEdtPreco = (EditText)findViewById(R.id.edt_preco);
        mBtnCategoria = (Button) findViewById(R.id.btnCategoria);
        mBtnSubCategoria = (Button) findViewById(R.id.btnSubCategoria);

        mEdtNome.setText(produto.getNome());
        mEdtEstoque.setText(String.valueOf(produto.getEstoque()));
        mEdtPreco.setText(String.valueOf(produto.getPreço()));
        mBtnCategoria.setText(produto.getCategoria().getNome());
        mBtnSubCategoria.setText(produto.getCategoria().getSubCategoria().getNome());

        findViewById(R.id.btnCategoria).setOnClickListener(tratadorDeEventos);
        findViewById(R.id.btnSubCategoria).setOnClickListener(tratadorDeEventos);
        findViewById(R.id.btnSalvar).setOnClickListener(tratadorDeEventos);
    }

    View.OnClickListener tratadorDeEventos = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent it;
            ProdutosDAO mDAO;

            switch (v.getId()){
                case R.id.btnCategoria:

                    Log.v("Editar Produto", "onClick - Categoria");

                    it = new Intent(EditarProdutosActivity.this, ListaCategoriasActivity.class);
                    it.putExtra("produto", produto);
                    startActivityForResult(it, 2);
                    break;
                case R.id.btnSubCategoria:

                    Log.v("Editar Produto", "onClick - Subcategoria");

                    it = new Intent(EditarProdutosActivity.this, ListaSubCategoriasActivity.class);
                    it.putExtra("produto", produto);
                    startActivityForResult(it, 1);
                    break;
                case R.id.btnSalvar:

                    Log.v("Editar Produto", "onClick - Salvar");

                    produto.setNome(mEdtNome.getText().toString());
                    produto.setEstoque(Integer.parseInt(mEdtEstoque.getText().toString()));
                    produto.setPreço(Double.parseDouble(mEdtPreco.getText().toString()));

                    mDAO = new ProdutosDAO(EditarProdutosActivity.this);
                    mDAO.atualizar(produto);

                    it = new Intent();
                    it.putExtra("produto", produto);
                    setResult(RESULT_OK, it);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK){

            Log.v("Editar Produto", "onActivityResult");

            SubCategorias subCategoria = data.getParcelableExtra("subCategoria");

            mBtnSubCategoria.setText(subCategoria.getNome());
            mBtnCategoria.setText("");

            produto.getCategoria().setSubCategoria(subCategoria);
        }
        if (requestCode == 2 && resultCode == RESULT_OK){

            Log.v("Editar Produto", "onActivityResult");

            Categorias categoria = data.getParcelableExtra("categoria");

            mBtnCategoria.setText(categoria.getNome());
            produto.setCategoria(categoria);
        }
    }
}
