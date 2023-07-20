package com.bango.bangoLive.fragments.reels;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bango.bangoLive.adapters.reelsadapter.VideoReelsAdapter;
import com.bango.bangoLive.databinding.FragmentFollowersBinding;

public class FollowersFragment extends Fragment {

    FragmentFollowersBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentFollowersBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecyclerInAdapter();
    }

    private void setRecyclerInAdapter() {
        VideoReelsAdapter videoReelsAdapter = new VideoReelsAdapter(requireContext());
        binding.recyclerView.setAdapter(videoReelsAdapter);
    }
}