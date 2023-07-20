package com.bango.bangoLive.fragments.reels;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bango.bangoLive.R;
import com.bango.bangoLive.databinding.FragmentReelsBinding;

public class Reels_Fragment extends Fragment {

    FragmentReelsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentReelsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onClick();
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.reelsFrameLayout,new FollowersFragment()).commit();
    }
    private void onClick() {
        binding.layoutFollowers.setOnClickListener(view1 -> {
            // textFollowers
            binding.textFollowers.setTextSize(16);
            binding.textFollowers.setTextColor(Color.parseColor("#8E0AAD"));
            binding.textFollowers.setShadowLayer(2, 1, 1, R.color.shade_color);
            binding.followersViewLine.setVisibility(View.VISIBLE);
            // textFollowing
            binding.textFollowing.setShadowLayer(0, 0, 0, 0);
            binding.textFollowing.setTextSize(14);
            binding.textFollowing.setTextColor(Color.parseColor("#979797"));
            binding.followingViewLine.setVisibility(View.INVISIBLE);
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.reelsFrameLayout,new FollowersFragment()).commit();
        });

        binding.followingsLayout.setOnClickListener(view1 -> {
            // textFollowing
            binding.textFollowing.setTextSize(16);
            binding.textFollowing.setTextColor(Color.parseColor("#8E0AAD"));
            binding.textFollowing.setShadowLayer(2, 1, 1, R.color.shade_color);
            binding.followingViewLine.setVisibility(View.VISIBLE);
            // textFollowers
            binding.textFollowers.setShadowLayer(0, 0, 0, 0);
            binding.textFollowers.setTextSize(14);
            binding.textFollowers.setTextColor(Color.parseColor("#979797"));
            binding.followersViewLine.setVisibility(View.INVISIBLE);
        });
    }
}