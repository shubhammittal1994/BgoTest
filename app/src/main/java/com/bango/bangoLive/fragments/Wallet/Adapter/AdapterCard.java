package com.bango.bangoLive.fragments.Wallet.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;
import com.bango.bangoLive.fragments.Wallet.Model.SavedCard;

import java.util.List;

public class AdapterCard extends RecyclerView.Adapter<AdapterCard.ViewHoldewr> {
    List<SavedCard.Detail> details;
    GetCardDetails getCardDetails;
    public AdapterCard(List<SavedCard.Detail> details, GetCardDetails getCardDetails) {
        this.details = details;
        this.getCardDetails = getCardDetails;
    }


    @NonNull
    @Override
    public ViewHoldewr onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHoldewr(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoldewr holder, int position) {
        holder.txtCardName.setText(details.get(position).getCardNumber());
        holder.txtExpiry.setText(details.get(position).getExpMonth()+"/"+details.get(position).getExpYear());
        if (details.get(position).getCardType().equalsIgnoreCase("visa")){
            holder.cardImage.setImageResource(R.drawable.master);
            holder.img1.setImageResource(R.drawable.visa_icon);
        }else {
            holder.cardImage.setImageResource(R.drawable.visa);
            holder.img1.setImageResource(R.drawable.mastercard);
        }


        holder.txtcvv.setText(details.get(position).getCvv());
        holder.itemView.setOnClickListener(view -> {
            getCardDetails.getCardDetails(details.get(position));
        });
        holder.img_cross.setOnClickListener(view -> {

        });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class ViewHoldewr extends RecyclerView.ViewHolder {
        private TextView txtCardName,txtExpiry,txtName,txtcvv;
        private ImageView cardImage,img1,img_cross;
        public ViewHoldewr(@NonNull View itemView) {
            super(itemView);

            txtcvv = itemView.findViewById(R.id.txt6);
            txtCardName = itemView.findViewById(R.id.txt1);
            txtExpiry = itemView.findViewById(R.id.txt4);
            txtName = itemView.findViewById(R.id.txt5);
            cardImage = itemView.findViewById(R.id.cardImage);
            img1 = itemView.findViewById(R.id.img1);
            img_cross = itemView.findViewById(R.id.img_cross);
        }
    }
    public  interface GetCardDetails{
        void getCardDetails(SavedCard.Detail detail);
        void remove(SavedCard.Detail detail);
    }
}
