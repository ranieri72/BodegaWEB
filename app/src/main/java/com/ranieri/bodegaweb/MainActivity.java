package com.ranieri.bodegaweb;

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
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.ranieri.bodegaweb.dao.CategoriasDAO;
import com.ranieri.bodegaweb.dao.SubCategoriasDAO;
import com.ranieri.bodegaweb.dao.UserDAO;
import com.ranieri.bodegaweb.fragments.ListCategoryFragment;
import com.ranieri.bodegaweb.fragments.ListCategoryFragment.ClickOnCategoryListener;
import com.ranieri.bodegaweb.fragments.ListOrderFragment.ClickOnOrderListener;
import com.ranieri.bodegaweb.fragments.ListProductsFragment;
import com.ranieri.bodegaweb.fragments.ListProductsFragment.ClickOnProductListener;
import com.ranieri.bodegaweb.fragments.ListProviderFragment.ClickOnProviderListener;
import com.ranieri.bodegaweb.fragments.ListSubCategoryFragment;
import com.ranieri.bodegaweb.fragments.ListSubCategoryFragment.ClickOnSubCategoryListener;
import com.ranieri.bodegaweb.model.Categorias;
import com.ranieri.bodegaweb.model.Order;
import com.ranieri.bodegaweb.model.Produtos;
import com.ranieri.bodegaweb.model.Provider;
import com.ranieri.bodegaweb.model.SubCategorias;
import com.ranieri.bodegaweb.pagerAdapter.MainPagerAdapter;
import com.ranieri.bodegaweb.util.Util;

import org.parceler.Parcels;

public class MainActivity extends AppCompatActivity implements ClickOnSubCategoryListener, ClickOnCategoryListener, ClickOnProviderListener, ClickOnOrderListener, ClickOnProductListener {

    ListCategoryFragment categoryFragment;
    ListProductsFragment productsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Util.isPhone = getResources().getBoolean(R.bool.isPhone);

        if (Util.isPhone) {
            onCreateViewPhone();
        } else {
            onCreateViewTablet();
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
        if (Util.isPhone) {
            Log.v("MainActivity", "subCategoryClicked - isPhone");
            Intent it = new Intent(this, ListaProdutosActivity.class);
            it.putExtra("subCategoria", Parcels.wrap(subCategoria));
            startActivity(it);
        } else {
            Log.v("MainActivity", "subCategoryClicked - isTablet");
            categoryFragment.notifyDataSetChanged(subCategoria);

            Categorias categoria = new CategoriasDAO(this).selecionarPrimeira(subCategoria);
            if (categoria != null) {
                productsFragment.notifyDataSetChanged(categoria);
            }
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
                Toast.makeText(MainActivity.this, "qtd " + input.getText().toString(), Toast.LENGTH_SHORT).show();
                produto.setEstoque(Integer.parseInt(input.getText().toString()));
                //new ProdutosDAO(MainActivity.this).atualizar(produto);
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    private void dialogLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.logout));
        builder.setMessage(getResources().getString(R.string.confirmlogout));
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                optionLogout();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
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
            case R.id.action_configuracoes:
                optionConfig();
                break;
            case R.id.action_logout:
                dialogLogout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void optionConfig() {
        Intent it = new Intent(this, ConfiguracoesActivity.class);
        startActivity(it);
    }

    private void optionLogout() {
        new UserDAO(this).setAutoLoginFalse();
        Intent it = new Intent(this, LoginActivity.class);
        startActivity(it);
        finish();
    }
}