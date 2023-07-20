package com.bango.bangoLive.ViewModel;

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
import com.bango.bangoLive.fragments.Search.modelClass.FollowingDataModel;
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
import com.google.gson.JsonArray;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface BangoRetrofitIntreface {

    /************************** LOGIN **************************/
    @FormUrlEncoded
    @POST("loginPhone")
    Call<Map> loginPhone(@Field("phone") String phone);

    /************************** loginRegisterUser **************************/
    @FormUrlEncoded
    @POST("loginRegisterUser")
    Call<LoginResponse> otpVerify(@Field("phone") String phone,
                                  @Field("otp") String otp,
                                  @Field("deviceId") String deviceId,
                                  @Field("reg_id") String reg_id,
                                  @Field("country") String country,
                                  @Field("password") String password,
                                  @Field("device_type") String device_type);

    /************************** add_password **************************/
    @FormUrlEncoded
    @POST("add_password")
    Call<PasswordResponse> addPassword(@
                                               Field("userId") String userId,
                                       @Field("password") String password);

    /************************** socialLogin **************************/
    @FormUrlEncoded
    @POST("socialLoginNc")
    Call<LoginResponse> socialLogin(@Field("name") String username,
                                    @Field("socialId") String social_id,
                                    @Field("email") String email,
                                    @Field("reg_id") String reg_id,
                                    @Field("latitude") String latitude,
                                    @Field("longitude") String longitude,
                                    @Field("image") String image,
                                    @Field("device_id") String device_id,
                                    @Field("country") String country);

    /************************** updateUserProfile **************************/
    @Multipart
    @POST("updateUserProfile")
    Call<LoginResponse> updateUser(@Part("name") RequestBody name,
                              @Part("gender") RequestBody gender,
                              @Part("dob") RequestBody dob,
                              @Part("latitude") RequestBody latitude,
                              @Part("longitude") RequestBody longitude,
                              @Part("id") RequestBody id,
                              @Part("password") RequestBody password,
                              @Part("bio") RequestBody bio,
                              @Part MultipartBody.Part image);

    /************************** LOGOUT USER **************************/
    @FormUrlEncoded
    @POST("logoutUser")
    Call<LoginResponse> logout_user(@Field("userId") String id);

    /************************** APPLY FOR HOST **************************/
    @Multipart
    @POST("hostAPi")
    Call<Map> getApplyForHost(@PartMap HashMap<String, RequestBody> data, @Part MultipartBody.Part image);

    /************************** GET COUNTRIES **************************/
    @GET("getCountries")
    Call<CountryList> getCountries();

    /************************** START LIVE **************************/
    @FormUrlEncoded
    @POST("start_live")
    Call<StartLiveModelClass> start_live(@Field("userId") String id,
                                         @Field("liveTitle") String liveTitle);

    /************************** GET LIVE HOST LIST AUDIO**************************/
    @FormUrlEncoded
    @POST("get_live_host_list_audioT")
    Call<GetLiveHostListAudioModelClass> getLiveHostListAudio(@Field("userId") String id);

    /************************** GET BANNER **************************/
    @GET("getBanner")
    Call<BannerRoot> getBanner();

    /************************** GET FRAMES **************************/
    @FormUrlEncoded
    @POST("getFrameByLevel")
    Call<RootFramesAll> getFrame(@Field("userId") String userId);

    /************************** PURCHASED FRAMES **************************/
    @FormUrlEncoded
    @POST("purchaseFrames")
    Call<RootFrames> purchaseFrames(@Field("userId") String userId,
                                    @Field("frameId") String gifId);

    /************************** GET ENTRY EFFECTS **************************/
    @FormUrlEncoded
    @POST("get_enrty_effects")
    Call<EntryEffectModel> get_enrty_effects(@Field("userId") String cardNumber);

    /************************** PURCHASE ENTRY EFFECTS **************************/
    @FormUrlEncoded
    @POST("purchase_entry_effects")
    Call<RootFrames> purchase_entry_effects(@Field("userId") String userId,
                                            @Field("entryId") String gifId,
                                            @Field("type") String type);

    /************************** USER STATUS **************************/
    @FormUrlEncoded
    @POST("userStats")
    Call<MothlyModel> userStats(@Field("userId") String s);

    /************************** USER ALL STATS **************************/
    @FormUrlEncoded
    @POST("userAllStats")
    Call<MonthlyHistory> userAllStats(@Field("userId") String s);

    /************************** SEARCH USERS API **************************/
    @FormUrlEncoded
    @POST("searchUsers")
    Call<SearchUserRoot> getUsersList(@Field("search") String search, @Field("userId") String userId);

    /************************** USER REPORT ACTION API **************************/
    @GET("getUserRepotActions")
    Call<RootReoprt> getUserReportList();

    /************************** USER REPORT API **************************/
    @FormUrlEncoded
    @POST("userReport")
    Call<RootSendReport> userReport(@Field("userReportActionId") String userReportActionId,
                                    @Field("userId") String userId,
                                    @Field("otherUserId") String otherUserId);

    /************************** USER BLOCK/UNBLOCK API **************************/
    @FormUrlEncoded
    @POST("blockUnblock")
    Call<RootBlockUser> blockUnblock(@Field("userId") String userId,
                                     @Field("blockUserId") String frameId);

    /************************** FOLLOW/UNFOLLOW API **************************/
    @FormUrlEncoded
    @POST("userFollow")
    Call<FollowingRoot> followUser(@Field("userId") String userId,
                                   @Field("followingUserId") String followingUserId);

    /************************** GET FOLLOWING API **************************/
    @FormUrlEncoded
    @POST("getFollowing")
    Call<FollowingDataModel> getFollowing(@Field("userId") String data);

    /************************** GET FOLLOWERS API **************************/
    @FormUrlEncoded
    @POST("getFollowers")
    Call<FollowingDataModel> getFollowers(@Field("userId") String data);

    /************************** GET FRIENDS **************************/
    @FormUrlEncoded
    @POST("getFriends")
    Call<FollowingDataModel> getFriends(@Field("userId") String data);

    /************************** TOP HOST **************************/
    @GET("topHost")
    Call<TopHostScreenModel> topHost();

    /************************** TOP GIFTER **************************/
    @FormUrlEncoded
    @POST("topGifter")
    Call<TopGifterModel> topGifter(@Field("type") String type);

    /************************** MONTHLY TOP GIFTER **************************/
    @FormUrlEncoded
    @POST("monthlyTopLiveUserGifting")
    Call<RootLeaderBoard> monthlyTopLiveUserGifting(@Field("userId") String userId);

    /************************** GET PURCHASE COINS **************************/
    @GET("getAdminCoins")
    Call<Root_My_Wallet> coinPrice();

    /************************** GET USERS COINS **************************/
    @FormUrlEncoded
    @POST("getUserCoin")
    Call<TotalCoinModal> getCoin(@Field("userId") String userId);

    /************************** GET SAVE CARD **************************/
    @FormUrlEncoded
    @POST("getSaveCard")
    Call<SavedCard> getSaveCard(@Field("userId") String userId);

    /************************** REMOVE SAVE CARD **************************/
    @FormUrlEncoded
    @POST("removeSavedCard")
    Call<SavedCard> removeSavedCard(@Field("cardId") String userId);

    /************************** ORDER DETAILS **************************/
    @FormUrlEncoded
    @POST("orderDetails")
    Call<OrderModel> orderDetails(@Field("userId") String userId,
                                  @Field("coinsId") String vipId);

    /************************** REGISTERED CARD DETAILS **************************/
    @FormUrlEncoded
    @POST("regeisterCard")
    Call<CardModel> regeisterCard(@Field("cardNumber") long cardNumber,
                                  @Field("expMonth") int expMonth,
                                  @Field("expYear") int expYear,
                                  @Field("cvv") int cvv,
                                  @Field("add_line_one") String add_line_one,
                                  @Field("name") String name,
                                  @Field("save") int save,
                                  @Field("userId") String userId,
                                  @Field("emailId") String emailId);

    /************************** MAKE PAYMENT API **************************/
    @FormUrlEncoded
    @POST("makePayment")
    Call<OrderModel> makePayment(@Field("orderId") String userId,
                                 @Field("customerId") String vipId);

    /************************** GET GIFT CATEGORIES API **************************/
    @GET("liveGiftCategory")
    Call<GiftCategoryModel> giftCategory();

    /************************** GET VIP DETAILS API **************************/
    @FormUrlEncoded
    @POST("getVipDetails")
    Call<VipDetailsModel> getVipDetails(@Field("userId") String data);

    /************************** LIVE GIFTS API **************************/
    @GET("get_live_gift")
    Call<GiftModel> getLiveGifts(@Query("userId") String userId,
                                 @Query("category") String giftCategoryId);

    /************************** SEND MULTI LIVE GIFT API **************************/
    @POST("send_multiple_live_gift")
    Call<SendGiftModel> send_multiple_live_gift(@Body JsonArray jsonArray);

//    /************************** SEND MULTI LIVE GIFT API **************************/
//    @POST("?Action=SendRoomMessage")
//    Call<Map> send_multiple_live_gift(@Body JsonArray jsonArray);

    /************************** ADD POSTER IMAGE API **************************/
    @Multipart
    @POST("addPosterImage")
    Call<AddPosterImage> addPosterImage(@Part("userId") RequestBody userId,
                                        @Part MultipartBody.Part posterImage);

    /************************** GET POSTER IMAGE API **************************/
    @FormUrlEncoded
    @POST("getPosterImage")
    Call<GetPosterImage> getPosterImage(@Field("userId") String userId);

    /************************** GET SOME FUNCTIONALITY API **************************/
    @FormUrlEncoded
    @POST("someFunctionality")
    Call<OtherUserDataModel> someFunctionality(@FieldMap HashMap<String, String> userId);

    /************************** IMAGE ON ENTRY API **************************/
    @FormUrlEncoded
    @POST("imageOnEntry")
    Call<EntryModelClass> imageOnEntryy(@Field("deviceId") String deviceId, @Field("userId") String userId);

    /************************** COIN PURCHASED COINS HISTORY API **************************/
    @GET("coin_purchased_history")
    Call<CardDetails> getHistory(@Query("userId") String userId);

    /************************** ARCHIVED LIVE API **************************/
    @FormUrlEncoded
    @POST("archivedLive")
    Call<ModelAgoraLiveUsers> stopLiveAgora(@Field("id") String id, @Field("waiting") String check);

    /************************** GET AUDIO LIVE IMAGES API **************************/
    @GET("getAudioLiveImages")
    Call<GetAudioLiveImages> getAudioLiveImages();

    /************************** GET PURCHASED FRAME API **************************/
    @FormUrlEncoded
    @POST("getPurchaseFrame")
    Call<RootFrames> getPurchaseFrame(@Field("userId") String userId);

    /************************** APPLIED FRAME API **************************/
    @FormUrlEncoded
    @POST("applyFrames")
    Call<RootFrames> appliedFrames(@Field("userId") String userId,
                                   @Field("frameId") String frameId, @Field("type") String data);

    /************************** PURCHASED ENTRY API **************************/
    @FormUrlEncoded
    @POST("my_purchased_entry_effects")
    Call<MyPuchageEffects> my_purchased_entry_effects(@Field("userId") String userId);

    /************************** APPLY ENTRY EFFECT API **************************/
    @FormUrlEncoded
    @POST("apply_entry_effect")
    Call<RootFrames> apply_entry_effec(@Field("userId") String userId,
                                       @Field("entryId") String frameId);

}
