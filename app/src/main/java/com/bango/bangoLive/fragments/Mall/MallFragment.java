package com.bango.bangoLive.fragments.Mall;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.bango.bangoLive.R;
import com.bango.bangoLive.databinding.FragmentMallBinding;
import com.google.android.material.tabs.TabLayout;

public class MallFragment extends Fragment {

    FragmentMallBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentMallBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Set Full Screen Background Window
        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setTabMall(view);
    }

    /************************** SET TABLAYOUT MALL **************************/
    private void setTabMall(View view) {

        binding.tabMall.addTab(binding.tabMall.newTab().setText("Frames"));
        binding.tabMall.addTab(binding.tabMall.newTab().setText("Entry Frames"));

        final PagerAdapter pagerAdapter = new Adapter_mall(getChildFragmentManager(), binding.tabMall.getTabCount());
        binding.viewpagerMall.setAdapter(pagerAdapter);
        binding.viewpagerMall.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabMall));
        binding.tabMall.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(binding.viewpagerMall));

        binding.tabMall.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    /************************** ADAPTER MALL CLASS **************************/
    public static class Adapter_mall extends FragmentStatePagerAdapter {
        private final int totalTabs;
        public Adapter_mall(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
            totalTabs = behavior;
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Fragment_Frames();
                case 1:
                    return new EntryFragment();
                default:
                    return null;
            }
        }
        @Override
        public int getCount() {
            return totalTabs;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        /************************** CLEAR FULL SCREEN BACKGROUND WINDOW **************************/
        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    public void onResume() {
        super.onResume();
        View view1 = requireActivity().findViewById(R.id.bottomNavigation);
        view1.setVisibility(View.GONE);
    }
}