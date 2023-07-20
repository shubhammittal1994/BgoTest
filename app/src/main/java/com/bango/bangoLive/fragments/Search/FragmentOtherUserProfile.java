package com.bango.bangoLive.fragments.Search;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.bango.bangoLive.R;
import com.bango.bangoLive.ViewModel.ApiViewModel;
import com.bango.bangoLive.databinding.FragmentOtherUserProfile2Binding;
import com.bango.bangoLive.fragments.Search.Adapter.Adapter_report;
import com.bango.bangoLive.fragments.Search.modelClass.FollowingDataModel;
import com.bango.bangoLive.fragments.Search.modelClass.FollowingRoot;
import com.bango.bangoLive.fragments.Search.modelClass.RootBlockUser;
import com.bango.bangoLive.fragments.Search.modelClass.RootReoprt;
import com.bango.bangoLive.fragments.Search.modelClass.RootSendReport;
import com.bango.bangoLive.fragments.Search.modelClass.SearchUserDetail;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bango.bangoLive.utils.CommonUtils;
import com.google.android.material.tabs.TabLayout;

import java.util.List;


public class FragmentOtherUserProfile extends Fragment {

    FragmentOtherUserProfile2Binding binding;
    private List<RootReoprt.Detail> list;
    Adapter_report adapterReport;
    SearchUserDetail userName_;

    public static String status;
    FollowingDataModel.Detail detail;
    private String getOtherUserId;
    Boolean blockStatus;

    public static SearchUserDetail searchUserDetail;
    public static FollowingDataModel.Detail followingDataModel;
    View view;

    SharedPreferences sharedpreferences;
    String profileName,profileId,profileImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentOtherUserProfile2Binding.inflate(inflater,container,false);
        requireActivity().getWindow().setBackgroundDrawableResource(R.drawable.bg_gradient);
        /************************** SHARED PREFERENCES **************************/
        sharedpreferences = getContext().getSharedPreferences("Bango", Context.MODE_PRIVATE);
        profileName = sharedpreferences.getString("name","");
        profileId = sharedpreferences.getString("id","");
        profileImage = sharedpreferences.getString("profileImage","");

        setTabPro(view);
        setData(view);
        setOnClicks(view);

        return binding.getRoot();
    }


    private void setOnClicks(View view) {
        binding.followUnfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                followOrUnfollow();
            }
        });
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });


        binding.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status == "1") {
//                    ChatFragment.searchUserDetail = searchUserDetail;
//                    ChatFragment.other_userId = searchUserDetail.getId();
//                    MessagesFragment.searchUserDetail = searchUserDetail;
                } else {
//                    ChatFragment.other_userId = detail.getId();
//                    ChatFragment.image = detail.getImage();
//                    ChatFragment.name = detail.getName();
                }
//                boolean followingStatus = getArguments().getBoolean("FollowStatus");
//                Bundle bundle = new Bundle();
//
//                bundle.putBoolean("FollowStatus", followingStatus);
//
//                Fragment fragment = new ChatFragment();
//
//                fragment.setArguments(bundle);
//
//                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, fragment).addToBackStack(null).commit();


            }
        });

        binding.menu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(requireContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_otheruserprofie);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setGravity(Gravity.TOP);
                dialog.show();
                TextView blockUser = dialog.findViewById(R.id.userBlock);
                TextView userReport = dialog.findViewById(R.id.userReport);
                boolean BlockStatus = getArguments().getBoolean("BlockStatus");

                if (BlockStatus) {

                    blockUser.setText("Unblock");
                    binding.followAndsms.setVisibility(View.GONE);

                } else {
                    blockUser.setText("Block");
                }
                blockUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.followAndsms.setVisibility(View.VISIBLE);
                        apiBlockUser(blockUser);
                    }
                });

                userReport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogReport();
                    }
                });
            }
        });
    }


    private void dialogReport() {
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.report_user);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();

        RecyclerView rv_report = dialog.findViewById(R.id.report_recycler);
        apiReportList(rv_report, dialog);

        TextView cancel;
        cancel = dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

    private void apiReportList(RecyclerView rv_report, Dialog dialog) {
        new ApiViewModel().getUserReport(requireActivity()).observe(requireActivity(), new Observer<RootReoprt>() {
            @Override
            public void onChanged(RootReoprt rootReoprt) {
                if (rootReoprt != null) {
                    if (rootReoprt.getSuccess().equalsIgnoreCase("1")) {
                        list = rootReoprt.getDetails();
                        adapterReport = new Adapter_report(list, requireContext(), new Adapter_report.CallBack() {
                            @Override
                            public void reportProfile(RootReoprt.Detail detail) {
                                apiReport(detail, dialog);
                            }
                        });
                        rv_report.setAdapter(adapterReport);
                    }
                } else {
                    Toast.makeText(requireActivity(), "Root is null", Toast.LENGTH_SHORT).show();
                }

                rv_report.setAdapter(adapterReport);
            }
        });

    }

    private void apiReport(RootReoprt.Detail detail, Dialog dialog) {


        new ApiViewModel().userReport(requireActivity(), detail.getId(), profileId, searchUserDetail.getId()).observe(requireActivity(), new Observer<RootSendReport>() {
            @Override
            public void onChanged(RootSendReport rootReoprt) {
                if (rootReoprt != null) {
                    if (rootReoprt.getSuccess().equalsIgnoreCase("1")) {
                        Toast.makeText(requireActivity(), "" + rootReoprt.getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(requireActivity(), "" + rootReoprt.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireActivity(), "Root is null", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    private void apiBlockUser(TextView blockUser) {
        boolean BlockStatus = getArguments().getBoolean("BlockStatus");

        new ApiViewModel().blockUser(requireActivity(), profileId, getOtherUserId).observe(requireActivity(), new Observer<RootBlockUser>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(RootBlockUser rootBlockUser) {
                if (rootBlockUser != null) {
                    if (rootBlockUser.getSuccess().equals(1)) {
                        blockStatus = rootBlockUser.getStatus();
                        blockUser.setText("Unblock");
                        binding.followAndsms.setVisibility(View.GONE);
                        Toast.makeText(requireActivity(), "" + rootBlockUser.getMessage(), Toast.LENGTH_SHORT).show();

                    } else {
                        binding.followAndsms.setVisibility(View.VISIBLE);
                        blockUser.setText("Block");
                    }
                } else {

                    Toast.makeText(requireActivity(), "Root is Null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void followOrUnfollow() {
         new ApiViewModel().followingRootLiveData(profileId, getOtherUserId).observe(requireActivity(), new Observer<FollowingRoot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(FollowingRoot followingRoot) {
                if (followingRoot.success.equals("1")) {
                    if (followingRoot.isFollow_status()) {
                        binding.followUnfo.setText("Following");
                        Toast.makeText(requireActivity(), "" + followingRoot.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        binding.followUnfo.setText("Follow");
                        Toast.makeText(requireActivity(), "" + followingRoot.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {

                }

            }
        });
    }

    private void setData(View view) {

        if (status == "1") {
            userName_ = (SearchUserDetail) getArguments().getSerializable("USERINFO");
            getOtherUserId = userName_.getId();

            if (userName_.getName() != null) {
                binding.userName.setText(userName_.getName());

            } else {
                binding.userName.setText(userName_.getUsername());
            }

            binding.textFollowers.setText(CommonUtils.prettyCount(Long.parseLong(userName_.getFollowerCount())));
            binding.following.setText(CommonUtils.prettyCount(Long.parseLong(userName_.getFollowingUser())));
            binding.send.setText(CommonUtils.prettyCount(Long.parseLong(userName_.getTotalSendCoin())));
            binding.recived.setText(CommonUtils.prettyCount(Long.parseLong(userName_.getTotalGetCoin())));
            binding.gender.setText(userName_.getGender());
            binding.age.setVisibility(View.GONE);
//        MyCoin.setText(CommonUtils.prettyCount(Long.parseLong(userName_.getCoin())));
            binding.bio.setText(userName_.getBio());

            if (userName_.getUserLiveId() != null) {
                binding.liveClick.setVisibility(View.VISIBLE);
            } else {
                binding.liveClick.setVisibility(View.GONE);
            }

            if (userName_.isFollowStatus()) {
                binding.followUnfo.setText("Following");
            } else {
                binding.followUnfo.setText("follow");
            }


            Glide.with(requireContext()).load(userName_.getImage()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    //forgot_loading.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//          forgot_loading.setVisibility(View.GONE);
                    return false;
                }
            }).placeholder(R.drawable.ic_users_copy).into(binding.userDp);
        } else {

            detail = (FollowingDataModel.Detail) getArguments().getSerializable("USERINFO");
            getOtherUserId = detail.getId();


            if (detail.getName() != null) {
                binding.userName.setText(detail.getName());

            } else {
                binding.userName.setText(detail.getUsername());
            }

            binding.textFollowers.setText(CommonUtils.prettyCount(Long.parseLong(detail.getFollowerCount())));
            binding.following.setText(CommonUtils.prettyCount(Long.parseLong(detail.getFollowingUser())));
            if (detail.getTotalSendCoin().equalsIgnoreCase("")) {
                binding.send.setText("0");
            } else {
                binding.send.setText(CommonUtils.prettyCount(Long.parseLong(detail.getTotalSendCoin())));
            }

            if (detail.getTotalGetCoins() != null) {
                if (detail.getTotalGetCoins().equalsIgnoreCase("")) {
                    binding.recived.setText("0");

                } else {
                    binding.recived.setText(CommonUtils.prettyCount(Long.parseLong(detail.getTotalGetCoins())));

                }
            }
            if (detail.getUserLiveId() != null) {
                binding.liveClick.setVisibility(View.VISIBLE);
            } else {
                binding.liveClick.setVisibility(View.GONE);
            }

            binding.gender.setText(detail.getGender());
//        MyCoin.setText(CommonUtils.prettyCount(Long.parseLong(userName_.getCoin())));
            binding.bio.setText(detail.getBio());


            binding.followUnfo.setText("Following");


            Glide.with(requireContext()).load(detail.getImage()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    //forgot_loading.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//          forgot_loading.setVisibility(View.GONE);
                    return false;
                }
            }).placeholder(R.drawable.ic_users_copy).into(binding.userDp);
        }


    }

    @Override
    public void onResume() {
        super.onResume();
//        requireActivity().findViewById(R.id.img_video).setVisibility(View.GONE);
//        requireActivity().findViewById(R.id.rl_main).setVisibility(View.GONE);
    }

    private void setTabPro(View view) {

        binding.tabPro.addTab(binding.tabPro.newTab().setIcon(R.drawable.audience).setText("Feed"));
        binding.tabPro.addTab(binding.tabPro.newTab().setIcon(R.drawable.padlock1).setText("subscription"));

        final PagerAdapter pagerAdapter = new Adapter_Pro(getChildFragmentManager(), binding.tabPro.getTabCount());
        binding.viewpagerPro.setAdapter(pagerAdapter);
        binding.viewpagerPro.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabPro));
        binding.tabPro.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(binding.viewpagerPro));

        binding.tabPro.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#be00ec"), PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#6AECECEC"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public static class Adapter_Pro extends FragmentStatePagerAdapter {
        private final int totalTabs;


        public Adapter_Pro(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
            totalTabs = behavior;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:

                   // return new feedOtherUserProfile();
                case 1:

                   // return new feedOtherUserProfile();

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return totalTabs;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        requireActivity().findViewById(R.id.img_video).setVisibility(View.VISIBLE);
//        requireActivity().findViewById(R.id.rl_main).setVisibility(View.VISIBLE);
    }
}