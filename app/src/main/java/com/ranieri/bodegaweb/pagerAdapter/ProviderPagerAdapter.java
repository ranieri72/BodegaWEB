package com.ranieri.bodegaweb.pagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ranieri.bodegaweb.fragments.ListProviderFragment;

/**
 * Created by ranie on 17 de jun.
 */

public class ProviderPagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public ProviderPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new ListProviderFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

