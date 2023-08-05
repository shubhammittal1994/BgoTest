package com.bango.bangoLive.loginCredentials.activities.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bango.bangoLive.HomeActivity;
import com.bango.bangoLive.R;
import com.bango.bangoLive.ZegoServices.internal.sdk.basic.ZEGOSDKCallBack;
import com.bango.bangoLive.ZegoServices.internal.sdk.zim.ZEGOSDKManager;
import com.bango.bangoLive.application.App;
import com.bango.bangoLive.databinding.FragmentVerificationOtpBinding;
import com.tapadoo.alerter.Alerter;

public class Verification_Otp_Fragment extends Fragment {
    FragmentVerificationOtpBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentVerificationOtpBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.nextButton.setOnClickListener(view1 ->{
            if (binding.verficationCodeEditText.getText().toString().isEmpty()){
                Alerter.create(getActivity()).setTitle("Verification Alert").setText("Please  Fill in verification code").setBackgroundColorRes(R.color.app_dark_color).show();
            } else if (binding.editPassword.getText().toString().isEmpty()) {
                Alerter.create(getActivity()).setTitle("Password Alert ").setText("Please  Fill in password").setBackgroundColorRes(R.color.app_dark_color).show();
            }else {
                String userID = "";
                String userName = "";

                if (TextUtils.isEmpty(userID) || TextUtils.isEmpty(userName)) {
                    if (TextUtils.isEmpty(userID)) {
                        App.showToast("please input userID");
                    } else if (TextUtils.isEmpty(userName)) {
                        App.showToast("please input username");
                    }
                    return;
                }

                signInZEGOSDK(userID, userName, (errorCode, message) -> {
                    if (errorCode == 0) {
                        startActivity(new Intent(requireContext(), HomeActivity.class));
                    } else {
                        //TODO Google signout code here
                    }
                });
            }
        });
        binding.backArrowIcon.setOnClickListener(view12 -> {
            requireActivity().onBackPressed();
        });
    }

    private void signInZEGOSDK(String userID, String userName, ZEGOSDKCallBack callback) {
        ZEGOSDKManager.getInstance().connectUser(userID, userName, callback);
    }
}