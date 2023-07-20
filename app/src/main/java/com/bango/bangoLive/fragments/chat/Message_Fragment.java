package com.bango.bangoLive.fragments.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bango.bangoLive.adapters.chat.RecentChatAdapter;
import com.bango.bangoLive.databinding.FragmentMessageBinding;

public class Message_Fragment extends Fragment {
    FragmentMessageBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentMessageBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecyclerInAdapter();
    }

    private void setRecyclerInAdapter() {
        RecentChatAdapter recentChatAdapter = new RecentChatAdapter();
        binding.recyclerView.setAdapter(recentChatAdapter);
    }
}