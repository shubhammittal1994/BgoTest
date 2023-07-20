package com.bango.bangoLive.themes.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.bango.bangoLive.themes.LiveFreeThemeFragment;
import com.bango.bangoLive.themes.LivePurchasedThemeFragment;

public class ThemeTabLayoutAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public ThemeTabLayoutAdapter(@NonNull FragmentManager fm, Context myContext, int totalTabs) {
        super(fm);
        this.myContext = myContext;
        this.totalTabs = totalTabs;
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                LiveFreeThemeFragment liveFreeThemeFragment = new LiveFreeThemeFragment();
                return liveFreeThemeFragment;
            case 1:
                LivePurchasedThemeFragment livePurchasedThemeFragment = new LivePurchasedThemeFragment();
                return livePurchasedThemeFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
