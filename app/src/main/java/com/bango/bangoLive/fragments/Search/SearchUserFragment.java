package com.bango.bangoLive.fragments.Search;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bango.bangoLive.R;
import com.bango.bangoLive.ViewModel.ApiViewModel;
import com.bango.bangoLive.databinding.FragmentSearchUserBinding;
import com.bango.bangoLive.fragments.Search.Adapter.SearchUserAdapter;
import com.bango.bangoLive.fragments.Search.modelClass.SearchUserDetail;
import com.bango.bangoLive.fragments.Search.modelClass.SearchUserRoot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SearchUserFragment extends Fragment implements SearchUserAdapter.callBackFromSearchUserAdapter {

    private RecyclerView search_recycler;
    private SearchUserAdapter searchUserAdapter;
    private EditText search;
    public static Boolean status  = true ;
    private List<SearchUserDetail> list = new ArrayList<>();

    FragmentSearchUserBinding binding;
    SharedPreferences sharedpreferences;
    String profileName,profileId,profileImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentSearchUserBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /************************** SHARED PREFERENCES **************************/
        sharedpreferences = getContext().getSharedPreferences("Bango", Context.MODE_PRIVATE);
        profileName = sharedpreferences.getString("name","");
        profileId = sharedpreferences.getString("id","");
        profileImage = sharedpreferences.getString("profileImage","");

        searchUserAdapter = new SearchUserAdapter(SearchUserFragment.this, new ArrayList<>(), getContext());
        binding.searchRecycler.setLayoutManager(new GridLayoutManager(getContext(), 1));
        binding.searchRecycler.setAdapter(searchUserAdapter);
        getUsersList();
        searchData();
    }

    private void searchData() {
        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                binding.search.requestFocus();
                binding.search.setSelection(s.toString().length());
                filter(s.toString());
            }
        });
    }

    private void filter(String text) {
        List<SearchUserDetail> filteredList = new ArrayList<>();
        for (SearchUserDetail item : list) {
            if (item.getName().toLowerCase().contains(text.toLowerCase()) || item.getName().toUpperCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        searchUserAdapter.filterList(filteredList);
        searchUserAdapter.notifyDataSetChanged();
    }


    private void getUsersList() {
        new ApiViewModel().searchUserRootLiveData("", profileId).observe(requireActivity(), new Observer<SearchUserRoot>() {
            @Override
            public void onChanged(SearchUserRoot searchUserRoot) {
                if (searchUserRoot != null) {
                    list = searchUserRoot.getDetails();
                    searchUserAdapter.loadData(list);
                }
            }
        });
    }

    private void findIds(View view) {
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });

    }

    @Override
    public void submit(SearchUserDetail userDetail) {

//        MessagesFragment.other_userId = userDetail.getId();
//        FragmentOtherUserProfile.searchUserDetail = userDetail ;
//        FragmentOtherUserProfile.status = "1";
//        feedOtherUserProfile.searchUserDetail = userDetail;

        Bundle bundle = new Bundle();
        bundle.putSerializable("USERINFO",userDetail);
        Fragment fragment = new FragmentOtherUserProfile();
        fragment.setArguments(bundle);

        // requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new OtherUserProfileFragment()).addToBackStack(null).commit();
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_Container, fragment).addToBackStack(null).commit();

    }

    @Override
    public void onResume() {
        super.onResume();
        View view1 = requireActivity().findViewById(R.id.bottomNavigation);
        view1.setVisibility(View.GONE);
    }


}