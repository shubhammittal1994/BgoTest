package com.bango.bangoLive.fragments.LeaderBoard.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;
import com.bango.bangoLive.fragments.LeaderBoard.ModelClassses.RootLeaderBoard;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_Monthly extends RecyclerView.Adapter<Adapter_Monthly.ViewHolder> {

    private List<RootLeaderBoard.Detail> list;
    Context context;

    public Adapter_Monthly(List<RootLeaderBoard.Detail> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leadboard , parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImage()).into(holder.img_contributer);
        holder.txtUsername.setText(list.get(position).getName());
        holder.txtCoins.setText(list.get(position).getCoin());
    }

    public void loadData(List<RootLeaderBoard.Detail> ListData) {
        list = ListData;

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userIdText, txtUsername, txtCoins, txtNumber;
        CircleImageView img_contributer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUsername = itemView.findViewById(R.id.txtName);
            img_contributer = itemView.findViewById(R.id.personImge);
            txtNumber = itemView.findViewById(R.id.txtNumber);
            txtCoins = itemView.findViewById(R.id.txtCoin);
        }
    }
}
