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

import com.ranieri.bodegaweb.fragments.ListProductsFragment.ClickOnProductListener;
import com.ranieri.bodegaweb.model.Produtos;
import com.ranieri.bodegaweb.model.Provider;
import com.ranieri.bodegaweb.pagerAdapter.ProviderPagerAdapter;

import org.parceler.Parcels;

public class ProviderActivity extends AppCompatActivity implements ClickOnProductListener {

    private Provider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.produtos)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        provider = Parcels.unwrap(getIntent().getParcelableExtra("provider"));

        setTitle(provider.getEmpresa());

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final ProviderPagerAdapter adapter = new ProviderPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(), provider);
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
    public void productClicked(Produtos produto) {
        Intent it;
        Log.v("ProviderActivity", "produtoFoiClicado");

        it = new Intent(this, ListOrderItemsActivity.class);
        it.putExtra("provider", Parcels.wrap(provider));
        it.putExtra("produto", Parcels.wrap(produto));
        startActivity(it);
    }
}
