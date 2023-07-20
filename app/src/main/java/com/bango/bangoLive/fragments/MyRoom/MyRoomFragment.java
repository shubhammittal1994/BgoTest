package com.bango.bangoLive.fragments.MyRoom;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bango.bangoLive.R;

public class MyRoomFragment extends Fragment {

    private TextView txtRoomName;
   // ModelGetToken.Details details;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_room, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findIds(view);

       // getRoom();
       // clicks();
    }

//    private void clicks() {
//
//
//        txtRoomName.setOnClickListener(view -> {
//
//            if (txtRoomName.getText().toString().equalsIgnoreCase("Join Room")) {
//                App.getSingleton().setHostType("1");
//
//                App.getSingleton().setMainHostUserId(CommonUtils.getUserId());
//
//                Intent intent = new Intent(requireActivity(), CallActivity.class);
//                intent.putExtra("channelName", details.getChannelName());
//                intent.putExtra("userId", CommonUtils.getUserId());
//                intent.putExtra("liveId", details.getId());
//                intent.putExtra("liveType", "multiLive");
//                intent.putExtra("liveStatus", "hostLive");
//                intent.putExtra("token", details.getToke());
//                intent.putExtra("level", details.getUserLeval());
//
//                intent.putExtra("coin", details.getCoin());
//                intent.putExtra("starCount", details.getStarCount());
//                intent.putExtra("status", "2");
//                intent.putExtra("name", details.getName());
//                intent.putExtra("image", App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class).getImage());
//                startActivity(intent);
//
////                                config().setChannelName(response.body().getDetails().getChannelName());
//                App.getSingleton().setAgoraToken(details.getToke());
//
//            } else {
//                Toast.makeText(requireContext(), "No Room", Toast.LENGTH_SHORT).show();
//            }
//
//
//        });
//    }
//
//    private void getRoom() {
//
//
//        new MvvmViewModelClass().checkLastLive(CommonUtils.getUserId()).observe(requireActivity(), new Observer<ModelGetToken>() {
//            @Override
//            public void onChanged(ModelGetToken modelGetToken) {
//                if (modelGetToken.getStatus() == 1) {
//                    txtRoomName.setText("Join Room");
//                    txtRoomName.setVisibility(View.VISIBLE);
//
//                    details = modelGetToken.getDetails();
//                } else {
//                    txtRoomName.setText("No Room");
//                }
//            }
//        });
//    }

    private void findIds(View view) {

        view.findViewById(R.id.back_wallet).setOnClickListener(views -> {
            requireActivity().onBackPressed();
        });

        txtRoomName = view.findViewById(R.id.txtRoomName);
    }
}