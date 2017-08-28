package com.ranieri.bodegaweb.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.TabLayoutOnPageChangeListener;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.ranieri.bodegaweb.R;
import com.ranieri.bodegaweb.asyncTask.RefreshDataTask;
import com.ranieri.bodegaweb.connection.AppSession;
import com.ranieri.bodegaweb.dao.CategoriasDAO;
import com.ranieri.bodegaweb.dao.StockMovementDAO;
import com.ranieri.bodegaweb.dao.SubCategoriasDAO;
import com.ranieri.bodegaweb.dao.UserDAO;
import com.ranieri.bodegaweb.model.Categorias;
import com.ranieri.bodegaweb.model.Order;
import com.ranieri.bodegaweb.model.Produtos;
import com.ranieri.bodegaweb.model.Provider;
import com.ranieri.bodegaweb.model.StockMovement;
import com.ranieri.bodegaweb.model.SubCategorias;
import com.ranieri.bodegaweb.model.UnidadeMedida;
import com.ranieri.bodegaweb.util.Util;
import com.ranieri.bodegaweb.view.fragments.ListCategoryFragment;
import com.ranieri.bodegaweb.view.fragments.ListCategoryFragment.ClickOnCategoryListener;
import com.ranieri.bodegaweb.view.fragments.ListOrderFragment.ClickOnOrderListener;
import com.ranieri.bodegaweb.view.fragments.ListProductsFragment;
import com.ranieri.bodegaweb.view.fragments.ListProductsFragment.ClickOnProductListener;
import com.ranieri.bodegaweb.view.fragments.ListProviderFragment.ClickOnProviderListener;
import com.ranieri.bodegaweb.view.fragments.ListSubCategoryFragment;
import com.ranieri.bodegaweb.view.fragments.ListSubCategoryFragment.ClickOnSubCategoryListener;
import com.ranieri.bodegaweb.view.pagerAdapter.MainPagerAdapter;

import org.parceler.Parcels;

import java.util.Date;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements ClickOnSubCategoryListener, ClickOnCategoryListener, ClickOnProviderListener, ClickOnOrderListener, ClickOnProductListener {

    ListCategoryFragment categoryFragment;
    ListProductsFragment productsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Util.isTablet = getResources().getBoolean(R.bool.isTablet);
        SharedPreferences sharedPreferences = getSharedPreferences(Util.tabletViewPreference, Context.MODE_PRIVATE);
        Util.isTablet = sharedPreferences.getBoolean(Util.tabletViewPreference, true);

        int layout = R.layout.activity_main;
        if (Util.isTablet) layout = R.layout.activity_main_tablet;
        setContentView(layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Util.isTablet) {
            onCreateViewTablet();
        } else {
            onCreateViewPhone();
        }
    }

    private void onCreateViewPhone() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.produtos)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.fornecedor)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.pedido)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

//        produto = Parcels.unwrap(getIntent().getParcelableExtra("produto"));
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("produto", Parcels.wrap(produto));

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

    private void onCreateViewTablet() {
        ListSubCategoryFragment subCategoryFragment = new ListSubCategoryFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_list_sub_category, subCategoryFragment, "detalhe").commit();

        SubCategorias subCategoria = new SubCategoriasDAO(this).selecionarPrimeira();

        if (subCategoria != null) {
            categoryFragment = ListCategoryFragment.novaInstancia(subCategoria);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_list_category, categoryFragment, "detalhe").commit();

            Categorias categoria = new CategoriasDAO(this).selecionarPrimeira(subCategoria);

            if (categoria != null) {
                productsFragment = ListProductsFragment.novaInstancia(categoria);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_list_products, productsFragment, "detalhe").commit();
            }
        }
    }

    @Override
    public void subCategoryClicked(SubCategorias subCategoria) {
        if (Util.isTablet) {
            Log.v("MainActivity", "subCategoryClicked - isTablet");
            categoryFragment.notifyDataSetChanged(subCategoria);

            Categorias categoria = new CategoriasDAO(this).selecionarPrimeira(subCategoria);
            if (categoria != null) {
                productsFragment.notifyDataSetChanged(categoria);
            }
        } else {
            Log.v("MainActivity", "subCategoryClicked - isTablet");
            Intent it = new Intent(this, ListaProdutosActivity.class);
            it.putExtra("subCategoria", Parcels.wrap(subCategoria));
            startActivity(it);
        }
    }

    @Override
    public void categoryClicked(Categorias categoria) {
        productsFragment.notifyDataSetChanged(categoria);
    }

    @Override
    public void productClicked(Produtos produto) {
        dialogProduct(produto);
    }

    @Override
    public void orderClicked(Order order) {
        Intent it;
        Log.v("MainActivity", "orderClicked");

        it = new Intent(this, OrderActivity.class);
        it.putExtra("order", Parcels.wrap(order));
        startActivity(it);
    }

    @Override
    public void providerClicked(Provider provider) {
        Intent it;
        Log.v("MainActivity", "providerClicked");

        it = new Intent(this, ProviderActivity.class);
        it.putExtra("provider", Parcels.wrap(provider));
        startActivity(it);
    }

    private void dialogProduct(final Produtos produto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        builder.setTitle(getResources().getString(R.string.stockMovement) + " - " + produto.getNome());
        builder.setMessage(getResources().getString(R.string.qtd));
        builder.setView(input);
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                StockMovement movement = new StockMovement();
                movement.setUser(AppSession.user);
                movement.setQtd(Integer.parseInt(input.getText().toString()));
                movement.setProduto(produto);
                movement.setUnidMedida(new UnidadeMedida(1));
                movement.setPerda(false);
                movement.setHora(new Date());
                movement.setData(new Date());
                new StockMovementDAO(MainActivity.this).inserir(movement);
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