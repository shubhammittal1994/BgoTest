package com.bango.bangoLive.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bango.bangoLive.R;
import com.bango.bangoLive.room.MusicTable;
import com.bango.bangoLive.utils.CommonUtils;

import java.util.List;

public class MusicRVAdapter extends RecyclerView.Adapter<MusicRVAdapter.ViewHolder> {
    List<MusicTable> list;
    Context context;
    Callback callback;
    private int checkedPosition = -1;


    public MusicRVAdapter(List<MusicTable> list, Context context, Callback callback) {
        this.list = list;
        this.context = context;
        this.callback = callback;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.music_rv_design, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (checkedPosition == position) {
            callback.playMusic(list.get(checkedPosition), 1, holder.playMusicImg, false, checkedPosition);
            holder.songNameTv.setTextColor(Color.GREEN);
        } else {
            holder.songNameTv.setTextColor(Color.BLACK);
        }
        holder.songNameTv.setText(list.get(position).getTitle());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
                callback.deleteMusic(list.get(position),position );
                list.remove(list.get(position));
               // notifyDataSetChanged();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.songNameTv.setTextColor(Color.GREEN);
//                callback.playMusic(list.get(checkedPosition), 1, holder.playMusicImg, false, checkedPosition);

                checkedPosition = position;
                notifyDataSetChanged();

            }
        });

        holder.songTimeAndWebsiteTv.setText(CommonUtils.formatDuration(list.get(position).getDuration()));

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView songNameTv, songTimeAndWebsiteTv;
        ImageView playMusicImg,delete;
        LottieAnimationView musicLotttie;
        RelativeLayout musiclayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            songNameTv = itemView.findViewById(R.id.songNameTv);
            songTimeAndWebsiteTv = itemView.findViewById(R.id.songTimeAndWebsiteTv);
            playMusicImg = itemView.findViewById(R.id.playMusicImg);
            musicLotttie = itemView.findViewById(R.id.musicLotttie);
            musiclayout = itemView.findViewById(R.id.musiclayout);
            delete = itemView.findViewById(R.id.delete);

        }
    }

    public interface Callback {
        void playMusic(MusicTable musicDetail, int musicPlayStatus, ImageView imageView, boolean status, int postin);
        void deleteMusic(MusicTable musicDetail,int posn);
    }
}

