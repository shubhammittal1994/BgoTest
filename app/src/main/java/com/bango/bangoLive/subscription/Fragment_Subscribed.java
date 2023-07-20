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
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;
import com.bango.bangoLive.ViewModel.ApiViewModel;
import com.bango.bangoLive.application.App;
import com.bango.bangoLive.fragments.Mall.ProfileFrame.RootFrames;
import com.bango.bangoLive.subscription.adapter.AdapterSubscribed;
import com.bango.bangoLive.utils.CommonUtils;
import com.bumptech.glide.Glide;
import com.opensource.svgaplayer.SVGAImageView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class Fragment_Subscribed extends Fragment implements AdapterSubscribed.Callback {
    View view;
    AdapterSubscribed adapterSubscribed;
    RecyclerView rvFrames;
    TextView frameExpire;
    List<RootFrames.Detail> list = new ArrayList<>();

    public Fragment_Subscribed() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment__subscribed, container, false);
        findId(view);
        apiGetPurchasedFrames(view);
        setAdapter(view);


        return view;
    }

    private void findId(View view) {

        rvFrames = view.findViewById(R.id.frame_recycler);
        frameExpire = view.findViewById(R.id.FramesExpiry);
        //refresh = view.findViewById(R.id.refresh);
    }

    private void setAdapter(View view) {

        adapterSubscribed = new AdapterSubscribed(new ArrayList<>(), requireContext(), Fragment_Subscribed.this);
        rvFrames.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    private void apiGetPurchasedFrames(View view) {

        new ApiViewModel().getPurchaseFrame(requireActivity(), App.getSharedpref().getString("id")).observe(requireActivity(), new Observer<RootFrames>() {
            @Override
            public void onChanged(RootFrames rootFrames) {
                if (rootFrames != null) {
                    if (rootFrames.getSuccess().equals(1)) {
                        list = rootFrames.getDetails();
                        frameExpire.setVisibility(View.GONE);
                        adapterSubscribed.loadData(list);

                        //   details = App.getSharedpref().getModel(AppConstants.FRAMEINFO, RootFrames.class);

                    }
                } else {
                    frameExpire.setVisibility(View.VISIBLE);
                    Toast.makeText(requireActivity(), "Root is null", Toast.LENGTH_SHORT).show();
                }
                rvFrames.setAdapter(adapterSubscribed);
            }
        });
    }

    @Override
    public void TryFrame(RootFrames.Detail detail) {

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
        Glide.with(requireContext()).load( App.getSharedpref().getString("image"))
                .error(R.drawable.profilemaleicon).into(zoomeImage);

        SVGAImageView svgaImage = viewDetails_box.findViewById(R.id.frames);
        CommonUtils.setAnimation(requireContext(), detail.getFrameIMage(), svgaImage);
        ImageView closeBtn = viewDetails_box.findViewById(R.id.close_BTN);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDetails_box.dismiss();
            }
        });
    }

    @Override
    public void Frame(RootFrames.Detail detail, TextView buy) {

        new ApiViewModel().appliedFrames(requireActivity(), App.getSharedpref().getString("id"), detail.getFrameId(),"1")
                .observe(requireActivity(), new Observer<RootFrames>() {
                    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
                    @Override
                    public void onChanged(RootFrames rootFrames) {
                        if (rootFrames != null) {

                            if (rootFrames.getStatus()==1) {
                                Toast.makeText(requireActivity(), "" + rootFrames.getMessage(), Toast.LENGTH_SHORT).show();
                                buy.setText("Applied");

                            } else {
                                buy.setText("Apply");
                            }

                        } else {
                            Toast.makeText(requireActivity(), "Root is null", Toast.LENGTH_SHORT).show();
                        }
                        //  adapterSubscribed.notifyDataSetChanged();
                    }
                });

    }
}