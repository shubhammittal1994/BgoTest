package com.bango.bangoLive.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bango.bangoLive.R;
import com.bumptech.glide.Glide;
import com.bango.bangoLive.ModelClasses.Banner.BannerImagesModel;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapterExample extends SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {
    private Context context;
    private List<BannerImagesModel> mSliderItems = new ArrayList<>();
    private HyperLinkClick hyperLinkClick;

    public SliderAdapterExample(Context context, List<BannerImagesModel> mSliderItems, HyperLinkClick hyperLinkClick) {
        this.context = context;
        this.mSliderItems = mSliderItems;
        this.hyperLinkClick = hyperLinkClick;
    }

    public interface HyperLinkClick{
        void getHyperLink(BannerImagesModel bannerImagesModel);
    }

    public void renewItems(List<BannerImagesModel> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(BannerImagesModel bannerImagesModel) {
        this.mSliderItems.add(bannerImagesModel);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    public void loadData(List<BannerImagesModel> mSliderItems){
        this.mSliderItems=mSliderItems;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        Glide.with(viewHolder.itemView)
                .load(mSliderItems.get(position).getBanner())
                .fitCenter()
                .into(viewHolder.image);

        viewHolder.itemView.setOnClickListener(view -> {
            hyperLinkClick.getHyperLink(mSliderItems.get(position));
        });

    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {
        View itemView;
        ImageView image;
        public SliderAdapterVH(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            this.itemView = itemView;
        }
    }

}
