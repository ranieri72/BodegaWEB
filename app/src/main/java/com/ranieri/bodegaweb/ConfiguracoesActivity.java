package com.ranieri.bodegaweb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ranieri.bodegaweb.asyncTask.RefreshOrderTask;
import com.ranieri.bodegaweb.asyncTask.RefreshProductsTask;

import java.util.concurrent.ExecutionException;

public class ConfiguracoesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        findViewById(R.id.btnAtualizarProdutos).setOnClickListener(tratadorDeEventos);
        findViewById(R.id.btnAtualizarPedidos).setOnClickListener(tratadorDeEventos);
    }

    View.OnClickListener tratadorDeEventos = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                int qtd;
                switch (v.getId()){
                    case R.id.btnAtualizarProdutos:
                        qtd = new RefreshProductsTask().execute(ConfiguracoesActivity.this).get();
                        Toast.makeText(ConfiguracoesActivity.this, getResources().getString(R.string.produtosAtualizados) + qtd, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btnAtualizarPedidos:
                        new RefreshOrderTask().execute(ConfiguracoesActivity.this).get();
                        break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    };
}
