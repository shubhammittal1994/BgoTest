package com.bango.bangoLive.fragments.Mall;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.bango.bangoLive.R;
import com.bango.bangoLive.ViewModel.ApiViewModel;
import com.bango.bangoLive.databinding.FragmentFramesBinding;
import com.bumptech.glide.Glide;
import com.bango.bangoLive.fragments.Mall.Adapter.MallFramesAdapter;
import com.bango.bangoLive.fragments.Mall.Model.RootFramesAll;
import com.bango.bangoLive.fragments.Mall.ProfileFrame.RootFrames;
//import com.opensource.svgaplayer.SVGAImageView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_Frames extends Fragment implements MallFramesAdapter.Callback {

    FragmentFramesBinding binding;
    SharedPreferences sharedpreferences;
    String profileName,profileId,profileImage;
    List<RootFramesAll.Detail> list = new ArrayList<>();
    RootFrames details;
    String frameId;
    MallFramesAdapter framesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding = FragmentFramesBinding.inflate(inflater, container, false);

        /************************** SHARED PREFERENCES **************************/
        sharedpreferences = getContext().getSharedPreferences("Bango", Context.MODE_PRIVATE);
        profileName = sharedpreferences.getString("name","");
        profileId = sharedpreferences.getString("id","");
        profileImage = sharedpreferences.getString("profileImage","");

        apiFrames(getView());
        setAdapter(getView());
        return binding.getRoot();
    }

    /************************** GET FRAMES **************************/
    private void apiFrames(View view) {
        new ApiViewModel().getFrames(requireActivity(),profileId).observe(requireActivity(), new Observer<RootFramesAll>() {
            @Override
            public void onChanged(RootFramesAll rootFrames) {
                if (rootFrames != null) {
                    if (rootFrames.getSuccess() == 1) {
                        list = rootFrames.getDetails();
                        framesAdapter.loadData(list);
                    }
                } else {
                    Toast.makeText(requireActivity(), "Root is null", Toast.LENGTH_SHORT).show();
                }
                binding.recyclerView.setAdapter(framesAdapter);
            }
        });
    }

    /************************** SET ADAPTER **************************/
    private void setAdapter(View view) {
        framesAdapter = new MallFramesAdapter(new ArrayList<>(), requireContext(), Fragment_Frames.this);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    /************************** TRY FRAMES **************************/
    @Override
    public void TryFrames(RootFramesAll.Detail detail) {
        Dialog viewDetails_box = new Dialog(getView().getContext());
        viewDetails_box.setContentView(R.layout.image_dialog);
        viewDetails_box.getWindow().setBackgroundDrawable(new ColorDrawable());
        Window window = viewDetails_box.getWindow();
        viewDetails_box.setCanceledOnTouchOutside(true);
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        viewDetails_box.show();
        viewDetails_box.setCanceledOnTouchOutside(true);

        CircleImageView zoomeImage = viewDetails_box.findViewById(R.id.img_profile);
        Glide.with(requireContext()).load(profileImage)
                .error(R.drawable.profilemaleicon).into(zoomeImage);

//        SVGAImageView svgaImage = viewDetails_box.findViewById(R.id.frames);
//        CommonUtils.setAnimation(requireContext(), detail.getImage(), svgaImage);
        ImageView closeBtn = viewDetails_box.findViewById(R.id.close_BTN);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDetails_box.dismiss();
            }
        });

    }

    /************************** PURCHASE FRAMES **************************/
    @Override
    public void purchaseFrame(RootFramesAll.Detail detailPurchase, TextView buy) {
        String frameId = detailPurchase.getId();
        new ApiViewModel().purchaseFrames(requireActivity(), profileId, frameId).observe(requireActivity(), new Observer<RootFrames>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(RootFrames rootPurchaseFrame) {
                if (rootPurchaseFrame != null) {
                    setDialog(rootPurchaseFrame.getMessage());
                    if (rootPurchaseFrame.getSuccess().equals(1)) {
                        buy.setText("Buy");
                        Toast.makeText(requireActivity(), "" + rootPurchaseFrame.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        buy.setText("Purchased");
                        Toast.makeText(requireActivity(), "" + rootPurchaseFrame.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireActivity(), "root is null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /************************** INSUFFICIENT BALANCE DIALOG **************************/
    private void setDialog(String message) {
        if (message.equalsIgnoreCase("Insufficient Balance")) {
            Dialog viewDetails_box = new Dialog(requireContext());
            viewDetails_box.setContentView(R.layout.dialog_are_you_sure);
            viewDetails_box.getWindow().setBackgroundDrawable(new ColorDrawable());
            Window window = viewDetails_box.getWindow();
            viewDetails_box.setCanceledOnTouchOutside(true);
            window.setGravity(Gravity.CENTER);
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
            window.setAttributes(wlp);
            viewDetails_box.show();
            viewDetails_box.setCanceledOnTouchOutside(true);
        } else {

        }

    }

}
