package com.bango.bangoLive.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bango.bangoLive.ModelClasses.PopularLiveUsersModelClass;
import com.bango.bangoLive.R;
import com.bango.bangoLive.databinding.FragmentFreshersBinding;

import java.util.ArrayList;
import java.util.List;

public class Freshers_Fragment extends Fragment {
     FragmentFreshersBinding binding;
     List<PopularLiveUsersModelClass> popularLiveUsersModelClassList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentFreshersBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        popularLiveUsersModelClassList=new ArrayList<>();
        popularLiveUsersModelClassList.add(new PopularLiveUsersModelClass(R.drawable.maduridixit));
        popularLiveUsersModelClassList.add(new PopularLiveUsersModelClass(R.drawable.jackquiline));
        popularLiveUsersModelClassList.add(new PopularLiveUsersModelClass(R.drawable.nora));
        popularLiveUsersModelClassList.add(new PopularLiveUsersModelClass(R.drawable.janvikapoor));
        popularLiveUsersModelClassList.add(new PopularLiveUsersModelClass(R.drawable.ashwariya));
        popularLiveUsersModelClassList.add(new PopularLiveUsersModelClass(R.drawable.actress));
        popularLiveUsersModelClassList.add(new PopularLiveUsersModelClass(R.drawable.nehasharma));
        popularLiveUsersModelClassList.add(new PopularLiveUsersModelClass(R.drawable.actress1));
        popularLiveUsersModelClassList.add(new PopularLiveUsersModelClass(R.drawable.priyanka));
        popularLiveUsersModelClassList.add(new PopularLiveUsersModelClass(R.drawable.ananya));
        popularLiveUsersModelClassList.add(new PopularLiveUsersModelClass(R.drawable.videya));
        popularLiveUsersModelClassList.add(new PopularLiveUsersModelClass(R.drawable.disha));
        setAdapterRecyclerViewInAdapter();
    }
    private void setAdapterRecyclerViewInAdapter() {
//        LiveUsersAdapter liveUsersAdapter = new LiveUsersAdapter(requireContext(),popularLiveUsersModelClassList);
//        binding.recyclerView.setAdapter(liveUsersAdapter);
    }
}