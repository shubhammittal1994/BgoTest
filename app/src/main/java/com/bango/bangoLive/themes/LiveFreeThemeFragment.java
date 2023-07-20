package com.bango.bangoLive.themes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.AudioRoom.MODEL.GetAudioLiveImages;
import com.bango.bangoLive.R;
import com.bango.bangoLive.ViewModel.ApiViewModel;
import com.bango.bangoLive.themes.adapter.AdapterLiveThemes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class LiveFreeThemeFragment extends Fragment {

    List<GetAudioLiveImages.Detail> liveBackgroundImagesList ;
    AdapterLiveThemes adapterLiveThemes;

    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference userLiveBackImgRef = firebaseDatabase.getReference().child("userLiveBackImgRef");
    public static String liveHostBackImg ="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live_free_theme, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//                new Mvvm().getLiveBackgroundImages(requireContext()).observe(requireActivity(), new Observer<GetLiveBackgroundImagesRoot>() {
//            @Override
//            public void onChanged(GetLiveBackgroundImagesRoot getLiveBackgroundImagesRoot) {
//                liveBackgroundImagesList = getLiveBackgroundImagesRoot.getDetails();
//
//                RecyclerView rvThemes = dialog_share.findViewById(R.id.recycler_viewThems);
//
//                adapterLiveThemes = new AdapterLiveThemes(liveBackgroundImagesList, new AdapterLiveThemes.Callback() {
//                    @Override
//                    public void setBackground(GetLiveBackgroundImagesRoot.Detail detail) {
//
//                        userLiveBackImgRef.child(liveHostBackImg).setValue(detail.getImages()).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (liveHostBackImg.equalsIgnoreCase(AppConstants.USER_ID)) {
//                                    Glide.with(getApplicationContext()).load(detail.getImages())
//                                            .error(R.drawable.night).into(binding.imgBackground);
//                                }
//                                dialog_share.dismiss();
//                                dialog.dismiss();
//                            }
//                        });
//                        adapterLiveThemes.notifyDataSetChanged();
//                        dialog_share.dismiss();
//                        dialog.dismiss();
//                    }
//                });
//                rvThemes.setAdapter(adapterLiveThemes);
//            }
//        });

        new ApiViewModel().getAudioLiveImagesLiveData(requireActivity()).observe(requireActivity(), new Observer<GetAudioLiveImages>() {
            @Override
            public void onChanged(GetAudioLiveImages getAudioLiveImages) {

                if(getAudioLiveImages.getStatus()==1){
                    liveBackgroundImagesList = getAudioLiveImages.getDetails();
//
                RecyclerView rvThemes = view.findViewById(R.id.recycler_viewThems);

                adapterLiveThemes = new AdapterLiveThemes(liveBackgroundImagesList, new AdapterLiveThemes.Callback() {
                    @Override
                    public void setBackground(GetAudioLiveImages.Detail detail) {

                        userLiveBackImgRef.child(liveHostBackImg).setValue(detail.getImage()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                //if (liveHostBackImg.equalsIgnoreCase(AppConstants.USER_ID)) {
//                                    Glide.with(getApplicationContext()).load(detail.getImages())
//                                            .error(R.drawable.night).into(binding.imgBackground);
                               // }
                            }
                        });
                        adapterLiveThemes.notifyDataSetChanged();

                    }
                });
                rvThemes.setAdapter(adapterLiveThemes);
                }

            }
        });




    }
}