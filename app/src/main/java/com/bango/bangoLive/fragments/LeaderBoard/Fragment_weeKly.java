package com.bango.bangoLive.fragments.LeaderBoard;

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
import com.bango.bangoLive.fragments.LeaderBoard.Adapter.Adapter_Weekly;
import com.bango.bangoLive.fragments.LeaderBoard.ModelClassses.TopGifterModel;
import com.bumptech.glide.Glide;

//import com.opensource.svgaplayer.SVGAImageView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class Fragment_weeKly extends Fragment {
    View view;
    RecyclerView weelkyRV;
    List<TopGifterModel.Detail> list = new ArrayList<>();

    CircleImageView ImageOne, ImageTwo, ImageThree;
//    SVGAImageView profieFrame, profieFrame2, profieFrame3;
    TextView name1, name2, name3, textNoUser, coin1, coin2, coin3, userId1, userId2, userId3;
    LinearLayout layout1, layout2, layout3;

    public Fragment_weeKly() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_wee_kly, container, false);
        findIds(view);

        apiWeekly(view);


        return view;
    }

    private void apiWeekly(View view) {
        new ApiViewModel().topGifter("4").observe(requireActivity(), new Observer<TopGifterModel>() {
            @Override
            public void onChanged(TopGifterModel topGifterModel) {
                // Toast.makeText(requireContext(), "1"+topGifterModel.getMessage(), Toast.LENGTH_SHORT).show();
                if (topGifterModel != null) {


                    if (topGifterModel.getStatus().equalsIgnoreCase("1")) {
                        Toast.makeText(requireContext(), "2" + topGifterModel.getMessage(), Toast.LENGTH_SHORT).show();
                        //    txtNoUser.setVisibility(View.GONE);
                        list = topGifterModel.getDetails();

                        setData(list);

                    } else {

                        Toast.makeText(requireContext(), "3" + topGifterModel.getMessage(), Toast.LENGTH_SHORT).show();
                        //  txtNoUser.setVisibility(View.VISIBLE);


                    }
                } else {

                    Toast.makeText(requireContext(), "root is null", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void setData(List<TopGifterModel.Detail> rootLeaderBoard) {
        if (rootLeaderBoard.size() == 0) {

            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.GONE);
            layout3.setVisibility(View.GONE);
            textNoUser.setVisibility(View.VISIBLE);
        }
        if (rootLeaderBoard.size() == 1) {


            Glide.with(requireContext()).load(rootLeaderBoard.get(0).getUserInfo().getImage()).placeholder(R.drawable.profilemaleicon).into(ImageOne);
            name1.setText(rootLeaderBoard.get(0).getUserInfo().getName());
            if (rootLeaderBoard.get(0).getCoin() != null) {
                if (rootLeaderBoard.get(0).getCoin().contains(".")) {
                    coin1.setText(rootLeaderBoard.get(0).getCoin().substring(0, rootLeaderBoard.get(0).getCoin().indexOf(".")));
                } else {
                    coin1.setText(rootLeaderBoard.get(0).getCoin());
                }
            }
            if (rootLeaderBoard.get(0).getUserInfo().getMoredetails().getFrame_details()!=null){
//                profieFrame.setVisibility(View.VISIBLE);
//                CommonUtils.setAnimation(requireContext(),rootLeaderBoard.get(0).getUserInfo().getMoredetails().getFrame_details(),profieFrame);
            }

            userId1.setText(rootLeaderBoard.get(0).getUserInfo().getUsername());
            layout3.setVisibility(View.GONE);
            layout2.setVisibility(View.GONE);
            layout1.setVisibility(View.VISIBLE);
            layout1.setHorizontalGravity(Gravity.CENTER);
            textNoUser.setVisibility(View.VISIBLE);

        }
        if (rootLeaderBoard.size() == 2) {
            Glide.with(requireContext()).load(rootLeaderBoard.get(0).getUserInfo().getImage()).placeholder(R.drawable.profilemaleicon).into(ImageOne);
            name1.setText(rootLeaderBoard.get(0).getUserInfo().getName());
            userId1.setText(rootLeaderBoard.get(0).getUserInfo().getUsername());
            Glide.with(requireActivity()).load(rootLeaderBoard.get(1).getUserInfo().getImage()).placeholder(R.drawable.profilemaleicon).into(ImageTwo);
            name2.setText(rootLeaderBoard.get(1).getUserInfo().getName());

            if (rootLeaderBoard.get(0).getCoin() != null) {
                if (rootLeaderBoard.get(0).getCoin().contains(".")) {
                    coin1.setText(rootLeaderBoard.get(0).getCoin().substring(0, rootLeaderBoard.get(0).getCoin().indexOf(".")));
                } else {
                    coin1.setText(rootLeaderBoard.get(0).getCoin());
                }
            }
            if (rootLeaderBoard.get(0).getUserInfo().getMoredetails().getFrame_details()!=null){
//                profieFrame.setVisibility(View.VISIBLE);
//                CommonUtils.setAnimation(requireContext(),rootLeaderBoard.get(0).getUserInfo().getMoredetails().getFrame_details(),profieFrame);
            } if (rootLeaderBoard.get(1).getUserInfo().getMoredetails().getFrame_details()!=null){
//                profieFrame2.setVisibility(View.VISIBLE);
//                CommonUtils.setAnimation(requireContext(),rootLeaderBoard.get(1).getUserInfo().getMoredetails().getFrame_details(),profieFrame2);
            }
            if (rootLeaderBoard.get(1).getCoin() != null) {
                if (rootLeaderBoard.get(1).getCoin().contains(".")) {
                    coin2.setText(rootLeaderBoard.get(1).getCoin().substring(0, rootLeaderBoard.get(1).getCoin().indexOf(".")));
                } else {
                    coin2.setText(rootLeaderBoard.get(1).getCoin());
                }
            }

            userId2.setText(rootLeaderBoard.get(1).getUserInfo().getUsername());
            layout3.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);
            layout1.setVisibility(View.VISIBLE);
            textNoUser.setVisibility(View.VISIBLE);
        }
        if (rootLeaderBoard.size() == 3) {
            Glide.with(requireContext()).load(rootLeaderBoard.get(0).getUserInfo().getImage()).placeholder(R.drawable.profilemaleicon).into(ImageOne);
            name1.setText(rootLeaderBoard.get(0).getUserInfo().getName());
            userId1.setText(rootLeaderBoard.get(0).getUserInfo().getUsername());
            Glide.with(requireContext()).load(rootLeaderBoard.get(1).getUserInfo().getImage()).placeholder(R.drawable.profilemaleicon).into(ImageTwo);
            name2.setText(rootLeaderBoard.get(1).getUserInfo().getName());
            userId2.setText(rootLeaderBoard.get(1).getUserInfo().getUsername());
            Glide.with(requireContext()).load(rootLeaderBoard.get(2).getUserInfo().getImage()).placeholder(R.drawable.profilemaleicon).into(ImageThree);
            name3.setText(rootLeaderBoard.get(2).getUserInfo().getName());
            userId3.setText(rootLeaderBoard.get(2).getUserInfo().getUsername());


            if (rootLeaderBoard.get(0).getCoin() != null) {
                if (rootLeaderBoard.get(0).getCoin().contains(".")) {
                    coin1.setText(rootLeaderBoard.get(0).getCoin().substring(0, rootLeaderBoard.get(0).getCoin().indexOf(".")));
                } else {
                    coin1.setText(rootLeaderBoard.get(0).getCoin());
                }
            }
            if (rootLeaderBoard.get(0).getUserInfo().getMoredetails().getFrame_details()!=null){
//                profieFrame.setVisibility(View.VISIBLE);
//                CommonUtils.setAnimation(requireContext(),rootLeaderBoard.get(0).getUserInfo().getMoredetails().getFrame_details(),profieFrame);
            } if (rootLeaderBoard.get(1).getUserInfo().getMoredetails().getFrame_details()!=null){
//                profieFrame2.setVisibility(View.VISIBLE);
//                CommonUtils.setAnimation(requireContext(),rootLeaderBoard.get(1).getUserInfo().getMoredetails().getFrame_details(),profieFrame2);
            }if (rootLeaderBoard.get(2).getUserInfo().getMoredetails().getFrame_details()!=null){
//                profieFrame3.setVisibility(View.VISIBLE);
//                CommonUtils.setAnimation(requireContext(),rootLeaderBoard.get(2).getUserInfo().getMoredetails().getFrame_details(),profieFrame3);
            }
            if (rootLeaderBoard.get(1).getCoin() != null) {
                if (rootLeaderBoard.get(1).getCoin().contains(".")) {
                    coin2.setText(rootLeaderBoard.get(1).getCoin().substring(0, rootLeaderBoard.get(1).getCoin().indexOf(".")));
                } else {
                    coin2.setText(rootLeaderBoard.get(1).getCoin());
                }
            }
            if (rootLeaderBoard.get(2).getCoin() != null) {
                if (rootLeaderBoard.get(2).getCoin().contains(".")) {
                    coin3.setText(rootLeaderBoard.get(2).getCoin().substring(0, rootLeaderBoard.get(2).getCoin().indexOf(".")));
                } else {
                    coin3.setText(rootLeaderBoard.get(2).getCoin());
                }
            }
            layout3.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout1.setVisibility(View.VISIBLE);
            textNoUser.setVisibility(View.VISIBLE);
        }
        if (rootLeaderBoard.size() > 3) {
            Glide.with(requireContext()).load(rootLeaderBoard.get(0).getUserInfo().getImage()).placeholder(R.drawable.profilemaleicon).into(ImageOne);
            name1.setText(rootLeaderBoard.get(0).getUserInfo().getName());
            Glide.with(requireContext()).load(rootLeaderBoard.get(1).getUserInfo().getImage()).placeholder(R.drawable.profilemaleicon).into(ImageTwo);
            name2.setText(rootLeaderBoard.get(1).getUserInfo().getName());
            Glide.with(requireContext()).load(rootLeaderBoard.get(2).getUserInfo().getImage()).placeholder(R.drawable.profilemaleicon).into(ImageThree);
            name3.setText(rootLeaderBoard.get(2).getUserInfo().getName());
            if (rootLeaderBoard.get(0).getUserInfo().getMoredetails().getFrame_details()!=null){
//                profieFrame.setVisibility(View.VISIBLE);
//                CommonUtils.setAnimation(requireContext(),rootLeaderBoard.get(0).getUserInfo().getMoredetails().getFrame_details(),profieFrame);
            } if (rootLeaderBoard.get(1).getUserInfo().getMoredetails().getFrame_details()!=null){
//                profieFrame2.setVisibility(View.VISIBLE);
//                CommonUtils.setAnimation(requireContext(),rootLeaderBoard.get(1).getUserInfo().getMoredetails().getFrame_details(),profieFrame2);
            }if (rootLeaderBoard.get(2).getUserInfo().getMoredetails().getFrame_details()!=null){
//                profieFrame3.setVisibility(View.VISIBLE);
//                CommonUtils.setAnimation(requireContext(),rootLeaderBoard.get(2).getUserInfo().getMoredetails().getFrame_details(),profieFrame3);
            }

            if (rootLeaderBoard.get(0).getCoin() != null) {
                if (rootLeaderBoard.get(0).getCoin().contains(".")) {
                    coin1.setText(rootLeaderBoard.get(0).getCoin().substring(0, rootLeaderBoard.get(0).getCoin().indexOf(".")));
                } else {
                    coin1.setText(rootLeaderBoard.get(0).getCoin());
                }
            }
            if (rootLeaderBoard.get(1).getCoin() != null) {
                if (rootLeaderBoard.get(1).getCoin().contains(".")) {
                    coin2.setText(rootLeaderBoard.get(1).getCoin().substring(0, rootLeaderBoard.get(1).getCoin().indexOf(".")));
                } else {
                    coin2.setText(rootLeaderBoard.get(1).getCoin());
                }
            }
            if (rootLeaderBoard.get(2).getCoin() != null) {
                if (rootLeaderBoard.get(2).getCoin().contains(".")) {
                    coin3.setText(rootLeaderBoard.get(2).getCoin().substring(0, rootLeaderBoard.get(2).getCoin().indexOf(".")));
                } else {
                    coin3.setText(rootLeaderBoard.get(2).getCoin());
                }
            }

            layout3.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout1.setVisibility(View.VISIBLE);

            rootLeaderBoard.remove(0);
            rootLeaderBoard.remove(0);
            rootLeaderBoard.remove(0);
            textNoUser.setVisibility(View.GONE);

            weelkyRV.setAdapter(new Adapter_Weekly(rootLeaderBoard, requireContext()));

        }


    }


    private void findIds(View view) {
        weelkyRV = view.findViewById(R.id.weeklyRV);
//        profieFrame3 = view.findViewById(R.id.profieFrame3);
//        profieFrame2 = view.findViewById(R.id.profieFrame2);
//        profieFrame = view.findViewById(R.id.profieFrame);
        ImageOne = view.findViewById(R.id.imageView1);
        ImageTwo = view.findViewById(R.id.imageViewWeekly2);
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
}