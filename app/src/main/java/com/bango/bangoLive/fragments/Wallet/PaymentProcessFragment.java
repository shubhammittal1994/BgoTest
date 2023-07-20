package com.bango.bangoLive.fragments.Wallet;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.airbnb.lottie.LottieAnimationView;
import com.bango.bangoLive.R;
import com.bango.bangoLive.ViewModel.ApiViewModel;
import com.bango.bangoLive.fragments.Wallet.Model.OrderModel;
import com.bango.bangoLive.fragments.profile.Profile_Fragment;


public class PaymentProcessFragment extends Fragment {
    private LottieAnimationView lottieAnimationView;
    private TextView paymentText;
    private String orderId, customerId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_process, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lottieAnimationView = view.findViewById(R.id.animationView);
        paymentText = view.findViewById(R.id.paymentText);


        if (getArguments() != null) {
            orderId = getArguments().getString("orderID");
            customerId = getArguments().getString("customerId");

            new ApiViewModel().makePayment(requireActivity(), orderId, customerId).observe(requireActivity(), new Observer<OrderModel>() {
                @Override
                public void onChanged(OrderModel map) {
                    if (map.getStatus() == 1) {
                        lottieAnimationView.setAnimation(R.raw.paymentsuccess);
                        paymentText.setText("Payment Successful");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_Container, new Profile_Fragment()).addToBackStack(null).commit();

                            }
                        }, 2000);

                    } else {
                        lottieAnimationView.setAnimation(R.raw.paymentfailed);
                        paymentText.setText("Payment failed");
                        requireActivity().onBackPressed();
                    }
                }
            });


        }


    }

    @Override
    public void onResume() {
        super.onResume();

    }
}