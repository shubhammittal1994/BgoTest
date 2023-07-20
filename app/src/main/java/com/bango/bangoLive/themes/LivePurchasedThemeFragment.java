package com.bango.bangoLive.themes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class LivePurchasedThemeFragment extends Fragment {
    public static String liveHostBackImg = "";
    //    List<GetLiveThemeRoot.Detail> liveThemeList;
//    LivePurchasedThemeRVAdapter livePurchasedThemeRVAdapter;
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference userLiveBackImgRef = firebaseDatabase.getReference().child("userLiveBackImgRef");
    private RecyclerView livePurchaseRv;

    // SharedViewModel sharedViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live_purchased_theme, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //  findIds(view);
        //  liveTheme();
//       sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
//        userLiveBackImgRef.child(liveHostBackImg).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    LivePurchasedThemeRVAdapter.themeUrl= snapshot.getValue().toString();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//
//    private void liveTheme() {
//
////        new Mvvm().getLiveTheme(requireActivity(),AppConstants.USER_ID).observe(requireActivity(), new Observer<GetLiveThemeRoot>() {
////            @Override
////            public void onChanged(GetLiveThemeRoot getLiveThemeRoot) {
////
////                liveThemeList=new ArrayList<>();
////
////                if(getLiveThemeRoot != null){
////
////                    if(getLiveThemeRoot.getSuccess()==1){
////
////                        liveThemeList= getLiveThemeRoot.getDetails();
////                        livePurchasedThemeRVAdapter=new LivePurchasedThemeRVAdapter(liveThemeList,requireContext(),LivePurchasedThemeFragment.this);
////                        livePurchaseRv.setAdapter(livePurchasedThemeRVAdapter);
////
////                    }else{
////
////                        if(getContext() != null){
////                            Toast.makeText(requireContext(), ""+getLiveThemeRoot.getMessage(), Toast.LENGTH_SHORT).show();
////                        }
////                    }
////
////                }else{
////
////                }
////            }
////        });
//    }
//
//    private void findIds(View view) {
//        livePurchaseRv=view.findViewById(R.id.livePurchaseRv);
//    }
//
//    public void ImageTestDrive(GetLiveThemeRoot.Detail detail){
//        sharedViewModel.setBackgroundImageUrl(detail.getTheme());
//        Dialog dialog_share = new Dialog(requireContext());
//        dialog_share.setContentView(R.layout.live_animation_theme_test_drive);
//        dialog_share.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//        dialog_share.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//        dialog_share.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog_share.setCanceledOnTouchOutside(true);
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//        layoutParams.copyFrom(dialog_share.getWindow().getAttributes());
//        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
//        dialog_share.getWindow().setAttributes(layoutParams);
//        Window window = dialog_share.getWindow();
//        window.setGravity(Gravity.CENTER);
//        dialog_share.show();
//
//        App.getSharedpref().saveString("tryTheme", detail.getTheme());
//
//       ImageView livePurchaseThemeTestDriveImg = (ImageView) dialog_share.findViewById(R.id.livePurchaseThemeTestDriveImg);
//
//        Glide.with(getActivity().getApplicationContext()).load(detail.getTheme()).into(livePurchaseThemeTestDriveImg);
//    }
//
//    @Override
//    public void callback(GetLiveThemeRoot.Detail detail) {
//
//        if(detail != null){
//            ImageTestDrive(detail);
//        }
//
//
//    }
//
//    @Override
//    public void purchaseLiveTheme(GetLiveThemeRoot.Detail detail,TextView textView) {
//
//        if(detail != null){
//
//            new Mvvm().purchaseLiveTheme(requireActivity(),AppConstants.USER_ID,detail.getId()).observe(LivePurchasedThemeFragment.this, new Observer<GetLiveThemeRoot>() {
//                @Override
//                public void onChanged(GetLiveThemeRoot getLiveThemeRoot) {
//
//                    if(getLiveThemeRoot != null){
//                        if(getLiveThemeRoot.getSuccess()==1){
//                            if (getContext() != null){
//                                textView.setText("Enable");
//                                Toast.makeText(requireContext(), ""+getLiveThemeRoot.getMessage(), Toast.LENGTH_SHORT).show();
//                            }else{
//                            }
//                        }else{
////                            CallActivity activity = new CallActivity();
////                            activity.notEnoughCoins();
//                        }
//                    }else{
//
//                    }
//                }
//            });
//        }
//
//    }
//
//    @Override
//    public void enableLiveTheme(GetLiveThemeRoot.Detail detail, TextView textView) {
//
//        if(detail != null){
//            userLiveBackImgRef.child(liveHostBackImg).setValue(detail.getTheme()).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    textView.setText("Enabled");
//                }
//            });
//        }
//    }
//
//    @Override
//    public void sendLiveTheme(GetLiveThemeRoot.Detail detail, TextView textView) {
//
//        SendGalleryDialogFragment.sendCheck=1;
//        Bundle bundle = new Bundle();
//        bundle.putString("liveThemeId",  detail.getId());
//        SendGalleryDialogFragment themesFragment = new SendGalleryDialogFragment();
//        themesFragment.setArguments(bundle);
//        themesFragment.show(getActivity().getSupportFragmentManager(), themesFragment.getTag());
//    }
//
//    private void notEnoughCoins() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//        builder.setMessage("Not enough coins, want to recharge?");
//        builder.setTitle("Tips");
//        builder.setCancelable(false);
//        builder.setPositiveButton("Recharge", (DialogInterface.OnClickListener) (dialog, which) -> {
////            Navigation.findNavController(requireActivity().findViewById(R.id.nav_home)).navigate(R.id.rechargeCointsFragment);
//
//            getActivity().findViewById(R.id.callAcitivtyMainRL).setVisibility(View.GONE);
//            getActivity().findViewById(R.id.callActivityFrameLayout).setVisibility(View.VISIBLE);
//            RechargeCointsFragment.CallActivityCheck=1;
//            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.callActivityFrameLayout, new RechargeCointsFragment()).addToBackStack(null).commit();
//            dialog.dismiss();
//        });
//
//        builder.setNegativeButton("Cancel", (DialogInterface.OnClickListener) (dialog, which) -> {
//            dialog.cancel();
//        });
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }
    }
}