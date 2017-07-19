package com.ranieri.bodegaweb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.TabLayoutOnPageChangeListener;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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

import org.parceler.Parcels;

public class MainActivity extends AppCompatActivity implements ClickOnSubCategoryListener, ClickOnCategoryListener, ClickOnProviderListener, ClickOnOrderListener, ClickOnProductListener {

    //Produtos produto;
    boolean isPhone;
    ListCategoryFragment categoryFragment;
    ListProductsFragment productsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        isPhone = getResources().getBoolean(R.bool.isPhone);

        if (isPhone) {
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

        SubCategoriasDAO subCategoriasDAO = new SubCategoriasDAO(this);
        SubCategorias subCategoria = subCategoriasDAO.selecionarPrimeira();

        if (subCategoria != null) {
            categoryFragment = ListCategoryFragment.novaInstancia(subCategoria);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_list_category, categoryFragment, "detalhe").commit();

            CategoriasDAO categoriasDAO = new CategoriasDAO(this);
            Categorias categoria = categoriasDAO.selecionarPrimeira(subCategoria);

            if (categoria != null) {
                productsFragment = ListProductsFragment.novaInstancia(categoria);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_list_products, productsFragment, "detalhe").commit();
            }
        }
    }

    @Override
    public void subCategoryClicked(SubCategorias subCategoria) {
        if (isPhone) {
            Log.v("MainActivity", "subCategoryClicked - isPhone");
            Intent it = new Intent(this, ListaProdutosActivity.class);
            it.putExtra("subCategoria", Parcels.wrap(subCategoria));
            startActivity(it);
        } else {
            Log.v("MainActivity", "subCategoryClicked - isTablet");
            categoryFragment.notifyDataSetChanged(subCategoria);

            CategoriasDAO categoriasDAO = new CategoriasDAO(this);
            Categorias categoria = categoriasDAO.selecionarPrimeira(subCategoria);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent it;
        UserDAO dao;
        Bundle bundle;
        switch (item.getItemId()) {
            case R.id.action_configuracoes:
                it = new Intent(this, ConfiguracoesActivity.class);
                startActivity(it);
                break;
            case R.id.action_logout:
                dao = new UserDAO(this);
                dao.setAutoLoginFalse();
                it = new Intent(this, LoginActivity.class);
                startActivity(it);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
