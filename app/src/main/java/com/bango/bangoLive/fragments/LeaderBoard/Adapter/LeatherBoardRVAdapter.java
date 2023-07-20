package com.bango.bangoLive.fragments.LeaderBoard.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;
import com.bango.bangoLive.fragments.LeaderBoard.ModelClassses.TopHostScreenModel;
import com.bumptech.glide.Glide;
//import com.opensource.svgaplayer.SVGAImageView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class LeatherBoardRVAdapter extends RecyclerView.Adapter<LeatherBoardRVAdapter.ViewHolder> {

    private List<TopHostScreenModel.Detail> list;
    Context context;

    public LeatherBoardRVAdapter(List<TopHostScreenModel.Detail> list, Context context) {
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

        Log.d("TAG", "list: "+list.toString());
        Glide.with(context).load(list.get(position).getImage()).placeholder(R.drawable.profilemaleicon).into(holder.img_contributer);
        if (list.get(position).getName()!=null)
        {
            if (list.get(position).getName().equalsIgnoreCase("")) {
                holder.txtUsername.setText(list.get(position).getUsername());
            } else {
                holder.txtUsername.setText(list.get(position).getName());
            }
        }



        if (list.get(position).getMoredetails().getFrameDetails()!=null){
//            holder.profieFrame2.setVisibility(View.VISIBLE);
//            CommonUtils.setAnimation(holder.profieFrame2.getContext(),list.get(position).getMoredetails().getFrameDetails(),holder.profieFrame2);
        }
        if (list.get(position).getCoin() != null) {
            if (list.get(position).getCoin().contains(".")) {
                holder.txtCoins.setText(list.get(position).getGiftdetails());
                        //.substring(0, list.get(position).getGiftdetails().indexOf(".")));
            } else {
                holder.txtCoins.setText(list.get(position).getGiftdetails());
            }
        }
        holder.txtNumber.setText(""+(4+position));


    }

    public void loadData(List<TopHostScreenModel.Detail> ListData) {
        list = ListData;

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userIdText, txtUsername, txtCoins, txtNumber;
        CircleImageView img_contributer;
//        SVGAImageView profieFrame2;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            userIdText = itemView.findViewById(R.id.userIdText);
            txtUsername = itemView.findViewById(R.id.txtName);
//            profieFrame2 = itemView.findViewById(R.id.profieFrame2);
            img_contributer = itemView.findViewById(R.id.personImge);
            txtNumber = itemView.findViewById(R.id.txtNumber);
            txtCoins = itemView.findViewById(R.id.txtCoin);
        }
    }
}

