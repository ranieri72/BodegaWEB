package com.ranieri.bodegaweb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ranieri.bodegaweb.asyncTask.RefreshOrderTask;
import com.ranieri.bodegaweb.asyncTask.RefreshProductsTask;
import com.ranieri.bodegaweb.connection.AsyncRequest;
import com.ranieri.bodegaweb.connection.ConnectionConstants;

import java.util.concurrent.ExecutionException;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfiguracoesActivity extends AppCompatActivity {

//    @BindView(R.id.progress_bar)
//    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getResources().getString(R.string.configuracoes));
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnAtualizarProdutos, R.id.btnAtualizarPedidos, R.id.btnIonTest})
    void onItemClicked(View v) {
        try {
            int qtd;
            //progressBar.setIndeterminate(true);
            switch (v.getId()) {
                case R.id.btnAtualizarProdutos:
                    Log.v("ConfiguracoesActivity", "btnAtualizarProdutos");
                    qtd = new RefreshProductsTask().execute(this).get();
                    Toast.makeText(this, getResources().getString(R.string.produtosAtualizados) + qtd, Toast.LENGTH_LONG).show();
                    break;
                case R.id.btnAtualizarPedidos:
                    Log.v("ConfiguracoesActivity", "btnAtualizarPedidos");
                    qtd = new RefreshOrderTask().execute(this).get();
                    Toast.makeText(this, getResources().getString(R.string.pedidosAtualizados) + qtd, Toast.LENGTH_LONG).show();
                    break;
                case R.id.btnIonTest:
                    Log.v("ConfiguracoesActivity", "btnIonTest");
                    new AsyncRequest(this, ConnectionConstants.urlProducts).getJson();
                    Toast.makeText(this, "Ion Teste", Toast.LENGTH_LONG).show();
                    break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //progressBar.setIndeterminate(false);
        }
    }
}
