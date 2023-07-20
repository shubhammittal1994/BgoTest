package com.bango.bangoLive.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bango.bangoLive.R;
import com.bango.bangoLive.databinding.FragmentHomeHotspotIconBinding;
import com.bango.bangoLive.games.GamesFragment;

public class Home_HotspotIcon_Fragment extends Fragment {
    FragmentHomeHotspotIconBinding binding;
    String[] permission = {android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
            android.Manifest.permission.CAMERA, android.Manifest.permission.INTERNET,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            android.Manifest.permission.CALL_PHONE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO, Manifest.permission.BLUETOOTH,
            Manifest.permission.ACCESS_NETWORK_STATE};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentHomeHotspotIconBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Replace Popular Fragment
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Popular_Fragment()).commit();
        onClick();
        //bottomNav
        View view1 = requireActivity().findViewById(R.id.bottomNavigation);
        view1.setVisibility(View.VISIBLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission, 10);
        } else {

        }
    }
    private void onClick() {
        binding.layoutPopular.setOnClickListener(view1 -> {
            //textPopular
            binding.textPopular.setTextSize(16);
            binding.textPopular.setTextColor(Color.parseColor("#8E0AAD"));
            binding.textPopular.setShadowLayer(2,1,1,R.color.shade_color);
            binding.popularViewLine.setVisibility(View.VISIBLE);
            //textFresher
            binding.textFreshers.setShadowLayer(0,0,0,0);
            binding.textFreshers.setTextSize(14);
            binding.textFreshers.setTextColor(Color.parseColor("#979797"));
            binding.fresherViewLine.setVisibility(View.INVISIBLE);
            //textBroadcast
            binding.textBroadcaste.setTextSize(14);
            binding.textBroadcaste.setShadowLayer(0,0,0,0);
            binding.textBroadcaste.setTextColor(Color.parseColor("#979797"));
            binding.broadcastViewLine.setVisibility(View.INVISIBLE);
            //textPK
            binding.textPk.setTextSize(14);
            binding.textPk.setShadowLayer(0,0,0,0);
            binding.textPk.setTextColor(Color.parseColor("#979797"));
            binding.pkViewLine.setVisibility(View.INVISIBLE);
            //textGames
            binding.textgame.setTextSize(14);
            binding.textgame.setShadowLayer(0,0,0,0);
            binding.textgame.setTextColor(Color.parseColor("#979797"));
            binding.gameViewLine.setVisibility(View.INVISIBLE);
            //Replace Popular Fragment
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Popular_Fragment()).addToBackStack(null).commit();
        });
        binding.freshersLayout.setOnClickListener(view12 -> {
            //textPopular
            binding.textPopular.setTextSize(14);
            binding.textPopular.setShadowLayer(0,0,0,0);
            binding.textPopular.setTextColor(Color.parseColor("#979797"));
            binding.popularViewLine.setVisibility(View.INVISIBLE);
            //textFresher
            binding.textFreshers.setShadowLayer(2,1,1,R.color.shade_color);
            binding.textFreshers.setTextSize(16);
            binding.textFreshers.setTextColor(Color.parseColor("#8E0AAD"));
            binding.fresherViewLine.setVisibility(View.VISIBLE);
            //textBroadcast
            binding.textBroadcaste.setTextSize(14);
            binding.textBroadcaste.setShadowLayer(0,0,0,0);
            binding.textBroadcaste.setTextColor(Color.parseColor("#979797"));
            binding.broadcastViewLine.setVisibility(View.INVISIBLE);
            //textPK
            binding.textPk.setTextSize(14);
            binding.textPk.setShadowLayer(0,0,0,0);
            binding.textPk.setTextColor(Color.parseColor("#979797"));
            binding.pkViewLine.setVisibility(View.INVISIBLE);
            //textGames
            binding.textgame.setTextSize(14);
            binding.textgame.setShadowLayer(0,0,0,0);
            binding.textgame.setTextColor(Color.parseColor("#979797"));
            binding.gameViewLine.setVisibility(View.INVISIBLE);
            //Replace Popular Fragment
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Freshers_Fragment()).addToBackStack(null).commit();
        });
        binding.broadcastLayout.setOnClickListener(view13 -> {
            //textPopular
            binding.textPopular.setTextSize(14);
            binding.textPopular.setShadowLayer(0,0,0,0);
            binding.textPopular.setTextColor(Color.parseColor("#979797"));
            binding.popularViewLine.setVisibility(View.INVISIBLE);
            //textFresher
            binding.textFreshers.setShadowLayer(0,0,0,0);
            binding.textFreshers.setTextSize(14);
            binding.textFreshers.setTextColor(Color.parseColor("#979797"));
            binding.fresherViewLine.setVisibility(View.INVISIBLE);
            //textBroadcast
            binding.textBroadcaste.setTextSize(16);
            binding.textBroadcaste.setShadowLayer(2,1,1,R.color.shade_color);
            binding.textBroadcaste.setTextColor(Color.parseColor("#8E0AAD"));
            binding.broadcastViewLine.setVisibility(View.VISIBLE);
            //textPK
            binding.textPk.setTextSize(14);
            binding.textPk.setShadowLayer(0,0,0,0);
            binding.textPk.setTextColor(Color.parseColor("#979797"));
            binding.pkViewLine.setVisibility(View.INVISIBLE);
            //textGames
            binding.textgame.setTextSize(14);
            binding.textgame.setShadowLayer(0,0,0,0);
            binding.textgame.setTextColor(Color.parseColor("#979797"));
            binding.gameViewLine.setVisibility(View.INVISIBLE);
            //Replace Popular Fragment
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Broadcast_Fragment()).addToBackStack(null).commit();
        });
        binding.pkLayout.setOnClickListener(view14 -> {
            //textPopular
            binding.textPopular.setTextSize(14);
            binding.textPopular.setShadowLayer(0,0,0,0);
            binding.textPopular.setTextColor(Color.parseColor("#979797"));
            binding.popularViewLine.setVisibility(View.INVISIBLE);
            //textFresher
            binding.textFreshers.setShadowLayer(0,0,0,0);
            binding.textFreshers.setTextSize(14);
            binding.textFreshers.setTextColor(Color.parseColor("#979797"));
            binding.fresherViewLine.setVisibility(View.INVISIBLE);
            //textBroadcast
            binding.textBroadcaste.setTextSize(14);
            binding.textBroadcaste.setShadowLayer(0,0,0,0);
            binding.textBroadcaste.setTextColor(Color.parseColor("#979797"));
            binding.broadcastViewLine.setVisibility(View.INVISIBLE);
            //textPK
            binding.textPk.setTextSize(16);
            binding.textPk.setTextColor(Color.parseColor("#8E0AAD"));
            binding.textPk.setShadowLayer(2,1,1,R.color.shade_color);
            binding.pkViewLine.setVisibility(View.VISIBLE);
            //textGames
            binding.textgame.setTextSize(14);
            binding.textgame.setShadowLayer(0,0,0,0);
            binding.textgame.setTextColor(Color.parseColor("#979797"));
            binding.gameViewLine.setVisibility(View.INVISIBLE);
            //Replace Popular Fragment
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Pk_Fragment()).addToBackStack(null).commit();
            binding.gamesLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //textPopular
                    binding.textPopular.setTextSize(14);
                    binding.textPopular.setShadowLayer(0,0,0,0);
                    binding.textPopular.setTextColor(Color.parseColor("#979797"));
                    binding.popularViewLine.setVisibility(View.INVISIBLE);
                    //textFresher
                    binding.textFreshers.setShadowLayer(0,0,0,0);
                    binding.textFreshers.setTextSize(14);
                    binding.textFreshers.setTextColor(Color.parseColor("#979797"));
                    binding.fresherViewLine.setVisibility(View.INVISIBLE);
                    //textBroadcast
                    binding.textBroadcaste.setTextSize(14);
                    binding.textBroadcaste.setShadowLayer(0,0,0,0);
                    binding.textBroadcaste.setTextColor(Color.parseColor("#979797"));
                    binding.broadcastViewLine.setVisibility(View.INVISIBLE);
                    //textPK
                    binding.textPk.setTextSize(14);
                    binding.textPk.setShadowLayer(0,0,0,0);
                    binding.textPk.setTextColor(Color.parseColor("#979797"));
                    binding.pkViewLine.setVisibility(View.INVISIBLE);
                    //textGames
                    binding.textgame.setTextSize(16);
                    binding.textgame.setTextColor(Color.parseColor("#8E0AAD"));
                    binding.textgame.setShadowLayer(2,1,1,R.color.shade_color);
                    binding.gameViewLine.setVisibility(View.VISIBLE);
                    //replace game fragment
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new GamesFragment()).addToBackStack(null).commit();

                }
            });
        });

        /************************** SEARCH ICON LISTENER **************************/
        binding.searchIcon.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_home_HotspotIcon_Fragment_to_searchUserFragment);
        });

        /************************** LEADERBOARD ICON LISTENER **************************/
        binding.leaderBoardIcon.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_home_HotspotIcon_Fragment_to_leadtherboardFragment);
        });
    }
}