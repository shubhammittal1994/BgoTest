package com.bango.bangoLive.fragments.Search.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;
import com.bango.bangoLive.databinding.SearchUserListBinding;
import com.bango.bangoLive.fragments.Search.modelClass.SearchUserDetail;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.Holder> {

    private final callBackFromSearchUserAdapter callBackFromSearchUserAdapter;
    private List<SearchUserDetail> list;
    private Context context;

    public SearchUserAdapter(callBackFromSearchUserAdapter callBackFromSearchUserAdapter, List<SearchUserDetail> list, Context context) {
        this.callBackFromSearchUserAdapter = callBackFromSearchUserAdapter;
        this.list = list;
        this.context = context;
    }

    public interface callBackFromSearchUserAdapter {
        void submit(SearchUserDetail userDetail);
    }

    @NonNull
    @NotNull
    @Override
    public Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_user_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Holder holder, @SuppressLint("RecyclerView") int position) {


            Glide.with(context).load(list.get(position).getImage()).placeholder(R.drawable.profilemaleicon).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                    holder.forgot_loading.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                    holder.forgot_loading.setVisibility(View.GONE);

                    return false;

                }
            }).into(holder.binding.userProfile);


        if (list.get(position).getUsername() != null && list.get(position).getUsername().length() != 0) {
            holder.binding.userId.setText(list.get(position).getUsername());
        }

        if (list.get(position).getName() != null && list.get(position).getName().length() != 0) {
            holder.binding.userName.setText(list.get(position).getName());
        }else {
            holder.binding.userName.setText(list.get(position).getUsername());
        }
        holder.binding.userLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBackFromSearchUserAdapter != null) {
                    callBackFromSearchUserAdapter.submit(list.get(position));
                }

            }
        });
    }

    public void loadData(List<SearchUserDetail> list) {

        this.list = list;
        notifyDataSetChanged();

    }

    public void filterList(List<SearchUserDetail> list) {
        this.list = list;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return (list != null && list.size() != 0 ? list.size() : 0);

    }

    public static class Holder extends RecyclerView.ViewHolder {

        SearchUserListBinding binding;

        public Holder(@NonNull @NotNull View itemView) {
            super(itemView);
            binding = SearchUserListBinding.bind(itemView);
        }
    }
}