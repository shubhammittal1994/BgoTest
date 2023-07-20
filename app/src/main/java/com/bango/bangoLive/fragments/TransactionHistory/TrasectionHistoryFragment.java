package com.bango.bangoLive.fragments.TransactionHistory;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;
import com.bango.bangoLive.ViewModel.ApiViewModel;
import com.bango.bangoLive.fragments.TransactionHistory.Adapter.AdapterTrnsection;
import com.bango.bangoLive.fragments.TransactionHistory.MODEL.CardDetails;

public class TrasectionHistoryFragment extends Fragment {

    private TextView txtNoSendMoney;
    private RecyclerView history_holder;
    private ImageView back;

    String profileName,profileId,profileImage;
    SharedPreferences sharedpreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trasection_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtNoSendMoney = view.findViewById(R.id.txtNoSendMoney);
        history_holder = view.findViewById(R.id.history_holder);
        back = view.findViewById(R.id.back);

        /************************** SHARED PREFERENCES **************************/
        sharedpreferences = getContext().getSharedPreferences("Bango", Context.MODE_PRIVATE);
        profileName = sharedpreferences.getString("name","");
        profileId = sharedpreferences.getString("id","");
        profileImage = sharedpreferences.getString("profileImage","");


        back.setOnClickListener(view1 -> {
            requireActivity().onBackPressed();
        });

        new ApiViewModel().getHistoryy(requireActivity(), profileId).observe(requireActivity(), new Observer<CardDetails>() {
            @Override
            public void onChanged(CardDetails cardDetails) {
                Log.d("meaasage","meaasage"+cardDetails.getMessage());
                if (cardDetails.getSuccess()==1){
                    history_holder.setAdapter(new AdapterTrnsection(cardDetails.getDetails()));
                }else {
                    txtNoSendMoney.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

        View view1 = requireActivity().findViewById(R.id.bottomNavigation);
        view1.setVisibility(View.GONE);

    }
}