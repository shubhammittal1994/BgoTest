package com.bango.bangoLive.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bango.bangoLive.AudioRoom.AudioRoomModelClass.GetLiveHostListAudioModelClass;
import com.bango.bangoLive.CallActivity;
import com.bango.bangoLive.ModelClasses.Banner.BannerRoot;
import com.bango.bangoLive.adapters.LiveUsersAdapter;
import com.bango.bangoLive.adapters.SliderAdapterExample;
import com.bango.bangoLive.ModelClasses.Banner.BannerImagesModel;
import com.bango.bangoLive.ViewModel.ApiViewModel;
import com.bango.bangoLive.databinding.FragmentPopularBinding;
import com.bango.bangoLive.utils.AppConstant;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;

import java.util.ArrayList;
import java.util.List;

public class Popular_Fragment extends Fragment implements LiveUsersAdapter.Callback {

    FragmentPopularBinding binding;
    List<GetLiveHostListAudioModelClass.Detail> getLiveHostListAudioModelClassList;
    SharedPreferences sharedpreferences;
    String profileId;
    SliderAdapterExample sliderAdapterExample;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentPopularBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /************************** SHARED PREFERENCES **************************/
        sharedpreferences = getContext().getSharedPreferences("Bango", Context.MODE_PRIVATE);
        profileId = sharedpreferences.getString("id","");

        /************************** GET ALL POPULAR LIVE HOST API LIST **************************/
        getLiveHostListApi();

        /************************** IMAGE SLIDER METHOD **************************/
        setImageSlider();

        sliderAdapterExample = new SliderAdapterExample(requireContext(), new ArrayList<>(), new SliderAdapterExample.HyperLinkClick() {
            @Override
            public void getHyperLink(BannerImagesModel bannerImagesModel) {
                Toast.makeText(requireContext(), "" + bannerImagesModel.getHyperlink(), Toast.LENGTH_SHORT).show();
                if (bannerImagesModel.getHyperlink() != null) {
                    if (!bannerImagesModel.getHyperlink().equalsIgnoreCase("")) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(bannerImagesModel.getBanner()));
                        startActivity(i);
                    }
                }
            }
        });
        binding.imageSlider.setSliderAdapter(sliderAdapterExample);
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.SWAP);
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.imageSlider.startAutoCycle();
    }

    private void getLiveHostListApi() {
        new ApiViewModel().getLiveHostList(requireActivity(),profileId).observe(requireActivity(), new Observer<GetLiveHostListAudioModelClass>() {
            @Override
            public void onChanged(GetLiveHostListAudioModelClass getLiveHostListAudioModelClass) {
                if (getLiveHostListAudioModelClass.getDetails()!=null){
                    getLiveHostListAudioModelClassList=new ArrayList<>();
                    getLiveHostListAudioModelClassList=getLiveHostListAudioModelClass.getDetails();
                    setAdapterInRecyclerView();
                }
            }
        });
    }

    private void setAdapterInRecyclerView() {
        LiveUsersAdapter liveUsersAdapter = new LiveUsersAdapter(requireContext(),getLiveHostListAudioModelClassList,this::onClickCallback);
        binding.recyclerView.setAdapter(liveUsersAdapter);
    }
    private void setImageSlider() {
        new ApiViewModel().bannerRootLiveData().observe(requireActivity(), new Observer<BannerRoot>() {
            @Override
            public void onChanged(BannerRoot bannerRoot) {
                if (bannerRoot != null) {
                    sliderAdapterExample.loadData(bannerRoot.getDetails());
                }

            }
        });
    }

    @Override
    public void onClickCallback(GetLiveHostListAudioModelClass.Detail getLiveHostListAudioModelClass) {
        Log.d("TAG", "onClickCallback: "+getLiveHostListAudioModelClass.getUserId());

        Intent intent = new Intent(requireContext(), CallActivity.class);
        intent.putExtra("profileName",getLiveHostListAudioModelClass.getName());
        //RoomID
        intent.putExtra("roomID",getLiveHostListAudioModelClass.getUserId());
        intent.putExtra("profileImage",getLiveHostListAudioModelClass.getImage());
        intent.putExtra("signature",getLiveHostListAudioModelClass.getSignature());
        intent.putExtra(AppConstant.AM_I_HOST,false);
        //Host Live Count : How much time a user comes Online
        intent.putExtra("liveId",getLiveHostListAudioModelClass.getId());

        intent.putExtra("liveType","multiLive");
        intent.putExtra("liveStatus", "host");
        /*Other UserId is Mine here*/
        intent.putExtra("otherUserId",profileId);
        intent.putExtra("status", "1");
        /*Uniqe ID */
        intent.putExtra("profileUniqueId",getLiveHostListAudioModelClass.getUsername());
        intent.putExtra("coverimage",getLiveHostListAudioModelClass.getPosterImage());
        intent.putExtra("coverName",getLiveHostListAudioModelClass.getLiveTitle());
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.DROP);
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.imageSlider.startAutoCycle();
    }
}