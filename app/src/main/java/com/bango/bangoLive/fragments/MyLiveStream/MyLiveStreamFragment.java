package com.bango.bangoLive.fragments.MyLiveStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bango.bangoLive.ModelClasses.MonthlyHistory;
import com.bango.bangoLive.ModelClasses.MothlyModel;
import com.bango.bangoLive.R;
import com.bango.bangoLive.ViewModel.ApiViewModel;
import com.bango.bangoLive.databinding.FragmentMyLiveStreamBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyLiveStreamFragment extends Fragment {

    FragmentMyLiveStreamBinding binding;
    SharedPreferences sharedpreferences;
    String profileName,profileId,profileImage;
    MonthlyHistory.Detail monthly;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMyLiveStreamBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /************************** SHARED PREFERENCES **************************/
        sharedpreferences = getContext().getSharedPreferences("Bango", Context.MODE_PRIVATE);
        profileName = sharedpreferences.getString("name","");
        profileId = sharedpreferences.getString("id","");
        profileImage = sharedpreferences.getString("profileImage","");

        binding.back.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        if (getArguments()==null){
            getCurrentMonthData();
            binding.txtMonthlyRecord.setVisibility(View.VISIBLE);
        }else {
            binding.txtMonthlyRecord.setVisibility(View.GONE);
            monthly = (MonthlyHistory.Detail)getArguments().getSerializable("data");
            setDataMine(monthly);
        }


        binding.txtMonthlyRecord.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_myLiveStreamFragment_to_mothlyLiveHistoryFragment);
        });

        binding.llTotal.setOnClickListener(v -> {
        });

        binding.rlTotalEarning.setOnClickListener(v -> {
        });

        binding.rlTotalEarningTime.setOnClickListener(v -> {
        });
    }

    private void setDataMine(MonthlyHistory.Detail monthly) {
        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObj;
        try {
            dateObj = curFormater.parse(monthly.getDateFrom());
            SimpleDateFormat postFormater = new SimpleDateFormat("MMMM, yyyy");
            binding.txtMonth.setText(postFormater.format(dateObj) );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        binding.txtTotal.setText(monthly.getMonthlyCoins());
        binding.erningRecord.setText(monthly.getMonthlyCoins());
        binding.erningRecord2.setText(monthly.getMonthlyCoins()+" Min");
        binding.erningRecord3.setText(monthly.getLiveDays());
        binding.zerroMinus3.setText(monthly.getBonus1());
        binding.zerroMinus.setText(monthly.getCoinExchange());
        binding.zerroMinusBase.setText(monthly.getBasicSalary());
    }

    private void getCurrentMonthData() {
        new ApiViewModel().userStats(profileId).observe(requireActivity(), new Observer<MothlyModel>() {
            @Override
            public void onChanged(MothlyModel mothlyModel) {
                if (mothlyModel.getStatus()==1){
                    setData(mothlyModel.getDetails());
                }else {
                   // requireActivity().onBackPressed();
                }
            }
        });
    }

    private void setData(MothlyModel.Details details) {
        Date d = new Date();
        CharSequence s  = DateFormat.format("MMMM, yyyy ", d.getTime());
        binding.txtMonth.setText(s);
        binding.txtTotal.setText(details.getMonthlyCoins().toString());
        binding.erningRecord.setText(details.getMonthlyCoins().toString());
        binding.erningRecord2.setText(details.getMonthlyLive()+" Min");
        binding.erningRecord3.setText(details.getLiveDays().toString());
        binding.zerroMinus3.setText(details.getBonus().toString());
        binding.zerroMinus.setText(details.getCoinExchange().toString());
        binding.zerroMinusBase.setText(details.getBasicSalary().toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        View view1 = requireActivity().findViewById(R.id.bottomNavigation);
        view1.setVisibility(View.GONE);
    }
}