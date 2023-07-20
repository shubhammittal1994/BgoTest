package com.bango.bangoLive.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;
import com.bango.bangoLive.room.MusicTable;
import com.bango.bangoLive.room.AppDatabase;
import com.bango.bangoLive.room.Music;
import com.bango.bangoLive.utils.CommonUtils;

import java.util.List;

public class LocalAddedAdapter extends RecyclerView.Adapter<LocalAddedAdapter.ViewHolder> {
    List<Music> list;
    Context context;

    AppDatabase appDatabase;

    public LocalAddedAdapter(List<Music> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.local_added_rv_design, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        appDatabase = AppDatabase.getInstance(context);

        if (appDatabase.userDao().isExist(Integer.parseInt(list.get(position).getId()))) {
            holder.localAddedBtn.setText("Added");
        } else {
            holder.localAddedBtn.setText("Add");
        }

        holder.songNameTv.setText(list.get(position).getTitle());
        holder.songTimeAndWebsiteTv.setText(CommonUtils.formatDuration(list.get(position).getDuration()));

        holder.localAddedBtn.setVisibility(View.VISIBLE);
        holder.playMusicImg.setVisibility(View.GONE);

        holder.localAddedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.localAddedBtn.getText().toString().equalsIgnoreCase("Add")) {

                    holder.localAddedBtn.setText("Added");
                    MusicTable musicTable = new MusicTable(Integer.parseInt(list.get(position).getId()),
                            list.get(position).getTitle(),
                            list.get(position).getAlbum(),
                            list.get(position).isStatus(),
                            list.get(position).getPath(),
                            list.get(position).getDuration(),
                            list.get(position).getArtUri(),
                            list.get(position).getArtist());
                    appDatabase.userDao().insert(musicTable);

                    notifyDataSetChanged();

                } else {holder.localAddedBtn.setText("Add");

                    appDatabase.userDao().deleteById(Integer.parseInt(list.get(position).getId()));
                    notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView songNameTv, songTimeAndWebsiteTv;
        ImageView playMusicImg;
        AppCompatButton localAddedBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            songNameTv = itemView.findViewById(R.id.localsongNameTv);
            songTimeAndWebsiteTv = itemView.findViewById(R.id.localsongTimeAndWebsiteTv);
            playMusicImg = itemView.findViewById(R.id.localplayMusicImg);
            localAddedBtn = itemView.findViewById(R.id.locallocalAddedBtn);
        }
    }

}
