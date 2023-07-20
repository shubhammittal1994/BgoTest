package com.bango.bangoLive.fragments.TopGifters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;
import com.bango.bangoLive.ViewModel.ApiViewModel;
import com.bango.bangoLive.fragments.LeaderBoard.ModelClassses.TopGifterModel;
import com.bango.bangoLive.fragments.TopGifters.Adapter.LeadeBordOverAll;

import java.util.ArrayList;
import java.util.List;

public class TopGiftersFragment extends Fragment {
    private RecyclerView LeaderboardRV;
    public List<TopGifterModel.Detail> amount = new ArrayList<>();
    private List<TopGifterModel.Detail> TopThree = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_gifters, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findIds(view);
        onClick(view);
        getTopGifter();

        view.findViewById(R.id.topGifterBackImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });
    }

    private void onClick(View view) {
        view.findViewById(R.id.topGifterBackImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             requireActivity().onBackPressed();
            }
        });

    }

    private void findIds(View view) {

        LeaderboardRV = view.findViewById(R.id.LeaderboardRV);

    }

    private void getTopGifter() {
        new ApiViewModel().topGifter("4").observe(requireActivity(), new Observer<TopGifterModel>() {
            @Override
            public void onChanged(TopGifterModel topGifterModel) {
                if (topGifterModel != null) {
                    if (topGifterModel.getStatus().equalsIgnoreCase("1")) {
                        Toast.makeText(requireContext(), "2" + topGifterModel.getMessage(), Toast.LENGTH_SHORT).show();
                        amount = topGifterModel.getDetails();
                        setData(amount);
                    } else {
                        Toast.makeText(requireContext(), "3" + topGifterModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(requireContext(), "root is null", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void setData(List<TopGifterModel.Detail> details) {


        LeadeBordOverAll leatherBoardRVAdapter = new LeadeBordOverAll(details, requireActivity());

        LeaderboardRV.setAdapter(leatherBoardRVAdapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        View view1 = requireActivity().findViewById(R.id.bottomNavigation);
        view1.setVisibility(View.GONE);
    }
}