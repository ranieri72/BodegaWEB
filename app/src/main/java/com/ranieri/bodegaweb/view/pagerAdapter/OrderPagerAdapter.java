package com.ranieri.bodegaweb.view.pagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ranieri.bodegaweb.view.fragments.ListOrderItemsFragment;
import com.ranieri.bodegaweb.model.Order;

/**
 * Created by ranie on 17 de jun.
 */

public class OrderPagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private Order order;

    public OrderPagerAdapter(FragmentManager fm, int NumOfTabs, Order order) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.order = order;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return ListOrderItemsFragment.novaInstancia(order);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

