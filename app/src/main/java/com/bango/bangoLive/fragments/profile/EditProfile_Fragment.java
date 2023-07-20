package com.bango.bangoLive.fragments.profile;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bango.bangoLive.ModelClasses.LoginResponse;
import com.bango.bangoLive.R;
import com.bango.bangoLive.ViewModel.ApiViewModel;
import com.bango.bangoLive.application.App;
import com.bango.bangoLive.databinding.FragmentEditProfileBinding;
import com.bango.bangoLive.utils.CommonUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditProfile_Fragment extends Fragment {

   FragmentEditProfileBinding binding;
   String stringPhotoPath;
    private String selected_date = "";
    private int mYear, mDay, mMonth;
    private MultipartBody.Part imagePart;
    private String user_name, user_gender, user_DOB, user_lat = "", user_long = "", user_image,userBio ="";
    SharedPreferences sharedpreferences;

    String profileName,profileId,profileImage;
    String profileUniqueId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Set Full Screen Background Window
        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        /************************** SHARED PREFERENCES **************************/
        sharedpreferences = getContext().getSharedPreferences("Bango", Context.MODE_PRIVATE);
        /************************** CLICK LISTENERS METHOD **************************/
        clickListeners();

    }
    private void clickListeners() {

        /************************** BUTTON CLICK OPEN GALLERY METHOD **************************/
        binding.cameraIcon.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 2);
        });

        /************************** GENDER SELECTION CLICK METHOD **************************/
        binding.getGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(requireContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.gender_layout);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.show();
                RadioGroup radioGrp = dialog.findViewById(R.id.radioGrp);
                dialog.findViewById(R.id.done_gender).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int selectedId = radioGrp.getCheckedRadioButtonId();
                        RadioButton radioSexButton = dialog.findViewById(selectedId);
                        //  Toast.makeText(requireContext(), radioSexButton.getText(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        binding.getGender.setText(radioSexButton.getText().toString());
                    }
                });
            }
        });

        /************************** BUTTON CLICK OPEN CALENDER AND SELECT DATEB OF BIRTH METHOD **************************/
        binding.getDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDOBCalender();
            }
        });

        /************************** SAVE BUTTON CLICK TO UPDATE PROFILE **************************/
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitApi();
            }
        });

    }

    private void hitApi() {

        user_name = binding.getName.getText().toString().trim();
        user_gender = binding.getGender.getText().toString().trim();
        userBio = binding.getAddBio.getText().toString().trim();
        user_long = binding.getLocation.getText().toString().trim();

        if (user_name.isEmpty()) {
            binding.getName.setError("Enter Username");
            binding.getName.requestFocus();
        } else if (user_gender.isEmpty()) {
            binding.getGender.setError("Enter gender");
            binding.getName.requestFocus();
        } else if (binding.getAddBio.getText().toString().isEmpty()) {
            binding.getAddBio.setError("Enter Bio");
            binding.getAddBio.requestFocus();
        }

        else {

            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), binding.getName.getText().toString());
            RequestBody number = RequestBody.create(MediaType.parse("text/plain"), binding.getNumber.getText().toString());
            RequestBody gender = RequestBody.create(MediaType.parse("text/plain"),  binding.getGender.getText().toString());
            RequestBody dob = RequestBody.create(MediaType.parse("text/plain"),binding.getDOB.getText().toString());
            RequestBody bio = RequestBody.create(MediaType.parse("text/plain"),binding.getAddBio.getText().toString());
            RequestBody location = RequestBody.create(MediaType.parse("text/plain"), binding.getLocation.getText().toString());
            RequestBody id = RequestBody.create(MediaType.parse("text/plain"),App.getSharedpref().getString("id"));
            RequestBody lat = RequestBody.create(MediaType.parse("text/plain"), "");
            RequestBody longnitude = RequestBody.create(MediaType.parse("text/plain"), "");

            RequestBody country_req = RequestBody.create(MediaType.parse("text/plain"), "");

            //image not send to profile

            Toast.makeText(requireContext(), "photo path :- "+stringPhotoPath, Toast.LENGTH_SHORT).show();
            Log.d("photopath","photooooooooi : "+stringPhotoPath);
            File file1 = new File(stringPhotoPath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image"), "");
            imagePart = MultipartBody.Part.createFormData("image", file1.getName(), requestBody);

            Log.d("Hello", "onChanged: " + name);
            Log.d("Hello", "onChanged: " + number);
            Log.d("Hello", "onChanged: " + gender);
            Log.d("Hello", "onChanged: " + dob);
            Log.d("Hello", "onChanged: " + bio);
            Log.d("Hello", "onChanged: " + id);
            Log.d("Hello", "onChanged: " + imagePart);
            new ApiViewModel().UpdateUserProfile(requireActivity(),
                            name, gender, dob, lat, longnitude,id,number,bio,imagePart)
                    .observe(requireActivity(), new Observer<LoginResponse>() {
                        @Override
                        public void onChanged(LoginResponse updateClass) {

                            if (updateClass.getSuccess().equals("1")) {
                                Log.d(TAG, "onChanged: image=-" + updateClass.getDetails().getImage());
                                Toast.makeText(requireContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("id",updateClass.getDetails().getId());
                                editor.putString("userUniqueId",updateClass.getDetails().getUsername());
                                editor.putString("name",updateClass.getDetails().getName());
                                editor.putString("profileImage",updateClass.getDetails().getImage());
                                editor.commit();
                                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_Container, new Profile_Fragment()).addToBackStack(null).commit();
                            } else {
                                Toast.makeText(requireContext(), "Update Profile Fail", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    /************************** ON ACTIVITY RESULT METHOD **************************/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Uri imageUriPhotos = data.getData();
            stringPhotoPath = uriToStringConvert(imageUriPhotos);
            binding.profileImageView.setImageURI(imageUriPhotos);
        }
    }

    /************************** URI TO STRING CONVERT IMAGE METHOD **************************/
    private  String uriToStringConvert(Uri newUri) {
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

    private void getDOBCalender() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int month, int date) {
                Date myDate = new Date();
                myDate.setMonth(month);
                myDate.setYear(year - 1900);
                myDate.setDate(date);
                getAge(year,month,date);
                SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
                // Format the date to Strings
                String mdy = dmyFormat.format(myDate);
                selected_date = mdy;
                binding.getDOB.setText(selected_date);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        dob.set(year, month, day);
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }
        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();
        user_DOB = ageS ;
        //   Toast.makeText(requireContext(), "age"+ageS, Toast.LENGTH_SHORT).show();
        return ageS;
    }


    @Override
    public void onPause() {
        super.onPause();
        /************************** CLEAR FULL SCREEN BACKGROUND WINDOW **************************/
        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    public void onStart() {
        super.onStart();
        //Set Full Screen Background Window
        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    public void onResume() {
        super.onResume();
        View view1 = requireActivity().findViewById(R.id.bottomNavigation);
        view1.setVisibility(View.GONE);
    }
}