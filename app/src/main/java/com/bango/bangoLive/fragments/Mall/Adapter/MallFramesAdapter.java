package com.bango.bangoLive.fragments.Mall.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;
import com.bango.bangoLive.databinding.ItemFramesBinding;
import com.bango.bangoLive.fragments.Mall.Model.RootFramesAll;
import com.bango.bangoLive.fragments.Mall.Model.TotalCoinModal;
import com.bango.bangoLive.utils.CommonUtils;
//import com.opensource.svgaplayer.SVGAImageView;
//import com.opensource.svgaplayer.SVGAParser;

import java.util.List;

public class MallFramesAdapter extends RecyclerView.Adapter<MallFramesAdapter.Holder> {

    private List<RootFramesAll.Detail> list;
    public static TotalCoinModal coinModal ;
    Context context;
    Callback callback;
//    SVGAParser parser;

    public MallFramesAdapter(List<RootFramesAll.Detail> list, Context context, Callback callback) {
        this.list = list;
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_frames, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {

        /************************** SET TEXT PURCHASE/BUY **************************/
        if (list.get(position).getPurchasedType().equalsIgnoreCase(String.valueOf(true))) {
            holder.binding.buy.setText("Purchased");
        }else {
            holder.binding.buy.setText("buy");
        }

        /************************** PURCHASE FRAME **************************/
        holder.binding.purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.purchaseFrame(list.get(position),holder.binding.buy);
                }
            });

        /************************** TRY NEW FRAME **************************/
        holder.binding.tryNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.TryFrames(list.get(position));
            }
        });

        /************************** SET DATA **************************/
        holder.binding.themetime.setText(list.get(position).getValidity() + " Days");
        holder.binding.FramePrice.setText(list.get(position).getPrice());
        CommonUtils.setAnimation(context,list.get(position).getImage(),holder.binding.framesImg);
    }

    /************************** LOAD DATA **************************/
    public void loadData(List<RootFramesAll.Detail> ListData) {
        list = ListData;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
         ItemFramesBinding binding;
        public Holder(@NonNull View itemView) {
            super(itemView);
            binding=ItemFramesBinding.bind(itemView);
        }
    }

    /************************** INTERFACE CALLBACK **************************/
    public interface Callback {
        void TryFrames(RootFramesAll.Detail detail);
        void purchaseFrame(RootFramesAll.Detail detail, TextView buy );
    }
}
