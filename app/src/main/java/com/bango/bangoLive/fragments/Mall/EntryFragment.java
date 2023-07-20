package com.bango.bangoLive.fragments.Mall;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bango.bangoLive.R;
import com.bango.bangoLive.ViewModel.ApiViewModel;
import com.bango.bangoLive.databinding.FragmentEntryBinding;
import com.bango.bangoLive.fragments.Mall.Adapter.EntryAdapter;
import com.bango.bangoLive.fragments.Mall.Model.EntryEffectModel;
import com.bango.bangoLive.fragments.Mall.ProfileFrame.RootFrames;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.makeramen.roundedimageview.RoundedImageView;
//import com.opensource.svgaplayer.SVGAImageView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class EntryFragment extends Fragment implements EntryAdapter.Callback {

    FragmentEntryBinding binding;
    SharedPreferences sharedpreferences;
    List<EntryEffectModel.Detail> list = new ArrayList<>();
    View view;
    RootFrames details;
    String profileName,profileId,profileImage;
    String frameId;
    EntryAdapter framesAdapter;
    private int typeData = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEntryBinding.inflate(inflater, container, false);
        /************************** SHARED PREFERENCES **************************/
        sharedpreferences = getContext().getSharedPreferences("Bango", Context.MODE_PRIVATE);
        profileName = sharedpreferences.getString("name","");
        profileId = sharedpreferences.getString("id","");
        profileImage = sharedpreferences.getString("profileImage","");
        apiFrames(view);
        setAdapter(view);
        return binding.getRoot();
    }

    private void apiFrames(View view) {
        new ApiViewModel().get_enrty_effects(requireActivity(),profileId).observe(requireActivity(), new Observer<EntryEffectModel>() {
            @Override
            public void onChanged(EntryEffectModel rootFrames) {
                if (rootFrames != null) {
                    if (rootFrames.getStatus() == 1) {
                        list = rootFrames.getDetails();
                        framesAdapter.loadData(list);
                    }
                } else {
                    Toast.makeText(requireActivity(), "Root is null", Toast.LENGTH_SHORT).show();
                }
                binding.frameRecycler.setAdapter(framesAdapter);
            }
        });
    }

    private void setAdapter(View view) {
        framesAdapter = new EntryAdapter(new ArrayList<>(), requireContext(), this);
        binding.frameRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    @Override
    public void TryFrames(EntryEffectModel.Detail detail) {

        Dialog viewDetails_box = new Dialog(requireContext());
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

    @SuppressLint("ResourceAsColor")
    private void OpenOtherBottomShgeet(EntryEffectModel.Detail detail, TextView buy) {

        BottomSheetDialog bottomSheet = new BottomSheetDialog(requireContext());
        View view = View.inflate(requireContext(), R.layout.choose_theme_audio, null);
        bottomSheet.setContentView(view);
        bottomSheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
        bottomSheetBehavior.setPeekHeight(bottomSheetBehavior.getPeekHeight());


        RoundedImageView civ_user_profile;
        AppCompatButton txtByThemeStore;
        TextView tvOneWeek, tvTwoWeek, tvThreeWeek;


        tvOneWeek = bottomSheet.findViewById(R.id.tvOneWeek);
        tvTwoWeek = bottomSheet.findViewById(R.id.tvTwoWeek);
        tvThreeWeek = bottomSheet.findViewById(R.id.tvThreeWeek);
        civ_user_profile = bottomSheet.findViewById(R.id.civ_user_profile);
        txtByThemeStore = bottomSheet.findViewById(R.id.txtByThemeStore);

        tvTwoWeek.setVisibility(View.GONE);
        Glide.with(civ_user_profile.getContext()).load(detail.getImage()).into(civ_user_profile);

        txtByThemeStore.setText(detail.getPrice());

        bottomSheet.show();
        bottomSheet.setDismissWithAnimation(true);


        tvOneWeek.setOnClickListener(v -> {

            typeData = 1;


            setTextBackGround(tvOneWeek, tvTwoWeek, tvThreeWeek, 1, txtByThemeStore, detail.getPrice());


        });

        tvThreeWeek.setOnClickListener(v -> {
            typeData = 2;

            setTextBackGround(tvThreeWeek, tvTwoWeek, tvOneWeek, 3, txtByThemeStore, detail.getPrice());


        });
        txtByThemeStore.setOnClickListener(v -> {
            hitApi(detail, typeData, buy, bottomSheet);
        });


    }

    private void hitApi(EntryEffectModel.Detail detail, int typeData, TextView txtByThemeStore, BottomSheetDialog bottomSheet) {
        new ApiViewModel().purchase_entry_effects(requireActivity(), profileId, detail.getId(), String.valueOf(typeData)).observe(requireActivity(), new Observer<RootFrames>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(RootFrames rootPurchaseFrame) {
                if (rootPurchaseFrame != null) {
                    if (rootPurchaseFrame.getStatus().equals(1)) {
                        txtByThemeStore.setText("Buy");
                        Toast.makeText(requireActivity(), "" + rootPurchaseFrame.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        txtByThemeStore.setText("Purchased");
                        Toast.makeText(requireActivity(), "" + rootPurchaseFrame.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    setDialog(rootPurchaseFrame.getMessage());
                }
            }
        });
    }

    @Override
    public void purchaseFrame(EntryEffectModel.Detail detailPurchase, TextView buy) {
        OpenOtherBottomShgeet(detailPurchase, buy);


    }

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
            //   viewDetails_box.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            viewDetails_box.show();
            viewDetails_box.setCanceledOnTouchOutside(true);
        } else {

        }

    }

    private void setTextBackGround(TextView tvOneWeek, TextView tvTwoWeek, TextView tvThreeWeek, int type, AppCompatButton txtByThemeStore, String price) {

        long priceWall = Long.parseLong(price);
        if (type == 1) {
            txtByThemeStore.setText(String.valueOf(priceWall));
        } else if (type == 2) {
            priceWall = 2 * priceWall;
            txtByThemeStore.setText(String.valueOf(priceWall));
        } else if (type == 3) {
            priceWall = 4 * priceWall;
            txtByThemeStore.setText(String.valueOf(priceWall));

        }
        tvOneWeek.setBackgroundDrawable(requireContext().getDrawable(R.drawable.stroke_primary));
        tvOneWeek.setTextColor(requireContext().getColor(R.color.app_dark_color));
        tvTwoWeek.setBackgroundDrawable(requireContext().getDrawable(R.drawable.stroke_week));
        tvTwoWeek.setTextColor(requireContext().getColor(R.color.black));
        tvThreeWeek.setBackgroundDrawable(requireContext().getDrawable(R.drawable.stroke_week));
        tvOneWeek.setTextColor(requireContext().getColor(R.color.black));
    }
}