package com.bango.bangoLive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bango.bangoLive.ZegoServices.zegoCloudChat.ChatSDKManager;
import com.bango.bangoLive.application.App;
import com.bango.bangoLive.databinding.ActivityHomeBinding;

import java.util.ArrayList;

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
        createZimUserInfo();


    }

    private void createZimUserInfo() {
        ZIMUserInfo zimUserInfo = new ZIMUserInfo();
        zimUserInfo.userID = App.getSharedpref().getString("id");
        zimUserInfo.userName = App.getSharedpref().getString("name");

        //App.showToast(HomeActivity.this,"Zim Logged In !!"+id +"&&"+name);
        App.showLog("--->>> " + zimUserInfo.userID + " & " + zimUserInfo.userName);
        ChatSDKManager.getChatSDKManager().login(zimUserInfo, error -> {
            App.showLog("--->>> Zim Logged In !!");
            App.showToast("Zim Logged In !!" + error.getMessage());

            ZIMGroupInfo zimGroupInfo = new ZIMGroupInfo();
            zimGroupInfo.groupID = "114311";
            zimGroupInfo.groupName = "Bella11";

            ChatSDKManager.getChatSDKManager().dismissGroup(zimGroupInfo.groupID, (groupID, errorInfo) -> {
                //App.showLog(groupID + " Error Info:- " + errorInfo.getCode() + " - " + errorInfo.getMessage());

                ChatSDKManager.getChatSDKManager().queryGroupList((groupList, errorInfoGroupList) -> {
                    boolean isGroupAvailable = false;
                    for (int i = 0; i < groupList.size(); i++) {
                        //App.showLog("Group Id:- " + groupList.get(i).baseInfo.groupID);
                        if (groupList.get(i).baseInfo.groupID.equals(zimGroupInfo.groupID)) {
                            isGroupAvailable = true;
                            break;
                        }
                    }

                    if (isGroupAvailable) {
                        //App.showLog("Group is already exists.");
                        ChatSDKManager.getChatSDKManager().joinGroup(zimGroupInfo.groupID, (groupInfo, errorInfoJoin) -> {
                            //App.showLog(groupInfo.baseInfo.groupName + " notificationStatus:- " + groupInfo.notificationStatus.value());
                            //App.showLog(groupInfo.baseInfo.groupName + " Error Info:- " + errorInfoJoin.getCode() + " - " + errorInfoJoin.getMessage());
                        });
                    } else {
                        //App.showLog("Group is not exists.");
                        ChatSDKManager.getChatSDKManager().createGroup(zimGroupInfo, new ArrayList<String>(),
                                (groupInfo, userList, errorUserList, errorInfo1) -> {
                                    //App.showLog(groupInfo.baseInfo.groupName + " User list size:- " + userList.size());
                                    //App.showLog(groupInfo.baseInfo.groupName + " Error User list size:- " + errorUserList.size());
                                    //App.showLog(groupInfo.baseInfo.groupName + " Error Info:- " + errorInfo1.getCode() + " - " + errorInfo1.getMessage());
                                });
                    }
                });
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