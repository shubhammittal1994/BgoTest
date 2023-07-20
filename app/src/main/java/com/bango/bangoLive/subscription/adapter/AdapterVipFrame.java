package com.bango.bangoLive.subscription.adapter;

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
import com.bango.bangoLive.subscription.model.VipFrameRoot;
import com.bango.bangoLive.utils.CommonUtils;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;

import java.util.List;

public class AdapterVipFrame extends RecyclerView.Adapter<AdapterVipFrame.Holder> {

    private List<VipFrameRoot.Details> list;
    Context context;
    Callback callback;
    SVGAParser parser;

    public AdapterVipFrame(List<VipFrameRoot.Details> list, Context context, Callback callback) {
        this.list = list;
        this.context = context;
        this.callback = callback;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subscribed, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {
//        if (list.get(position).getApplied().equals(true))
//        {holder.applyed.setText("Applied");
//        }else{
//            holder.applyed.setText("Apply");
//        }

        holder.framePrice.setText(list.get(position).getPrice());
//        Glide.with()/

        CommonUtils.setAnimation(context,list.get(position).getVipIconImage(),holder.frameImage);

        holder.applied_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.Frame(list.get(position),holder.applyed);
            }
        });

        holder.tryNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.TryFrame(list.get(position));
            }
        });

        setDays(position);


        // Daybetween(startDate,endDate,pattern);
//        holder.Validity.setText("Days "+list.get(position).getVipFrames().get(3).getValidity());



    }

    private void setDays(int position) {
    }

    public void loadData(List<VipFrameRoot.Details> ListData) {
        list = ListData;

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        RelativeLayout tryNew, applied_ly;
        SVGAImageView frameImage;
        RelativeLayout ApplyFrme;

        TextView Validity, framePrice ,applyed;
        public Holder(@NonNull View itemView) {
            super(itemView);
            frameImage = itemView.findViewById(R.id.framesImg);
            applyed = itemView.findViewById(R.id.applyed);
            applied_ly = itemView.findViewById(R.id.applied_lay);
            framePrice = itemView.findViewById(R.id.Frame_price);
            tryNew = itemView.findViewById(R.id.tryNew);
            Validity = itemView.findViewById(R.id.themetime);

        }
    }

    public interface Callback {
        void TryFrame(VipFrameRoot.Details detail);

        void Frame(VipFrameRoot.Details detail, TextView buy );
    }
}
