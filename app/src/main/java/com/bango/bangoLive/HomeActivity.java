package com.bango.bangoLive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bango.bangoLive.ZegoServices.zegoCloudChat.ChatSDKManager;
import com.bango.bangoLive.application.App;
import com.bango.bangoLive.databinding.ActivityHomeBinding;

import java.util.ArrayList;

import im.zego.zim.callback.ZIMLoggedInCallback;
import im.zego.zim.entity.ZIMError;
import im.zego.zim.entity.ZIMGroupInfo;
import im.zego.zim.entity.ZIMUserInfo;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        /************************** SHARED PREFERENCES **************************/
        sharedpreferences = this.getSharedPreferences("Bango", Context.MODE_PRIVATE);

        binding.bottomNavigation.hotspotShadow.setVisibility(View.VISIBLE);
        binding.bottomNavigation.reelsShadow.setVisibility(View.INVISIBLE);
        binding.bottomNavigation.messageShadow.setVisibility(View.INVISIBLE);
        binding.bottomNavigation.profileShadow.setVisibility(View.INVISIBLE);
        bottomNavigationClicks();

        /************************** SET VISIBLITY OF BOTTOM NAVIGATION **************************/
        binding.bottomNavigation.getRoot().setVisibility(View.VISIBLE);
        createZimUserInfo("7193856", "manish");

    }

    private void createZimUserInfo(String id, String name) {
        ZIMUserInfo zimUserInfo = new ZIMUserInfo();
        zimUserInfo.userID = id;
        zimUserInfo.userName = name;

        //App.showToast(HomeActivity.this,"Zim Logged In !!"+id +"&&"+name);
        Log.e("--->>>", "" + id + "&&" + name);
        ChatSDKManager.getChatSDKManager().login(zimUserInfo, error -> {
            Log.e("--->>>", "Zim Logged In !!");
            App.showToast(HomeActivity.this, "Zim Logged In !!" + error.getMessage());

            ZIMGroupInfo zimGroupInfo = new ZIMGroupInfo();
            zimGroupInfo.groupID = "4141";
            zimGroupInfo.groupName = "Bella";

            ChatSDKManager.getChatSDKManager().createGroup(zimGroupInfo, new ArrayList<String>(),
                    (groupInfo, userList, errorUserList, errorInfo) -> {
                        Log.e(groupInfo.baseInfo.groupName, "User list size:- " + userList.size());
                        Log.e(groupInfo.baseInfo.groupName, "Error User list size:- " + errorUserList.size());
                        Log.e(groupInfo.baseInfo.groupName, "Error Info:- " + errorInfo.getCode() + " - " + errorInfo.getMessage());
                    });
        });
    }

    private void bottomNavigationClicks() {

        binding.bottomNavigation.hotspotHomeIcon.setOnClickListener(view -> {
            Navigation.findNavController(this, R.id.home_fragment_Container).navigate(R.id.home_HotspotIcon_Fragment);
            binding.bottomNavigation.hotspotShadow.setVisibility(View.VISIBLE);
            binding.bottomNavigation.reelsShadow.setVisibility(View.INVISIBLE);
            binding.bottomNavigation.messageShadow.setVisibility(View.INVISIBLE);
            binding.bottomNavigation.profileShadow.setVisibility(View.INVISIBLE);
        });

        binding.bottomNavigation.reelsIcon.setOnClickListener(view -> {
            Navigation.findNavController(this, R.id.home_fragment_Container).navigate(R.id.reels_Fragment);
            binding.bottomNavigation.hotspotShadow.setVisibility(View.INVISIBLE);
            binding.bottomNavigation.reelsShadow.setVisibility(View.VISIBLE);
            binding.bottomNavigation.messageShadow.setVisibility(View.INVISIBLE);
            binding.bottomNavigation.profileShadow.setVisibility(View.INVISIBLE);
        });

        binding.bottomNavigation.messageIcon.setOnClickListener(view -> {
            Navigation.findNavController(this, R.id.home_fragment_Container).navigate(R.id.message_Fragment);
            binding.bottomNavigation.hotspotShadow.setVisibility(View.INVISIBLE);
            binding.bottomNavigation.reelsShadow.setVisibility(View.INVISIBLE);
            binding.bottomNavigation.messageShadow.setVisibility(View.VISIBLE);
            binding.bottomNavigation.profileShadow.setVisibility(View.INVISIBLE);
        });

        binding.bottomNavigation.profileIcon.setOnClickListener(view -> {
            Navigation.findNavController(this, R.id.home_fragment_Container).navigate(R.id.profile_Fragment);
            binding.bottomNavigation.hotspotShadow.setVisibility(View.INVISIBLE);
            binding.bottomNavigation.reelsShadow.setVisibility(View.INVISIBLE);
            binding.bottomNavigation.messageShadow.setVisibility(View.INVISIBLE);
            binding.bottomNavigation.profileShadow.setVisibility(View.VISIBLE);
        });

        binding.bottomNavigation.liveJoinIcon.setOnClickListener(v -> {

            Navigation.findNavController(this, R.id.home_fragment_Container).navigate(R.id.liveMainFragment);
//                final Dialog dialog = new Dialog(this);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.enteroomidialog);
//                dialog.setCanceledOnTouchOutside(true);
//                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.getWindow().setGravity(Gravity.CENTER);
//                dialog.show();
//                EditText  roomEditText = dialog.findViewById(R.id.roomEditText);
//                dialog.findViewById(R.id.yesText).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (roomEditText.getText().toString().trim().isEmpty()){
//                            Alerter.create(HomeActivity.this)
//                                    .setTitle("Room id")
//                                    .setText("Please enter the room id number")
//                                    .setBackgroundColorRes(R.color.app_dark_color)
//                                    .show();
//                        }else {
//                            dialog.dismiss();


//                        }
//
//                    }
//                });
//                dialog.findViewById(R.id.noText).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.dismiss();
//                    }
//                });
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}