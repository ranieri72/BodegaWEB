package com.ranieri.bodegaweb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.ranieri.bodegaweb.asyncTask.RefreshOrderTask;
import com.ranieri.bodegaweb.asyncTask.RefreshProductsTask;

import java.util.concurrent.ExecutionException;

import butterknife.OnClick;

public class ConfiguracoesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);
        setTitle(getResources().getString(R.string.configuracoes));
    }

    @OnClick({R.id.btnAtualizarProdutos, R.id.btnAtualizarPedidos})
    void onItemClicked(View v) {
        try {
            int qtd;
            switch (v.getId()) {
                case R.id.btnAtualizarProdutos:
                    qtd = new RefreshProductsTask().execute(this).get();
                    Toast.makeText(this, getResources().getString(R.string.produtosAtualizados) + qtd, Toast.LENGTH_LONG).show();
                    break;
                case R.id.btnAtualizarPedidos:
                    qtd = new RefreshOrderTask().execute(this).get();
                    Toast.makeText(this, getResources().getString(R.string.erroConexao) + qtd, Toast.LENGTH_LONG).show();
                    break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
