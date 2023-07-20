package com.bango.bangoLive.fragments.LeaderBoard;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;
import com.bango.bangoLive.ViewModel.ApiViewModel;
import com.bango.bangoLive.fragments.LeaderBoard.Adapter.LeatherBoardRVAdapter;
import com.bango.bangoLive.fragments.LeaderBoard.ModelClassses.TopHostScreenModel;
import com.bumptech.glide.Glide;
//import com.opensource.svgaplayer.SVGAImageView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class Fragment_Daily extends Fragment {
    View view;
    RecyclerView LeaderboardRV;
    List<TopHostScreenModel.Detail> list = new ArrayList<>();
    LeatherBoardRVAdapter leatherBoardRVAdapter;
    CircleImageView ImageOne, ImageTwo, ImageThree;
//    SVGAImageView profieFrame,profieFrame2,profieFrame3;
    TextView name1, name2, name3, textNoUser, coin1, coin2, coin3, userId1, userId2, userId3;
    LinearLayout layout1, layout2, layout3;

    Context context;


    public Fragment_Daily() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment__daily, container, false);
        findIds(view);
        setAdapter(view);
        apiDaily(view);


        return view;
    }

    private void findIds(View view) {
        LeaderboardRV = view.findViewById(R.id.LeaderboardRV);
//        profieFrame = view.findViewById(R.id.profieFrame);
//        profieFrame2 = view.findViewById(R.id.profieFrame2);
//        profieFrame3 = view.findViewById(R.id.profieFrame3);
        ImageOne = view.findViewById(R.id.imageView1);
        ImageTwo = view.findViewById(R.id.imageView2);
        ImageThree = view.findViewById(R.id.ImageView3);
        name1 = view.findViewById(R.id.name1);
        name2 = view.findViewById(R.id.name2);
        name3 = view.findViewById(R.id.name3);
        layout1 = view.findViewById(R.id.layout1);
        layout2 = view.findViewById(R.id.layout2);
        layout3 = view.findViewById(R.id.layout3);
        textNoUser = view.findViewById(R.id.txtNoUsers_);
        coin1 = view.findViewById(R.id.coin1);
        coin2 = view.findViewById(R.id.coin2);
        coin3 = view.findViewById(R.id.coin3);
        userId1 = view.findViewById(R.id.userId1);
        userId2 = view.findViewById(R.id.userId2);
        userId3 = view.findViewById(R.id.userId3);


    }

    private void setAdapter(View view) {
        leatherBoardRVAdapter = new LeatherBoardRVAdapter(new ArrayList<>(), requireContext());

    }

    private void apiDaily(View view) {

        new ApiViewModel().topHost(requireActivity()).observe(requireActivity(), new Observer<TopHostScreenModel>() {
            @Override
            public void onChanged(TopHostScreenModel rootLeaderBoard) {
                if (rootLeaderBoard != null) {
                    if (rootLeaderBoard.getStatus().equalsIgnoreCase("1") ) {
                        list.clear();
                        list = rootLeaderBoard.getDetails();
                        setData(list);
                    } else {
                        Toast.makeText(requireActivity(), "" + rootLeaderBoard.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireActivity(), "Root is null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setData(List<TopHostScreenModel.Detail> rootLeaderBoard) {

        if (rootLeaderBoard.size() == 0) {

            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.GONE);
            layout3.setVisibility(View.GONE);
            textNoUser.setVisibility(View.VISIBLE);
        }
        if (rootLeaderBoard.size() == 1) {
            Glide.with(requireContext()).load(rootLeaderBoard.get(0).getImage()).placeholder(R.drawable.profilemaleicon).into(ImageOne);
            name1.setText(rootLeaderBoard.get(0).getName());
            userId1.setText(rootLeaderBoard.get(0).getUsername());
            if (rootLeaderBoard.get(0).getGiftdetails() != null) {
                if (rootLeaderBoard.get(0).getGiftdetails().contains(".")) {
                    coin1.setText(rootLeaderBoard.get(0).getGiftdetails().substring(0, rootLeaderBoard.get(0).getGiftdetails().indexOf(".")));
                } else {
                    coin1.setText(rootLeaderBoard.get(0).getGiftdetails());
                }
            }
            if (rootLeaderBoard.get(0).getMoredetails().getFrameDetails()!=null){
//                CommonUtils.setAnimation(requireContext(),rootLeaderBoard.get(0).getMoredetails().getFrameDetails(),profieFrame);
            }

            layout3.setVisibility(View.GONE);
            layout2.setVisibility(View.GONE);
            layout1.setVisibility(View.VISIBLE);
            layout1.setHorizontalGravity(Gravity.CENTER);
            textNoUser.setVisibility(View.VISIBLE);

        } else if (rootLeaderBoard.size() == 2) {
            Glide.with(requireContext()).load(rootLeaderBoard.get(0).getImage()).placeholder(R.drawable.profilemaleicon).into(ImageOne);
            name1.setText(rootLeaderBoard.get(0).getName());
            userId1.setText(rootLeaderBoard.get(0).getUsername());
            Glide.with(requireActivity()).load(rootLeaderBoard.get(1).getImage()).placeholder(R.drawable.profilemaleicon).into(ImageTwo);
            name2.setText(rootLeaderBoard.get(1).getName());

            userId2.setText(rootLeaderBoard.get(1).getUsername());
            if (rootLeaderBoard.get(0).getGiftdetails() != null) {
                if (rootLeaderBoard.get(0).getGiftdetails().contains(".")) {
                    coin1.setText(rootLeaderBoard.get(0).getGiftdetails().substring(0, rootLeaderBoard.get(0).getGiftdetails().indexOf(".")));
                } else {
                    coin1.setText(rootLeaderBoard.get(0).getGiftdetails());
                }
            }
            if (rootLeaderBoard.get(0).getMoredetails().getFrameDetails()!=null){
//                profieFrame.setVisibility(View.VISIBLE);
//                CommonUtils.setAnimation(requireContext(),rootLeaderBoard.get(0).getMoredetails().getFrameDetails(),profieFrame);
            } if (rootLeaderBoard.get(1).getMoredetails().getFrameDetails()!=null){
//                profieFrame2.setVisibility(View.VISIBLE);
//                CommonUtils.setAnimation(requireContext(),rootLeaderBoard.get(1).getMoredetails().getFrameDetails(),profieFrame2);
            }
            if (rootLeaderBoard.get(1).getGiftdetails() != null) {
                if (rootLeaderBoard.get(1).getGiftdetails().contains(".")) {
                    coin2.setText(rootLeaderBoard.get(1).getGiftdetails().substring(0, rootLeaderBoard.get(1).getGiftdetails().indexOf(".")));
                } else {
                    coin2.setText(rootLeaderBoard.get(1).getGiftdetails());
                }
            }
            layout3.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);
            layout1.setVisibility(View.VISIBLE);
            textNoUser.setVisibility(View.VISIBLE);
        } else if (rootLeaderBoard.size() == 3) {

            if (rootLeaderBoard.get(0).getMoredetails().getFrameDetails()!=null){
//                profieFrame.setVisibility(View.VISIBLE);
//                CommonUtils.setAnimation(requireContext(),rootLeaderBoard.get(0).getMoredetails().getFrameDetails(),profieFrame);

            } if (rootLeaderBoard.get(1).getMoredetails().getFrameDetails()!=null){
//                profieFrame2.setVisibility(View.VISIBLE);
//                CommonUtils.setAnimation(requireContext(),rootLeaderBoard.get(1).getMoredetails().getFrameDetails(),profieFrame2);
            }if (rootLeaderBoard.get(2).getMoredetails().getFrameDetails()!=null){
//                profieFrame3.setVisibility(View.VISIBLE);
//                CommonUtils.setAnimation(requireContext(),rootLeaderBoard.get(2).getMoredetails().getFrameDetails(),profieFrame3);
            }
            Toast.makeText(requireContext(), ""+rootLeaderBoard.get(0).getName(), Toast.LENGTH_SHORT).show();
            Glide.with(requireContext()).load(rootLeaderBoard.get(0).getImage()).placeholder(R.drawable.profilemaleicon).into(ImageOne);
            name1.setText(rootLeaderBoard.get(0).getName());

            userId1.setText(rootLeaderBoard.get(0).getUsername());
            Glide.with(requireContext()).load(rootLeaderBoard.get(1).getImage()).placeholder(R.drawable.profilemaleicon).into(ImageTwo);
            name2.setText(rootLeaderBoard.get(1).getName());
            userId2.setText(rootLeaderBoard.get(1).getUsername());
            Glide.with(requireContext()).load(rootLeaderBoard.get(2).getImage()).placeholder(R.drawable.profilemaleicon).into(ImageThree);
            name3.setText(rootLeaderBoard.get(2).getName());
            userId3.setText(rootLeaderBoard.get(2).getUsername());

            if (rootLeaderBoard.get(0).getGiftdetails() != null) {
                if (rootLeaderBoard.get(0).getGiftdetails().contains(".")) {
                    coin1.setText(rootLeaderBoard.get(0).getGiftdetails().substring(0, rootLeaderBoard.get(0).getGiftdetails().indexOf(".")));
                } else {
                    coin1.setText(rootLeaderBoard.get(0).getGiftdetails());
                }
            }
            if (rootLeaderBoard.get(1).getGiftdetails() != null) {
                if (rootLeaderBoard.get(1).getGiftdetails().contains(".")) {
                    coin2.setText(rootLeaderBoard.get(1).getGiftdetails().substring(0, rootLeaderBoard.get(1).getGiftdetails().indexOf(".")));
                } else {
                    coin2.setText(rootLeaderBoard.get(1).getGiftdetails());
                }
            }
            if (rootLeaderBoard.get(2).getGiftdetails() != null) {
                if (rootLeaderBoard.get(2).getGiftdetails().contains(".")) {
                    coin3.setText(rootLeaderBoard.get(2).getGiftdetails().substring(0, rootLeaderBoard.get(2).getGiftdetails().indexOf(".")));
                } else {
                    coin3.setText(rootLeaderBoard.get(2).getGiftdetails());
                }
            }

            layout3.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout1.setVisibility(View.VISIBLE);
            textNoUser.setVisibility(View.VISIBLE);
        } else if (rootLeaderBoard.size() > 3) {
            Glide.with(requireContext()).load(rootLeaderBoard.get(0).getImage()).placeholder(R.drawable.profilemaleicon).into(ImageOne);
            name1.setText(rootLeaderBoard.get(0).getName());
            coin1.setText(rootLeaderBoard.get(0).getGiftdetails());
            Glide.with(requireContext()).load(rootLeaderBoard.get(1).getImage()).placeholder(R.drawable.profilemaleicon).into(ImageTwo);
            name2.setText(rootLeaderBoard.get(1).getName());
            coin2.setText(rootLeaderBoard.get(1).getGiftdetails());
            Glide.with(requireContext()).load(rootLeaderBoard.get(2).getImage()).placeholder(R.drawable.profilemaleicon).into(ImageThree);
            name3.setText(rootLeaderBoard.get(2).getName());
            coin3.setText(rootLeaderBoard.get(2).getGiftdetails());
            layout3.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout1.setVisibility(View.VISIBLE);
            if (rootLeaderBoard.get(0).getMoredetails().getFrameDetails()!=null){
//                profieFrame.setVisibility(View.VISIBLE);
//                CommonUtils.setAnimation(requireContext(),rootLeaderBoard.get(0).getMoredetails().getFrameDetails(),profieFrame);

            } if (rootLeaderBoard.get(1).getMoredetails().getFrameDetails()!=null){
//                profieFrame2.setVisibility(View.VISIBLE);
//                CommonUtils.setAnimation(requireContext(),rootLeaderBoard.get(1).getMoredetails().getFrameDetails(),profieFrame2);
            }if (rootLeaderBoard.get(2).getMoredetails().getFrameDetails()!=null){
//                profieFrame3.setVisibility(View.VISIBLE);
//                CommonUtils.setAnimation(requireContext(),rootLeaderBoard.get(2).getMoredetails().getFrameDetails(),profieFrame3);
            }
            rootLeaderBoard.remove(0);
            rootLeaderBoard.remove(1);
            rootLeaderBoard.remove(2);
            textNoUser.setVisibility(View.GONE);

            LeaderboardRV.setAdapter(new LeatherBoardRVAdapter(rootLeaderBoard, requireContext()));

        }

    }
}