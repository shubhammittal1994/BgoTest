package com.bango.bangoLive.loginCredentials.activities.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bango.bangoLive.HomeActivity;
import com.bango.bangoLive.R;
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
                startActivity(new Intent(requireContext(), HomeActivity.class));
            }
        });
        binding.backArrowIcon.setOnClickListener(view12 -> {
            requireActivity().onBackPressed();
        });
    }
}