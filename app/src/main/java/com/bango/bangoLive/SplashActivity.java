package com.bango.bangoLive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;

import com.bango.bangoLive.ViewModel.ApiViewModel;
import com.bango.bangoLive.databinding.ActivitySplashBinding;
import com.bango.bangoLive.loginCredentials.activities.fragments.LoginActivity;
import com.bumptech.glide.Glide;
import com.bango.bangoLive.ModelClasses.EntryModelClass;
import com.tapadoo.alerter.Alerter;

public class SplashActivity extends AppCompatActivity {
  ActivitySplashBinding binding;
    SharedPreferences sharedpreferences;
    private String deviceId;
    private boolean canClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        /************************** SHARED PREFERENCES **************************/
        sharedpreferences = this.getSharedPreferences("Bango", Context.MODE_PRIVATE);

        /************************** GET DEVICEID **************************/
        deviceId = Settings.Secure.getString(SplashActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
        sessionManage();
        clicks();
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
    }

    private void clicks() {
        binding.txtSkip.setOnClickListener(view -> {
            if (canClick) {
                if (!sharedpreferences.getString("id","").isEmpty()) {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
            }
        });
    }

    private void sessionManage() {
        if (!sharedpreferences.getString("id","").isEmpty()){
            image(sharedpreferences.getString("id",""));
            //startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        }else {
            image("0");
           // startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }
    }

    private void image(String userId) {
        new ApiViewModel().imageOnEntry(this, deviceId,userId).observe(this, new Observer<EntryModelClass>() {
            @Override
            public void onChanged(EntryModelClass entryModelClass) {
                if (entryModelClass.getStatus() == 1) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            binding.rlBottom.setVisibility(View.GONE);
                            binding.rlSkip.setVisibility(View.VISIBLE);
                            Glide.with(binding.add).load(entryModelClass.getDetails().getMedia()).into(binding.add);
                            startTimer();
                        }
                    }, 8000);
                } else if (entryModelClass.getStatus() == 0) {
                    Alerter.create(SplashActivity.this).setTitle("Banned Alert").setText(entryModelClass.getMessage()).setBackgroundColorRes(R.color.app_dark_color).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    },8000);
//                    Snackbar snackbar = Snackbar.make(binding.getRoot(), entryModelClass.getMessage(), Snackbar.LENGTH_INDEFINITE).setTextColor(Color.WHITE);
//                    snackbar.show();
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (App.getSharedpref().getString(AppConstant.SESSION).equalsIgnoreCase("1")) {
//                                startActivity(new Intent(requireActivity(), HomeMainActivity.class));
//                            } else {
//                                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new LoginFragment()).commit();
//                            }
//                        }
//                    }, 3000);
                }
            }
        });
    }

    private void startTimer() {
        new CountDownTimer(5000, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long l) {
                long minutes = (l / 1000) / 60;
                int seconds = (int) ((l / 1000) % 60);
                String sec, min;
                binding.txtSkip.setText("skip " + seconds);
            }

            @Override
            public void onFinish() {
                canClick = true;
                binding.txtSkip.setText("skip");
//                calculateGifts(pkBattleId);
            }
        }.start();

    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}