package com.bolasepakmalaysia.bm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bolasepakmalaysia.bm.NewsFragment;
import com.bolasepakmalaysia.bm.Tab1;
import com.bolasepakmalaysia.bm.Tab2;

/**
 * Created by zul on 19-Feb-15.
 */
public class MainTabViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[];
    int NumbOfTabs;

    public MainTabViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabs) {

        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {

            NewsFragment newsFragment = new NewsFragment();
            return newsFragment;

        } else if (position == 1) {
            Tab2 tab2 = new Tab2();
            return tab2;
        } else if (position == 2) {
            Tab2 tab2 = new Tab2();
            return tab2;
        } else if (position == 3) {
            Tab2 tab2 = new Tab2();
            return tab2;
        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
