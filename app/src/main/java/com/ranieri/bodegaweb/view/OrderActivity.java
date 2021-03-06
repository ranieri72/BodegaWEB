package com.ranieri.bodegaweb.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.TabLayoutOnPageChangeListener;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ranieri.bodegaweb.R;
import com.ranieri.bodegaweb.model.Order;
import com.ranieri.bodegaweb.view.pagerAdapter.OrderPagerAdapter;

import org.parceler.Parcels;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.items)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        Order order = Parcels.unwrap(getIntent().getParcelableExtra("order"));
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("produto", Parcels.wrap(produto));

        setTitle(order.getFornecedor().getEmpresa());

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final OrderPagerAdapter adapter = new OrderPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(), order);
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
}
