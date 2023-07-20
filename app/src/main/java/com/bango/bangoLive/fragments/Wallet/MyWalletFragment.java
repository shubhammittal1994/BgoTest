package com.bango.bangoLive.fragments.Wallet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;
import com.bango.bangoLive.ViewModel.ApiViewModel;
import com.bango.bangoLive.fragments.Wallet.Adapter.Adapter_My_Wallet;
import com.bango.bangoLive.fragments.Wallet.Model.Root_My_Wallet;
import com.bango.bangoLive.fragments.Mall.Model.TotalCoinModal;

import java.util.ArrayList;
import java.util.List;


public class MyWalletFragment extends Fragment implements Adapter_My_Wallet.Callback {
    View view;
    ImageView back_btn;
    TextView prchasedCoin;
    RecyclerView rv_my_wallet;
    Adapter_My_Wallet adapterMyWallet;
    List<Root_My_Wallet.Detail> list = new ArrayList<>();

    SharedPreferences sharedpreferences;

    String profileName,profileId,profileImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_wallet, container, false);
        /************************** SHARED PREFERENCES **************************/
        sharedpreferences = getContext().getSharedPreferences("Bango", Context.MODE_PRIVATE);
        profileName = sharedpreferences.getString("name","");
        profileId = sharedpreferences.getString("id","");
        profileImage = sharedpreferences.getString("profileImage","");

        findIds(view);
        setData(view);
        onClick(view);


        apiCoinPrice(view);

        return view;
    }

    private void apiCoinPrice(View view) {

        new ApiViewModel().coinPrice(requireActivity()).observe(requireActivity(), new Observer<Root_My_Wallet>() {
            @Override
            public void onChanged(Root_My_Wallet root_my_wallet) {
                if (root_my_wallet != null) {
                    if (root_my_wallet.getSuccess().equalsIgnoreCase("1")) {
                        list = root_my_wallet.getDetails();
                        setAdapter(list);
                    }
                } else {
                    Toast.makeText(requireActivity(), "Root is null", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void setAdapter(List<Root_My_Wallet.Detail> list) {

        adapterMyWallet = new Adapter_My_Wallet(list, view.getContext()  , MyWalletFragment.this);
        rv_my_wallet.setAdapter(adapterMyWallet);
    }

    private void setData(View view) {

        new ApiViewModel().getCoinRootLiveData(profileId).observe(requireActivity(), new Observer<TotalCoinModal>() {
            @Override
            public void onChanged(TotalCoinModal getCoinRoot) {

                if (getCoinRoot != null) {

                    if (getCoinRoot.getSuccess().equalsIgnoreCase("1")) {

                        if (getCoinRoot.getDetails() == null) {
                            prchasedCoin.setText("0");

                        } else {
                            prchasedCoin.setText((getCoinRoot.getDetails().getPurchasedCoin()));

                            // Toast.makeText(requireContext(), "coin"+getCoinRoot.getDetails().getCoin(), Toast.LENGTH_SHORT).show();

                        }


                    } else {
                        prchasedCoin.setText("0");

                    }


                }

            }
        });


    }

    private void onClick(View view) {

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_Container,new ProfileFragment()).addToBackStack(null).commit();
            }
        });
    }

    private void findIds(View view) {
        back_btn = view.findViewById(R.id.back_button);
        prchasedCoin = view.findViewById(R.id.purchasedCoin);
        rv_my_wallet = view.findViewById(R.id.My_wallet_recycler);
    }


    @Override
    public void report(Root_My_Wallet.Detail rootMyWallet) {
        Bundle bundle = new Bundle();
        Fragment fragment = new CardFragment();
        bundle.putString("purchasedId", rootMyWallet.getId());

        fragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_Container, fragment).addToBackStack(null).commit();


    }

    @Override
    public void onResume() {
        super.onResume();
        View view1 = requireActivity().findViewById(R.id.bottomNavigation);
        view1.setVisibility(View.VISIBLE);
    }
}