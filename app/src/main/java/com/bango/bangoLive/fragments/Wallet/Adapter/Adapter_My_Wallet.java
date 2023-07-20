package com.bango.bangoLive.fragments.Wallet.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;
import com.bango.bangoLive.fragments.Wallet.Model.Root_My_Wallet;

import java.util.List;

public class Adapter_My_Wallet extends RecyclerView.Adapter<Adapter_My_Wallet.ViewHolder> {

    private List<Root_My_Wallet.Detail> list;
    Context context;
    Callback callback;

    public Adapter_My_Wallet(List<Root_My_Wallet.Detail> list, Context context, Callback callback) {
        this.list = list;
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_wallet, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (list != null) {
            holder.total_coin.setText(list.get(position).getCoins());
            holder.coinPrice.setText("$ " + Integer.parseInt(list.get(position).getDollars()) / 100);
        } else {
            holder.total_coin.setText("0");
            holder.coinPrice.setText("$0");
        }

        holder.buttonPay.setOnClickListener(view ->
                callback.report(list.get(position))
        );
    }

    public void loadData(List<Root_My_Wallet.Detail> ListData) {
        list = ListData;

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView total_coin, coinPrice;
        RelativeLayout buttonPay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            total_coin = itemView.findViewById(R.id.coin_total);
            coinPrice = itemView.findViewById(R.id.coin_price);
            buttonPay = itemView.findViewById(R.id.buttonPay);
        }
    }

    public interface Callback {
        void report(Root_My_Wallet.Detail rootMyWallet);
    }
}
