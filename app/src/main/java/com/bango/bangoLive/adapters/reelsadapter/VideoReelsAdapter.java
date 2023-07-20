package com.bango.bangoLive.adapters.reelsadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;

public class VideoReelsAdapter extends RecyclerView.Adapter<VideoReelsAdapter.holder> {
    Context context;

    public VideoReelsAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public VideoReelsAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.reelsdesign,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoReelsAdapter.holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class holder extends RecyclerView.ViewHolder {
        public holder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
