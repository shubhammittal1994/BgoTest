package com.bango.bangoLive.fragments.Gifts.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;
import com.bango.bangoLive.fragments.Gifts.Model.GiftCategoryModel;

import java.util.List;


public class AdapterGiftCategory extends RecyclerView.Adapter<AdapterGiftCategory.ViewHolder> {

    private Context context;
    private List<GiftCategoryModel.Detail> list;
    private Click click;
    private int viewposition = 0;

    public AdapterGiftCategory(Context context, List<GiftCategoryModel.Detail> list, Click click) {
        this.context = context;
        this.list = list;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_giftcategory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText(list.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewposition = position;
                click.onClick(list.get(position).getId(),list.get(position).getTitle());
                notifyDataSetChanged();
            }
        });
        if (viewposition == position) {
            holder.view.setVisibility(View.VISIBLE);
        } else {
            holder.view.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_giftcategory);
            view = itemView.findViewById(R.id.view);
        }
    }


    public interface Click {
        void onClick(String id,String data);
    }
}
