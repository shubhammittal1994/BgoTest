package com.bango.bangoLive.subscription;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;
import com.bango.bangoLive.ViewModel.ApiViewModel;
import com.bango.bangoLive.application.App;
import com.bango.bangoLive.fragments.Mall.ProfileFrame.RootFrames;
import com.bango.bangoLive.fragments.Vip.Model.VipDetailsModel;
import com.bango.bangoLive.subscription.adapter.AdapterVipFrame;
import com.bango.bangoLive.utils.CommonUtils;
import com.bumptech.glide.Glide;
import com.opensource.svgaplayer.SVGAImageView;

import de.hdodenhof.circleimageview.CircleImageView;


public class Fragment_Vip_Frame extends Fragment {
    View view;
    AdapterVipFrame adapterVipFrame;
    RecyclerView rvFrames;
    TextView frameExpire, applyed;
    private RelativeLayout applied_lay, tryNew;
    VipDetailsModel.Details list;
    private SVGAImageView framesImg;


    public Fragment_Vip_Frame() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment__vip__frame, container, false);
        findId(view);
        apiGetVIPFrames();

        onclick(view);

        return view;
    }

    private void onclick(View view) {


    }

    private void ApplyFrame(View view) {

    }

    private void setAdapter(VipDetailsModel.Details list) {


        CommonUtils.setAnimation(requireContext(), list.getFramesvg(), framesImg);

    }

    private void apiGetVIPFrames() {

        new ApiViewModel().getVipDetails(App.getSharedpref().getString("id")).observe(requireActivity(), new Observer<VipDetailsModel>() {
            @Override
            public void onChanged(VipDetailsModel vipDetailsModel) {
                if (vipDetailsModel.getStatus() == 1) {
                    list = vipDetailsModel.getDetails();
                    setAdapter(list);
                    frameExpire.setVisibility(View.GONE);
                } else {
                    frameExpire.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    private void findId(View view) {

        frameExpire = view.findViewById(R.id.FramesExpiry);
        applied_lay = view.findViewById(R.id.applied_lay);
        framesImg = view.findViewById(R.id.framesImg);
        applyed = view.findViewById(R.id.applyed);
        tryNew = view.findViewById(R.id.tryNew);

        clicks();

    }

    private void clicks() {

        tryNew.setOnClickListener(view1 -> {
            openDialog();
        });


        applied_lay.setOnClickListener(view1 -> {
            new ApiViewModel().appliedFrames(requireActivity(), App.getSharedpref().getString("id"), "", "2")
                    .observe(requireActivity(), new Observer<RootFrames>() {
                        @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
                        @Override
                        public void onChanged(RootFrames rootFrames) {
                            if (rootFrames != null) {
                                if (rootFrames.getStatus() == 1) {
                                    Toast.makeText(requireActivity(), "" + rootFrames.getMessage(), Toast.LENGTH_SHORT).show();
                                    applyed.setText("Applied");
                                } else {
                                    applyed.setText("Apply");
                                }
                            } else {
                                Toast.makeText(requireActivity(), "Root is null", Toast.LENGTH_SHORT).show();
                            }
                            //  adapterSubscribed.notifyDataSetChanged();
                        }
                    });
        });

    }

    private void openDialog() {

        Dialog viewDetails_box = new Dialog(view.getContext());
        viewDetails_box.setContentView(R.layout.image_dialog);
        viewDetails_box.getWindow().setBackgroundDrawable(new ColorDrawable());
        Window window = viewDetails_box.getWindow();
        viewDetails_box.setCanceledOnTouchOutside(true);
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        //   viewDetails_box.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        viewDetails_box.show();
        viewDetails_box.setCanceledOnTouchOutside(true);

        CircleImageView zoomeImage = viewDetails_box.findViewById(R.id.img_profile);
        Glide.with(requireContext()).load(App.getSharedpref().getString("image"))
                .error(R.drawable.profilemaleicon).into(zoomeImage);
        SVGAImageView svgaImage = viewDetails_box.findViewById(R.id.frames);
        CommonUtils.setAnimation(requireContext(), list.getFramesvg(), svgaImage);
        ImageView closeBtn = viewDetails_box.findViewById(R.id.close_BTN);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDetails_box.dismiss();
            }
        });

    }


}