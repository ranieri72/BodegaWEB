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

import com.ranieri.bodegaweb.fragments.ListOrderFragment.ClickOnOrderListener;
import com.ranieri.bodegaweb.fragments.ListProviderFragment.ClickOnProviderListener;
import com.ranieri.bodegaweb.fragments.ListSubCategoryFragment.ClickOnSubCategoryListener;
import com.ranieri.bodegaweb.model.Order;
import com.ranieri.bodegaweb.model.Produtos;
import com.ranieri.bodegaweb.model.Provider;
import com.ranieri.bodegaweb.model.SubCategorias;

import org.parceler.Parcels;

public class MainActivity extends AppCompatActivity implements ClickOnSubCategoryListener, ClickOnProviderListener, ClickOnOrderListener {

    Produtos produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.categoria)));
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

    @Override
    public void subCategoryClicked(SubCategorias subCategoria) {
        Intent it;

        if (produto == null) {
            Log.v("MainActivity", "subCategoryClicked - Produto nulo");

            it = new Intent(this, ListaProdutosActivity.class);
            it.putExtra("subCategoria", Parcels.wrap(subCategoria));
            startActivity(it);
        }
//        else {
//            Log.v("MainActivity", "OnItemClickListener - Produto recebido");
//
//            it = new Intent();
//            it.putExtra("subCategoria", Parcels.wrap(subCategoria));
//            setResult(RESULT_OK, it);
//            finish();
//        }
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
        switch (item.getItemId()) {
            case R.id.action_configuracoes:
                it = new Intent(this, ConfiguracoesActivity.class);
                startActivity(it);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
