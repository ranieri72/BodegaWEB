package com.ranieri.bodegaweb.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.TabLayoutOnPageChangeListener;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.Trace;
import com.ranieri.bodegaweb.R;
import com.ranieri.bodegaweb.connection.AppSession;
import com.ranieri.bodegaweb.model.Order;
import com.ranieri.bodegaweb.model.Provider;
import com.ranieri.bodegaweb.model.SubCategorias;
import com.ranieri.bodegaweb.view.fragments.ListOrderFragment.ClickOnOrderListener;
import com.ranieri.bodegaweb.view.fragments.ListProviderFragment.ClickOnProviderListener;
import com.ranieri.bodegaweb.view.pagerAdapter.MainPagerAdapter;

import org.parceler.Parcels;

public class MainActivity extends MainGenericActivity implements ClickOnProviderListener, ClickOnOrderListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Trace myTrace = FirebasePerformance.getInstance().newTrace("onCreate - PhoneView");
        myTrace.start();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.produtos)));

        if (AppSession.user.getPermissionsApp().isVerFornecedores()) {
            tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.fornecedor)));
        }
        if (AppSession.user.getPermissionsApp().isVerPedidos()) {
            tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.pedido)));
        }
        tabLayout.setTabGravity(TabLayout.MODE_SCROLLABLE);

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
        myTrace.stop();
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
}