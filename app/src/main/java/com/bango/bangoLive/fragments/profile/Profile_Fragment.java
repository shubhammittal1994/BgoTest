package com.bango.bangoLive.fragments.profile;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.bango.bangoLive.HomeActivity;
import com.bango.bangoLive.R;
import com.bango.bangoLive.databinding.FragmentProfileBinding;
import com.bango.bangoLive.application.App;
import com.bango.bangoLive.ModelClasses.LoginResponse;
import com.bango.bangoLive.SplashActivity;
import com.bango.bangoLive.ViewModel.ApiViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Profile_Fragment extends Fragment {

    FragmentProfileBinding binding;
    SharedPreferences sharedpreferences;

    String profileName,profileId,profileImage;
    String profileUniqueId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /************************** SHARED PREFERENCES **************************/
        sharedpreferences = getContext().getSharedPreferences("Bango", Context.MODE_PRIVATE);
        clickListeners();
        /************************** SET PROFILE DATA **************************/
        setProfileData();
        /************************** GET DATA **************************/
        getData(view);

    }

    private void setProfileData() {
        profileName = sharedpreferences.getString("name","");
        profileId = sharedpreferences.getString("id","");
        profileUniqueId = sharedpreferences.getString("userUniqueId","");
        profileImage = sharedpreferences.getString("profileImage","");
        App.getSharedpref().saveString("id",profileId);
        Picasso.with(requireContext()).load(profileImage).error(R.drawable.actress).into(binding.circularImageView);
        binding.profileName.setText(profileName);
        binding.profileId.setText("Id:"+profileUniqueId);
    }

    private void clickListeners() {

        /************************** EDIT PROFILE CLICK LISTENER **************************/
        binding.editProfileIcon.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_profile_Fragment_to_editProfile_Fragment);
        });

        /************************** MY LIVE STREAM **************************/
        binding.myLiveStreamIcon.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_profile_Fragment_to_myLiveStreamFragment);
        });

        /************************** APPLY FOR HOST LISTENER **************************/
        binding.applyForHost.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_profile_Fragment_to_applyForHostFragment);
        });

        /************************** TOP GIFTERS ICON LISTENER **************************/
        binding.topGiftersIcon.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_profile_Fragment_to_topGiftersFragment);
        });

        /************************** WALLET ICON LISTENER **************************/
        binding.walletIcon.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_profile_Fragment_to_myWalletFragment);
        });

        /************************** MALL ICON CLICK LISTENER **************************/
        binding.mallIcon.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_profile_Fragment_to_mallFragment);
        });

        /************************** TRANSACTION HISTORY CLICK LISTENER **************************/
        binding.transactionHistory.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_profile_Fragment_to_trasectionHistoryFragment);
        });

        /************************** MY SUBSCRIPTION ICON CLICK LISTENER **************************/
        binding.mySubscriptionIcon.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_profile_Fragment_to_fragment_MySubscription);
        });

        /************************** LOGOUT USER CLICK LISTENER **************************/
        binding.logOut.setOnClickListener(v -> {
            final Dialog dialog = new Dialog(requireContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.log_out_dialog);
            dialog.setCanceledOnTouchOutside(true);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.show();
            dialog.findViewById(R.id.yesText).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new ApiViewModel().logOutClassLiveData(requireActivity(),sharedpreferences.getString("id","")).observe(requireActivity(), new Observer<LoginResponse>() {
                        @Override
                        public void onChanged(LoginResponse loginResponse) {
                            sharedpreferences.edit().clear().commit();
                            dialog.dismiss();
                            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
                            // Build a GoogleSignInClient with the options specified by gso.
                            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
                            mGoogleSignInClient.signOut();
                            startActivity(new Intent(requireContext(), SplashActivity.class));
                        }
                    });
                }
            });
            dialog.findViewById(R.id.noText).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        });
    }

    private void getData(View view) {
        HashMap<String, String> data = new HashMap<>();
        data.put("userId", profileId);
        data.put("otherUserId", profileId);

        new ApiViewModel().someFunctionality(requireActivity(), data).observe(requireActivity(), spinOneModal -> {
            if (spinOneModal.getStatus().equalsIgnoreCase("1")) {
                if (spinOneModal.getDetails()!=null) {
                   binding.purchasedCoins.setText(spinOneModal.getDetails().getPurchasedCoin());
                   binding.monthlySending.setText(spinOneModal.getDetails().getMonthlySendCoins());
                   binding.monthlyReceiving.setText(spinOneModal.getDetails().getMonthlyCoins());
                   binding.totalSending.setText(spinOneModal.getDetails().getTotalSendCoin());
                   binding.totalReceiving.setText(spinOneModal.getDetails().getCoin());
                    Picasso.with(requireContext()).load(spinOneModal.getDetails().getImage()).error(R.drawable.actress).into(binding.circularImageView);
                    binding.profileName.setText(spinOneModal.getDetails().getName());
                    binding.profileId.setText("Id:"+spinOneModal.getDetails().getUsername());
                }
            }

            if (spinOneModal.getMessage().contains("not exists")) {
                App.getSharedpref().clearPreferences();
                //LoginManager.getInstance().logOut();
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
                // Build a GoogleSignInClient with the options specified by gso.
                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
                Toast.makeText(requireContext(), "Login Again ", Toast.LENGTH_SHORT).show();
                mGoogleSignInClient.signOut();
                startActivity(new Intent(requireContext(), HomeActivity.class));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        View view1 = requireActivity().findViewById(R.id.bottomNavigation);
        view1.setVisibility(View.VISIBLE);
    }

}