package com.bango.bangoLive.fragments.Gifts.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;
import com.bumptech.glide.Glide;
import com.bango.bangoLive.fragments.Gifts.Model.AlgorithmItem;

import java.util.List;

public class AdapterCustomSpinner extends RecyclerView.Adapter<AdapterCustomSpinner.ViewHolder> {

    Context context;
    List<AlgorithmItem> dataGift;
    private int currentPositio = 0;
    private int currentPositioTwo = 0;
    private Click click;


    public AdapterCustomSpinner(Context requireContext, List<AlgorithmItem> dataGift, int currentPositioTwo, Click click) {
        this.dataGift = dataGift;
        this.context = requireContext;
        this.currentPositioTwo = currentPositioTwo;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_spiiner_gift, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        if (currentPositioTwo == 0) {
            holder.rlTop.setBackground(context.getResources().getDrawable(R.drawable.bg_btn));
            Glide.with(holder.image_view).load(dataGift.get(position).getUserImage()).placeholder(R.drawable.profilemaleicon).into(holder.image_view);


        } else {
            holder.rlTop.setBackground(context.getResources().getDrawable(R.drawable.bg_btn));
            if (currentPositio == position) {

                holder.rlTop.setBackground(context.getResources().getDrawable(R.drawable.bg_btn));

            } else {

                holder.text_view.setText(String.valueOf(position));
                holder.rlTop.setBackground(context.getResources().getDrawable(R.drawable.profilemaleicon));
            }
            Glide.with(holder.image_view).load(dataGift.get(position).getUserImage()).placeholder(R.drawable.profilemaleicon).into(holder.image_view);

            holder.itemView.setOnClickListener(view -> {


                currentPositio = position;
                notifyDataSetChanged();
                click.getData(dataGift.get(position));
            });

            if (position == 0) {
                holder.text_view_image.setVisibility(View.VISIBLE);
                holder.text_view.setVisibility(View.GONE);
            } else {
                holder.text_view_image.setVisibility(View.GONE);
                holder.text_view.setVisibility(View.VISIBLE);
            }

        }


    }

    @Override
    public int getItemCount() {
        return dataGift.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_view, text_view_image;
        private RelativeLayout rlTop;
        private TextView text_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image_view = itemView.findViewById(R.id.image_view);
            text_view_image = itemView.findViewById(R.id.text_view_image);
            text_view = itemView.findViewById(R.id.text_view);
            rlTop = itemView.findViewById(R.id.rlTop);

        }
    }

    public interface Click {
        void getData(AlgorithmItem data);
    }
}

