package com.bango.bangoLive.ViewModel;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bango.bangoLive.AudioRoom.AudioRoomModelClass.GetLiveHostListAudioModelClass;
import com.bango.bangoLive.AudioRoom.MODEL.AddPosterImage;
import com.bango.bangoLive.AudioRoom.MODEL.GetAudioLiveImages;
import com.bango.bangoLive.AudioRoom.MODEL.GetPosterImage;
import com.bango.bangoLive.AudioRoom.MODEL.ModelAgoraLiveUsers;
import com.bango.bangoLive.AudioRoom.MODEL.OtherUserDataModel;
import com.bango.bangoLive.ModelClasses.Banner.BannerRoot;
import com.bango.bangoLive.ModelClasses.LoginResponse;
import com.bango.bangoLive.ModelClasses.MonthlyHistory;
import com.bango.bangoLive.ModelClasses.MothlyModel;
import com.bango.bangoLive.fragments.LeaderBoard.ModelClassses.RootLeaderBoard;
import com.bango.bangoLive.fragments.LeaderBoard.ModelClassses.TopGifterModel;
import com.bango.bangoLive.fragments.LeaderBoard.ModelClassses.TopHostScreenModel;
import com.bango.bangoLive.AudioRoom.AudioRoomModelClass.StartLiveModelClass;
import com.bango.bangoLive.ModelClasses.CountryList;
import com.bango.bangoLive.ModelClasses.EntryModelClass;
import com.bango.bangoLive.ModelClasses.PasswordResponse;
import com.bango.bangoLive.fragments.Gifts.Model.GiftCategoryModel;
import com.bango.bangoLive.fragments.Gifts.Model.GiftModel;
import com.bango.bangoLive.fragments.Gifts.Model.SendGiftModel;
import com.bango.bangoLive.fragments.Mall.Model.EntryEffectModel;
import com.bango.bangoLive.fragments.Mall.Model.RootFramesAll;
import com.bango.bangoLive.fragments.Mall.Model.TotalCoinModal;
import com.bango.bangoLive.fragments.Mall.ProfileFrame.RootFrames;
import com.bango.bangoLive.fragments.Search.modelClass.FollowingRoot;
import com.bango.bangoLive.fragments.Search.modelClass.RootBlockUser;
import com.bango.bangoLive.fragments.Search.modelClass.RootReoprt;
import com.bango.bangoLive.fragments.Search.modelClass.RootSendReport;
import com.bango.bangoLive.fragments.Search.modelClass.SearchUserRoot;
import com.bango.bangoLive.fragments.TransactionHistory.MODEL.CardDetails;
import com.bango.bangoLive.fragments.Vip.Model.VipDetailsModel;
import com.bango.bangoLive.fragments.Wallet.Model.CardModel;
import com.bango.bangoLive.fragments.Wallet.Model.OrderModel;
import com.bango.bangoLive.fragments.Wallet.Model.Root_My_Wallet;
import com.bango.bangoLive.fragments.Wallet.Model.SavedCard;
import com.bango.bangoLive.subscription.model.MyPuchageEffects;
import com.bango.bangoLive.utils.CommonUtils;
import com.google.gson.JsonArray;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiViewModel extends ViewModel {

    BangoRetrofitIntreface bangoRetrofitIntreface = BangoBaseUrl.getRetrofit().create(BangoRetrofitIntreface.class);
    BangoRetrofitIntreface bangoRetrofitIntrefaceSendGift = BangoBaseUrl.sendGift().create(BangoRetrofitIntreface.class);
    /************************** LOGIN WITH PHONE **************************/

    private MutableLiveData<Map> loginWithPhoneMutableLiveData;
    public LiveData<Map> loginWithPhoneLiveData(Activity activity, String phoneNumber) {
        loginWithPhoneMutableLiveData = new MutableLiveData<>();
        bangoRetrofitIntreface.loginPhone(phoneNumber).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(@NonNull Call<Map> call, @NonNull Response<Map> response) {
                if (response.body() != null) {
                    loginWithPhoneMutableLiveData.postValue(response.body());
                }
                else {
                    Toast.makeText(activity, "Technical Issue Occurred", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<Map> call, @NonNull Throwable t) {
                loginWithPhoneMutableLiveData.postValue(null);
            }
        });
        return loginWithPhoneMutableLiveData;
    }


    /************************** OTP_VERIFY **************************/

    private MutableLiveData<LoginResponse> verify;
    public LiveData<LoginResponse> checkOtpVerfication(Activity activity, String phone, String otp, String deviceId, String regId, String country,String password, String device_type) {
        verify = new MutableLiveData<>();
        bangoRetrofitIntreface.otpVerify(phone, otp, deviceId, regId, country,password,device_type).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    if (response.body() != null) {
                        verify.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Technical Issue Occurred", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                    verify.postValue(null);
                }
            });
        return verify;
    }


    /************************** ADD_PASSWORD **************************/

    private MutableLiveData<PasswordResponse> addPassword;
    public LiveData<PasswordResponse> addPassword(Activity activity, String userId,String password) {
        addPassword = new MutableLiveData<>();
            bangoRetrofitIntreface.addPassword(userId,password).enqueue(new Callback<PasswordResponse>() {
                @Override
                public void onResponse(Call<PasswordResponse> call, Response<PasswordResponse> response) {
                    if (response.body() != null) {
                        addPassword.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Technical Issue Occurred", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<PasswordResponse> call, Throwable t) {
                    Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        return addPassword;
    }


    /************************** SOCIAL_LOGIN **************************/

    public MutableLiveData<LoginResponse> socialLoginResponseMutableLiveData;
    public LiveData<LoginResponse> socialLoginResponseLiveData(String username, String social_id, String email, String reg_id, String latitude, String longitude, String image, String device_id, String country) {
        socialLoginResponseMutableLiveData = new MutableLiveData<>();
        bangoRetrofitIntreface.socialLogin(username, social_id, email, reg_id, latitude, longitude, image, device_id, country).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                socialLoginResponseMutableLiveData.postValue(response.body());
            }
            @Override
            public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                socialLoginResponseMutableLiveData.postValue(null);
            }
        });
        return socialLoginResponseMutableLiveData;
    }


    /************************** UpdateUserProfile **************************/

    private MutableLiveData<LoginResponse> update;
    public LiveData<LoginResponse> UpdateUserProfile(Activity activity, RequestBody name, RequestBody gender, RequestBody dob, RequestBody latitude, RequestBody longitude, RequestBody id, RequestBody password, RequestBody bio, MultipartBody.Part image) {
        update = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgressDialog(activity);
            bangoRetrofitIntreface.updateUser(name, gender, dob, latitude, longitude, id, password, bio, image).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    CommonUtils.dismissDialog();
                    if (response.body() != null) {
                        update.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Technical  Issue", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                    CommonUtils.dismissDialog();
                    Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else
        {
            Toast.makeText(activity, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return update;
    }

    /************************** LOGOUT **************************/
    private MutableLiveData<LoginResponse> logout;
    public LiveData<LoginResponse> logOutClassLiveData(Activity activity, String id) {
        logout = new MutableLiveData<>();
            bangoRetrofitIntreface.logout_user(id).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    CommonUtils.dismissDialog();
                    if (response.body() != null) {
                        logout.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Technical  Issue", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    CommonUtils.dismissDialog();
                    Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        return logout;
    }

    /************************** APPLY HOST **************************/
    private MutableLiveData<Map> getApplyForHost;

    public LiveData<Map> getApplyForHost(Activity activity, HashMap<String, RequestBody> data, MultipartBody.Part image) {
        getApplyForHost = new MutableLiveData<>();
            bangoRetrofitIntreface.getApplyForHost(data, image).enqueue(new Callback<Map>() {
                @Override
                public void onResponse(Call<Map> call, Response<Map> response) {
                    CommonUtils.dismissDialog();
                    if (response.body() != null) {
                        getApplyForHost.postValue(response.body());
                    }
                }
                @Override
                public void onFailure(Call<Map> call, Throwable t) {
                    CommonUtils.dismissDialog();
                    Log.i("Agora :Get live", t.getMessage());
                }
            });
        return getApplyForHost;
    }

    /************************** COUNTRY LIST **************************/
    private MutableLiveData<CountryList> getCountries;
    public LiveData<CountryList> getCountries(Activity activity) {
        getCountries = new MutableLiveData<>();
        bangoRetrofitIntreface.getCountries().enqueue(new Callback<CountryList>() {
            @Override
            public void onResponse(Call<CountryList> call, Response<CountryList> response) {
                if (response.body() != null) {
                    getCountries.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<CountryList> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return getCountries;
    }

    /************************** START LIVE **************************/
    private MutableLiveData<StartLiveModelClass> startLiveModelClassMutableLiveData;
    public LiveData<StartLiveModelClass> startAudioLive(Activity activity, String id, String liveTitle) {
        startLiveModelClassMutableLiveData = new MutableLiveData<>();
        bangoRetrofitIntreface.start_live(id,liveTitle).enqueue(new Callback<StartLiveModelClass>() {
            @Override
            public void onResponse(Call<StartLiveModelClass> call, Response<StartLiveModelClass> response) {
                CommonUtils.dismissDialog();
                if (response.body() != null) {
                    startLiveModelClassMutableLiveData.postValue(response.body());
                } else {
                    Toast.makeText(activity, "Technical  Issue", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<StartLiveModelClass> call, Throwable t) {
                CommonUtils.dismissDialog();
                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return startLiveModelClassMutableLiveData;
    }

    /************************** GET LIVE  **************************/
    private MutableLiveData<GetLiveHostListAudioModelClass> getLiveHostListAudioModelClassMutableLiveData;
    public LiveData<GetLiveHostListAudioModelClass> getLiveHostList(Activity activity, String id) {
        getLiveHostListAudioModelClassMutableLiveData = new MutableLiveData<>();
        bangoRetrofitIntreface.getLiveHostListAudio(id).enqueue(new Callback<GetLiveHostListAudioModelClass>() {
            @Override
            public void onResponse(Call<GetLiveHostListAudioModelClass> call, Response<GetLiveHostListAudioModelClass> response) {
                CommonUtils.dismissDialog();
                if (response.body() != null) {
                    getLiveHostListAudioModelClassMutableLiveData.postValue(response.body());
                } else {
                    Toast.makeText(activity, "Technical  Issue", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<GetLiveHostListAudioModelClass> call, Throwable t) {
                CommonUtils.dismissDialog();
                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return getLiveHostListAudioModelClassMutableLiveData;
    }

    /************************** GET BANNER **************************/
    private MutableLiveData<BannerRoot> bannerRootMutableLiveData;
    public LiveData<BannerRoot> bannerRootLiveData() {
        bannerRootMutableLiveData = new MutableLiveData<>();
        bangoRetrofitIntreface.getBanner().enqueue(new Callback<BannerRoot>() {
            @Override
            public void onResponse(@NotNull Call<BannerRoot> call, @NotNull Response<BannerRoot> response) {
                bannerRootMutableLiveData.setValue(response.body());
            }
            @Override
            public void onFailure(@NotNull Call<BannerRoot> call, @NotNull Throwable t) {
                bannerRootMutableLiveData.setValue(null);
            }
        });
        return bannerRootMutableLiveData;
    }

    /************************** GET FRAMES **************************/
    private MutableLiveData<RootFramesAll> getFrameMLD;
    public LiveData<RootFramesAll> getFrames(Activity activity, String userId) {
        getFrameMLD = new MutableLiveData<>();
            bangoRetrofitIntreface.getFrame(userId).enqueue(new Callback<RootFramesAll>() {
                @Override
                public void onResponse(Call<RootFramesAll> call, Response<RootFramesAll> response) {
                    if (response.body() != null) {
                        getFrameMLD.postValue(response.body());
                    }
                }
                @Override
                public void onFailure(Call<RootFramesAll> call, Throwable t) {
                    Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        return getFrameMLD;
    }

    /************************** PURCHASED FRAMES **************************/
    private MutableLiveData<RootFrames> purchaseFramesMLD;
    public LiveData<RootFrames> purchaseFrames(Activity activity, String userId, String frameId) {
        purchaseFramesMLD = new MutableLiveData<>();
            bangoRetrofitIntreface.purchaseFrames(userId, frameId).enqueue(new Callback<RootFrames>() {
                @Override
                public void onResponse(Call<RootFrames> call, Response<RootFrames> response) {
                    if (response.body() != null) {
                        CommonUtils.dismissDialog();
                        purchaseFramesMLD.postValue(response.body());
                    }
                }
                @Override
                public void onFailure(Call<RootFrames> call, Throwable t) {
                    Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("reportList", t.getMessage());
                }
            });
        return purchaseFramesMLD;
    }

    /************************** GET ENTRY EFFECTS **************************/
    private MutableLiveData<EntryEffectModel> get_enrty_effects;
    public LiveData<EntryEffectModel> get_enrty_effects(Activity activity, String userId) {
        get_enrty_effects = new MutableLiveData<>();
        bangoRetrofitIntreface.get_enrty_effects(userId).enqueue(new Callback<EntryEffectModel>() {
            @Override
            public void onResponse(Call<EntryEffectModel> call, Response<EntryEffectModel> response) {
                if (response.body() != null) {
                    get_enrty_effects.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<EntryEffectModel> call, Throwable t) {
                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("reportList", t.getMessage());
            }
        });
        return get_enrty_effects;
    }

    /************************** PURCHASE ENTRY EFFECTS **************************/
    private MutableLiveData<RootFrames> purchase_entry_effects;
    public LiveData<RootFrames> purchase_entry_effects(Activity activity, String userId, String frameId,String type) {
        purchase_entry_effects = new MutableLiveData<>();
            bangoRetrofitIntreface.purchase_entry_effects(userId, frameId,type).enqueue(new Callback<RootFrames>() {
                @Override
                public void onResponse(Call<RootFrames> call, Response<RootFrames> response) {
                    if (response.body() != null) {
                        purchase_entry_effects.postValue(response.body());
                    }
                }
                @Override
                public void onFailure(Call<RootFrames> call, Throwable t) {
                    Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("reportList", t.getMessage());
                }
            });
        return purchase_entry_effects;
    }

    /************************** USER STATUS **************************/
    public MutableLiveData<MothlyModel> userStats;
    public LiveData<MothlyModel> userStats(String s) {
        userStats = new MutableLiveData<>();
        bangoRetrofitIntreface.userStats(s).enqueue(new Callback<MothlyModel>() {
            @Override
            public void onResponse(@NotNull Call<MothlyModel> call, @NotNull Response<MothlyModel> response) {
                if (response.body() != null) {
                    userStats.setValue(response.body());
                }
            }
            @Override
            public void onFailure(@NotNull Call<MothlyModel> call, @NotNull Throwable t) {
                userStats.setValue(null);
            }
        });
        return userStats;
    }

    /************************** USER ALL STATS **************************/
    MutableLiveData<MonthlyHistory> userAllStats;
    public LiveData<MonthlyHistory> userAllStats(final Activity activity, String data) {
        userAllStats = new MutableLiveData<>();
        if ((CommonUtils.isNetworkConnected(activity))) {
            bangoRetrofitIntreface.userAllStats(data).enqueue(new Callback<MonthlyHistory>() {
                @Override
                public void onResponse(Call<MonthlyHistory> call, Response<MonthlyHistory> response) {
                    if (response.body() != null) {
                        userAllStats.postValue(response.body());
                    }
                }
                @Override
                public void onFailure(Call<MonthlyHistory> call, Throwable t) {
                    Log.i("updatePassword", t.getMessage());
                }
            });
        }
        return userAllStats;
    }

    /************************** SEARCH USERS VIEW MODEL **************************/
    private MutableLiveData<SearchUserRoot> searchUserRootMutableLiveData;
    public LiveData<SearchUserRoot> searchUserRootLiveData(String search, String userId) {
        searchUserRootMutableLiveData = new MutableLiveData<>();
        bangoRetrofitIntreface.getUsersList(search, userId).enqueue(new Callback<SearchUserRoot>() {
            @Override
            public void onResponse(@NotNull Call<SearchUserRoot> call, @NotNull Response<SearchUserRoot> response) {
                searchUserRootMutableLiveData.postValue(response.body());
            }
            @Override
            public void onFailure(@NotNull Call<SearchUserRoot> call, @NotNull Throwable t) {
                searchUserRootMutableLiveData.postValue(null);
            }
        });
        return searchUserRootMutableLiveData;
    }

    /************************** USER REPORT ACTION API VIEW MODEL **************************/
    private MutableLiveData<RootReoprt> getUserReportMLD;
    public LiveData<RootReoprt> getUserReport(Activity activity) {
        getUserReportMLD = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            bangoRetrofitIntreface.getUserReportList().enqueue(new Callback<RootReoprt>() {
                @Override
                public void onResponse(Call<RootReoprt> call, Response<RootReoprt> response) {
                    if (response.body() != null) {
                        getUserReportMLD.postValue(response.body());
                    }
                }
                @Override
                public void onFailure(Call<RootReoprt> call, Throwable t) {
                    Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("reportList", t.getMessage());
                }
            });
        } else {
            Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
        }
        return getUserReportMLD;
    }

    /************************** USER REPORT API VIEW MODEL **************************/
    private MutableLiveData<RootSendReport> userReportMLD;
    public LiveData<RootSendReport> userReport(Activity activity, String userReportActionId, String userId, String otherUserId) {
        userReportMLD = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            bangoRetrofitIntreface.userReport(userReportActionId, userId ,otherUserId).enqueue(new Callback<RootSendReport>() {
                @Override
                public void onResponse(Call<RootSendReport> call, Response<RootSendReport> response) {
                    if (response.body() != null) {
                        userReportMLD.postValue(response.body());
                    }
                }
                @Override
                public void onFailure(Call<RootSendReport> call, Throwable t) {
                    Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("reportList", t.getMessage());
                }
            });
        } else {
            Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
        }
        return userReportMLD;
    }

    /************************** USER BLOCK API VIEW MODEL **************************/
    private MutableLiveData<RootBlockUser> blockUserMLD;
    public LiveData<RootBlockUser> blockUser(Activity activity, String userId, String frameId) {
        blockUserMLD = new MutableLiveData<>();
        bangoRetrofitIntreface.blockUnblock(userId, frameId).enqueue(new Callback<RootBlockUser>() {
            @Override
            public void onResponse(Call<RootBlockUser> call, Response<RootBlockUser> response) {
                if (response.body() != null) {
                    CommonUtils.dismissDialog();
                    blockUserMLD.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<RootBlockUser> call, Throwable t) {
                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("reportList", t.getMessage());
            }
        });
        return blockUserMLD;
    }

    /************************** FOLLOW/UNFOLLOW API VIEW MODEL **************************/
    public MutableLiveData<FollowingRoot> followingRootMutableLiveData;
    public LiveData<FollowingRoot> followingRootLiveData(String userId, String followingUserId) {
        followingRootMutableLiveData = new MutableLiveData<>();
        bangoRetrofitIntreface.followUser(userId, followingUserId).enqueue(new Callback<FollowingRoot>() {
            @Override
            public void onResponse(@NotNull Call<FollowingRoot> call, @NotNull Response<FollowingRoot> response) {
                followingRootMutableLiveData.postValue(response.body());
            }
            @Override
            public void onFailure(@NotNull Call<FollowingRoot> call, @NotNull Throwable t) {
                followingRootMutableLiveData.postValue(null);
            }
        });
        return followingRootMutableLiveData;
    }

    /************************** TOP HOST API VIEW MODEL **************************/
    private MutableLiveData<TopHostScreenModel> topHost;
    public LiveData<TopHostScreenModel> topHost(Activity activity) {
        topHost = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            bangoRetrofitIntreface.topHost().enqueue(new Callback<TopHostScreenModel>() {
                @Override
                public void onResponse(Call<TopHostScreenModel> call, Response<TopHostScreenModel> response) {
                    if (response.body() != null) {
                        topHost.postValue(response.body());
                    }
                }
                @Override
                public void onFailure(Call<TopHostScreenModel> call, Throwable t) {
                    Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("reportList", t.getMessage());
                }
            });
        } else {
            Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
        }
        return topHost;
    }

    /************************** TOP GIFTERS API VIEW MODEL **************************/
    public MutableLiveData<TopGifterModel> topGifter;
    public LiveData<TopGifterModel> topGifter(String type) {
        topGifter = new MutableLiveData<>();
        bangoRetrofitIntreface.topGifter(type).enqueue(new Callback<TopGifterModel>() {
            @Override
            public void onResponse(@NotNull Call<TopGifterModel> call, @NotNull Response<TopGifterModel> response) {
                topGifter.postValue(response.body());
            }
            @Override
            public void onFailure(@NotNull Call<TopGifterModel> call, @NotNull Throwable t) {
                Log.d("onFailure", "onFailure: " + t.getMessage());
            }
        });
        return topGifter;
    }

    /************************** MONTHLY TOP GIFTER VIEW MODEL **************************/
    private MutableLiveData<RootLeaderBoard> monthlyTopGiftersMLd;
    public LiveData<RootLeaderBoard> monthlyTopGifter(Activity activity, String userId) {
        monthlyTopGiftersMLd = new MutableLiveData<>();
        bangoRetrofitIntreface.monthlyTopLiveUserGifting(userId).enqueue(new Callback<RootLeaderBoard>() {
            @Override
            public void onResponse(Call<RootLeaderBoard> call, Response<RootLeaderBoard> response) {
                if (response.body() != null) {
                    monthlyTopGiftersMLd.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<RootLeaderBoard> call, Throwable t) {
                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("reportList", t.getMessage());
            }
        });
        return monthlyTopGiftersMLd;
    }

    /************************** PURCHASE COINS VIEW MODEL **************************/
    private MutableLiveData<Root_My_Wallet> coinPriceMLd;
    public LiveData<Root_My_Wallet> coinPrice(Activity activity) {
        coinPriceMLd = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            bangoRetrofitIntreface.coinPrice().enqueue(new Callback<Root_My_Wallet>() {
                @Override
                public void onResponse(Call<Root_My_Wallet> call, Response<Root_My_Wallet> response) {
                    if (response.body() != null) {
                        coinPriceMLd.postValue(response.body());
                    }
                }
                @Override
                public void onFailure(Call<Root_My_Wallet> call, Throwable t) {
                    Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("reportList", t.getMessage());
                }
            });
        } else {
            Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
        }
        return coinPriceMLd;
    }

    /************************** GET COINS VIEW MODEL **************************/
    public MutableLiveData<TotalCoinModal> getCoinRootMutableLiveData;
    public LiveData<TotalCoinModal> getCoinRootLiveData(String userId) {
        getCoinRootMutableLiveData = new MutableLiveData<>();
        bangoRetrofitIntreface.getCoin(userId).enqueue(new Callback<TotalCoinModal>() {
            @Override
            public void onResponse(@NotNull Call<TotalCoinModal> call, @NotNull Response<TotalCoinModal> response) {
                getCoinRootMutableLiveData.postValue(response.body());
            }
            @Override
            public void onFailure(@NotNull Call<TotalCoinModal> call, @NotNull Throwable t) {
                getCoinRootMutableLiveData.postValue(null);
            }
        });
        return getCoinRootMutableLiveData;
    }

    /************************** GET SAVE CARD VIEW MODEL **************************/
    private MutableLiveData<SavedCard> getSaveCard;
    public LiveData<SavedCard> getSaveCard(Activity activity, String userId) {
        getSaveCard = new MutableLiveData<>();
        bangoRetrofitIntreface.getSaveCard(userId).enqueue(new Callback<SavedCard>() {
            @Override
            public void onResponse(Call<SavedCard> call, Response<SavedCard> response) {
                if (response.body() != null) {
                    getSaveCard.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<SavedCard> call, Throwable t) {
                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("reportList", t.getMessage());
            }
        });
        return getSaveCard;
    }

    /************************** REMOVE CARD VIEW MODEL **************************/
    private MutableLiveData<SavedCard> removeSavedCard;
    public LiveData<SavedCard> removeSavedCard(Activity activity, String userId) {
        removeSavedCard = new MutableLiveData<>();
        bangoRetrofitIntreface.removeSavedCard(userId).enqueue(new Callback<SavedCard>() {
            @Override
            public void onResponse(Call<SavedCard> call, Response<SavedCard> response) {
                if (response.body() != null) {
                    removeSavedCard.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<SavedCard> call, Throwable t) {
                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("reportList", t.getMessage());
            }
        });
        return removeSavedCard;
    }

    /************************** ORDERED DETAILS VIEW MODEL **************************/
    private MutableLiveData<OrderModel> orderDetails;
    public LiveData<OrderModel> orderDetails(Activity activity, String userId, String vipId) {
        orderDetails = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            bangoRetrofitIntreface.orderDetails(userId, vipId).enqueue(new Callback<OrderModel>() {
                @Override
                public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                    if (response.body() != null) {
                        orderDetails.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Technical issue", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<OrderModel> call, Throwable t) {
                    orderDetails.postValue(null);
                    Toast.makeText(activity, "onFailure " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Connect to network", Toast.LENGTH_SHORT).show();
        }
        return orderDetails;
    }

    /************************** REGISTERED CARD VIEW MODEL **************************/
    private MutableLiveData<CardModel> regeisterCard;
    public LiveData<CardModel> regeisterCard(Activity activity, long cardNumber, int expMonth, int expDay, int cvv, String add_line_one,String name,int save, String data, String dataTwo) {
        regeisterCard = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgressDialog(activity);
            bangoRetrofitIntreface.regeisterCard(cardNumber, expMonth, expDay, cvv, add_line_one,name,save, data, dataTwo).enqueue(new Callback<CardModel>() {
                @Override
                public void onResponse(Call<CardModel> call, Response<CardModel> response) {
                    if (response.body() != null) {
                        CommonUtils.dismissDialog();
                        regeisterCard.postValue(response.body());
                    } else {
                        CommonUtils.dismissDialog();
                        Toast.makeText(activity, "Technical issue", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<CardModel> call, Throwable t) {
                    regeisterCard.postValue(null);
                    CommonUtils.dismissDialog();
                    Toast.makeText(activity, "onFailure " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Connect to network", Toast.LENGTH_SHORT).show();
        }
        return regeisterCard;
    }

    /************************** MAKE PAYMENT VIEW MODEL **************************/
    private MutableLiveData<OrderModel> makePayment;
    public LiveData<OrderModel> makePayment(Activity activity, String userId, String vipId) {
        makePayment = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            bangoRetrofitIntreface.makePayment(userId, vipId).enqueue(new Callback<OrderModel>() {
                @Override
                public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                    if (response.body() != null) {
                        makePayment.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Technical issue", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<OrderModel> call, Throwable t) {
                    makePayment.postValue(null);
                    Toast.makeText(activity, "onFailure " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Connect to network", Toast.LENGTH_SHORT).show();
        }
        return makePayment;
    }

    /************************** GET GIFT CATEGORIES **************************/
    private MutableLiveData<GiftCategoryModel> giftCategoryModelMutableLiveData;
    public LiveData<GiftCategoryModel> giftCategory(Activity activity) {
        giftCategoryModelMutableLiveData = new MutableLiveData<>();
        bangoRetrofitIntreface.giftCategory().enqueue(new Callback<GiftCategoryModel>() {
            @Override
            public void onResponse(Call<GiftCategoryModel> call, Response<GiftCategoryModel> response) {
                if (response.body() != null) {
                    giftCategoryModelMutableLiveData.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<GiftCategoryModel> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return giftCategoryModelMutableLiveData;
    }

    /************************** GET VIP DETAILS **************************/
    public MutableLiveData<VipDetailsModel> getVipDetails;
    public LiveData<VipDetailsModel> getVipDetails(String userId) {
        getVipDetails = new MutableLiveData<>();
        bangoRetrofitIntreface.getVipDetails(userId).enqueue(new Callback<VipDetailsModel>() {
            @Override
            public void onResponse(@NotNull Call<VipDetailsModel> call, @NotNull Response<VipDetailsModel> response) {
                getVipDetails.postValue(response.body());
            }
            @Override
            public void onFailure(@NotNull Call<VipDetailsModel> call, @NotNull Throwable t) {
                getVipDetails.postValue(null);
            }
        });
        return getVipDetails;
    }

    /************************** LIVE GIFTS **************************/
    private MutableLiveData<GiftModel> lievGiftData;
    public LiveData<GiftModel> sendLiveGift(final Activity activity, String userId, String giftCategoryId) {
        lievGiftData = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            bangoRetrofitIntreface.getLiveGifts(userId, giftCategoryId).enqueue(new Callback<GiftModel>() {
                @Override
                public void onResponse(Call<GiftModel> call, Response<GiftModel> response) {
                    if (response.body() != null) {
                        lievGiftData.postValue(response.body());
                    }
                }
                @Override
                public void onFailure(Call<GiftModel> call, Throwable t) {
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        return lievGiftData;
    }

    /************************** SEND MULTI LIVE GIFT VIEW MODEL **************************/
    private MutableLiveData<SendGiftModel> addContacts;
    public LiveData<SendGiftModel> send_multiple_live_gift(Activity activity, JsonArray userContact) {
        addContacts = new MutableLiveData<>();
        bangoRetrofitIntreface.send_multiple_live_gift(userContact).enqueue(new Callback<SendGiftModel>() {
            @Override
            public void onResponse(Call<SendGiftModel> call, Response<SendGiftModel> response) {
                if (response.body() != null) {
                    addContacts.postValue(response.body());
                } else {
                    Toast.makeText(activity, "Issue in  Model" + response.body(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SendGiftModel> call, Throwable t) {

                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return addContacts;
    }

    /************************** ADD POSTER IMAGE API VIEW MODEL **************************/
    private MutableLiveData<AddPosterImage> addPosterImageMutableLiveData;
    public LiveData<AddPosterImage> addPosterImagee(Activity activity,RequestBody userId, MultipartBody.Part posterImage) {
        addPosterImageMutableLiveData = new MutableLiveData<>();
        bangoRetrofitIntreface.addPosterImage(userId, posterImage).enqueue(new Callback<AddPosterImage>() {
            @Override
            public void onResponse(@NotNull Call<AddPosterImage> call, @NotNull Response<AddPosterImage> response) {
                addPosterImageMutableLiveData.postValue(response.body());
                Toast.makeText(activity, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(@NotNull Call<AddPosterImage> call, @NotNull Throwable t) {
                addPosterImageMutableLiveData.postValue(null);
                Toast.makeText(activity, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return addPosterImageMutableLiveData;
    }

    /************************** GET POSTER IMAGE API VIEW MODEL **************************/
    public MutableLiveData<GetPosterImage> getPosterImage;
    public LiveData<GetPosterImage> getPosterImage(String userId) {
        getPosterImage = new MutableLiveData<>();
        bangoRetrofitIntreface.getPosterImage(userId).enqueue(new Callback<GetPosterImage>() {
            @Override
            public void onResponse(@NotNull Call<GetPosterImage> call, @NotNull Response<GetPosterImage> response) {
                getPosterImage.postValue(response.body());
            }
            @Override
            public void onFailure(@NotNull Call<GetPosterImage> call, @NotNull Throwable t) {
                getPosterImage.postValue(null);
            }
        });
        return getPosterImage;
    }

    /************************** GET SOME FUNCTIONALITY API VIEW MODEL **************************/
    public MutableLiveData<OtherUserDataModel> someFunctionality;
    public LiveData<OtherUserDataModel> someFunctionality(Activity activity, HashMap<String, String> data) {
        someFunctionality = new MutableLiveData<>();
        bangoRetrofitIntreface.someFunctionality(data).enqueue(new Callback<OtherUserDataModel>() {
            @Override
            public void onResponse(@NotNull Call<OtherUserDataModel> call, @NotNull Response<OtherUserDataModel> response) {
                if (response.body()!=null){
                    someFunctionality.setValue(response.body());
                }else {
                    Toast.makeText(activity, "response null", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NotNull Call<OtherUserDataModel> call, @NotNull Throwable t) {
                Log.d("apiiiiiiii","failure :"+t.getMessage());
                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                someFunctionality.setValue(null);
            }
        });
        return someFunctionality;
    }

    /************************** IMAGE ON ENTRY API VIEW MODEL **************************/
    private MutableLiveData<EntryModelClass> imageOnEntry;
    public LiveData<EntryModelClass> imageOnEntry(Activity activity, String deviceId, String userId) {
        imageOnEntry = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            bangoRetrofitIntreface.imageOnEntryy(deviceId,userId).enqueue(new Callback<EntryModelClass>() {
                @Override
                public void onResponse(Call<EntryModelClass> call, Response<EntryModelClass> response) {
                        if (response.code()==401){
                            Log.d("reponseeeeeeeeeeeeeeee","responseeedgfddedtetrdetre : "+response.code());
                        }else {
                            imageOnEntry.postValue(response.body());
                        }

                }
                @Override
                public void onFailure(Call<EntryModelClass> call, Throwable t) {
                    Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("reportList", t.getMessage());
                }
            });
        } else {
            Toast.makeText(activity, "Check  Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return imageOnEntry;
    }

    /************************** COIN PURCHASED COINS HISTORY API VIEW MODEL **************************/
    private MutableLiveData<CardDetails> getHistory;
    public LiveData<CardDetails> getHistoryy(Activity activity, String userId) {
        getHistory = new MutableLiveData<>();
        bangoRetrofitIntreface.getHistory(userId).enqueue(new Callback<CardDetails>() {
            @Override
            public void onResponse(Call<CardDetails> call, Response<CardDetails> response) {
                if (response.body() != null) {
                    getHistory.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<CardDetails> call, Throwable t) {
                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("reportList", t.getMessage());
            }
        });
        return getHistory;
    }

    /************************** ARCHIEVED  LIVE API VIEW MODEL **************************/
    private MutableLiveData<ModelAgoraLiveUsers> stopAgoraLiveData;
    public LiveData<ModelAgoraLiveUsers> stopAgoraBroadcasting(final Activity activity, String userId,String check) {
        stopAgoraLiveData = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            bangoRetrofitIntreface.stopLiveAgora(userId,check).enqueue(new Callback<ModelAgoraLiveUsers>() {
                @Override
                public void onResponse(Call<ModelAgoraLiveUsers> call, Response<ModelAgoraLiveUsers> response) {
                    if (response.body() != null) {
                        stopAgoraLiveData.postValue(response.body());
                    }
                }
                @Override
                public void onFailure(Call<ModelAgoraLiveUsers> call, Throwable t) {
                }
            });
        } else {
            Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
        }
        return stopAgoraLiveData;
    }

    /************************** GET AUDIO LIVE IMAGES API VIEW MODEL **************************/
    private MutableLiveData<GetAudioLiveImages> getAudioLiveImagesMutableLiveData;
    public LiveData<GetAudioLiveImages> getAudioLiveImagesLiveData(Activity activity) {
        getAudioLiveImagesMutableLiveData = new MutableLiveData<>();
        bangoRetrofitIntreface.getAudioLiveImages().enqueue(new Callback<GetAudioLiveImages>() {
            @Override
            public void onResponse(Call<GetAudioLiveImages> call, Response<GetAudioLiveImages> response) {
                if (response.body() != null) {
                    getAudioLiveImagesMutableLiveData.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<GetAudioLiveImages> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return getAudioLiveImagesMutableLiveData;
    }

    /************************** PURCHASED FRAME API VIEW MODEL **************************/
    private MutableLiveData<RootFrames> getPurchaseFrameMLD;
    public LiveData<RootFrames> getPurchaseFrame(Activity activity, String userId) {
        getPurchaseFrameMLD = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            bangoRetrofitIntreface.getPurchaseFrame(userId).enqueue(new Callback<RootFrames>() {
                @Override
                public void onResponse(Call<RootFrames> call, Response<RootFrames> response) {
                    if (response.body() != null) {
                        getPurchaseFrameMLD.postValue(response.body());
                    }
                }
                @Override
                public void onFailure(Call<RootFrames> call, Throwable t) {

                    Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("reportList", t.getMessage());
                }
            });
        } else {
            Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
        }
        return getPurchaseFrameMLD;
    }

    /************************** APPLIED FRAME API VIEW MODEL **************************/
    private MutableLiveData<RootFrames> AppliedFramesMLD;
    public LiveData<RootFrames> appliedFrames(Activity activity, String userId, String frameId, String data) {
        AppliedFramesMLD = new MutableLiveData<>();
        bangoRetrofitIntreface.appliedFrames(userId, frameId, data).enqueue(new Callback<RootFrames>() {
            @Override
            public void onResponse(Call<RootFrames> call, Response<RootFrames> response) {
                if (response.body() != null) {
                    AppliedFramesMLD.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<RootFrames> call, Throwable t) {
                CommonUtils.dismissDialog();
                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("reportList", t.getMessage());
            }
        });
        return AppliedFramesMLD;
    }

    /************************** GET PURCHASED ENTERY VIEW MODEL **************************/
    private MutableLiveData<MyPuchageEffects> my_purchased_entry_effects;
    public LiveData<MyPuchageEffects> my_purchased_entry_effects(Activity activity, String userId) {
        my_purchased_entry_effects = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            bangoRetrofitIntreface.my_purchased_entry_effects(userId).enqueue(new Callback<MyPuchageEffects>() {
                @Override
                public void onResponse(Call<MyPuchageEffects> call, Response<MyPuchageEffects> response) {
                    if (response.body() != null) {
                        my_purchased_entry_effects.postValue(response.body());
                    }
                }
                @Override
                public void onFailure(Call<MyPuchageEffects> call, Throwable t) {
                    Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("reportList", t.getMessage());
                }
            });
        } else {
            Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
        }
        return my_purchased_entry_effects;
    }

    /************************** APPLY ENTERY VIEW MODEL **************************/
    private MutableLiveData<RootFrames> apply_entry_effec;
    public LiveData<RootFrames> apply_entry_effec(Activity activity, String userId, String frameId) {
        apply_entry_effec = new MutableLiveData<>();
        bangoRetrofitIntreface.apply_entry_effec(userId, frameId).enqueue(new Callback<RootFrames>() {
            @Override
            public void onResponse(Call<RootFrames> call, Response<RootFrames> response) {
                if (response.body() != null) {
                    apply_entry_effec.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<RootFrames> call, Throwable t) {
                CommonUtils.dismissDialog();
                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("reportList", t.getMessage());
            }
        });
        return apply_entry_effec;
    }
}
