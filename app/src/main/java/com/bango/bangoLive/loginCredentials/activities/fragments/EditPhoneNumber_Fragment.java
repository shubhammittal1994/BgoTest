package com.bango.bangoLive.loginCredentials.activities.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bango.bangoLive.R;
import com.bango.bangoLive.ViewModel.ApiViewModel;
import com.bango.bangoLive.databinding.FragmentEditPhoneNumberBinding;
import com.tapadoo.alerter.Alerter;

import java.util.Map;

public class EditPhoneNumber_Fragment extends Fragment {
    FragmentEditPhoneNumberBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentEditPhoneNumberBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.nextButton.setOnClickListener(view1 -> {
            if (binding.phoneNumberEditText.getText().toString().isEmpty()) {
                Alerter.create(requireActivity()).setTitle("Phone Number Alert").setText("Please  enter your phone number").setBackgroundColorRes(R.color.app_dark_color).show();
            }
            else {
                hitApi();
            }
        });
        binding.backArrow.setOnClickListener(view12 -> {requireActivity().onBackPressed();});
    }

    /************************** hitApi **************************/
    private void hitApi() {

        Toast.makeText(requireContext(), ""+binding.countryCode.getSelectedCountryCodeWithPlus()+binding.phoneNumberEditText.getText().toString(), Toast.LENGTH_SHORT).show();
        new ApiViewModel().loginWithPhoneLiveData(requireActivity(),binding.countryCode.getSelectedCountryCodeWithPlus()+binding.phoneNumberEditText.getText().toString())
                .observe(requireActivity(), new Observer<Map>() {
                    @Override
                    public void onChanged(Map map) {
                        //Fragment fragment = new Verification_Otp_Fragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("phone", binding.countryCode.getSelectedCountryCodeWithPlus()+binding.phoneNumberEditText.getText().toString());
                        bundle.putString("status", map.get("success").toString());
                        //fragment.setArguments(bundle);
                        Navigation.findNavController(requireActivity(), R.id.nav_host_login_fragment).navigate(R.id.action_editPhoneNumber_Fragment_to_verification_Otp_Fragment,bundle);
                    }
                });
    }
}