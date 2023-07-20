package com.bango.bangoLive.fragments.Search.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;
import com.bango.bangoLive.fragments.Search.modelClass.RootReoprt;

import java.util.List;

public class Adapter_report extends RecyclerView.Adapter<Adapter_report.ViewHolder> {
    private List<RootReoprt.Detail> list;
    Context context;
    CallBack callBack;

    public Adapter_report(List<RootReoprt.Detail> list, Context context, CallBack callBack) {
        this.list = list;
        this.context = context;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repotable,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
    holder.reportItem.setText(list.get(position).getReportAction());

    holder.reportItem.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            callBack.reportProfile(list.get(position));
        }
    });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView reportItem ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reportItem =itemView.findViewById(R.id.report_item);
        }
    }

    public  interface CallBack {
         void reportProfile(RootReoprt.Detail  detail);
    }
}
