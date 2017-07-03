package com.ranieri.bodegaweb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.ranieri.bodegaweb.dao.ProdutosDAO;
import com.ranieri.bodegaweb.model.Produtos;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditStockActivity extends AppCompatActivity {

    @BindView(R.id.edt_estoque)
    EditText mEdtStock;

    Produtos produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stock);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        setTitle(getResources().getString(R.string.editar));

        Log.v("EditStockActivity", "onCreate");

        produto = Parcels.unwrap(getIntent().getParcelableExtra("produto"));
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
                Log.v("Edit Stock", "onClick - Salvar");

                produto.setNovoEstoque(Integer.parseInt(mEdtStock.getText().toString()));
                produto.setAlterado(true);

                mDAO = new ProdutosDAO(EditStockActivity.this);
                mDAO.atualizar(produto);

                it = new Intent();
                it.putExtra("produto", Parcels.wrap(produto));
                setResult(RESULT_OK, it);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
