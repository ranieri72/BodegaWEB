package com.ranieri.bodegaweb;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ranieri.bodegaweb.fragments.ListOrderFragment;
import com.ranieri.bodegaweb.fragments.ListProviderFragment;
import com.ranieri.bodegaweb.fragments.ListSubCategoryFragment;

/**
 * Created by ranie on 17 de jun.
 */

class MainPagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    MainPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new ListSubCategoryFragment();
            case 1:
                return new ListProviderFragment();
            case 2:
                return new ListOrderFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

