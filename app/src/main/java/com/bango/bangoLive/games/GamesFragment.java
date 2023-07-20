package com.bango.bangoLive.games;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bango.bangoLive.HomeActivity;
import com.bango.bangoLive.ViewModel.ApiViewModel;
import com.bango.bangoLive.databinding.FragmentGamesBinding;
import com.bango.bangoLive.games.activity.GameScreenActivity;
import com.bango.bangoLive.application.App;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.net.URLEncoder;
import java.util.HashMap;

public class GamesFragment extends Fragment {

    FragmentGamesBinding binding;

    SharedPreferences sharedpreferences;
    String profileName,profileId,profileImage;
    String profileUniqueId;
    String authToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentGamesBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /************************** SHARED PREFERENCES **************************/
        sharedpreferences = getContext().getSharedPreferences("Bango", Context.MODE_PRIVATE);
        profileId = sharedpreferences.getString("id","");
            HashMap<String, String> data = new HashMap<>();
            data.put("userId", profileId);
            data.put("otherUserId", profileId);
            new ApiViewModel().someFunctionality(requireActivity(), data).observe(requireActivity(), spinOneModal -> {
                if (spinOneModal.getStatus().equalsIgnoreCase("1")) {
                    if (spinOneModal.getDetails()!=null) {
                        authToken = spinOneModal.getDetails().getAuthToken();
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

        binding.spinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer buffer=new StringBuffer("https://d1irpl18x20qhe.cloudfront.net/bango/greedy-box/index.html");
                buffer.append("?token="+ URLEncoder.encode(authToken.substring(1,authToken.length()-1)));
                buffer.append("&lang="+URLEncoder.encode("en"));
                GameScreenActivity.gameLink = buffer.toString();
                startActivity(new Intent(requireContext(), GameScreenActivity.class));
            }
        });
        binding.neon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                StringBuffer buffer=new StringBuffer("https://d1irpl18x20qhe.cloudfront.net/bango/kitty-cat-full/index.html");
                buffer.append("?token="+ URLEncoder.encode(authToken.substring(1,authToken.length()-1)));
                buffer.append("&lang="+URLEncoder.encode("en"));
                GameScreenActivity.gameLink = buffer.toString();
                startActivity(new Intent(requireContext(), GameScreenActivity.class));
            }
        });
        binding.neon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                StringBuffer buffer=new StringBuffer("https://d1irpl18x20qhe.cloudfront.net/bango/bar7-full/index.html");
                buffer.append("?token="+ URLEncoder.encode(authToken.substring(1,authToken.length()-1)));
                buffer.append("&lang="+URLEncoder.encode("en"));
                GameScreenActivity.gameLink = buffer.toString();
                Log.d("TAG", "onClick: "+GameScreenActivity.gameLink);
                               startActivity(new Intent(requireContext(), GameScreenActivity.class));

            }
        });
        binding.lucky77.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                StringBuffer buffer=new StringBuffer("https://d1irpl18x20qhe.cloudfront.net/bango/luck77-half/index.html");
                buffer.append("?token="+ URLEncoder.encode(authToken.substring(1,authToken.length()-1)));
                buffer.append("&lang="+URLEncoder.encode("en"));
                GameScreenActivity.gameLink = buffer.toString();
                Log.d("TAG", "onClick: "+GameScreenActivity.gameLink);
                startActivity(new Intent(requireContext(), GameScreenActivity.class));
            }
        });
        binding.dvt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                StringBuffer buffer=new StringBuffer("https://d1irpl18x20qhe.cloudfront.net/bango/dragon-tiger/index.html");
                buffer.append("?token="+ URLEncoder.encode(authToken.substring(1,authToken.length()-1)));
                buffer.append("&lang="+URLEncoder.encode("en"));
                GameScreenActivity.gameLink = buffer.toString();
                Log.d("TAG", "onClick: "+GameScreenActivity.gameLink);
                startActivity(new Intent(requireContext(), GameScreenActivity.class));

            }
        });
    }
}