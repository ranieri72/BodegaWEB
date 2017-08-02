package com.ranieri.bodegaweb.view.pagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ranieri.bodegaweb.view.fragments.ListProductsFragment;
import com.ranieri.bodegaweb.model.Provider;

/**
 * Created by ranie on 17 de jun.
 */

public class ProviderPagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private Provider provider;

    public ProviderPagerAdapter(FragmentManager fm, int NumOfTabs, Provider provider) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.provider = provider;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return ListProductsFragment.novaInstancia(provider);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

