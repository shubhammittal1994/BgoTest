package com.bango.bangoLive.adapters.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;

public class RecentChatAdapter extends RecyclerView.Adapter<RecentChatAdapter.holder> {
    @NonNull
    @Override
    public RecentChatAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recentchatsdesign,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecentChatAdapter.holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class holder extends RecyclerView.ViewHolder {
        public holder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
