package com.bango.bangoLive.subscription;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bango.bangoLive.R;
import com.bango.bangoLive.application.App;
import com.google.android.material.tabs.TabLayout;


public class Fragment_MySubscription extends Fragment {

    private TabLayout tablayout;
    private ViewPager viewPager;
    View view;


    public Fragment_MySubscription() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment__my_subscription, container, false);

        findId(view);
        setTabMall(view);
        onClick(view);
        return view;
    }

    private void onClick(View view) {


        view.findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });
    }

    private void setTabMall(View view) {
        if (App.getSharedpref().getString("adminStatus").equalsIgnoreCase("0")) {
            tablayout.addTab(tablayout.newTab().setText("Purchased"));
            tablayout.addTab(tablayout.newTab().setText("VIP Frame"));
            tablayout.addTab(tablayout.newTab().setText("Entry Frame"));

        } else {
            tablayout.addTab(tablayout.newTab().setText("Purchased"));
            tablayout.addTab(tablayout.newTab().setText("VIP Frame"));
            tablayout.addTab(tablayout.newTab().setText("Entry Frame"));
            tablayout.addTab(tablayout.newTab().setText("AdminFrame"));
        }

        final PagerAdapter pagerAdapter = new Adapter_sub(getChildFragmentManager(), tablayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    public static class Adapter_sub extends FragmentStatePagerAdapter {
        private final int totalTabs;


        public Adapter_sub(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
            totalTabs = behavior;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if (App.getSharedpref().getString("adminStatus").equalsIgnoreCase("0")) {

                switch (position) {


                    case 0:

                        return new Fragment_Subscribed();
                    case 1:

                       return new Fragment_Vip_Frame();

                    case 2:

                        return new MyEntryEffectFragment();


                    default:
                        return null;
                }
            } else {
                switch (position) {


                    case 0:

                        return new Fragment_Subscribed();
                    case 1:

                       return new Fragment_Vip_Frame();


                    case 2:

                        return new MyEntryEffectFragment();

                    case 3:

                       // return new OtherFrameFragment();

                    default:
                        return null;
                }
            }


        }

        @Override
        public int getCount() {
            if (App.getSharedpref().getString("adminStatus").equalsIgnoreCase("0")) {


            } else {

            }
            return totalTabs;
        }
    }


    private void findId(View view) {

        tablayout = view.findViewById(R.id.tab_mySub);
        viewPager = view.findViewById(R.id.viewpager_mySub);
    }

}