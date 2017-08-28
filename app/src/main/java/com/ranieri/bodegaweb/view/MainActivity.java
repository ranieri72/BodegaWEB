package com.ranieri.bodegaweb.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.TabLayoutOnPageChangeListener;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ranieri.bodegaweb.R;
import com.ranieri.bodegaweb.asyncTask.RefreshDataTask;
import com.ranieri.bodegaweb.dao.UserDAO;
import com.ranieri.bodegaweb.model.Order;
import com.ranieri.bodegaweb.model.Provider;
import com.ranieri.bodegaweb.model.SubCategorias;
import com.ranieri.bodegaweb.view.fragments.ListOrderFragment.ClickOnOrderListener;
import com.ranieri.bodegaweb.view.fragments.ListProviderFragment.ClickOnProviderListener;
import com.ranieri.bodegaweb.view.fragments.ListSubCategoryFragment.ClickOnSubCategoryListener;
import com.ranieri.bodegaweb.view.pagerAdapter.MainPagerAdapter;

import org.parceler.Parcels;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements ClickOnSubCategoryListener, ClickOnProviderListener, ClickOnOrderListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.produtos)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.fornecedor)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.pedido)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final MainPagerAdapter adapter = new MainPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void subCategoryClicked(SubCategorias subCategoria) {
        Log.v("MainActivity", "subCategoryClicked");

        Intent it = new Intent(this, ListaProdutosActivity.class);
        it.putExtra("subCategoria", Parcels.wrap(subCategoria));
        startActivity(it);
    }

    @Override
    public void orderClicked(Order order) {
        Log.v("MainActivity", "orderClicked");

        Intent it = new Intent(this, OrderActivity.class);
        it.putExtra("order", Parcels.wrap(order));
        startActivity(it);
    }

    @Override
    public void providerClicked(Provider provider) {
        Log.v("MainActivity", "providerClicked");

        Intent it = new Intent(this, ProviderActivity.class);
        it.putExtra("provider", Parcels.wrap(provider));
        startActivity(it);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_update:
                dialogUpdate();
                break;
            case R.id.action_configuracoes:
                optionConfig();
                break;
            case R.id.action_logout:
                dialogLogout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogUpdate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.atualizarDados));
        builder.setMessage(getResources().getString(R.string.areYouSure));
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                updateData();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    private void updateData() {
        try {
            int qtd = new RefreshDataTask().execute(this).get();
            Toast.makeText(this, getResources().getString(R.string.produtosAtualizados) + qtd, Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void optionConfig() {
        Intent it = new Intent(this, ConfiguracoesActivity.class);
        startActivity(it);
    }

    private void dialogLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.logout));
        builder.setMessage(getResources().getString(R.string.confirmlogout));
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                logout();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    private void logout() {
        new UserDAO(this).setAutoLoginFalse();
        Intent it = new Intent(this, LoginActivity.class);
        startActivity(it);
        finish();
    }
}