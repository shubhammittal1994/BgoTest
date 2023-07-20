package com.bango.bangoLive.fragments.LeaderBoard.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;
import com.bango.bangoLive.fragments.LeaderBoard.ModelClassses.TopGifterModel;
import com.bumptech.glide.Glide;

//import com.opensource.svgaplayer.SVGAImageView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_Weekly extends RecyclerView.Adapter<Adapter_Weekly.ViewHolder> {
    private List<TopGifterModel.Detail> list;
    Context context;

    public Adapter_Weekly(List<TopGifterModel.Detail> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leadboard, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getUserInfo().getImage()).placeholder(R.drawable.profilemaleicon).into(holder.img_contributer);


        if (list.get(position).getUserInfo().getName()!=null){
            if (list.get(position).getUserInfo().getName().equalsIgnoreCase("")) {
                holder.txtUsername.setText(list.get(position).getUserInfo().getName());
            } else {
                holder.txtUsername.setText(list.get(position).getUserInfo().getName());
            }
        }
        if (list.get(position).getUserInfo().getMoredetails().getFrame_details()!=null){
//            holder.profieFrame2.setVisibility(View.VISIBLE);
//            CommonUtils.setAnimation(holder.profieFrame2.getContext(),list.get(position).getUserInfo().getMoredetails().getFrame_details(),holder.profieFrame2);
        }
        if (list.get(position).getCoin() != null) {
            if (list.get(position).getCoin().contains(".")) {
                holder.txtCoins.setText(list.get(position).getCoin().substring(0, list.get(position).getCoin().indexOf(".")));
            } else {
                holder.txtCoins.setText(list.get(position).getCoin());
            }
        }
        holder.txtNumber.setText(""+(4+position));

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtUsername, txtCoins, txtNumber;
        CircleImageView img_contributer;
//        SVGAImageView profieFrame2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUsername = itemView.findViewById(R.id.txtName);
//            profieFrame2 = itemView.findViewById(R.id.profieFrame2);
            img_contributer = itemView.findViewById(R.id.personImge);
            txtNumber = itemView.findViewById(R.id.txtNumber);
            txtCoins = itemView.findViewById(R.id.txtCoin);
        }
    }
}
