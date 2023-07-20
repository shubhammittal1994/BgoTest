package com.bango.bangoLive.fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.provider.MediaStore;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bango.bangoLive.R;
import com.bango.bangoLive.ViewModel.ApiViewModel;
import com.bango.bangoLive.ModelClasses.CountryList;
import com.bango.bangoLive.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

public class ApplyForHostFragment extends Fragment {


    private Spinner spinner_country_select;
    private EditText get_name, get_number, get_address, get_email_address, get_national_id, get_agency_id;
    private String name, number, address, email_address, national_id, agency_id, country, paymentTypeName, paymentMethodName;
    private RelativeLayout apply_for_host_button;
    private ImageView imageNationaliId;
    private CheckBox checkAudio,checkVideo;
    private LinearLayout clickImage;
    private ActivityResultLauncher<String> requestPermissionLauncher;

    RadioButton paymentMethodButton, paymentTypeButton;
    private RadioGroup paymentMethod, paymentType;
    ArrayList<String> countryList = new ArrayList<>();
    private String stringPhotoPath = "";
    CompoundButton.OnCheckedChangeListener listener;

    SharedPreferences sharedpreferences;
    String profileName,profileId,profileImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_apply_for_host, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        /************************** SHARED PREFERENCES **************************/
        sharedpreferences = getContext().getSharedPreferences("Bango", Context.MODE_PRIVATE);
        profileName = sharedpreferences.getString("name","");
        profileId = sharedpreferences.getString("id","");
        profileImage = sharedpreferences.getString("profileImage","");
        findId(view);
        getCountry();
        OnClick(view);

        if (stringPhotoPath != null) {
            clickImage.setVisibility(View.GONE);
        }
        else {
            clickImage.setVisibility(View.VISIBLE);
        }
    }


    private void changeStatus(RadioButton radioButton, boolean status) {
        radioButton.setOnCheckedChangeListener(null);
        radioButton.setChecked(status);
        radioButton.setOnCheckedChangeListener(listener);
    }

    private void getCountry() {

        new ApiViewModel().getCountries(requireActivity()).observe(requireActivity(), new Observer<CountryList>() {
            @Override
            public void onChanged(CountryList countryClass) {
                if (countryClass.getSuccess().equalsIgnoreCase("1")) {
                    setAdapter(countryClass.getDetails());
                } else {
                   Toast.makeText(requireContext(), "" + countryClass.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void setAdapter(List<CountryList.Detail> details) {

        for (int i = 0; i < details.size(); i++) {
            countryList.add(details.get(i).getName());
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, countryList);
        spinner_country_select.setAdapter(arrayAdapter);
        spinner_country_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner_country_select.setSelection(i);
                country = spinner_country_select.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_CODE_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            setupImagePickerLauncher();
//        } else {
//            Toast.makeText(requireContext(), "Permission denied. Cannot access images.", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//    private void setupImagePickerLauncher() {
//        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
//                Uri selectedImageUri = result.getData().getData();
//                // Use the selected image URI to perform operations on the image
//                // For example, display the image or perform any other required tasks
//                stringPhotoPath = uriToStringConvert(selectedImageUri);
//                imageNationaliId.setImageURI(selectedImageUri);
//            }
//        });
//    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 2 && resultCode == RESULT_OK) {
//
//            Uri imageUriPhotos = data.getData();
//            stringPhotoPath = uriToStringConvert(imageUriPhotos);
//            imageNationaliId.setImageURI(imageUriPhotos);
//
//
//        }
//    }


    private void OnClick(View viewnew) {
        viewnew.findViewById(R.id.imageBack_applyHost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });
        viewnew.findViewById(R.id.imageBack_applyHost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });


        imageNationaliId.setOnClickListener(view -> {


          Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 2);

//            Intent intent = new Intent(Intent.ACTION_PICK);
//            intent.setType("image/*");
//            startActivityForResult(intent, 2);
//
//            int permission = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
//            if (permission != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
//            }
//            else
//            {

//                    Intent intent = new Intent(Intent.ACTION_PICK);
//                    intent.setType("image/*");
//                    startActivityForResult(intent, 2);
//            }
        });



        apply_for_host_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int paymentMethodId = paymentMethod.getCheckedRadioButtonId();
                int paymentTypeId = paymentType.getCheckedRadioButtonId();


                paymentMethodButton = viewnew.findViewById(paymentMethodId);
                paymentTypeButton = viewnew.findViewById(paymentTypeId);

                paymentTypeName = paymentTypeButton.getText().toString();
                paymentMethodName = paymentMethodButton.getText().toString();


                name = get_name.getText().toString();
                number = get_number.getText().toString();
                address = get_address.getText().toString();
                email_address = get_email_address.getText().toString();
                national_id = get_national_id.getText().toString();
                agency_id = get_agency_id.getText().toString();

                if (name.length() == 0) {
                    get_name.setError("Add Real Name");
                    get_name.requestFocus();
                } else if (number.length() == 0 || !Patterns.PHONE.matcher(get_number.getText().toString()).matches()) {
                    get_number.setError("Add Valid Number");
                    get_number.requestFocus();
                } else if (country.length() == 0) {
                    Toast.makeText(requireActivity(), "Select Country", Toast.LENGTH_SHORT).show();
                } else if (email_address.length() == 0 || !Patterns.EMAIL_ADDRESS.matcher(get_email_address.getText().toString()).matches()) {
                    get_email_address.setError("Add  Valid Email Address");
                    get_email_address.requestFocus();
                } else if (national_id.length() == 0) {
                    get_national_id.setError("Enter Valid Id");
                    get_national_id.requestFocus();
                } else if (stringPhotoPath == "") {
                    Toast.makeText(requireActivity(), "Upload National Id", Toast.LENGTH_SHORT).show();

                } else if (agency_id.length() == 0) {

                    get_agency_id.setError("Add  Agency Id");
                    get_agency_id.requestFocus();


                } else {
                    if (!checkAudio.isChecked()&&!checkVideo.isChecked()){
                        Toast.makeText(requireContext(), "Select Host Type", Toast.LENGTH_SHORT).show();
                    }else {
                        if (checkVideo.isChecked()&&checkAudio.isChecked()){
                            hitApi("3");
                        }else if (checkAudio.isChecked()) {
                            hitApi("1");
                        }else if (checkVideo.isChecked()){
                            hitApi("2");

                        }
                    }



//

                }
            }


        });
    }


    private void hitApi(String type_of_host) {

        HashMap<String, RequestBody> data = new HashMap<>();
        data.put("phone", CommonUtils.getRequestBodyText(number));
        data.put("email", CommonUtils.getRequestBodyText(email_address));
        data.put("name", CommonUtils.getRequestBodyText(name));
        data.put("country", CommonUtils.getRequestBodyText(country));
        data.put("national_no", CommonUtils.getRequestBodyText(national_id));
        data.put("address", CommonUtils.getRequestBodyText(address));
        data.put("agencyId", CommonUtils.getRequestBodyText(agency_id));
        data.put("paymentType", CommonUtils.getRequestBodyText(paymentTypeName));
        data.put("userId", CommonUtils.getRequestBodyText(profileId));
        data.put("paymentMethod", CommonUtils.getRequestBodyText(paymentMethodName));
        data.put("type_of_host", CommonUtils.getRequestBodyText(type_of_host));

        new ApiViewModel().getApplyForHost(requireActivity(), data, CommonUtils.getFileData(stringPhotoPath, "nationalId"))
                .observe(requireActivity(), new Observer<Map>() {
                    @Override
                    public void onChanged(Map uploadClass) {
                        if (uploadClass.get("status").toString().equalsIgnoreCase("1")) {
                            Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
                            requireActivity().onBackPressed();
                        }
                        else {
                            Toast.makeText(requireContext(), "" + uploadClass.get("message"), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


    private void findId(View view) {
        spinner_country_select = view.findViewById(R.id.spinner_country_select);
        imageNationaliId = view.findViewById(R.id.imageNationaliId);
//        imageNationaliId.setClickable(true);
        clickImage = view.findViewById(R.id.clickImage);
        paymentMethod = view.findViewById(R.id.radioGrp2);
        checkAudio = view.findViewById(R.id.checkAudio);
        checkVideo = view.findViewById(R.id.checkVideo);
        paymentType = view.findViewById(R.id.radioGrp);
        get_name = view.findViewById(R.id.get_name);
        get_number = view.findViewById(R.id.get_number);
        get_address = view.findViewById(R.id.get_address);
        get_email_address = view.findViewById(R.id.get_email_address);
        get_national_id = view.findViewById(R.id.get_national_id);
        get_agency_id = view.findViewById(R.id.get_agency_id);
        apply_for_host_button = view.findViewById(R.id.apply_for_host_button);
    }

    @Override
    public void onResume() {
        super.onResume();

        View view1 = requireActivity().findViewById(R.id.bottomNavigation);
        view1.setVisibility(View.GONE);

        if (stringPhotoPath != "") {
            clickImage.setVisibility(View.GONE);
        } else {
            clickImage.setVisibility(View.VISIBLE);
        }

    }

  /*  @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {

            assert data != null;
            Uri imageUriPhotos = data.getData();
//            imageNationaliId.setClickable(true);
            stringPhotoPath = RealPathUtil.getRealPath(requireActivity(), imageUriPhotos);

            clickImage.setVisibility(View.GONE);
            imageNationaliId.setImageURI(imageUriPhotos);

        } else {
            Toast.makeText(requireContext(), "Image Uploading Cancelled", Toast.LENGTH_SHORT).show();
            imageNationaliId.setClickable(true);
        }
    }*/

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 2 && resultCode == RESULT_OK) {
//
//            Uri imageUriPhotos = data.getData();
//            stringPhotoPath = uriToStringConvert(imageUriPhotos);
//            imageNationaliId.setImageURI(imageUriPhotos);
//
//
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {

            Uri imageUriPhotos = data.getData();
            stringPhotoPath = uriToStringConvert(imageUriPhotos);
            imageNationaliId.setImageURI(imageUriPhotos);


        }
    }

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


}