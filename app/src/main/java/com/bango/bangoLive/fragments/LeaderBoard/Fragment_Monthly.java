package com.bango.bangoLive.fragments.LeaderBoard;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.bango.bangoLive.fragments.LeaderBoard.Adapter.Adapter_Monthly;
import com.bango.bangoLive.fragments.LeaderBoard.ModelClassses.RootLeaderBoard;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class Fragment_Monthly extends Fragment {
    View view;
    RecyclerView monthlyRv;
    List<RootLeaderBoard.Detail> listDetail = new ArrayList<>();
    Adapter_Monthly adapter_monthly;
    CircleImageView ImageOne, ImageTwo, ImageThree;
    TextView name1, name2, name3 ,textNoUser;
    LinearLayout layout1 , layout2, layout3 ;

    SharedPreferences sharedpreferences;

    String profileName,profileId,profileImage;


    public Fragment_Monthly() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view  = inflater.inflate(R.layout.fragment__monthly, container, false);
        findIds(view);
        setAdapter(view);
        apiMonthly(view);
        /************************** SHARED PREFERENCES **************************/
        sharedpreferences = getContext().getSharedPreferences("Bango", Context.MODE_PRIVATE);
        profileName = sharedpreferences.getString("name","");
        profileId = sharedpreferences.getString("id","");
        profileImage = sharedpreferences.getString("profileImage","");

        return view ;
    }

    private void findIds(View view) {

        monthlyRv = view.findViewById(R.id.monthly_RV);
        ImageOne = view.findViewById(R.id.imageView1);
        ImageTwo = view.findViewById(R.id.imageView2);
        ImageThree = view.findViewById(R.id.ImageView3);
        name1 = view.findViewById(R.id.name1);
        name2 = view.findViewById(R.id.name2);
        name2 = view.findViewById(R.id.name3);
        layout1 = view.findViewById(R.id.layout1);
        layout2 = view.findViewById(R.id.layout2);
        layout3 = view.findViewById(R.id.layout3);
        textNoUser = view.findViewById(R.id.txtNoUsers_);
    }

    private void setAdapter(View view) {
        adapter_monthly = new Adapter_Monthly(new ArrayList<>(), requireContext());
    }

    private void apiMonthly(View view) {

        new ApiViewModel().monthlyTopGifter(requireActivity(),profileId).observe(requireActivity(), new Observer<RootLeaderBoard>() {
            @Override
            public void onChanged(RootLeaderBoard rootLeaderBoard) {
                if (rootLeaderBoard != null) {
                    if (rootLeaderBoard .getSuccess().equals("1")) {
                        listDetail = rootLeaderBoard.getDetails();
                        try {
                            setData(listDetail);
                        }catch (Exception e){
                        }
                    } else {
                        Toast.makeText(requireActivity(), "" + rootLeaderBoard.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireActivity(), "Root is null", Toast.LENGTH_SHORT).show();
                }
                monthlyRv.setAdapter(adapter_monthly);
            }
        });
    }

    private void setData(List<RootLeaderBoard.Detail>  rootLeaderBoard) {

        if (rootLeaderBoard.size() ==0) {

            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.GONE);
            layout3.setVisibility(View.GONE);
            textNoUser.setVisibility(View.VISIBLE);
        }
        if (rootLeaderBoard.size()==1) {
            Glide.with(requireContext()).load(rootLeaderBoard.get(0).getImage()).into(ImageOne);
            name1.setText(rootLeaderBoard.get(0).getName());
            layout3.setVisibility(View.GONE);
            layout2.setVisibility(View.GONE);
            layout1.setVisibility(View.VISIBLE);
            layout1.setHorizontalGravity(Gravity.CENTER);
            textNoUser.setVisibility(View.VISIBLE);

        }
        if (rootLeaderBoard.size()==2) {
            Glide.with(requireContext()).load(rootLeaderBoard.get(0).getImage()).into(ImageOne);
            name1.setText(rootLeaderBoard.get(0).getName());
            Glide.with(requireActivity()).load(rootLeaderBoard.get(1).getImage()).into(ImageTwo);
            name2.setText(rootLeaderBoard.get(1).getName());
            layout3.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);
            layout1.setVisibility(View.VISIBLE);
            textNoUser.setVisibility(View.VISIBLE);
        }
        if (rootLeaderBoard.size()==3) {
            Glide.with(requireContext()).load(rootLeaderBoard.get(0).getImage()).into(ImageOne);
            name1.setText(rootLeaderBoard.get(0).getName());
            Glide.with(requireContext()).load(rootLeaderBoard.get(1).getImage()).into(ImageTwo);
            name2.setText(rootLeaderBoard.get(1).getName());
            Glide.with(requireContext()).load(rootLeaderBoard.get(1).getImage()).into(ImageThree);
            name3.setText(rootLeaderBoard.get(1).getName());
            layout3.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout1.setVisibility(View.VISIBLE);
            textNoUser.setVisibility(View.VISIBLE);
        }
        if (rootLeaderBoard.size()>3){
            Glide.with(requireContext()).load(rootLeaderBoard.get(0).getImage()).into(ImageOne);
            name1.setText(rootLeaderBoard.get(0).getName());
            Glide.with(requireContext()).load(rootLeaderBoard.get(1).getImage()).into(ImageTwo);
            name2.setText(rootLeaderBoard.get(1).getName());
            Glide.with(requireContext()).load(rootLeaderBoard.get(1).getImage()).into(ImageThree);
            name3.setText(rootLeaderBoard.get(1).getName());
            layout3.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.VISIBLE);
            layout1.setVisibility(View.VISIBLE);

            rootLeaderBoard.remove(0);
            rootLeaderBoard.remove(1);
            rootLeaderBoard.remove(2);
            textNoUser.setVisibility(View.GONE);

            monthlyRv.setAdapter(new Adapter_Monthly(rootLeaderBoard, requireContext()));
            adapter_monthly.loadData(listDetail);
        }

    }

}