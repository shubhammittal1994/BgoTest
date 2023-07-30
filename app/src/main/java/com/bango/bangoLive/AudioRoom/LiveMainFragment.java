package com.bango.bangoLive.AudioRoom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.bango.bangoLive.AudioRoom.AudioRoomModelClass.StartLiveModelClass;
import com.bango.bangoLive.AudioRoom.MODEL.AddPosterImage;
import com.bango.bangoLive.AudioRoom.MODEL.GetPosterImage;
import com.bango.bangoLive.HomeActivity;
import com.bango.bangoLive.R;
import com.bango.bangoLive.ViewModel.ApiViewModel;
import com.bango.bangoLive.ZegoServices.AudioCallPageActivity;
import com.bango.bangoLive.ZegoServices.zegoCloudChat.ChatSDKManager;
import com.bango.bangoLive.application.App;
import com.bango.bangoLive.utils.AppConstant;
import com.bango.bangoLive.utils.CommonUtils;
import com.bango.bangoLive.CallActivity;
import com.bango.bangoLive.fragments.ApplyForHostFragment;
import com.bango.bangoLive.fragments.profile.EditProfile_Fragment;
import com.bumptech.glide.Glide;
import com.tapadoo.alerter.Alerter;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import im.zego.zim.entity.ZIMRoomAdvancedConfig;
import im.zego.zim.entity.ZIMRoomInfo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class LiveMainFragment extends Fragment {
    private ImageView circular_image;
    String userName;
    private final String maxValueOfJoiners = "1";
    androidx.appcompat.app.AlertDialog dailogbox, dialog;
    private String posterImage, test = "";

    SharedPreferences sharedpreferences;

    String profileName, profileId, profileImage, profileUniqueId;
    private RequestBody userIId;
    private EditText liveTitle;
    Animation animation;
    Uri imageUriPhotos;
    String stringPhotoPath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live_main, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @SuppressLint("NewApi")
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Set Full Screen Background Window
        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        /************************** SHARED PREFERENCES **************************/
        sharedpreferences = getContext().getSharedPreferences("Bango", Context.MODE_PRIVATE);
        profileName = sharedpreferences.getString("name", "");
        profileId = sharedpreferences.getString("id", "");
        profileImage = sharedpreferences.getString("profileImage", "");

        animation = AnimationUtils.loadAnimation(requireContext(), R.anim.middle_animation_code);
        view.findViewById(R.id.audio_live).setAnimation(animation);

        liveTitle = view.findViewById(R.id.liveTitle);

        //  getStatus();
        //  getDataSome();
        findIds(view);
        getPosterImage(profileId);

        view.findViewById(R.id.circular_image).setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 2);
        });

        view.findViewById(R.id.cancel_live).setOnClickListener(view13 -> startActivity(new Intent(requireContext(), HomeActivity.class)));

        view.findViewById(R.id.audio_live).setOnClickListener(view1 -> {
            if (profileName.equals("")) {
                updateProfile();
            } else if (App.getSharedpref().getString("image").isEmpty()) {
                Alerter.create(requireActivity()).setTitle("Cover Image Alert").setText("Please select your cover image").setBackgroundColorRes(R.color.app_dark_color).show();
            } else if (liveTitle.getText().toString().isEmpty()) {
                Alerter.create(requireActivity()).setTitle("Live Title Alert").setText("Please select your Live Title").setBackgroundColorRes(R.color.app_dark_color).show();
            } else {
                new ApiViewModel().startAudioLive(requireActivity(), sharedpreferences.getString("id", ""), liveTitle.getText().toString().trim()).observe(requireActivity(), new Observer<StartLiveModelClass>() {
                    @Override
                    public void onChanged(StartLiveModelClass startLiveModelClass) {
                        if (startLiveModelClass.getStatus() == 1) {
                       //     navigateToNextActivity(startLiveModelClass);
                           createRoom(sharedpreferences.getString("id", ""), startLiveModelClass);
                            // Toast.makeText(requireContext(), "liveId" + startLiveModelClass.getDetails().getId(), Toast.LENGTH_SHORT).show();

                        } else {
                            Alerter.create(requireActivity()).setTitle("Alert").setText(startLiveModelClass.getMessage()).setBackgroundColorRes(R.color.app_dark_color).show();
                        }
                    }
                });
            }
        });

    }

//    private void ApplyHost() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//        builder.setMessage("If you want to go Live , Apply for host ");
//        builder.setTitle("Apply for Host");
//        builder.setCancelable(false);
//        builder.setPositiveButton("Yes", (dialog, which) -> {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                requireActivity().getSupportFragmentManager().beginTransaction().
//                        replace(R.id.main_frame_layout, new ApplyForHost()).addToBackStack(null).commit();
//            }else {
//                requireActivity().getSupportFragmentManager().beginTransaction().
//                        replace(R.id.main_frame_layout, new ApplyForHost()).addToBackStack(null).commit();
//            }
//            dialog.dismiss();
//        });
//
//        builder.setNegativeButton("Cancel", (dialog, which) -> {
//            dialog.cancel();
//        });
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void updateProfile() {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage(R.string.if_you_want_to_go_live_create_a_profile_first);
        builder.setTitle(R.string.create_profile);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.yes, (dialog, which) -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_Container, new EditProfile_Fragment()).addToBackStack(null).commit();
            } else {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_Container, new EditProfile_Fragment()).addToBackStack(null).commit();
            }
            dialog.dismiss();
        });

        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void createRoom(String roomID, StartLiveModelClass startLiveModelClass) {
        ZIMRoomInfo zimRoomInfo = new ZIMRoomInfo();
        zimRoomInfo.roomID = roomID;
        zimRoomInfo.roomName = liveTitle.getText().toString();
        ZIMRoomAdvancedConfig config=new ZIMRoomAdvancedConfig();
        config.roomDestroyDelayTime=30;
        ChatSDKManager.getChatSDKManager().enterRoom(zimRoomInfo,config, (roomInfo, errorInfo) ->
                {
                    /* 0 --->>Success
                     * 1--->> Failed
                     * 6000323-->> Room Already Exist
                     * */
                    if (errorInfo.getCode().value() == 0) {
                        navigateToNextActivity(startLiveModelClass);
                       // App.showLog("Room is created" + roomID);
                    } else if (errorInfo.getCode().value() == 1) {
                        App.showToast( getString(R.string.room_creation_failed));
                      //  App.showLog(roomID + " Room creation failed ! " + errorInfo.getMessage());
                    } else if (errorInfo.getCode().value() == 6000323) {
                       // App.showLog(roomID + " room already exists ! " + errorInfo.getMessage());
                    } else {
                        App.showToast( getString(R.string.something_went_wrong_during_room_creation));
                     //   App.showLog(roomID + " Room not created ! " + errorInfo.getMessage());
                    }
                }
        );
    }

    void logoutRoom(){

    }

    void navigateToNextActivity(StartLiveModelClass startLiveModelClass) {
        Intent intent = new Intent(requireContext(), CallActivity.class);
        /*intent.putExtra("userID", sharedpreferences.getString("userUniqueId", ""));
        intent.putExtra("userName", profileName);
        intent.putExtra("roomID",  sharedpreferences.getString("id", ""));
        intent.putExtra("isHost", true);*/
        intent.putExtra("host", true);
        intent.putExtra("liveTitle", liveTitle.getText().toString());
        intent.putExtra("liveId", startLiveModelClass.getDetails().getId());
        intent.putExtra("token", startLiveModelClass.getDetails().getSignature());
        intent.putExtra("roomID", sharedpreferences.getString("id", ""));
        intent.putExtra("profileImage", profileImage);
        intent.putExtra("profileName", profileName);
        intent.putExtra("status", "2");
        intent.putExtra("liveStatus", "hostLive");
        intent.putExtra("liveType", "multiLive");
        intent.putExtra("profileUniqueId", sharedpreferences.getString("userUniqueId", ""));
        intent.putExtra("coverimage", App.getSharedpref().getString("image"));
        intent.putExtra("coverName", startLiveModelClass.getDetails().getLiveTitle());
        intent.putExtra(AppConstant.AM_I_HOST, true);
        startActivity(intent);
    }


//    @SuppressLint("UseCompatLoadingForDrawables")
//    private void streamName() {
//
//        Dialog dialog_share = new Dialog(requireContext());
//        dialog_share.setContentView(R.layout.stream_name);
//        dialog_share.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//        //dialog_share.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//        dialog_share.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.stream_name_back));
//        dialog_share.setCanceledOnTouchOutside(true);
//        Window window = dialog_share.getWindow();
//        window.setGravity(Gravity.CENTER);
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        AppCompatEditText stream_name = dialog_share.findViewById(R.id.stream_name);
//        AppCompatButton confirm_ = dialog_share.findViewById(R.id.confirm_);
//        AppCompatButton cancel = dialog_share.findViewById(R.id.cancle_);
//
//        confirm_.setOnClickListener(v -> {
//            String liveName = Objects.requireNonNull(stream_name.getText()).toString();
//            hitGenerateTokenApi(CommonUtils.getUserId(), "3", liveName);
//            dialog_share.dismiss();
//        });
//        cancel.setOnClickListener(v -> dialog_share.dismiss());
//        dialog_share.show();
//    }


//    private void getStatus() {
//        new MvvmViewModelClass().checkStatusRootLiveData(CommonUtils.getUserId()).observe(requireActivity(), checkStatusRoot -> {
//            if (checkStatusRoot != null) {
//                if (Objects.equals(checkStatusRoot.get("status"), "1")) {
//                    details = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);
//
//                    details.setHost_status(Objects.requireNonNull(checkStatusRoot.get("host_status")).toString());
//
//                    App.getSharedpref().saveModel(AppConstant.USER_INFO, details);
//
//                    Log.d(TAG, "onChanged: " + details.getHost_status());
//                }
//            } else {
//                Toast.makeText(requireContext(), "root is null", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

    private void findIds(@NotNull View view) {
        circular_image = view.findViewById(R.id.circular_image);
    }

    @SuppressLint("SetTextI18n")
    private void showStatus() {

        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.show_host_status);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.show();
        ImageView status_image = dialog.findViewById(R.id.status_image);
        TextView about_status = dialog.findViewById(R.id.about_status);
        status_image.setImageResource(R.drawable.expired);
        about_status.setText("Your request is Rejected");
    }


    @Override
    public void onResume() {
        super.onResume();
        View view1 = requireActivity().findViewById(R.id.bottomNavigation);
        view1.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            assert data != null;
            imageUriPhotos = data.getData();

//            String stringPhotoPath = RealPathUtil.getRealPath(requireActivity(), imageUriPhotos);
//            test = RealPathUtil.getRealPath(requireActivity(), imageUriPhotos);
            stringPhotoPath = uriToStringConvert(imageUriPhotos);
//            String image = imageUriPhotos.getPath().toString();
            hitApiPosterApp(profileId, stringPhotoPath);
            //  getImagePathFromUri(requireContext(),imageUriPhotos);
            circular_image.setImageURI(imageUriPhotos);
        } else {
            Toast.makeText(requireContext(), "Image Uploading Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private String uriToStringConvert(Uri newUri) {
        String path;
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = requireActivity().getContentResolver().query(newUri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        path = cursor.getString(columnIndex);
        cursor.close();
        Toast.makeText(requireActivity(), "" + path, Toast.LENGTH_SHORT).show();
        return path;
    }


//    private String getImagePathFromUri(Context context, Uri imageUri) {
//

//        String imagePath = null;
//        String[] projection = { MediaStore.Images.Media.DATA };
//
//        // Retrieve the file path using the ContentResolver
//        ContentResolver contentResolver = context.getContentResolver();
//        Cursor cursor = contentResolver.query(imageUri, projection, null, null, null);
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                imagePath = cursor.getString(columnIndex);
//                hitApiPosterApp(profileId, imagePath);
//                Toast.makeText(context, "gggggggg"+imagePath, Toast.LENGTH_SHORT).show();
//            }
//            cursor.close();
//        }
//
//          return imagePath;
//    }


    private void hitApiPosterApp(String userId, String stringPhotoPath) {
        userIId = RequestBody.create(MediaType.parse("text/plain"), userId);

        File file1 = new File(stringPhotoPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image"), file1);
        MultipartBody.Part photoPath = MultipartBody.Part.createFormData("image", file1.getName(), requestBody);

//        File file1 = new File(stringPhotoPath);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file1);
//        MultipartBody.Part  photoPath = MultipartBody.Part.createFormData("image/jpeg", file1.getName(), requestBody);
        Log.d("photoPath", "photoPath: " + photoPath);
        Log.d("photoPath", "photoPath: " + userIId);
        new ApiViewModel().addPosterImagee(requireActivity(), userIId, CommonUtils.getFileData(stringPhotoPath, "posterImage")).observe(requireActivity(), new Observer<AddPosterImage>() {
            @Override
            public void onChanged(AddPosterImage addPosterImage) {
                if (addPosterImage.getStatus().equalsIgnoreCase("1")) {
                    Toast.makeText(requireContext(), "" + addPosterImage.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "hhhhh", Toast.LENGTH_SHORT).show();
                }
            }
        });
//            new ApiViewModel().addPosterImagee(userIId,photoPath).observe(requireActivity(), map -> {
//                if (Objects.requireNonNull(map.get("status")).toString().equals("1")) {
//                    Toast.makeText(requireContext(), "" + map.get("message"), Toast.LENGTH_SHORT).show();
//                }
//            });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @SuppressLint("SetTextI18n")
    private void banDialog(String requeststatus) {
        LayoutInflater layoutInflater = LayoutInflater.from(requireContext());
        final View confirmdailog = layoutInflater.inflate(R.layout.dialog_banned, null);
        dailogbox = new androidx.appcompat.app.AlertDialog.Builder(requireContext()).create();
        dailogbox.setCancelable(false);
        dailogbox.setCanceledOnTouchOutside(true);
        dailogbox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dailogbox.setView(confirmdailog);
        dailogbox.show();
        TextView bantext = dailogbox.findViewById(R.id.tv_bantext);
        RelativeLayout button = dailogbox.findViewById(R.id.bt_sentRequest);
        assert bantext != null;
        if (requeststatus == null) {
            bantext.setText("Apply for Host To Go Live");
        } else {
            bantext.setText("Request Under Process");
            assert button != null;
            button.setVisibility(View.GONE);
        }


        assert button != null;
        button.setOnClickListener((View view) -> {
            if (requeststatus == null) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_Container, new ApplyForHostFragment()).addToBackStack(null).commit();
            } else {
                Toast.makeText(requireActivity(), "Request already send to the admin", Toast.LENGTH_SHORT).show();
            }
            dailogbox.dismiss();
        });
    }

    private void getPosterImage(String userId) {
        Toast.makeText(requireContext(), "userId" + userId, Toast.LENGTH_SHORT).show();
        new ApiViewModel().getPosterImage(userId).observe(requireActivity(), new Observer<GetPosterImage>() {
            @Override
            public void onChanged(GetPosterImage getPosterImage) {
                if (getPosterImage.getStatus().equals("1")) {
                    if (!getPosterImage.getDetails().isEmpty()) {
                        App.getSharedpref().saveString("image", getPosterImage.getDetails());
                        Glide.with(requireContext()).load(getPosterImage.getDetails()).placeholder(R.drawable.profilemaleicon).into(circular_image);
                    }
                }
            }
        });
//        new ApiViewModel().getPosterImage(userId).observe(requireActivity(), map -> {
//            if (Objects.requireNonNull(map.get("status")).toString().equals("1")) {
//                if (map.get("details") != null) {
//                    posterImage = map.get("details").toString();
//                    Log.d("posterImage","poster :"+posterImage);
//                    Toast.makeText(requireContext(), "fwgefg"+Objects.requireNonNull(map.get("details")).toString(), Toast.LENGTH_SHORT).show();
//                    Glide.with(requireContext()).load(Objects.requireNonNull(map.get("details")).toString()).placeholder(R.drawable.profilemaleicon).into(circular_image);
//                }else{
//                    Toast.makeText(requireContext(), "deatils null", Toast.LENGTH_SHORT).show();
//                }
//            }else{
//                Toast.makeText(requireContext(), "status null", Toast.LENGTH_SHORT).show();
//            }
//        });


    }

//    private void getDataSome() {
//
//        HashMap<String, String> data = new HashMap<>();
//        data.put("userId", profileId);
//        data.put("otherUserId", profileId);
//
//
//        new ApiViewModel().someFunctionality(requireActivity(), data).observe(requireActivity(), new Observer<OtherUserDataModel>() {
//            @Override
//            public void onChanged(OtherUserDataModel spinOneModal) {
//                if (spinOneModal != null) {
//                    if (spinOneModal.getStatus().equals("1")) {
////                        App.getSharedpref().saveString(AppConstants.MY_FRAME, spinOneModal.getDetails().getMyAppliedFrame());
////                        App.getSharedpref().saveString(AppConstants.Admin_Status, spinOneModal.getDetails().getAdmin());
//                    }
//
//                }
//
//            }
//        });
//    }

    @Override
    public void onPause() {
        super.onPause();
        /************************** CLEAR FULL SCREEN BACKGROUND WINDOW **************************/
        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }


}