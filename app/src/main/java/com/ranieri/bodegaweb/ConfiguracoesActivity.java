package com.ranieri.bodegaweb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ranieri.bodegaweb.asyncTask.ProdutosTask;
import com.ranieri.bodegaweb.dao.ProdutosDAO;
import com.ranieri.bodegaweb.model.Estoque;

import java.util.concurrent.ExecutionException;

public class ConfiguracoesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        findViewById(R.id.btnAtualizarProdutos).setOnClickListener(tratadorDeEventos);
    }

    View.OnClickListener tratadorDeEventos = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Estoque estoque = new ProdutosTask().execute().get();
                ProdutosDAO produtosDAO = new ProdutosDAO(ConfiguracoesActivity.this);
                int qtd = produtosDAO.inserirEstoque(estoque);
                Toast.makeText(ConfiguracoesActivity.this, getResources().getString(R.string.produtosAtualizados) + qtd, Toast.LENGTH_SHORT).show();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    };
}
