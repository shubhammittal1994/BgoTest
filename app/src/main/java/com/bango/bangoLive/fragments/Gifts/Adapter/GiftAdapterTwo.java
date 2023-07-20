package com.bango.bangoLive.fragments.Gifts.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;
import com.bumptech.glide.Glide;
import com.bango.bangoLive.fragments.Gifts.Model.GiftModel;
import com.bango.bangoLive.utils.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GiftAdapterTwo extends RecyclerView.Adapter<GiftAdapterTwo.ViewHolder> {

    private Context context;
    List<GiftModel.Gift> list;
    Click click;
    int check = -1;

    public GiftAdapterTwo(Context context, List<GiftModel.Gift> list, Click click) {
        this.context = context;
        this.list = list;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_coins, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_giftname.setText(list.get(position).getTitle());
      //  holder.textView.setText(CommonUtils.prettyCount(Long.parseLong(list.get(position).getPrimeAccount())));
        holder.textView.setText(list.get(position).getPrimeAccount());
        Glide.with(context).load(list.get(position).getThumbnail()).into(holder.imageView);
        if (check == position) {
            holder.view.setVisibility(View.VISIBLE);
            holder.linearLayout.setBackgroundResource(R.drawable.bg_gifts);
        } else {
            holder.view.setVisibility(View.GONE);
            holder.linearLayout.setBackgroundResource(0);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView, tv_giftname;
        LinearLayout linearLayout;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.viewgift);
            linearLayout = itemView.findViewById(R.id.ll_giftview);
            tv_giftname = itemView.findViewById(R.id.tv_giftname);
             imageView = itemView.findViewById(R.id.iv_giftImage);
            textView = itemView.findViewById(R.id.tv_giftCoin);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check = getAdapterPosition();
                    click.OnClick(getLayoutPosition(), textView.getText().toString().trim(), "", list.get(getLayoutPosition()).getPrimeAccount(), list.get(getLayoutPosition()).getId(), list.get(getLayoutPosition()).getImage(), "", list.get(getLayoutPosition()).getTitle(), list.get(getLayoutPosition()).getThumbnail());
                    notifyDataSetChanged();
                }
            });
        }
    }

    public interface Click {
        void OnClick(int position, String balance, String soundId, String price, String id, String giftImage, String gifttime, String giftname, String thumbnail);
    }
}
