package com.bango.bangoLive.loginCredentials.activities.fragments;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.helper.widget.MotionEffect;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.bango.bangoLive.HomeActivity;
import com.bango.bangoLive.ModelClasses.LoginResponse;
import com.bango.bangoLive.R;
import com.bango.bangoLive.ViewModel.ApiViewModel;
import com.bango.bangoLive.application.App;
import com.bango.bangoLive.databinding.FragmentLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.tapadoo.alerter.Alerter;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import im.zego.zim.entity.ZIMUserInfo;

public class Login_Fragment extends Fragment {

    FragmentLoginBinding fragmentLoginBinding;
    private GoogleSignInClient mGoogleSignInClient;
    public FusedLocationProviderClient locationProviderClient;
    private static final int PERMISSION_ID = 44;
    private double latitude, longitude;
    private String country,deviceId;
    public static String regId = "";
    SharedPreferences sharedpreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentLoginBinding=FragmentLoginBinding.inflate(inflater,container,false);
        return fragmentLoginBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // set full Screen Window
        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        /************************** SHARED PREFERENCES **************************/
        sharedpreferences = getContext().getSharedPreferences("Bango", Context.MODE_PRIVATE);

        /************************** GET FIREBASE MESSAGING TOKEN **************************/
        getRegId();

        /************************** SET FULL SCREEN BACKGROUND WINDOW **************************/
        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        /************************** GET DEVICE ID **************************/
        deviceId = Settings.Secure.getString(requireActivity().getContentResolver(), Settings.Secure.ANDROID_ID);

        /************************** GET LOCATION METHOD **************************/
        locationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        getLastLocation();

        /************************** GOOGLE LOGIN METHOD **************************/
        googleLogin();

        /************************** CLICK LISTENER METHOD **************************/
        clickListeners();

    }

    /************************** GET FIREBASE MESSAGING TOKEN **************************/
    private void getRegId() {
//        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
//            if (!task.isSuccessful()) {
//                Log.w(MotionEffect.TAG, "Fetching FCM registration token failed", task.getException());
//                return;
//            }
//            regId = task.getResult();
//        });
    }

    /************************** GET LAST LOCATION **************************/
    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationProviderClient.getLastLocation().addOnCompleteListener(task -> {
                    Location location = task.getResult();
                    if (location == null) {
                        requestNewLocationData();
                    } else {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        getCompleteAddressString();
                    }
                });
            } else {
                Toast.makeText(requireActivity(), "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    /************************** LOGIN GOOGLE SIGN IN OPTIONS **************************/
    private void googleLogin() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
    }

    private void clickListeners() {

        /************************** CLICK IN PHONE ICON **************************/
        fragmentLoginBinding.connectWithPhoneButton.setOnClickListener(view1 -> {
            if (fragmentLoginBinding.checkBox.isChecked()) {
                Navigation.findNavController(requireActivity(), R.id.nav_host_login_fragment).navigate(R.id.action_login_Fragment_to_editPhoneNumber_Fragment);
            }else {
                Alerter.create(requireActivity())
                        .setTitle("By using Bango")
                        .setText("Please  you agree to the Bango Live Terms of Services and Privacy Policy")
                        .setBackgroundColorRes(R.color.app_dark_color)
                        .show();
            }
        });

        /************************** CLICK IN GOOGLE ICON **************************/
        fragmentLoginBinding.googleIcon.setOnClickListener(v -> {
            if (fragmentLoginBinding.checkBox.isChecked()) {
                signIn();
            }else {
                Alerter.create(requireActivity())
                        .setTitle("By using Bango")
                        .setText("Please  you agree to the Bango Live Terms of Services and Privacy Policy")
                        .setBackgroundColorRes(R.color.app_dark_color)
                        .show();
            }
        });
    }

    /************************** GOOGLE SIGN IN INTENT **************************/
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        someActivityResultLauncher.launch(signInIntent);
    }

    /************************** ON ACTIVITY RESULT METHOD **************************/
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        handleSignInResult(task);
                    }
                }
            });


    /************************** HANDLE GOOGLE SIGN IN RESULT **************************/
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            if (account != null) {
                socialLoginWithGoogleandFacebookCaller(account.getEmail(), account.getDisplayName(),account.getId(), String.valueOf(account.getPhotoUrl()));
            }else {
                Toast.makeText(requireContext(), "Google SignIn Account Is Null", Toast.LENGTH_SHORT).show();
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    /************************** SOCIAL LOGIN API METHOD CALL **************************/
    private void socialLoginWithGoogleandFacebookCaller(String email, String displayName, String id, String toString) {
        if (country != null) {
            new ApiViewModel().socialLoginResponseLiveData(displayName,id,email,regId, String.valueOf(latitude), String.valueOf(longitude),toString,deviceId,country).observe(requireActivity(), new Observer<LoginResponse>() {
                @Override
                public void onChanged(LoginResponse loginResponse) {
                    if (loginResponse.getSuccess()!=null) {

                        if (loginResponse.getSuccess().equalsIgnoreCase("1")) {

                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("id",loginResponse.getDetails().getId());
                            editor.putString("userUniqueId",loginResponse.getDetails().getUsername());
                            editor.putString("name",loginResponse.getDetails().getName());
                            editor.putString("profileImage",loginResponse.getDetails().getImage());
                            App.getSharedpref().saveString("id",loginResponse.getDetails().getId());
                            App.getSharedpref().saveString("image",loginResponse.getDetails().getImage());
                            App.getSharedpref().saveString("adminStatus",loginResponse.getDetails().getAdmin());
                            Log.d("key","userId"+loginResponse.getDetails().getId());
                            Log.d("key","name"+loginResponse.getDetails().getName());
                            Log.d("key","profileImage"+loginResponse.getDetails().getImage());
                            editor.commit();
                            startActivity(new Intent(requireContext(), HomeActivity.class));
                        } else {
                            Toast.makeText(requireActivity(), "" + loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(requireContext(), "null", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            if (checkPermissions()) {
                getLastLocation();
            } else {
                Toast.makeText(requireContext(), "Waiting For location Fetching", Toast.LENGTH_SHORT).show();
            }
        }
    }



    /************************** GET COMPLETE ADDRESS **************************/
    private void getCompleteAddressString() {
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(requireActivity(), Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null) {
            Address returnedAddress = addresses.get(0);
            StringBuilder strReturnedAddress = new StringBuilder();
            for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(" ");
            }
            country = addresses.get(0).getCountryName();
        }
    }

    /************************** REQUEST NEW LOCATION DATA **************************/
    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {
        com.google.android.gms.location.LocationRequest mLocationRequest = new com.google.android.gms.location.LocationRequest();
        mLocationRequest.setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);
        locationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        locationProviderClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );
    }

    /************************** GET LOCATION RESULT **************************/
    private final LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
            Geocoder geocoder;
            List<Address> addresses = null;
            geocoder = new Geocoder(requireActivity(), Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert addresses != null;
            if (!addresses.isEmpty()) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();
                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(" ");
                }
                country = addresses.get(0).getCountryName();
            }
        }
    };

    /************************** CHECK LOCATION REQUEST MANIFEST PERMISSION **************************/
    private void requestPermissions() {
        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    /************************** CHECK LOCATION SERVICES **************************/
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /************************** CHECK LOCATION PERMISSION **************************/
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onPause() {
        super.onPause();
        /************************** CLEAR FULL SCREEN BACKGROUND WINDOW **************************/
        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}