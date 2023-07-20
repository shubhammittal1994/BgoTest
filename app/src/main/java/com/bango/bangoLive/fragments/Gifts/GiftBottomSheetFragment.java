package com.bango.bangoLive.fragments.Gifts;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bango.bangoLive.AudioRoom.MODEL.ChatMessageModel;
import com.bango.bangoLive.R;
import com.bango.bangoLive.ViewModel.ApiViewModel;
import com.bango.bangoLive.application.App;
import com.bango.bangoLive.fragments.Gifts.Adapter.AdapterCustomSpinner;
import com.bango.bangoLive.fragments.Gifts.Adapter.AdapterGiftCategory;
import com.bango.bangoLive.fragments.Gifts.Adapter.GiftAdapterTwo;
import com.bango.bangoLive.fragments.Gifts.Model.AlgorithmItem;
import com.bango.bangoLive.fragments.Gifts.Model.FirebaseHelper;
import com.bango.bangoLive.fragments.Gifts.Model.GiftCategoryModel;
import com.bango.bangoLive.fragments.Gifts.Model.GiftModel;
import com.bango.bangoLive.fragments.Gifts.Model.GoLiveModelClass;
import com.bango.bangoLive.fragments.Gifts.Model.ModelAgoraMessages;
import com.bango.bangoLive.fragments.Gifts.Model.ModelSendGift;
import com.bango.bangoLive.fragments.Gifts.Model.SendGiftModel;
import com.bango.bangoLive.fragments.Vip.Model.VipDetailsModel;
import com.bango.bangoLive.utils.CommonUtils;
import com.google.android.gms.common.internal.Objects;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tapadoo.alerter.Alerter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GiftBottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private View view;
    private RecyclerView rv_coins, rv_categoryGift;
    Button getcoin;
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference ref = firebaseDatabase.getReference().child("userInfo");
    AdapterCustomSpinner adapters;
    TextView balance;
    private boolean checkVip = false;
    private GetLuckyGift getLuckyGiftl;
    private List<String> dataAllUSer = new ArrayList<>();
    private List<GiftModel.Gift> listgift = new ArrayList<>();
    private String userchannelId, channelName, categoryId = "", type, getComboCount = "", liveid, pkbattleid = "", level, smsColorfull, vipImage, LevelImage, adminStatuscheck;
    private ProgressBar progress_bar, progress_bar_gifts;
    private LinearLayout ll_main;
    private ImageView iv_back;
    ArrayAdapter<CharSequence> adapter2, adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    List<AlgorithmItem> dataGift;
    private List<GiftCategoryModel.Detail> list = new ArrayList<>();
    private Button sendgift, All;
    private RelativeLayout relativeLayout, rlTwo;
    private Spinner spinner;
    List<GoLiveModelClass> liveJoinedHostUserList;
    private RecyclerView rv_category;
    SharedPreferences sharedpreferences;
    String profileName,profileId,profileImage;

    public GiftBottomSheetFragment() {
        // Required empty public constructor
    }

    public GiftBottomSheetFragment(String userchannelId, String channelName, String liveid, String pkbattleid, GetLuckyGift getLuckyGiftl, List<AlgorithmItem> dataGift, String level, String smsColorfull, String vipImage, String LevelImage, String adminStatuscheck, String type, List<GoLiveModelClass> liveJoinedHostUserList) {
        this.channelName = channelName;
        this.userchannelId = userchannelId;
        this.liveid = liveid;
        this.pkbattleid = pkbattleid;
        this.getLuckyGiftl = getLuckyGiftl;
        this.dataGift = dataGift;
        this.vipImage = vipImage;
        this.smsColorfull = smsColorfull;
        this.level = level;
        this.LevelImage = LevelImage;
        this.adminStatuscheck = adminStatuscheck;
        this.type = type;
        this.liveJoinedHostUserList = liveJoinedHostUserList;

    }

    public GiftBottomSheetFragment(String userchannelId, String channelName, String liveid, String pkbattleid) {
        this.channelName = channelName;
        this.userchannelId = userchannelId;
        this.liveid = liveid;
        this.pkbattleid = pkbattleid;

    }

    public GiftBottomSheetFragment(String roomID) {
        this.userchannelId = roomID;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_gift_bottom_sheet, container, false);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialog);

        /************************** SHARED PREFERENCES **************************/
        sharedpreferences = getContext().getSharedPreferences("Bango", Context.MODE_PRIVATE);
        profileName = sharedpreferences.getString("name","");
        profileId = sharedpreferences.getString("id","");
        profileImage = sharedpreferences.getString("profileImage","");
        findIds();
        getCoins();

        getGiftCategories();
        return view;
    }

    private void getCoins() {
        new ApiViewModel().getVipDetails(profileId).observe(this, new Observer<VipDetailsModel>() {
            @Override
            public void onChanged(VipDetailsModel vipDetailsModel) {
                if (vipDetailsModel!=null) {
                    if (vipDetailsModel.getStatus() == 1) {
                        checkVip = true;
                    }
                }
            }
        });
    }

    private void getGiftCategories() {
        new ApiViewModel().giftCategory(getActivity()).observe(getViewLifecycleOwner(), new Observer<GiftCategoryModel>() {
            @Override
            public void onChanged(GiftCategoryModel giftCategoryModel) {
                if (giftCategoryModel.getSuccess().equalsIgnoreCase("1")) {
                    progress_bar.setVisibility(View.GONE);
                    list = giftCategoryModel.getDetails();
                    categoryId = list.get(0).getId();
                    getGifts(categoryId, list.get(0).getTitle());
                    AdapterGiftCategory adapterGiftCategory = new AdapterGiftCategory(getActivity(), list, new AdapterGiftCategory.Click() {
                        @Override
                        public void onClick(String id, String title) {
                            if (title.equalsIgnoreCase("lucky")) {
                                spinner.setAdapter(adapter2);

                            } else {
                                spinner.setAdapter(adapter);
                            }
                            getGifts(id, title);
                        }
                    });
                    rv_categoryGift.setAdapter(adapterGiftCategory);
                } else {
                    Toast.makeText(getActivity(), giftCategoryModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setAdapter() {
//        GiftingTabAdapter giftingTabAdapter = new GiftingTabAdapter(getChildFragmentManager());
//        viewPager.setAdapter(giftingTabAdapter);
//        tabLayout.setupWithViewPager(viewPager);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
                setupRatio(bottomSheetDialog);
            }
        });
        return dialog;
    }

    private void setupRatio(BottomSheetDialog bottomSheetDialog) {

      /*  FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheet.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.app_dark_color)));
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        layoutParams.height = displaySize(getActivity())[1] * 55 / 100;
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);*/
    }

    private static int[] displaySize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        Log.i("height", String.valueOf(height));
        Log.i("width", String.valueOf(width));
        return new int[]{width, height};
    }

    private void getGifts(String id, String title) {
        progress_bar_gifts.setVisibility(View.VISIBLE);
        new ApiViewModel().sendLiveGift(getActivity(), profileId, id).observe(getActivity(), new Observer<GiftModel>() {
            @Override
            public void onChanged(GiftModel giftModel) {
                if (giftModel.getStatus()==1) {
                    progress_bar_gifts.setVisibility(View.GONE);
                    listgift = giftModel.getDetails().getGifts();
                    if (!giftModel.getDetails().getGifts().get(0).getPrimeAccount().equalsIgnoreCase("")) {
                        balance.setText("My Coin: " +giftModel.getDetails().getUser());
                    } else {
                        balance.setText("My Coin : 0");
                    }
                    GiftAdapterTwo giftAdapter = new GiftAdapterTwo(getActivity(), listgift, new GiftAdapterTwo.Click() {
                        @Override
                        public void OnClick(int position, String balance, String sound, String price, String id, String image, String timing, String giftname, String thumbnail) {

                                relativeLayout.setVisibility(View.VISIBLE);
                                sendgift.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Log.d("TAG", "onClick: in");
//                                        if (balance.equalsIgnoreCase("0") || balance.equalsIgnoreCase("")) {
////                                            startActivity(new Intent(getActivity(), PurchaseCoinActivity.class));} else {
//                                            Toast.makeText(requireContext(), "Add Coin", Toast.LENGTH_SHORT).show();
//
//                                        }else {

                                            double balancevalue = Double.parseDouble(balance.trim());
                                            double priceValue = Double.parseDouble(price);
                                            double combovalue = Double.parseDouble(getComboCount);
                                            double newPrice = priceValue * combovalue;
                                            String finalprice = String.valueOf((int) newPrice);
                                            if (balancevalue >= newPrice) {
                                                if (title.equalsIgnoreCase("lucky")) {
                                                    long newTs = new Date().getTime();
                                                   // OtpRoot details1 = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);
                                                    String username = profileName;
                                                    String name = profileName;
                                                    String userImage = profileImage;
                                                    ModelSendGift modelSendGift = new ModelSendGift();
                                                    modelSendGift.setGiftId(id);
                                                    modelSendGift.setGiftImage(image);
                                                    modelSendGift.setGiftPrice(Integer.parseInt(price));
                                                    modelSendGift.setGiftType(title);
                                                    modelSendGift.setUserName(username);
                                                    modelSendGift.setUserId(profileId);
                                                    modelSendGift.setName(name);
                                                    modelSendGift.setTime(newTs);
                                                    modelSendGift.setGiftedUserId(userchannelId);
                                                    modelSendGift.setGiftCount(getComboCount);
                                                    modelSendGift.setGiftName(giftname);
                                                    modelSendGift.setUserImage(userImage);
                                                    modelSendGift.setMybox("");
                                                    modelSendGift.setThumbnail(thumbnail);
                                                    if (All.getText().toString().equalsIgnoreCase("Remove")){
                                                        getLuckyGiftl.getGift(modelSendGift,dataGift,null);

                                                    }else {
                                                        getLuckyGiftl.getGift(modelSendGift, null, userchannelId);
                                                    }
                                                    dismiss();
                                                } else if (title.equalsIgnoreCase("Vip")) {
                                                    if (checkVip) {
                                                        if (userchannelId.equalsIgnoreCase(CommonUtils.getUserId())) {
                                                            Alerter.create(requireActivity()).setTitle("Gift Alert").setText("Can't Send Gift To Your Self").setBackgroundColorRes(R.color.app_dark_color).show();
                                                            dismiss();
                                                        } else {

                                                                ChatMessageModel chatMessageModel = new ChatMessageModel();
                                                                chatMessageModel.setGift(thumbnail);
                                                                chatMessageModel.setImage(image);
                                                                chatMessageModel.setKey(ref.push().getKey());
                                                                chatMessageModel.setMessage("Send you a gift");
                                                                chatMessageModel.setName(profileName);
                                                                chatMessageModel.setUserId(profileId);
                                                                chatMessageModel.setAnnouncement("0");
                                                                chatMessageModel.setTimeStamp(getCurrentTimeStamp());
                                                                sendMessage(chatMessageModel, chatMessageModel.getKey());


                                                        }

                                                    } else {
                                                        dismiss();
                                                        Toast.makeText(requireContext(), "Only Vip Can Send", Toast.LENGTH_SHORT).show();
                                                    }

                                                } else {
                                                    if (userchannelId.equalsIgnoreCase(profileId)) {
                                                        Alerter.create(requireActivity()).setTitle("Gift Alert").setText("Can't Send Gift To Your Self").setBackgroundColorRes(R.color.app_dark_color).show();
                                                        dismiss();
                                                    } else {
                                                        sendgift.setVisibility(View.GONE);
                                                        giftSend(finalprice, sound, id, userchannelId, image, timing, giftname, thumbnail,title);

                                                    }

                                                }

                                            } else {
                                                Alerter.create(requireActivity()).setTitle("Recharge Alert").setText("Add Coins to your wallet").setBackgroundColorRes(R.color.app_dark_color).show();
                                            }
                                       // }
                                    }
                                });
                            }

                    });
                    rv_coins.setAdapter(giftAdapter);
                    progress_bar.setVisibility(View.GONE);
                    ll_main.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void sendMessage(ChatMessageModel chatMessageModel, String key) {
        ref.child(requireActivity().getIntent().getStringExtra("roomID")).child("multiLive").child(requireActivity().getIntent().getStringExtra("roomID")).child("chat comments").child(key).setValue(chatMessageModel);

    }

    private Long getCurrentTimeStamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp.getTime());
        return timestamp.getTime();
    }

    private JsonArray ApiJsonMap(List<AlgorithmItem> dataGift, String price, String liveid, String type, String id) {
        JsonArray JsonArray = new JsonArray();


        for (int i = 0; i < dataGift.size(); i++) {
            JsonObject jsonObj_ = new JsonObject();

            jsonObj_.addProperty("userId", CommonUtils.getUserId());
            jsonObj_.addProperty("giftUserId", dataGift.get(i).getUserId());
            jsonObj_.addProperty("giftId", id);
            jsonObj_.addProperty("coin", price);
            jsonObj_.addProperty("liveId", requireActivity().getIntent().getStringExtra("liveId"));
            jsonObj_.addProperty("type", type);

            JsonArray.add(jsonObj_);
        }

        return JsonArray;
    }

    private void giftSend(String price, String sound, String id, String getOtherUserid, String image, String time, String giftname, String thumbnail, String title) {


        if (All.getText().equals("Remove")) {


            new ApiViewModel().send_multiple_live_gift(requireActivity(), ApiJsonMap(dataGift, price, profileId, type, id)).observe(getViewLifecycleOwner(), new Observer<SendGiftModel>() {
                @Override
                public void onChanged(SendGiftModel sendGiftModel) {
                    if (sendGiftModel.getStatus() == 1) {

                        sendgift.setVisibility(View.VISIBLE );
                        //  String myLevel = details.getString("myLevel");
                        // String liveLevel = details.getString("liveLevel");
                        String coinsRecieved = sendGiftModel.getDetails().getCoinsRecieved();
                        String liveStars = sendGiftModel.getDetails().getStarcount();
                        String livebox = sendGiftModel.getDetails().getStartStatus();

                       // OtpRoot details1 = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);
                        String username = profileName;
                        String name = profileName;
                        String userImage = profileImage;
                     //   String leve = details1.getMy_level();


                       updateCoins(sendGiftModel.getDetails().getGiftSum());

                        ChatMessageModel chatMessageModel = new ChatMessageModel();
                        chatMessageModel.setGift(thumbnail);
                        chatMessageModel.setImage(userImage);
                        chatMessageModel.setKey(ref.push().getKey());
                        chatMessageModel.setMessage("Send you gift");
                        chatMessageModel.setName(username);
                        chatMessageModel.setUserId(profileId);
                        chatMessageModel.setAnnouncement("0");
                        chatMessageModel.setTimeStamp(getCurrentTimeStamp());
                        sendMessage(chatMessageModel, chatMessageModel.getKey());

                        ModelSendGift modelSendGift = new ModelSendGift();



                        modelSendGift.setGiftId(id);
                        modelSendGift.setGiftImage(image);
                        modelSendGift.setGiftType(title);
                        modelSendGift.setGiftPrice(Integer.parseInt(price));
                        modelSendGift.setUserName(username);
                        modelSendGift.setUserId(profileId);
                        modelSendGift.setName(name);
                        //   modelSendGift.setMyLevel(myLevel);
                        //    modelSendGift.setLiveLevel(liveLevel);
                        //    modelSendGift.setMyStars(myStars);
                        modelSendGift.setSound(sound);
                        modelSendGift.setLiveStars(liveStars);
                        modelSendGift.setGifttime(time);
                        modelSendGift.setGiftName(giftname);
                        modelSendGift.setUserImage(userImage);
                        modelSendGift.setMybox(coinsRecieved);
                        modelSendGift.setLivebox(livebox);
                        modelSendGift.setThumbnail(thumbnail);
                        ModelAgoraMessages modelAgoraMessages;
                        if (name.equalsIgnoreCase("")) {
                            modelAgoraMessages = new ModelAgoraMessages("Send a Gift: ", userImage, username, CommonUtils.getUserId(), String.valueOf(level), 1, adminStatuscheck, image, LevelImage, vipImage, smsColorfull, System.currentTimeMillis());

                        } else {
                            modelAgoraMessages = new ModelAgoraMessages("Send a Gift: ", userImage, name, CommonUtils.getUserId(), String.valueOf(level), 1, adminStatuscheck, image, LevelImage, vipImage, smsColorfull, System.currentTimeMillis());

                        }
                        FirebaseHelper.sendMessageFireBase(channelName, modelAgoraMessages);
                        FirebaseHelper.sendGift(channelName, modelSendGift);
                        dismiss();

                    }
                }
            });

        } else {

            JsonArray JsonArray = new JsonArray();
            JsonObject jsonObj_ = new JsonObject();

            jsonObj_.addProperty("userId", profileId);
            jsonObj_.addProperty("giftUserId", userchannelId);
            jsonObj_.addProperty("giftId", id);
            jsonObj_.addProperty("coin", price);
            jsonObj_.addProperty("liveId", requireActivity().getIntent().getStringExtra("liveId"));
            jsonObj_.addProperty("type", type);

            JsonArray.add(jsonObj_);

            new ApiViewModel().send_multiple_live_gift(requireActivity(), JsonArray).observe(getViewLifecycleOwner(), new Observer<SendGiftModel>() {
                @Override
                public void onChanged(SendGiftModel sendGiftModel) {
                    if (sendGiftModel.getStatus() == 1) {


                        //  String myLevel = details.getString("myLevel");
                        // String liveLevel = details.getString("liveLevel");
                        String coinsRecieved = sendGiftModel.getDetails().getCoinsRecieved();
                        String liveStars = sendGiftModel.getDetails().getStarcount();
                        String livebox = sendGiftModel.getDetails().getStartStatus();

                        //OtpRoot details1 = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);
                        String username = profileName;
                        String name = profileName;
                        String userImage = profileImage;
                       // String leve = details1.getMy_level();

                        Toast.makeText(requireContext(), "receive gift", Toast.LENGTH_SHORT).show();
                        ChatMessageModel chatMessageModel = new ChatMessageModel();
                        chatMessageModel.setGift(thumbnail);
                        chatMessageModel.setImage(userImage);
                        chatMessageModel.setKey(ref.push().getKey());
                        chatMessageModel.setMessage("Send you a gift");
                        chatMessageModel.setName(username);
                        chatMessageModel.setUserId(getOtherUserid);
                        chatMessageModel.setAnnouncement("0");
                        chatMessageModel.setTimeStamp(getCurrentTimeStamp());
                        sendMessage(chatMessageModel, chatMessageModel.getKey());





                        ModelSendGift modelSendGift = new ModelSendGift();
                        modelSendGift.setGiftId(id);
                        modelSendGift.setGiftImage(image);
                        modelSendGift.setGiftType(title);
                        modelSendGift.setGiftPrice(Integer.parseInt(price));
                        modelSendGift.setUserName(username);
                        modelSendGift.setUserId(profileId);
                        modelSendGift.setName(name);
                        //   modelSendGift.setMyLevel(myLevel);
                        //    modelSendGift.setLiveLevel(liveLevel);
                        //    modelSendGift.setMyStars(myStars);
                        modelSendGift.setSound(sound);
                        modelSendGift.setLiveStars(liveStars);
                        modelSendGift.setGifttime(time);
                        modelSendGift.setGiftName(giftname);
                        modelSendGift.setUserImage(userImage);
                        modelSendGift.setMybox(liveStars);
                        modelSendGift.setLivebox(livebox);
                        modelSendGift.setThumbnail(thumbnail);
                        ModelAgoraMessages modelAgoraMessages;
                        if (name.equalsIgnoreCase("")) {
                            modelAgoraMessages = new ModelAgoraMessages("Send a Gift: ", userImage, username, profileId, String.valueOf(level), 1, adminStatuscheck, image, LevelImage, vipImage, smsColorfull, System.currentTimeMillis());

                        } else {
                            modelAgoraMessages = new ModelAgoraMessages("Send a Gift: ", userImage, name, profileId, String.valueOf(level), 1, adminStatuscheck, image, LevelImage, vipImage, smsColorfull, System.currentTimeMillis());

                        }
                        FirebaseHelper.sendMessageFireBase(requireActivity().getIntent().getStringExtra("roomID"), modelAgoraMessages);
                        FirebaseHelper.sendGift(requireActivity().getIntent().getStringExtra("roomID"), modelSendGift);
                        dismiss();

                    }
                }
            });


        }


//        videoMvvm.giftLiveSend(requireActivity(), CommonUtils.getUserId(), price, getOtherUserid, id, pkbattleid, liveid).observe(this, new Observer<SendGiftModel>() {
//            @Override
//            public void onChanged(SendGiftModel map) {
//                if (map.getSuccess().equalsIgnoreCase("1")) {
//
//
//                    //  String myLevel = details.getString("myLevel");
//                    // String liveLevel = details.getString("liveLevel");
//                    String coinsRecieved = map.getDetails().getCoinsRecieved();
//                    String liveStars = map.getDetails().getLiveStar();
//                    String livebox = map.getDetails().getStartStatus();
//
//                    OtpRoot details1 = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);
//                    String username = details1.getUsername();
//                    String name = details1.getName();
//                    String userImage = details1.getImage();
//                    String leve = details1.getMy_level();
//
//                    ModelSendGift modelSendGift = new ModelSendGift();
//                    modelSendGift.setGiftId(id);
//                    modelSendGift.setGiftImage(image);
//                    modelSendGift.setGiftType("normal");
//                    modelSendGift.setGiftPrice(Integer.parseInt(price));
//                    modelSendGift.setUserName(username);
//                    modelSendGift.setUserId(CommonUtils.getUserId());
//                    modelSendGift.setName(name);
//                    //   modelSendGift.setMyLevel(myLevel);
//                    //    modelSendGift.setLiveLevel(liveLevel);
//                    //    modelSendGift.setMyStars(myStars);
//                    modelSendGift.setSound(sound);
//                    modelSendGift.setLiveStars(liveStars);
//                    modelSendGift.setGifttime(time);
//                    modelSendGift.setGiftName(giftname);
//                    modelSendGift.setUserImage(userImage);
//                    modelSendGift.setMybox(coinsRecieved);
//                    modelSendGift.setLivebox(livebox);
//                    modelSendGift.setThumbnail(thumbnail);
//                    ModelAgoraMessages modelAgoraMessages;
//                    if (name.equalsIgnoreCase("")) {
//                        modelAgoraMessages = new ModelAgoraMessages("Send a Gift: ", userImage, username, CommonUtils.getUserId(), String.valueOf(level), 1, adminStatuscheck, image, LevelImage, vipImage, smsColorfull, System.currentTimeMillis());
//
//                    } else {
//                        modelAgoraMessages = new ModelAgoraMessages("Send a Gift: ", userImage, username, CommonUtils.getUserId(), String.valueOf(level), 1, adminStatuscheck, image, LevelImage, vipImage, smsColorfull, System.currentTimeMillis());
//
//                    }
//                    FirebaseHelper.sendMessageFireBase(channelName, modelAgoraMessages);
//                    FirebaseHelper.sendGift(channelName, modelSendGift);
//
////                        if (otherChannelName!=null||!otherChannelName.equalsIgnoreCase("")){
////                            FirebaseHelper.sendGift(otherChannelName, modelSendGift);
////                        }
//
//                    dismiss();
//
//                } else {
//                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    private void updateCoins(List<SendGiftModel.Details.GiftSum> giftSum) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference ref = firebaseDatabase.getReference().child("userInfoAudioLive");

        for (int i = 0; i < giftSum.size(); i++) {


            for (int j = 0; j < liveJoinedHostUserList.size(); j++) {

                if (!liveJoinedHostUserList.get(j).getUserID().equalsIgnoreCase("")) {

                    if (liveJoinedHostUserList.get(j).getUserID().equalsIgnoreCase(giftSum.get(i).getUserId())) {
                        Map data = new HashMap();
                        data.put("coin", giftSum.get(i).getCoin());
                        ref.child(userchannelId).child("multiLive").child(userchannelId).child("multiLiveRequest").child(giftSum.get(i).getUserId()).updateChildren(data);

                    }

                }


            }


        }


    }

    private void findIds() {
        relativeLayout = view.findViewById(R.id.rl_bottomSend);
        All = view.findViewById(R.id.All);
        rlTwo = view.findViewById(R.id.rlTwo);
        rv_category = view.findViewById(R.id.rv_category);
        spinner = view.findViewById(R.id.sp_comboGift);


        All.setOnClickListener(view1 -> {
            if (All.getText().equals("All")) {
                All.setText("Remove");
                adapters = new AdapterCustomSpinner(requireContext(), dataGift, 0, new AdapterCustomSpinner.Click() {
                    @Override
                    public void getData(AlgorithmItem data) {
                        userchannelId = data.getUserId();
                        dataAllUSer.add(data.getUserId());


                    }
                });
                rv_category.setAdapter(adapters);
            } else {
                All.setText("All");
                adapters = new AdapterCustomSpinner(requireContext(), dataGift, 1, new AdapterCustomSpinner.Click() {
                    @Override
                    public void getData(AlgorithmItem data) {
                        userchannelId = data.getUserId();
                        dataAllUSer.add(data.getUserId());


                    }
                });
                rv_category.setAdapter(adapters);
            }

        });

        if (dataGift != null) {

            if (dataGift.size() > 1) {
                All.setVisibility(View.VISIBLE);
            } else {
                All.setVisibility(View.GONE);

            }


            rlTwo.setVisibility(View.VISIBLE);
            adapters = new AdapterCustomSpinner(requireContext(), dataGift, 1, new AdapterCustomSpinner.Click() {
                @Override
                public void getData(AlgorithmItem data) {
                    userchannelId = data.getUserId();
                    dataAllUSer.add(data.getUserId());


                }
            });
            rv_category.setAdapter(adapters);


        } else {
            rlTwo.setVisibility(View.GONE);

        }

        adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.spinnercombo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2 = ArrayAdapter.createFromResource(this.getActivity(), R.array.spinnerLucky, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getComboCount = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                getComboCount = "1";
            }
        });
        sendgift = view.findViewById(R.id.sendGift);
        progress_bar_gifts = view.findViewById(R.id.progress_bar_gifts);
        rv_categoryGift = view.findViewById(R.id.rv_categoryGift);
        tabLayout = view.findViewById(R.id.tabLayoutGift);
        viewPager = view.findViewById(R.id.viewPagerGift);
        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        ll_main = view.findViewById(R.id.ll_main);
        progress_bar = view.findViewById(R.id.progress_bar);
        getcoin = view.findViewById(R.id.bt_getcoin);
        getcoin.setOnClickListener(this);
        balance = view.findViewById(R.id.balance);
        rv_coins = view.findViewById(R.id.rv_coins);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_getcoin:
                Toast.makeText(requireContext(), "Add COin", Toast.LENGTH_SHORT).show();

                break;
            case R.id.iv_back:
                dismiss();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int dialogHeight = getResources().getDimensionPixelSize(R.dimen.bottom_sheet_height);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight);
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        }
    }

    public interface GetLuckyGift {
        void getGift(ModelSendGift modelSendGift, List<AlgorithmItem> gift, String dataGift);
    }
}