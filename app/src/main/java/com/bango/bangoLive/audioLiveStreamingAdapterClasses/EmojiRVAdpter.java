package com.bango.bangoLive.audioLiveStreamingAdapterClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;
import com.bango.bangoLive.fragments.Gifts.Model.GiftModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class EmojiRVAdpter extends RecyclerView.Adapter<EmojiRVAdpter.ViewHolder> {

    List<GiftModel.Gift> list = new ArrayList();
    Context context;
    Callback callback;

    public EmojiRVAdpter(List<GiftModel.Gift> list, Context context, Callback callback) {
        this.list = list;
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.emoji_rv_design,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(holder.emojiImg.getContext()).load(list.get(position).getImage()).error(R.drawable.emoji_24).into(holder.emojiImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.callback(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView emojiImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            emojiImg=itemView.findViewById(R.id.emojiImg);
        }
    }
    public interface Callback{
        void callback(GiftModel.Gift detail);
    }
}
