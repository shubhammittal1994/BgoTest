package com.bango.bangoLive.themes.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.AudioRoom.MODEL.GetAudioLiveImages;
import com.bango.bangoLive.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterLiveThemes extends RecyclerView.Adapter<AdapterLiveThemes.Holder> {
    List<GetAudioLiveImages.Detail> list;
    Callback callback;

    public AdapterLiveThemes(List<GetAudioLiveImages.Detail> list, Callback callback) {
        this.list = list;
        this.callback = callback;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_themes, parent, false);
        Holder viewHolder = new Holder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(holder.theamImage.getContext()).load(list.get(position).getImage())
                        .error(R.drawable.maduridixit).into(holder.theamImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.setBackground(list.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView theamImage;

        public Holder(@NonNull View itemView) {
            super(itemView);
            theamImage = itemView.findViewById(R.id.theamImage);
        }
    }

    public interface Callback {
        void setBackground(GetAudioLiveImages.Detail list);
    }
}