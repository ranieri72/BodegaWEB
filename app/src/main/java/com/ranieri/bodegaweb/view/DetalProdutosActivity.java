package com.ranieri.bodegaweb.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ranieri.bodegaweb.R;
import com.ranieri.bodegaweb.dao.ProdutosDAO;
import com.ranieri.bodegaweb.model.Produtos;
import com.ranieri.bodegaweb.util.Util;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetalProdutosActivity extends AppCompatActivity {

    @BindView(R.id.txt_nome)
    TextView mTxtNome;

    @BindView(R.id.txt_estoque)
    TextView mTxtEstoque;

    @BindView(R.id.txt_preco)
    TextView mTxtPreco;

    @BindView(R.id.txt_categoria)
    TextView mTxtCategoria;

    @BindView(R.id.txt_subcategoria)
    TextView mTxtSubCategoria;

    @BindView(R.id.btn_novo_estoque)
    Button mBtnNovoEstoque;

    private Produtos produto;

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
        definirTextView();
    }

    @OnClick(R.id.btn_novo_estoque)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_novo_estoque:
                newStockDialog();
                break;
        }
    }

    private void newStockDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        builder.setTitle(getResources().getString(R.string.stockMovement) + " - " + produto.getNome());
        builder.setMessage(getResources().getString(R.string.qtd));
        builder.setView(input);
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                produto.setNovoEstoque(Integer.parseInt(input.getText().toString()));
                new ProdutosDAO(DetalProdutosActivity.this).atualizar(produto);
                mBtnNovoEstoque.setText(getResources().getString(R.string.novoestoque) + ": " + String.valueOf(produto.getNovoEstoque()));
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        AlertDialog alerta = builder.create();
        alerta.show();
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
        mBtnNovoEstoque.setText(getResources().getString(R.string.novoestoque) + ": " + String.valueOf(produto.getNovoEstoque()));
        mTxtPreco.setText(Util.moneyFormatter(produto.getPrecoSugerido()));
        mTxtCategoria.setText(produto.getCategoria().getNome());
        mTxtSubCategoria.setText(produto.getCategoria().getSubCategoriaProd().getNome());
    }
}
