package com.bango.bangoLive.fragments.LeaderBoard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bango.bangoLive.R;
import com.bango.bangoLive.fragments.LeaderBoard.ModelClassses.GiftTopGifters;
import com.google.android.material.tabs.TabLayout;

import java.util.List;


public class LeadtherboardFragment extends Fragment {
    private RecyclerView LeaderboardRV;
    List<GiftTopGifters.Detail> amount;
    private TabLayout tablayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leadtherboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findId(view);
        setTabMall(view);


        view.findViewById(R.id.img_back_ranking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });

        view.findViewById(R.id.leaderboardTV).setOnClickListener(view1 -> {
//            getActivity().getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.home_fragment_Container, new WalletFragments()).addToBackStack(null).commit();
        });


    }

    private void setTabMall(View view) {

        tablayout.addTab(tablayout.newTab().setText("Host"));
        tablayout.addTab(tablayout.newTab().setText("Gifter"));


        final PagerAdapter pagerAdapter = new Adapter_LeadBoard(getChildFragmentManager(), tablayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    public static class Adapter_LeadBoard extends FragmentStatePagerAdapter {
        private final int totalTabs;

        public Adapter_LeadBoard(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
            totalTabs = behavior;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Fragment_Daily();
                case 1:
                    return new Fragment_weeKly();

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return totalTabs;
        }
    }

    private void findId(View view) {
        tablayout = view.findViewById(R.id.tab_leadboard);
        viewPager = view.findViewById(R.id.viewpager_leadboard);
    }

    @Override
    public void onResume() {
        super.onResume();
        View view1 = requireActivity().findViewById(R.id.bottomNavigation);
        view1.setVisibility(View.GONE);
    }
}