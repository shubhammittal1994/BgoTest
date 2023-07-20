package com.bango.bangoLive.fragments.MyLiveStream;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.bango.bangoLive.ModelClasses.MonthlyHistory;
import com.bango.bangoLive.R;
import com.bango.bangoLive.databinding.FragmentMothlyLiveHistoryBinding;
import com.bango.bangoLive.fragments.MyLiveStream.Adapter.AdapterMonthlyHistory;
import com.bango.bangoLive.ViewModel.ApiViewModel;


import java.util.List;

public class MothlyLiveHistoryFragment extends Fragment {

    FragmentMothlyLiveHistoryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMothlyLiveHistoryBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMontlyData();
        binding.back.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });
    }

    private void getMontlyData() {
        new ApiViewModel().userAllStats(requireActivity(), "49").observe(requireActivity(), new Observer<MonthlyHistory>() {
            @Override
            public void onChanged(MonthlyHistory monthlyHistory) {
                if (monthlyHistory.getStatus() == 1) {
                    binding.noPastLive.setVisibility(View.GONE);
                    setDataAdapter(monthlyHistory.getDetails());
                } else {
                    binding.noPastLive.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setDataAdapter(List<MonthlyHistory.Detail> details) {
        AdapterMonthlyHistory adapterMonthlyHistory = new AdapterMonthlyHistory(details, new AdapterMonthlyHistory.GetData() {
            @Override
            public void getMyData(MonthlyHistory.Detail monthly) {
                Bundle bundle = new Bundle();
                Fragment live = new MyLiveStreamFragment();
                bundle.putSerializable("data", monthly);
                live.setArguments(bundle);
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_Container, live).addToBackStack(null).commit();
            }
        });
        binding.recyMothly.setAdapter(adapterMonthlyHistory);
    }
}