package com.bango.bangoLive.fragments.TransactionHistory.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;
import com.bango.bangoLive.fragments.TransactionHistory.MODEL.CardDetails;

import java.util.List;

public class AdapterTrnsection extends RecyclerView.Adapter<AdapterTrnsection.ViewHolder> {
    List<CardDetails.Detail> details;
    public AdapterTrnsection(List<CardDetails.Detail> details) {
        this.details = details;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.coin_history_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.source.setText("OrderId : " +details.get(position).getOrderId());
        holder.date.setText("Tr. Id : " +details.get(position).getTransactionId());
        holder.amount.setText(details.get(position).getCoins());
        if (details.get(position).getStatus().equals("0")){
            holder.profile_image3.setImageResource(R.drawable.receivearroww);
        }else {
            holder.profile_image3.setImageResource(R.drawable.sendarrow);
        }

    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView source,date,amount;
        private ImageView profile_image3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            source = itemView.findViewById(R.id.source);
            amount = itemView.findViewById(R.id.amount);
            date = itemView.findViewById(R.id.date);
            profile_image3 = itemView.findViewById(R.id.profile_image3);

        }
    }
}
