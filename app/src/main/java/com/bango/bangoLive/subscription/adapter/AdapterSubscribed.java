package com.bango.bangoLive.subscription.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;
import com.bango.bangoLive.fragments.Mall.ProfileFrame.RootFrames;
import com.bango.bangoLive.utils.CommonUtils;
import com.opensource.svgaplayer.SVGAImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class AdapterSubscribed extends RecyclerView.Adapter<AdapterSubscribed.Holder> {

    private List<RootFrames.Detail> list;
    Context context;
    Callback callback;


    public AdapterSubscribed(List<RootFrames.Detail> list, Context context, Callback callback) {
        this.list = list;
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subscribed, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {

//        if (list.get(position).getApplied().equals(true)) {
//            holder.applyed.setText("Applied");
//        } else {
//            holder.applyed.setText("Apply");
//        }
        Toast.makeText(context, ""+list.get(position).getFrameIMage(), Toast.LENGTH_SHORT).show();

        holder.framePrice.setText(list.get(position).getPrice());
        CommonUtils.setAnimation(context, list.get(position).getFrameIMage(), holder.frameImage);

        holder.applied_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.Frame(list.get(position), holder.applyed);
            }
        });

        holder.tryNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.TryFrame(list.get(position));
            }
        });

        setDays(position);
        LocalDate startDate = LocalDate.parse(list.get(position).getDateFrom());
        LocalDate endDate = LocalDate.parse(list.get(position).getDateTo());
        String pattern = null;
        LocalDate eDate = endDate;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            eDate = LocalDate.now(ZoneId.of("Europe/Paris"));
        }
        LocalDate sDate = startDate;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //  sDate = eDate.minusDays(127);
        }

        String daysBetween;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Date secondDate = null;
        Date firstDate = null;
        try {
            secondDate = sdf.parse(list.get(position).getDateTo());
            firstDate = sdf.parse(list.get(position).getDateFrom());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        daysBetween = String.valueOf(TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS));


        // Daybetween(startDate,endDate,pattern);
        holder.Validity.setText("Days " + daysBetween);

    }

    private void setDays(int position) {
    }


    public void loadData(List<RootFrames.Detail> ListData) {
        list = ListData;

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        RelativeLayout tryNew, applied_ly;
        SVGAImageView frameImage;
        TextView Validity, framePrice, applyed;

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
        void TryFrame(RootFrames.Detail detail);

        void Frame(RootFrames.Detail detail, TextView buy);
    }
}
