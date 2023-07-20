package com.bango.bangoLive.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.AudioRoom.AudioRoomModelClass.GetLiveHostListAudioModelClass;
import com.bango.bangoLive.R;
import com.bango.bangoLive.databinding.PopularliveUserlayoutdesignBinding;
import com.bumptech.glide.Glide;
import com.google.android.material.shape.CornerFamily;

import java.util.List;

public class LiveUsersAdapter extends RecyclerView.Adapter<LiveUsersAdapter.holder> {
    Context context;
    List<GetLiveHostListAudioModelClass.Detail> list;
    Callback callback;
    int lastPosition =-1;
    int cornerFamily= CornerFamily.ROUNDED;

    public LiveUsersAdapter(Context context, List<GetLiveHostListAudioModelClass.Detail> list, Callback callback) {
        this.context = context;
        this.list = list;
        this.callback = callback;
    }

    public interface Callback{
        void onClickCallback(GetLiveHostListAudioModelClass.Detail getLiveHostListAudioModelClass);
    }

    @NonNull
    @Override
    public LiveUsersAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popularlive_userlayoutdesign,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull LiveUsersAdapter.holder holder, int position) {

        // Set Animation
        if (holder.getAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = position;
        } else {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = position;
        }

        holder.binding.nameText.setText(list.get(position).getLiveTitle());
        Glide.with(context).load(list.get(position).getPosterImage()).placeholder(R.drawable.profilemaleicon).into(holder.binding.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClickCallback(list.get(position));
            }
        });

        // Set Backgoud
        if (position%2==0){
            holder.binding.imageView.setShapeAppearanceModel(holder.binding.imageView.getShapeAppearanceModel().toBuilder()
                    .setTopLeftCorner(cornerFamily,55)
                    .setTopRightCorner(cornerFamily,10)
                    .setBottomLeftCorner(cornerFamily,10)
                    .setBottomRightCorner(cornerFamily,55)
                    .build());
        }else {
            holder.binding.imageView.setShapeAppearanceModel(holder.binding.imageView.getShapeAppearanceModel().toBuilder()
                    .setTopLeftCorner(cornerFamily,10)
                    .setTopRightCorner(cornerFamily,55)
                    .setBottomLeftCorner(cornerFamily,55)
                    .setBottomRightCorner(cornerFamily,10)
                    .build());
        }
    }
    @Override
    public int getItemCount() {
        return list.size()>0 ? list.size() : 0;
    }

    public class holder extends RecyclerView.ViewHolder {
        PopularliveUserlayoutdesignBinding binding;
        public holder(@NonNull View itemView) {
            super(itemView);
            binding = PopularliveUserlayoutdesignBinding.bind(itemView);
        }
    }
}
