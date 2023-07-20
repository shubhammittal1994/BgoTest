package com.bango.bangoLive.fragments.TopGifters.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;
import com.bango.bangoLive.fragments.LeaderBoard.ModelClassses.TopGifterModel;
import com.bumptech.glide.Glide;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeadeBordOverAll extends RecyclerView.Adapter<LeadeBordOverAll.ViewHolder> {

    private List<TopGifterModel.Detail> amount;
    Context context;

    public LeadeBordOverAll(List<TopGifterModel.Detail> amount, Context context) {
        this.amount = amount;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_item_list, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        if (amount.get(position).getUserInfo() != null) {
            holder.mainLy.setVisibility(View.VISIBLE);
            holder.view.setVisibility(View.VISIBLE);

            if (amount.get(position).getUserInfo().getMyAppliedFrame() != null) {
//                CommonUtils.setAnimation(holder.itemView.getContext(), amount.get(position).getUserInfo().getMyAppliedFrame(), holder.profieFrame);
            }

            Glide.with(context).load(amount.get(position).getUserInfo().getImage()).placeholder(R.drawable.profilemaleicon).into(holder.img_contributer);
            if (amount.get(position).getUserInfo().getName() != null) {
                if (amount.get(position).getUserInfo().getName().equalsIgnoreCase("")) {
                    holder.txtUsername.setText("Name :  user@" + position);
                } else {
                    holder.txtUsername.setText("Name : " + amount.get(position).getUserInfo().getName());
                }
            }
            holder.txtNumber.setText("ID: " + amount.get(position).getUserInfo().getUsername());
            holder.txtCoins.setText(amount.get(position).getUserInfo().getTotalSendCoin());
        } else {
            holder.txtUsername.setTextColor(Color.RED);
            holder.txtUsername.setText("null");
            holder.mainLy.setVisibility(View.GONE);
            holder.view.setVisibility(View.GONE);

        }

        if (position == 0) {
            holder.crown.setImageResource(R.drawable.golden_crown);
        } else if (position == 2) {
            //holder.crown.setVisibility(View.VISIBLE);
            holder.crown.setImageResource(R.drawable.silver_crown);
        } else if (position == 3) {
            holder.crown.setImageResource(R.drawable.brown_crown);

        } else if (position == 4) {
            holder.countNumber.setText("4");
            holder.crown.setVisibility(View.GONE);
            holder.countNumber.setVisibility(View.VISIBLE);

        } else if (position == 5) {
            holder.countNumber.setText("5");
            holder.crown.setVisibility(View.GONE);
            holder.countNumber.setVisibility(View.VISIBLE);

        } else if (position == 6) {
            holder.countNumber.setText("6");
            holder.crown.setVisibility(View.GONE);
            holder.countNumber.setVisibility(View.VISIBLE);

        } else if (position == 7) {
            holder.countNumber.setText("7");
            holder.crown.setVisibility(View.GONE);
            holder.countNumber.setVisibility(View.VISIBLE);

        } else if (position == 8) {
            holder.countNumber.setText("8");
            holder.crown.setVisibility(View.GONE);
            holder.countNumber.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public int getItemCount() {
        return amount.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userIdText, txtUsername, txtCoins, txtNumber, countNumber;
        CircleImageView img_contributer;
        ImageView crown;
        LinearLayout mainLy;
        View view;
//        private SVGAImageView profieFrame;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            userIdText = itemView.findViewById(R.id.userIdText);
            txtUsername = itemView.findViewById(R.id.topGiftername);
//            profieFrame = itemView.findViewById(R.id.profieFrame);
            img_contributer = itemView.findViewById(R.id.gifterImage);
            txtNumber = itemView.findViewById(R.id.topGifterId);
            txtCoins = itemView.findViewById(R.id.coinTopGifter);
            mainLy = itemView.findViewById(R.id.mainLy);
            view = itemView.findViewById(R.id.view_);
            crown = itemView.findViewById(R.id.goldenMedal);
            countNumber = itemView.findViewById(R.id.counting);
        }
    }
}


