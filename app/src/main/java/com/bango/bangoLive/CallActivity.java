package com.bango.bangoLive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bango.bangoLive.AudioRoom.Adapter.CommentAdapter;
import com.bango.bangoLive.AudioRoom.AudioRoomLeaderBoardFragment;
import com.bango.bangoLive.AudioRoom.MODEL.AdminFirebaseRoot;
import com.bango.bangoLive.AudioRoom.MODEL.ChatMessageModel;
import com.bango.bangoLive.AudioRoom.MODEL.OtherUserDataModel;
import com.bango.bangoLive.ViewModel.ApiViewModel;
import com.bango.bangoLive.ZegoServices.ZegoSdkEnums.MessageTypeEnum;
import com.bango.bangoLive.ZegoServices.zegoCloudChat.ChatFunctions;
import com.bango.bangoLive.ZegoServices.zegoCloudChat.ChatSDKManager;
import com.bango.bangoLive.ZegoServices.zegoCloudChat.ZIMCustomTextMessage;
import com.bango.bangoLive.ZegoServices.zegoCloudChat.model.MessageModel;
import com.bango.bangoLive.adapters.LocalAddedAdapter;
import com.bango.bangoLive.adapters.MusicRVAdapter;
import com.bango.bangoLive.application.App;
import com.bango.bangoLive.audioLiveStreamingAdapterClasses.EmojiRVAdpter;
import com.bango.bangoLive.audioLiveStreamingAdapterClasses.MultiLiveAudioAdapter;
import com.bango.bangoLive.databinding.ActivityCallBinding;
import com.bango.bangoLive.databinding.DialogSendMessageBinding;
import com.bango.bangoLive.databinding.ProfileBottomSheetBinding;
import com.bango.bangoLive.fragments.Gifts.GiftBottomSheetFragment;
import com.bango.bangoLive.fragments.Gifts.Model.GiftModel;
import com.bango.bangoLive.fragments.Gifts.Model.GoLiveModelClass;
import com.bango.bangoLive.games.activity.GameScreenActivity;
import com.bango.bangoLive.room.AppDatabase;
import com.bango.bangoLive.room.Music;
import com.bango.bangoLive.room.MusicTable;
import com.bango.bangoLive.themes.LiveFreeThemeFragment;
import com.bango.bangoLive.themes.LivePurchasedThemeFragment;
import com.bango.bangoLive.themes.LiveThemeFragment;
import com.bango.bangoLive.utils.AppConstant;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bango.bangoLive.fragments.Gifts.Model.AlgorithmItem;
import com.bango.bangoLive.fragments.Gifts.Model.FirebaseHelper;
import com.bango.bangoLive.fragments.Gifts.Model.ModelSendGift;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGADynamicEntity;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.squareup.picasso.Picasso;
import com.tapadoo.alerter.Alerter;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import im.delight.android.webview.AdvancedWebView;
import im.zego.zegoexpress.ZegoAudioEffectPlayer;

import im.zego.zegoexpress.ZegoExpressEngine;
import im.zego.zegoexpress.callback.IZegoEventHandler;
import im.zego.zegoexpress.constants.ZegoAudioConfigPreset;
import im.zego.zegoexpress.constants.ZegoPlayerState;
import im.zego.zegoexpress.constants.ZegoPublisherState;
import im.zego.zegoexpress.constants.ZegoRoomStateChangedReason;
import im.zego.zegoexpress.constants.ZegoStreamResourceMode;
import im.zego.zegoexpress.constants.ZegoUpdateType;
import im.zego.zegoexpress.entity.ZegoAudioConfig;
import im.zego.zegoexpress.entity.ZegoAudioEffectPlayConfig;
import im.zego.zegoexpress.entity.ZegoPlayerConfig;
import im.zego.zegoexpress.entity.ZegoRoomConfig;
import im.zego.zegoexpress.entity.ZegoStream;
import im.zego.zegoexpress.entity.ZegoUser;
import im.zego.zim.callback.ZIMGroupJoinedCallback;
import im.zego.zim.callback.ZIMRoomLeftCallback;
import im.zego.zim.entity.ZIMError;
import im.zego.zim.entity.ZIMGroupFullInfo;
import im.zego.zim.entity.ZIMMessageSendConfig;
import im.zego.zim.entity.ZIMPushConfig;
import im.zego.zim.enums.ZIMConversationType;
import im.zego.zim.enums.ZIMMessagePriority;

public class CallActivity extends AppCompatActivity implements GiftBottomSheetFragment.GetLuckyGift {

    ActivityCallBinding binding;

    SharedPreferences sharedpreferences;
    ZegoAudioEffectPlayer audioEffectPlayer;

    private AdvancedWebView mWebView;
    private Dialog loading_box;
    public static String gameLink = "";
    public boolean isHostStatus = false;


    private RecyclerView musicRv;
    AppDatabase appDatabase;
    AppCompatSeekBar seekbar1;
    public MusicTable musicDetails;
    private MusicRVAdapter musicRVAdapter;
    LiveThemeFragment themesFragment;

    CountDownTimer musicCountDownTimer;
    CountDownTimer countDownTimer;

    List<AdminFirebaseRoot> adminList;
    List<MusicTable> musicList = new ArrayList<>();
    ArrayList<Music> audioList = new ArrayList<>();
    private final List<GoLiveModelClass> requestMultiLiveList = new ArrayList<>();
    private final List<ChatMessageModel> chatMessages = new ArrayList<>();

    public static String liveType = "";
    public static String liveHostid = "";

    float valueTime = 0;

    int musicStatus = 0;
    int liveChatBanned = 0;
    int musicpos;
    int SizeMB = 0;
    int possss = 0;
    private boolean isMute = false;
    int MusicId = 0;
    int resumeCheck = 0;
    int musicOnOffStatus = 0;
    int outSideBoxMusicCheck = 0;
    int adminStatus = 0;
    private String mutevalueOfUSer = "1";
    private String liveHostBackImg = "";
    private Long currentTimeStamp;
    static String authToken, other;

    //  UserJoinedAdapter userJoinedAdapter;

    String status = "";
    String musictime, profileUniqueId, liveTitle, liveId, profileName, profileId, profileImageSave, profileImage, roomID, otherUserId, liveStatus, coverImage, coverName;
    String adminId = "", adminIdThroughCallback = "";

    /*
    * Host-->> RoomStart-->>
    * RoomID==ProfileID
    * LiveID==RoomID
    * otherUserId==userID
    * profileId== RoomCreate
    * profileUniqueId==UserName
    * liveHostid==
    * adminId==
    * */
    Boolean am_i_host = false;
    ImageView playMusicDialogImg;
    int emptyPosition;
    ZegoExpressEngine zegoExpressEngine;
    BottomSheetDialog sendMessageBottomSheet;
    private CommentAdapter commentAdapter;

    MultiLiveAudioAdapter multiLiveAudioAdapter;
    private final List<GoLiveModelClass> liveJoinedUserList = new ArrayList<>();
    private final List<GoLiveModelClass> liveJoinedHostUserList = new ArrayList<>();
    private final List<String> muteUsers = new ArrayList<>();

    private String token = "";


    /************************** FIREBASE DATABASE **************************/
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference ref = firebaseDatabase.getReference().child("userInfo");
    private final DatabaseReference liveUsersRef = firebaseDatabase.getReference().child("liveUsersRef");
    private final DatabaseReference adminLiveRef = firebaseDatabase.getReference().child("adminLiveRef");
    private final DatabaseReference emojiRef = firebaseDatabase.getReference().child("emojiRef");
    private final DatabaseReference lockSeat = firebaseDatabase.getReference().child("lockSeat");
    private final DatabaseReference lockRef = firebaseDatabase.getReference().child("lockRef");
    private final DatabaseReference muteMicRef = firebaseDatabase.getReference().child("muteMicRef");
    private final DatabaseReference userLiveBackImgRef = firebaseDatabase.getReference().child("userLiveBackImgRef");

    private String audioRoomId = "test123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCallBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Set Full Screen Background Window
        CallActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        String[] permissionNeeded = {"android.permission.RECORD_AUDIO"};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.RECORD_AUDIO") != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissionNeeded, 101);
            }
        }
        zegoExpressEngine = ((App) getApplication()).getExpressService().getEngine();
        // startListenEvent();

        /************************** APP DATABASE **************************/
        appDatabase = AppDatabase.getInstance(CallActivity.this);

        /************************** SHARED PREFERENCES **************************/
        sharedpreferences = this.getSharedPreferences("Bango", Context.MODE_PRIVATE);

        /************************** GET DATA THROUGH INTENT **************************/
        am_i_host = getIntent().getBooleanExtra(AppConstant.AM_I_HOST, false);

        roomID = getIntent().getStringExtra("roomID");
        liveTitle = getIntent().getStringExtra("liveTitle");
        liveId = getIntent().getStringExtra("liveId");

        profileName = sharedpreferences.getString("name", "");
        profileId = sharedpreferences.getString("id", "");
        profileImage = getIntent().getStringExtra("profileImage");
        //  profileName = getIntent().getStringExtra("profileName");
        profileUniqueId = getIntent().getStringExtra("profileUniqueId");
        status = getIntent().getStringExtra("status");
        liveType = getIntent().getStringExtra("liveType");
        liveStatus = getIntent().getStringExtra("liveStatus");
        liveHostid = getIntent().getStringExtra("roomID");
        coverImage = getIntent().getStringExtra("coverimage");
        coverName = getIntent().getStringExtra("coverName");
        otherUserId = getIntent().getStringExtra("roomID");

        token = getIntent().getStringExtra("token");

        if (am_i_host) {
            profileImageSave = sharedpreferences.getString("profileImage", "");
        } else {
            joinRoom(roomID);
            profileName = getIntent().getStringExtra("profileName");
            other = getIntent().getStringExtra("otherUserId");
        }

        MultiLiveAudioAdapter.directHostId = getIntent().getStringExtra("roomID");
        MultiLiveAudioAdapter.liveType = getIntent().getStringExtra("liveType");
        //  InviteAudienceRVAdapter.directHostId = getIntent().getStringExtra("liveHostIds");

        startListenEvent();
        FirebaseHelper.giftsListener(roomID, giftsEventListener);
        getMultiLiveRequest();
        loginRoom(profileId, profileName, audioRoomId, am_i_host);

        binding.txtUserName.setText(liveTitle);
        binding.txtId.setText(profileUniqueId);
        binding.hostName.setText(profileName);
        Picasso.with(this).load(coverImage).error(R.drawable.actress).into(binding.imgProfileuser);
        Picasso.with(this).load(profileImage).error(R.drawable.actress).into(binding.imgHostProfile);

        liveHostBackImg = getIntent().getStringExtra("roomID");
        Picasso.with(this).load(coverImage).error(R.drawable.actress).into(binding.imgProfileuser);
        binding.txtUserName.setText(coverName);
        CommentAdapter.liveHostBackImg = roomID;
        initUIandEvent();
        getBackgroundImageFromFirebase();
        getCommentChatMessageFirebase();

        HashMap<String, String> data = new HashMap<>();
        data.put("userId", profileId);
        data.put("otherUserId", profileId);
        new ApiViewModel().someFunctionality(this, data).observe(this, otherUserDataModel -> {
            if (otherUserDataModel.getStatus().equalsIgnoreCase("1")) {
                if (otherUserDataModel.getDetails() != null) {
                    authToken = otherUserDataModel.getDetails().getAuthToken();
                    Log.d("Authtoken", "authhhh: " + authToken);
                }
            }

            if (otherUserDataModel.getMessage().contains("not exists")) {
                App.getSharedpref().clearPreferences();
                //LoginManager.getInstance().logOut();
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
                // Build a GoogleSignInClient with the options specified by gso.
                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
                Toast.makeText(this, "Login Again ", Toast.LENGTH_SHORT).show();
                mGoogleSignInClient.signOut();
                startActivity(new Intent(this, HomeActivity.class));
            }
        });


        /************************** SET AUDIO EFFECT PLAYER **************************/
        audioEffectPlayer = ((App) getApplication()).getExpressService().getEngine().createAudioEffectPlayer();

        /************************** GIFT ICON CLICK OPEN A GIFT FRAGMENT **************************/
        binding.giftIcon.setOnClickListener(v -> {
            GiftBottomSheetFragment giftBottomSheetFragment = new GiftBottomSheetFragment(roomID);
            giftBottomSheetFragment.show(getSupportFragmentManager(), giftBottomSheetFragment.getTag());
        });

        /************************** OPEN A LIVE LEADERBOARD FRAGMENT **************************/
        binding.leaderBoardTropy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CallActivity.this, "liveId :- " + liveId, Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("liveId", liveId);
                AudioRoomLeaderBoardFragment audioRoomLeaderBoardFragment = new AudioRoomLeaderBoardFragment();
                audioRoomLeaderBoardFragment.setArguments(bundle);
                audioRoomLeaderBoardFragment.show(getSupportFragmentManager(), audioRoomLeaderBoardFragment.getTag());
            }
        });

        /************************** PLAY MUSIC DIALOG IN ROOM **************************/
        binding.musicPlayCirImg.setOnClickListener(v -> playMusicDialogBox());

        /************************** MENU ICON CLICK ROOM **************************/
        binding.moreIcon.setOnClickListener(v -> {
            menuDialogBox();
        });

        /************************** BROAD SWITCH ICON **************************/
        binding.switchIcon.setOnClickListener(v -> {
            onBackPressed();
        });

        /************************** EMOJI LISTENER ICON **************************/
        binding.callEmojiLinearLayout.setOnClickListener(v -> {
            emojiBottomSheet();
        });

        /************************** GAMES LISTENER ICON **************************/
        binding.gamesIcon.setOnClickListener(v -> {
            gamesDialogBox();
        });


        if (roomID.equalsIgnoreCase(profileId)) {
            HashMap<String, String> liveUserHs = new HashMap<>();
            liveUserHs.put("liveId", liveId);
            liveUserHs.put("hostId", profileId);
            liveUserHs.put("liveType", liveType);
            liveUsersRef.child(roomID).setValue(liveUserHs);
        }

        liveJoinedHostUserList.add(new GoLiveModelClass());
        liveJoinedHostUserList.add(new GoLiveModelClass());
        liveJoinedHostUserList.add(new GoLiveModelClass());
        liveJoinedHostUserList.add(new GoLiveModelClass());
        liveJoinedHostUserList.add(new GoLiveModelClass());
        liveJoinedHostUserList.add(new GoLiveModelClass());
        liveJoinedHostUserList.add(new GoLiveModelClass());
        liveJoinedHostUserList.add(new GoLiveModelClass());
        setAcceptedGustHostAdapter(liveJoinedHostUserList);

        adminLiveRef.child(roomID).child(profileId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    adminStatus = 1;
                    binding.moreIcon.setVisibility(View.VISIBLE);
                } else {
                    adminStatus = 0;
                    if (otherUserId.equalsIgnoreCase(profileId)) {
                        binding.moreIcon.setVisibility(View.VISIBLE);
                    } else {
                        binding.moreIcon.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        adminLiveRef.child(roomID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        GoLiveModelClass goLiveModelClass = new GoLiveModelClass();
                        goLiveModelClass.setImage(snapshot1.child("adminImg").getValue().toString());
                        goLiveModelClass.setUserID(snapshot1.child("adminId").getValue().toString());
                        goLiveModelClass.setAdminStatus(true);
                        goLiveModelClass.setName(snapshot1.child("adminName").getValue().toString());
                        // inviteAudienceList.add(goLiveModelClass);
                    }
                } else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        emojiRef.child(roomID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    if (snapshot.hasChild("status") && snapshot.child("status").getValue().toString().equalsIgnoreCase("1")) {
                        emojiRef.child(roomID).removeValue();
                    } else {
                        String emojiImg = snapshot.child("emojiImg").getValue().toString();
                        String emojiSenderId = snapshot.child("emojiSenderId").getValue().toString();
                        String emojiSenderName = snapshot.child("emojiSenderName").getValue().toString();
                        String emojiSenderImg = snapshot.child("emojiSenderImg").getValue().toString();
                        String status = snapshot.child("status").getValue().toString();
                        String giftStatus;
                        if (!snapshot.child("giftStatus").getValue().toString().isEmpty()) {
                            giftStatus = snapshot.child("giftStatus").getValue().toString();
                        } else {
                            giftStatus = "2";
                        }

//                        binding.giftSendUserId.setText(emojiSenderId);
//                        binding.giftSendUserName.setText(emojiSenderName);
                        if (emojiSenderId.equalsIgnoreCase(roomID) && status.equalsIgnoreCase("0")) {

                            if (giftStatus.equalsIgnoreCase("1")) {
//                                binding.globaGiftAnimation.startAnimation(animation2);
//                                binding.globaGiftAnimation.setVisibility(View.VISIBLE);
                                try { // new URL needs try catch.
                                    binding.muCarsRVImagee.setVisibility(View.VISIBLE);
                                    SVGAParser parser = new SVGAParser(CallActivity.this);
                                    parser.decodeFromURL(new URL(emojiImg), new SVGAParser.ParseCompletion() {
                                        @Override
                                        public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                                            SVGADynamicEntity dynamicEntity = new SVGADynamicEntity();
                                            dynamicEntity.setDynamicImage(emojiImg, "99"); // Here is the KEY implementation.
                                            SVGADrawable drawable = new SVGADrawable(videoItem, dynamicEntity);
                                            binding.muCarsRVImagee.setImageDrawable(drawable);
                                            binding.muCarsRVImagee.startAnimation();
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    binding.muCarsRVImagee.stopAnimation();
                                                    binding.muCarsRVImagee.setVisibility(View.GONE);
                                                }
                                            }, 14000);
                                        }

                                        @Override
                                        public void onError() {

                                        }
                                    }, null);
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Glide.with(getApplicationContext()).load(emojiImg).into(binding.emojiShowImg);
                                binding.emojiShowImg.setVisibility(View.VISIBLE);
                                new Handler().postDelayed(() -> binding.emojiShowImg.setVisibility(View.GONE), 5000);
                            }
//                            binding.globaGiftAnimation.setVisibility(View.GONE);
//                            animation2.cancel();
                            emojiRef.child(roomID).removeValue();
                        } else if (status.equalsIgnoreCase("0")) {
                            MultiLiveAudioAdapter.userId = emojiSenderId;
                            MultiLiveAudioAdapter.emoji = emojiImg;
                            MultiLiveAudioAdapter.hostId = roomID;
                            multiLiveAudioAdapter.notifyDataSetChanged();
                        } else {
                            if (giftStatus.equalsIgnoreCase("1")) {
//                                binding.globaGiftAnimation.startAnimation(animation2);
//                                binding.globaGiftAnimation.setVisibility(View.VISIBLE);
                                try { // new URL needs try catch.
                                    binding.muCarsRVImagee.setVisibility(View.VISIBLE);
                                    SVGAParser parser = new SVGAParser(CallActivity.this);
                                    parser.decodeFromURL(new URL(emojiImg), new SVGAParser.ParseCompletion() {
                                        @Override
                                        public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                                            SVGADynamicEntity dynamicEntity = new SVGADynamicEntity();
                                            dynamicEntity.setDynamicImage(emojiImg, "99"); // Here is the KEY implementation.
                                            SVGADrawable drawable = new SVGADrawable(videoItem, dynamicEntity);
                                            binding.muCarsRVImagee.setImageDrawable(drawable);
                                            binding.muCarsRVImagee.startAnimation();
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    binding.muCarsRVImagee.stopAnimation();
                                                    binding.muCarsRVImagee.setVisibility(View.GONE);
                                                }
                                            }, 14000);
                                        }

                                        @Override
                                        public void onError() {

                                        }
                                    }, null);
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                binding.liveShowEmojiImg.setVisibility(View.VISIBLE);
                                Glide.with(getApplicationContext()).load(emojiImg).into(binding.liveShowEmojiImg);
                                new Handler().postDelayed(() -> binding.liveShowEmojiImg.setVisibility(View.GONE), 2000);
                            }

                            emojiRef.child(roomID).removeValue();
                        }
                    }
                } else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        binding.callMsgLinearLayout.setOnClickListener(v -> openDialogForSendMessage());

        binding.imgHostProfile.setOnClickListener(v -> {
            GoLiveModelClass goLiveModelClass = new GoLiveModelClass();
            goLiveModelClass.setImage(profileImage);
            goLiveModelClass.setName(profileName);
            goLiveModelClass.setUserID(roomID);
            openOtherUserProfile(goLiveModelClass, adminStatus);
        });

        binding.imgProfileuser.setOnClickListener(v -> {
            GoLiveModelClass goLiveModelClass = new GoLiveModelClass();
            goLiveModelClass.setImage(profileImage);
            goLiveModelClass.setName(profileName);
            goLiveModelClass.setUserID(roomID);
            openOtherUserProfile(goLiveModelClass, adminStatus);
        });

        binding.callMuteIMg.setOnClickListener(v -> {
            muteMicRef.child(roomID).child(profileId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String status = "";
                        if (snapshot.getValue().toString() != null) {
                            status = snapshot.getValue().toString();
                        }
                        if (status.equalsIgnoreCase("0")) {
                            binding.imgMuteMic.setImageResource(R.drawable.ic_baseline_mic_off_24);
                        } else {
                            onVoiceMuteClicked(binding.imgMuteMic, "1");
                        }
                    } else {
                        onVoiceMuteClicked(binding.imgMuteMic, "");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        });

        currentTimeStamp = getCurrentTimeStamp();
    }

    private void joinRoom(String roomID) {
        ChatFunctions.joinRoom(roomID);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageModel event) {
        Log.e("--->>> onmessage event", event.getMessage());
        ChatMessageModel chatMessageModels = new ChatMessageModel();
        chatMessageModels.setMessage(event.getMessage());
        chatMessageModels.setUserId(profileId);
        chatMessages.add(chatMessageModels);
        if (chatMessages != null) {
            binding.recyclerAllMessage.scrollToPosition(chatMessages.size() - 1);
        }
        commentAdapter.notifyDataSetChanged();


    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void gamesDialogBox() {

        Dialog dialog_share = new Dialog(CallActivity.this);
        dialog_share.setContentView(R.layout.gameslayoutdesign);
        dialog_share.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog_share.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog_share.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_share.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog_share.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog_share.getWindow().setAttributes(layoutParams);
        Window window = dialog_share.getWindow();
        window.setGravity(Gravity.CENTER_HORIZONTAL);
        dialog_share.show();

        LinearLayout greddyBox = dialog_share.findViewById(R.id.greedyBox);
        LinearLayout greedy = dialog_share.findViewById(R.id.greedy);
        LinearLayout neon = dialog_share.findViewById(R.id.neon);
        LinearLayout lucky = dialog_share.findViewById(R.id.lucky);
        LinearLayout dragonCard = dialog_share.findViewById(R.id.card);

        greddyBox.setOnClickListener(v -> {
            Log.d("AuthToken", "AuthToken" + authToken);
            StringBuffer buffer = new StringBuffer("https://d1irpl18x20qhe.cloudfront.net/bango/greedy-box/index.html");
            buffer.append("?token=" + URLEncoder.encode(authToken.substring(1, authToken.length() - 1)));
            buffer.append("&lang=" + URLEncoder.encode("en"));
            gameLink = buffer.toString();
            dialog_share.dismiss();
            openDialog();
            //   startActivity(new Intent(this, GameScreenActivity.class));
        });

        greedy.setOnClickListener(v -> {
            StringBuffer buffer = new StringBuffer("https://d1irpl18x20qhe.cloudfront.net/bango/kitty-cat-half/index.html");
            buffer.append("?token=" + URLEncoder.encode(authToken.substring(1, authToken.length() - 1)));
            buffer.append("&lang=" + URLEncoder.encode("en"));
            GameScreenActivity.gameLink = buffer.toString();
            Log.d("TAG", "onClick: " + GameScreenActivity.gameLink);
            dialog_share.dismiss();
            openDialog();

        });

        neon.setOnClickListener(v -> {
            StringBuffer buffer = new StringBuffer("https://d1irpl18x20qhe.cloudfront.net/bango/bar7-half/index.html");
            buffer.append("?token=" + URLEncoder.encode(authToken.substring(1, authToken.length() - 1)));
            buffer.append("&lang=" + URLEncoder.encode("en"));
            GameScreenActivity.gameLink = buffer.toString();
            Log.d("TAG", "onClick: " + GameScreenActivity.gameLink);
            dialog_share.dismiss();
            openDialog();
        });

        lucky.setOnClickListener(v -> {

            StringBuffer buffer = new StringBuffer("https://d1irpl18x20qhe.cloudfront.net/bango/luck77-half/index.html");
            buffer.append("?token=" + URLEncoder.encode(authToken.substring(1, authToken.length() - 1)));
            buffer.append("&lang=" + URLEncoder.encode("en"));
            GameScreenActivity.gameLink = buffer.toString();
            Log.d("TAG", "onClick: " + GameScreenActivity.gameLink);
            dialog_share.dismiss();
            openDialog();

        });

        dragonCard.setOnClickListener(v -> {
            StringBuffer buffer = new StringBuffer("https://d1irpl18x20qhe.cloudfront.net/bango/dragon-tiger/index.html");
            buffer.append("?token=" + URLEncoder.encode(authToken.substring(1, authToken.length() - 1)));
            buffer.append("&lang=" + URLEncoder.encode("en"));
            GameScreenActivity.gameLink = buffer.toString();
            Log.d("TAG", "onClick: " + GameScreenActivity.gameLink);
            dialog_share.dismiss();
            openDialog();
        });

    }

    void openDialog() {

        Dialog gamesShow = new Dialog(this);
        gamesShow.setContentView(R.layout.gamesshowdesign);
        gamesShow.getWindow().setBackgroundDrawable(new ColorDrawable());
        Window window2 = gamesShow.getWindow();
        gamesShow.setCanceledOnTouchOutside(true);
        window2.setGravity(Gravity.BOTTOM);
        gamesShow.show();
        window2.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mWebView = gamesShow.findViewById(R.id.webview);
        mWebView.setListener(this, new AdvancedWebView.Listener() {
            @Override
            public void onPageStarted(String url, Bitmap favicon) {
                loading_box.dismiss();
            }

            @Override
            public void onPageFinished(String url) {

            }

            @Override
            public void onPageError(int errorCode, String description, String failingUrl) {

            }

            @Override
            public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

            }

            @Override
            public void onExternalPageRequest(String url) {

            }
        });
        mWebView.setMixedContentAllowed(false);

        if (!gameLink.equals("")) {

            mWebView.loadUrl(gameLink);

            openLoadingBox();

        } else {

            Toast.makeText(this, "Something went wrong , please try again later", Toast.LENGTH_SHORT).show();

            finish();

        }

    }

    private void openLoadingBox() {
        loading_box = new Dialog(this);
        loading_box.setContentView(R.layout.loading_box);
        loading_box.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loading_box.setCanceledOnTouchOutside(false);
        loading_box.setCancelable(false);
        Window window = loading_box.getWindow();
        window.setGravity(Gravity.CENTER);
        loading_box.show();

    }

    private void getBackgroundImageFromFirebase() {
        if (liveHostBackImg != null && !liveHostBackImg.isEmpty()) {
            userLiveBackImgRef.child(liveHostBackImg).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        binding.progressImg.cancelAnimation();
                        binding.progressImg.setVisibility(View.GONE);
                        App.getSharedpref().saveString("theme", snapshot.getValue().toString());
                        Picasso.with(getApplicationContext()).load(snapshot.getValue().toString()).error(R.drawable.themeblack).into(binding.imgBackground);
//                        Glide.with(getApplicationContext()).load(snapshot.getValue().toString()).error(R.drawable.themeblack).into(binding.imgBackground);
                    } else {
                        binding.imgBackground.setImageResource(R.drawable.themeblack);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } else {
        }
    }

    private void openDialogForSendMessage() {

        sendMessageBottomSheet = new BottomSheetDialog(this);
        DialogSendMessageBinding dialogGiftBinding = DialogSendMessageBinding.inflate(LayoutInflater.from(this));
        sendMessageBottomSheet.setContentView(dialogGiftBinding.getRoot());
        sendMessageBottomSheet.show();
        dialogGiftBinding.edtMessage.requestFocus();
        InputMethodManager methodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert methodManager != null && dialogGiftBinding.edtMessage != null;
        methodManager.showSoftInput(dialogGiftBinding.edtMessage, InputMethodManager.SHOW_IMPLICIT);

        Objects.requireNonNull(dialogGiftBinding.rlSend).setOnClickListener(view -> {
            if (liveChatBanned == 0) {
                if (dialogGiftBinding.edtMessage.getText().toString().trim().equalsIgnoreCase("")) {
                } else {
                    if (muteUsers.contains(profileId)) {
                        Toast.makeText(this, "You can not send a message", Toast.LENGTH_SHORT).show();
                        dialogGiftBinding.edtMessage.setText("");
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                String mess = "";
                                mess = dialogGiftBinding.edtMessage.getText().toString();
                                dialogGiftBinding.edtMessage.setText("");
                                sendMessageBottomSheet.dismiss();
                                sendCustomeMessage(mess, "");
                            }
                        });
                    }
                }
            } else {
                Toast.makeText(this, "You are Banned", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendCustomeMessage(String mess, String s) {
        ChatMessageModel chatMessageModel = new ChatMessageModel();
        //sumit
        // chatMessageModel.setGift(gift);
        chatMessageModel.setImage(profileImageSave);
        chatMessageModel.setKey(ref.push().getKey());
        chatMessageModel.setMessage(mess);
        chatMessageModel.setName(sharedpreferences.getString("name", ""));
        chatMessageModel.setUserId(profileId);
        chatMessageModel.setAnnouncement("0");
        chatMessageModel.setTimeStamp(getCurrentTimeStamp());
        sendMessage(chatMessageModel, chatMessageModel.getKey());
    }

    private void sendJoinedMessage() {
        ChatMessageModel chatMessageModel = new ChatMessageModel();
        chatMessageModel.setGift("");
        chatMessageModel.setImage(profileImageSave);
        chatMessageModel.setKey(ref.push().getKey());
        chatMessageModel.setMessage("joined Stream");
        chatMessageModel.setName(sharedpreferences.getString("name", ""));
        chatMessageModel.setUserId(profileId);
        chatMessageModel.setTimeStamp(getCurrentTimeStamp());
        sendMessage(chatMessageModel, chatMessageModel.getKey());
    }

    private void sendMessage(ChatMessageModel chatMessageModel, String key) {
        //ref.child(otherUserId).child(liveType).child(otherUserId).child("chat comments").child(key).setValue(chatMessageModel);

        ZIMCustomTextMessage zimMessage = new ZIMCustomTextMessage();
        zimMessage.message = chatMessageModel.getMessage();
        zimMessage.setMessageTypeEnum(MessageTypeEnum.ROOM_MESSAGE);

        ZIMMessageSendConfig config = new ZIMMessageSendConfig();

        config.priority = ZIMMessagePriority.LOW;

        ZIMPushConfig pushConfig = new ZIMPushConfig();
        pushConfig.title = "Title of the offline push";
        pushConfig.content = "Content of the offline push";
        // pushConfig.extendedData = "Extended info of the offline push";
        config.pushConfig = pushConfig;


        ChatFunctions.sendMessage(roomID, zimMessage, pushConfig, config, ZIMConversationType.ROOM);
        chatMessages.add(chatMessageModel);
        binding.recyclerAllMessage.scrollToPosition(chatMessages.size() - 1);
        if (commentAdapter != null)
            commentAdapter.notifyDataSetChanged();
    }


    private void getCommentChatMessageFirebase() {

        ref.child(otherUserId).child(liveType).child(otherUserId).child("chat comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    // chatMessages.clear();dadasd
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        ChatMessageModel chatMessageModel = dataSnapshot.getValue(ChatMessageModel.class);
                        if (chatMessageModel.getTimeStamp() != null) {
                            if (currentTimeStamp < chatMessageModel.getTimeStamp()) {
                                chatMessages.add(chatMessageModel);
                            }
                        } else {
                            ChatMessageModel chatMessageModels = new ChatMessageModel();
                            chatMessageModels.setGift("");
                            chatMessageModels.setImage(profileImageSave);
                            chatMessageModels.setKey(ref.push().getKey());
                            chatMessageModels.setMessage("Welcome To Bango Live Stream");
                            chatMessageModels.setName(sharedpreferences.getString("name", ""));
                            chatMessageModels.setUserId(profileId);
//                          sendMessage(chatMessageModel, chatMessageModel.getKey());
                            chatMessages.add(chatMessageModels);
                        }
                    }

                    if (chatMessages != null) {
                        binding.recyclerAllMessage.scrollToPosition(chatMessages.size() - 1);
                    }
                    commentAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
    }

    private void sendWelcomeMessageFirebase() {
        ChatMessageModel chatMessageModel = new ChatMessageModel();
        chatMessageModel.setGift("");
        chatMessageModel.setImage(profileImageSave);
        chatMessageModel.setKey(ref.push().getKey());
        chatMessageModel.setMessage("Welcome To Bango Live Stream");
        chatMessageModel.setName(sharedpreferences.getString("name", ""));
        chatMessageModel.setUserId(profileId);

        sendMessage(chatMessageModel, chatMessageModel.getKey());
    }


    public void onVoiceMuteClicked(View view, String status) {
        HashMap<String, Object> data = new HashMap<>();
        if (!isMute) {
            isMute = true;
            // rtcEngine.muteLocalAudioStream(true);
            zegoExpressEngine.mutePublishStreamAudio(true);
            data.put("mute", "0");
        } else {
            isMute = false;
            data.put("mute", "1");
            //   rtcEngine.muteLocalAudioStream(false);
            zegoExpressEngine.mutePublishStreamAudio(false);
            ref.child(otherUserId).child(liveType).child(otherUserId).child("multiLiveRequest").child(profileId).updateChildren(data);
        }

        ImageView iv = (ImageView) view;
        if (isMute) {
            iv.setImageResource(R.drawable.ic_baseline_mic_off_24);
        } else {
            iv.setImageResource(R.drawable.ic_baseline_mic_24);
        }
    }

    private Long getCurrentTimeStamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp.getTime());
        return timestamp.getTime();
    }


    void initUIandEvent() {
        //sumit
        //firebaseAddDataUnderHostId();

        if (liveStatus == "host") {
            sendJoinedMessage();
            if (liveType.equalsIgnoreCase("singleLive")) {
                binding.llOption.setVisibility(View.GONE);
                binding.rlGiftHeart.setVisibility(View.VISIBLE);
                binding.rlGift.setVisibility(View.GONE);
            } else {
                binding.llOption.setVisibility(View.GONE);
                binding.rlGiftHeart.setVisibility(View.VISIBLE);
                binding.rlGift.setVisibility(View.GONE);
                //  getMultiLiveRequestStatus();
            }
        } else {
            binding.rlMultiLiveRequest.setVisibility(View.GONE);
            if (liveType.equalsIgnoreCase("singleLive")) {
                binding.rlMultiLiveRequest.setVisibility(View.GONE);
            } else {
                binding.rlMultiLiveRequest.setVisibility(View.VISIBLE);
            }
            GoLiveModelClass goLiveModelClass = new GoLiveModelClass();
            goLiveModelClass.setUserID(profileId);
            goLiveModelClass.setImage(sharedpreferences.getString("name", ""));
            goLiveModelClass.setLiveStatus(liveStatus);
            goLiveModelClass.setName(sharedpreferences.getString("name", ""));
            goLiveModelClass.setLiveType(liveType);
            goLiveModelClass.setLive(true);
            ref.child(profileId).child(liveType).child(profileId).child("hostLiveInfo").setValue(goLiveModelClass);
//          binding.llOption.setVisibility(View.VISIBLE); this is for all requst this will show for host only
            binding.rlGiftHeart.setVisibility(View.GONE);
            sendWelcomeMessageFirebase();
            // inviteAudienceList.add(goLiveModelClass);
        }
        if (liveStatus == "host") {
            //  exitLiveStream();
        }
        //sumit
        //       getViewerListFirebase();
//        getHeart();
//        getGift();
        commentAdapter = new CommentAdapter(this, chatMessages);
        binding.recyclerAllMessage.setAdapter(commentAdapter);
//        viewerAdapter = new ViewerAdapter(this, viewerList, liveStatus, new ViewerAdapter.Click() {
//            @Override
//            public void onBanned(int position, boolean liveHost) {
//                if (liveHost) {
//                    openOtherUserProfile(viewerList.get(position), 0);
//                } else {
//                    openOtherUserProfile(viewerList.get(position), 1);
//                }
//            }
//        });
//        binding.recyclerViewers.setAdapter(viewerAdapter);
        //Viewer list Gone
        binding.recyclerViewers.setVisibility(View.GONE);
//        addEventHandler(this);
//        final String encryptionKey = getIntent().getStringExtra(ConstantApp.ACTION_KEY_ENCRYPTION_KEY);
//        final String encryptionMode = getIntent().getStringExtra(ConstantApp.ACTION_KEY_ENCRYPTION_MODE);
//        doConfigEngine(encryptionKey, encryptionMode);
//        mGridVideoViewContainer = findViewById(R.id.grid_video_view_container);

        if (liveStatus != "host") {
//            SurfaceView surfaceV = RtcEngine.CreateRendererView(getApplicationContext());
//            preview(true, surfaceV, 0);
//            surfaceV.setZOrderOnTop(false);
//            surfaceV.setZOrderMediaOverlay(false);
//            mUidsList.put(0, surfaceV); // get first surface view
//            rtcEngine().enableLocalVideo(false);
//            rtcEngine().enableLocalAudio(true);
//            rtcEngine().enableAudio();

        } else {
//            rtcEngine().enableLocalVideo(false);
//            rtcEngine().enableLocalAudio(false);
        }
//        mGridVideoViewContainer.initViewContainer(this, 0, mUidsList, mIsLandscape, mGridVideoViewContainer, RecyclerView.VERTICAL); // first is now full view
//        rtcEngine().setAudioProfile(AUDIO_PROFILE_MUSIC_HIGH_QUALITY_STEREO, Constants.AUDIO_SCENARIO_SHOWROOM);
//        joinChannel(getChannelName, config().mUid, getAccessToken);
//        optional();
        binding.rlHeart.setOnClickListener(view -> {
            // sendFlyingHeartInFirebase();
        });

        binding.edtMessage.setOnClickListener(view -> {
            openDialogForSendMessage();
        });

        binding.rlGift.setOnClickListener(view -> {
            //  openGiftDialog();
        });

        binding.rlEmojiGift.setOnClickListener(v -> {
            // setEmojiGifts();
        });

        binding.rlMultiLiveRequest.setOnClickListener(view -> {
            // openRequestMultiLiveDialog();
        });

        binding.rlSendMessage.setOnClickListener(view -> {
            binding.edtMessage.requestFocus();
            openDialogForSendMessage();
        });

        AudioManager audioManager = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        }

        audioManager.setMode(AudioManager.MODE_NORMAL);
        audioManager.setSpeakerphoneOn(false);

    }

    private void getMultiLiveRequest() {
        ref.child(otherUserId).child(liveType).child(otherUserId).child("multiLiveRequest").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    requestMultiLiveList.clear();
                    liveJoinedUserList.clear();
                    liveJoinedHostUserList.clear();
                    liveJoinedHostUserList.add(new GoLiveModelClass());
                    liveJoinedHostUserList.add(new GoLiveModelClass());
                    liveJoinedHostUserList.add(new GoLiveModelClass());
                    liveJoinedHostUserList.add(new GoLiveModelClass());
                    liveJoinedHostUserList.add(new GoLiveModelClass());
                    liveJoinedHostUserList.add(new GoLiveModelClass());
                    liveJoinedHostUserList.add(new GoLiveModelClass());
                    liveJoinedHostUserList.add(new GoLiveModelClass());

                    GoLiveModelClass hostUserDetails = new GoLiveModelClass();
                    hostUserDetails.setUserID(otherUserId);
                    hostUserDetails.setUserName(profileName);
                    hostUserDetails.setImage(profileImage);
                    hostUserDetails.setName(profileName);
                    liveJoinedUserList.clear();
                    liveJoinedUserList.add(0, hostUserDetails);

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        GoLiveModelClass goLiveModelClass = snapshot1.getValue(GoLiveModelClass.class);
                        if (goLiveModelClass.getRequestStatus().equalsIgnoreCase("0")) {
                            if (roomID.equalsIgnoreCase(otherUserId)) {
                                boolean isSlotAvailable = false;
                                for (int i = 0; i < liveJoinedHostUserList.size(); i++) {
                                    isSlotAvailable = !liveJoinedHostUserList.get(i).getUserID().equalsIgnoreCase("");
                                }
                                if (!isSlotAvailable) {
                                    //sumit
                                    // openRequestDialogForMultiLive(goLiveModelClass);
                                }
                            }
                            requestMultiLiveList.add(goLiveModelClass);
                        } else if (goLiveModelClass.getRequestStatus().equalsIgnoreCase("1")) {

                            liveJoinedUserList.add(goLiveModelClass);

                            Toast.makeText(CallActivity.this, "sumitsumit ;-" + goLiveModelClass.getRequestStatus(), Toast.LENGTH_SHORT).show();

                            //empty postion method on the seat
                            if (goLiveModelClass.getSeatPosition() != null) {
                                liveJoinedHostUserList.set(Integer.parseInt(goLiveModelClass.getSeatPosition()), goLiveModelClass);
                            } else {
                                liveJoinedHostUserList.set(getListEmptyPosition(), goLiveModelClass);
                            }

                            if (goLiveModelClass.getUserID().equalsIgnoreCase(profileId)) {

                                //Mute
                                //for mute host to another users
                                if (goLiveModelClass.getMute().equalsIgnoreCase("0")) {
                                    zegoExpressEngine.muteLocalAudioMixing(true);
                                    //  rtcEngine().muteLocalAudioStream(true);
//                                    muteMicRef.child(liveHostBackImg).child(goLiveModelClass.getUserID()).child("status").setValue("0");
                                    muteMicRef.child(roomID).child(goLiveModelClass.getUserID()).setValue("0");
                                    binding.imgMuteMic.setImageResource(R.drawable.ic_baseline_mic_off_24);


                                } else {
                                    zegoExpressEngine.muteLocalAudioMixing(false);
                                    // rtcEngine().muteLocalAudioStream(false);
//                                  muteMicRef.child(liveHostBackImg).child(goLiveModelClass.getUserID()).child("status").setValue("1");
                                    muteMicRef.child(roomID).child(goLiveModelClass.getUserID()).setValue("1");
                                    binding.imgMuteMic.setImageResource(R.drawable.ic_baseline_mic_24);

                                }
                            }
                        }
                    }
                    //sumit
//                    if (userJoinedAdapter != null) {
//                        userJoinedAdapter.notifyDataSetChanged();
//                    }
                    if (multiLiveAudioAdapter != null) {
                        multiLiveAudioAdapter.notifyDataSetChanged();
                    }
//                    if (!liveStatus.equalsIgnoreCase("host")) {
//                        setRequestListAdapter(requestMultiLiveList);
//                        requestMultiLiveAdapter.notifyDataSetChanged();
//                    } else {
//                        if (allPendingRequestAdapter != null) {
//                            allPendingRequestAdapter.notifyDataSetChanged();
//                        }
//                        setAllPendingAdapter(requestMultiLiveList);
//                    }
                    setAcceptedGustHostAdapter(liveJoinedHostUserList);
                    if (multiLiveAudioAdapter != null) {
                        multiLiveAudioAdapter.notifyDataSetChanged();
                    }
                    binding.txtTotalRequest.setText("" + requestMultiLiveList.size());
                } else {
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
    }

    private void setAcceptedGustHostAdapter(List<GoLiveModelClass> liveJoinedUserList) {

        multiLiveAudioAdapter = new MultiLiveAudioAdapter(this, liveJoinedUserList, new MultiLiveAudioAdapter.Click() {
            @Override
            public void setOnUserKickListener(GoLiveModelClass goLiveModelClass, String admin) {
                checkAdmin();
                if (profileId.equalsIgnoreCase(otherUserId) || admin.equalsIgnoreCase(profileId)) {
                    // openKickDialog(goLiveModelClass);
                } else if (profileId.equalsIgnoreCase(goLiveModelClass.getUserID())) {
                }
            }

            @Override
            public void setOnShowUserProfile(GoLiveModelClass goLiveModelClass, int adminStatus, String adminIdThroughCall) {
                //1 means admin hai
                openOtherUserProfile(goLiveModelClass, adminStatus);
                adminIdThroughCallback = adminIdThroughCall;
            }

            @Override
            public void muteMic(GoLiveModelClass goLiveModelClass, AppCompatImageView imgMic, String status) {

                checkAdmin();

                if (roomID.equalsIgnoreCase(profileId) || roomID.equalsIgnoreCase(profileId)) {
                    Map data = new HashMap<>();
                    data.put("mute", status);
                    ref.child(otherUserId).child(liveType).child(otherUserId).child("multiLiveRequest").child(goLiveModelClass.getUserID()).updateChildren(data);
                } else {
                    Toast.makeText(CallActivity.this, "You are  muted by host", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void showEmojiBackToActivity(String emoji, String userId, String hostId) {
            }

            @Override
            public void selectSeat(GoLiveModelClass goLiveModelClass, String position) {
                if (goLiveModelClass.getUserID().equalsIgnoreCase(profileId)) {
                    //lock seat
//                    sendRequestForMultiLive(position,position);
                } else {
                    isHostStatus = true;
                    Toast.makeText(CallActivity.this, "" + isHostStatus, Toast.LENGTH_SHORT).show();
                    sendRequestForMultiLive(position, "", goLiveModelClass.getName());
                }

            }

            @Override
            public void lockSeat(GoLiveModelClass goLiveModelClass, String positon) {
                sendRequestForMultiLive("9", "", goLiveModelClass.getName());
            }

            @Override
            public void inviteForSeat(String position) {

                //  inviteAudienceDialogBox(position);

            }
        });
        binding.rvMultiLiveAudios.setAdapter(multiLiveAudioAdapter);
    }

    GoLiveModelClass goLiveModelClass;

    private void openOtherUserProfile(@NotNull GoLiveModelClass goLiveModelClass, int userOrAdmin) {

        this.goLiveModelClass = goLiveModelClass;
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        ProfileBottomSheetBinding profileBottomSheetBinding = ProfileBottomSheetBinding.inflate(LayoutInflater.from(this));
        bottomSheetDialog.setContentView(profileBottomSheetBinding.getRoot());
        bottomSheetDialog.show();
//sumit
        //    profileBottomSheetBinding.sendGiftsBottomSheetLl.setOnClickListener(view -> openGiftDialog());

        //   profileBottomSheetBinding.inviteUserLineayout.setOnClickListener(view -> inviteAudienceRef.child(roomID).child(goLiveModelClass.getUserID()).setValue("0"));

        profileBottomSheetBinding.otherUserDialogOpenProfileRL.setOnClickListener(view -> {
            App.getSharedpref().saveString("userCheck", goLiveModelClass.getUserID());
            //setPIPScreen();
        });

        if (goLiveModelClass.getUserID().equalsIgnoreCase(profileId)) {
            profileBottomSheetBinding.blockUserImg.setVisibility(View.GONE);
        } else {
            profileBottomSheetBinding.blockUserImg.setVisibility(View.VISIBLE);
        }


        if (userOrAdmin == 0) {

            Toast.makeText(CallActivity.this, "0 admin 1", Toast.LENGTH_SHORT).show();
            if (goLiveModelClass.getUserID().equalsIgnoreCase(profileId)) {

                profileBottomSheetBinding.liveProfileAdminLyout.setVisibility(View.GONE);
                profileBottomSheetBinding.profileBottomFollowingRL.setVisibility(View.GONE);
                profileBottomSheetBinding.profileBottomReminderLLayout.setVisibility(View.GONE);

                if (goLiveModelClass.getUserID().equalsIgnoreCase(profileId) && roomID.equalsIgnoreCase(profileId)) {
                    profileBottomSheetBinding.adminIconImg.setVisibility(View.VISIBLE);
                    profileBottomSheetBinding.adminIconImg.setImageResource(R.drawable.host_icon);
                }

                Toast.makeText(CallActivity.this, "0 admin 2", Toast.LENGTH_SHORT).show();
            } else {
                profileBottomSheetBinding.liveProfileAdminLyout.setVisibility(View.GONE);

                Toast.makeText(CallActivity.this, "0 admin 3", Toast.LENGTH_SHORT).show();
                if (!goLiveModelClass.getUserID().equalsIgnoreCase(profileId) && roomID.equalsIgnoreCase(goLiveModelClass.getUserID())) {
                    profileBottomSheetBinding.liveProfileAdminLyout.setVisibility(View.GONE);

                    Toast.makeText(CallActivity.this, "0 admin 33", Toast.LENGTH_SHORT).show();
                    profileBottomSheetBinding.adminIconImg.setVisibility(View.VISIBLE);
                    profileBottomSheetBinding.adminIconImg.setImageResource(R.drawable.host_icon);

                } else if (roomID.equalsIgnoreCase(profileId)) {

                    profileBottomSheetBinding.liveProfileAdminLyout.setVisibility(View.VISIBLE);

                    Toast.makeText(CallActivity.this, "0 admin 333", Toast.LENGTH_SHORT).show();

                    adminLiveRef.child(roomID).child(goLiveModelClass.getUserID()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Toast.makeText(CallActivity.this, "0 admin 4", Toast.LENGTH_SHORT).show();
                                if (roomID.equalsIgnoreCase(profileId)) {
                                    profileBottomSheetBinding.liveProfileBottomAdminLlyout.setVisibility(View.GONE);
                                    profileBottomSheetBinding.adminStatusTv.setVisibility(View.VISIBLE);

                                    profileBottomSheetBinding.adminIconImg.setVisibility(View.VISIBLE);
                                    profileBottomSheetBinding.adminIconImg.setImageResource(R.drawable.live_admin_icon);
                                } else {
                                    profileBottomSheetBinding.adminStatusTv.setVisibility(View.VISIBLE);

                                    profileBottomSheetBinding.adminIconImg.setVisibility(View.VISIBLE);
                                    profileBottomSheetBinding.adminIconImg.setImageResource(R.drawable.live_admin_icon);
                                }
                            } else {
                                profileBottomSheetBinding.liveProfileAdminLyout.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                } else if (!goLiveModelClass.getUserID().equalsIgnoreCase(profileId)) {

                    Toast.makeText(CallActivity.this, "0 admin 5", Toast.LENGTH_SHORT).show();
                    profileBottomSheetBinding.liveProfileAdminLyout.setVisibility(View.GONE);

                    adminLiveRef.child(roomID).child(goLiveModelClass.getUserID()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                if (goLiveModelClass.getUserID().equalsIgnoreCase(profileId)) {
                                    profileBottomSheetBinding.adminStatusTv.setVisibility(View.VISIBLE);
                                    profileBottomSheetBinding.adminIconImg.setVisibility(View.VISIBLE);
                                    profileBottomSheetBinding.adminIconImg.setImageResource(R.drawable.live_admin_icon);
                                } else {
                                    profileBottomSheetBinding.adminStatusTv.setVisibility(View.VISIBLE);
                                    profileBottomSheetBinding.adminIconImg.setVisibility(View.VISIBLE);
                                    profileBottomSheetBinding.adminIconImg.setImageResource(R.drawable.live_admin_icon);
                                }
                            } else {
                                profileBottomSheetBinding.liveProfileAdminLyout.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                } else {
                    profileBottomSheetBinding.liveProfileAdminLyout.setVisibility(View.VISIBLE);
                    Toast.makeText(CallActivity.this, "0 admin 6", Toast.LENGTH_SHORT).show();
                }
            }

        } else {
            if (adminStatus == 1) {
                Toast.makeText(CallActivity.this, "1 admin 7", Toast.LENGTH_SHORT).show();
                profileBottomSheetBinding.adminStatusTv.setVisibility(View.VISIBLE);

                profileBottomSheetBinding.adminIconImg.setVisibility(View.VISIBLE);
                profileBottomSheetBinding.adminIconImg.setImageResource(R.drawable.live_admin_icon);

                profileBottomSheetBinding.liveProfileBottomAdminLlyout.setVisibility(View.GONE);

                if (goLiveModelClass.getUserID().equalsIgnoreCase(profileId)) {

                    profileBottomSheetBinding.liveProfileAdminLyout.setVisibility(View.GONE);
                    profileBottomSheetBinding.profileBottomFollowingRL.setVisibility(View.GONE);
                    profileBottomSheetBinding.profileBottomReminderLLayout.setVisibility(View.GONE);
//                    Toast.makeText(CallActivity.this, "admin1 show", Toast.LENGTH_SHORT).show();

                } else {

                    profileBottomSheetBinding.liveProfileAdminLyout.setVisibility(View.VISIBLE);
                    profileBottomSheetBinding.liveProfileBottomAdminLlyout.setVisibility(View.GONE);

                    adminLiveRef.child(roomID).child(goLiveModelClass.getUserID()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                if (roomID.equalsIgnoreCase(profileId)) {
//                                    Toast.makeText(CallActivity.this, "test1", Toast.LENGTH_SHORT).show();
                                    profileBottomSheetBinding.liveProfileBottomAdminLlyout.setVisibility(View.GONE);
                                    profileBottomSheetBinding.adminStatusTv.setVisibility(View.VISIBLE);
                                } else if (goLiveModelClass.getUserID().equalsIgnoreCase(profileId)) {

//                                    Toast.makeText(CallActivity.this, "test2", Toast.LENGTH_SHORT).show();
                                    profileBottomSheetBinding.adminStatusTv.setVisibility(View.VISIBLE);
                                    profileBottomSheetBinding.liveProfileAdminLyout.setVisibility(View.GONE);

                                } else if (!goLiveModelClass.getUserID().equalsIgnoreCase(profileId)) {
//                                    Toast.makeText(CallActivity.this, "test3", Toast.LENGTH_SHORT).show();
                                    profileBottomSheetBinding.adminStatusTv.setVisibility(View.VISIBLE);
                                    profileBottomSheetBinding.liveProfileAdminLyout.setVisibility(View.GONE);
                                } else {
//                                    Toast.makeText(CallActivity.this, "test4", Toast.LENGTH_SHORT).show();
                                    profileBottomSheetBinding.adminStatusTv.setVisibility(View.VISIBLE);
                                    profileBottomSheetBinding.liveProfileAdminLyout.setVisibility(View.GONE);
                                }
                            } else {
//                                Toast.makeText(CallActivity.this, "test5", Toast.LENGTH_SHORT).show();

                                if (goLiveModelClass.getUserID().equalsIgnoreCase(roomID)) {
                                    profileBottomSheetBinding.liveProfileBottomAdminLlyout.setVisibility(View.GONE);
                                    profileBottomSheetBinding.liveProfileAdminLyout.setVisibility(View.GONE);
                                    profileBottomSheetBinding.adminStatusTv.setVisibility(View.GONE);
//                                    Toast.makeText(CallActivity.this, "test8", Toast.LENGTH_SHORT).show();
                                } else {
                                    profileBottomSheetBinding.liveProfileBottomAdminLlyout.setVisibility(View.GONE);
                                    profileBottomSheetBinding.liveProfileAdminLyout.setVisibility(View.VISIBLE);
                                    profileBottomSheetBinding.adminStatusTv.setVisibility(View.GONE);
//                                    Toast.makeText(CallActivity.this, "test9", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
//                    Toast.makeText(CallActivity.this, "admin2 show", Toast.LENGTH_SHORT).show();

                    if (goLiveModelClass.getUserID().equalsIgnoreCase(roomID)) {
                        profileBottomSheetBinding.liveProfileAdminLyout.setVisibility(View.GONE);
//                        Toast.makeText(this, "test6", Toast.LENGTH_SHORT).show();
                    } else {
                        profileBottomSheetBinding.liveProfileBottomAdminLlyout.setVisibility(View.GONE);
//                        Toast.makeText(this, "test7", Toast.LENGTH_SHORT).show();
                    }
                }

            } else {
//                Toast.makeText(this, "2 admin", Toast.LENGTH_SHORT).show();
                profileBottomSheetBinding.liveProfileAdminLyout.setVisibility(View.GONE);

                if (goLiveModelClass.getUserID().equalsIgnoreCase(roomID)) {
                    profileBottomSheetBinding.profileBottomFollowingRL.setVisibility(View.GONE);
                    profileBottomSheetBinding.profileBottomReminderLLayout.setVisibility(View.GONE);
//                    Toast.makeText(CallActivity.this, "admin3 show", Toast.LENGTH_SHORT).show();
                } else {

                    profileBottomSheetBinding.profileBottomFollowingRL.setVisibility(View.VISIBLE);
                    profileBottomSheetBinding.profileBottomReminderLLayout.setVisibility(View.VISIBLE);

//                    Toast.makeText(CallActivity.this, "admin4 show", Toast.LENGTH_SHORT).show();
                    profileBottomSheetBinding.adminStatusTv.setVisibility(View.VISIBLE);
                }
            }


            // for mute the user mic
//            setMuteUnMuteUser(GoLiveModelClass goLiveModelClass, String userId);
        }

        //sumit
//        banChatRef.child(roomID).child(goLiveModelClass.getUserID()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    int chatBanStatus = Integer.parseInt(snapshot.getValue().toString());
//                    if (chatBanStatus == 0) {
//                        profileBottomSheetBinding.banChatTv.setText("Chat");
//                    } else {
//                        profileBottomSheetBinding.banChatTv.setText("Banned");
//                    }
//                    profileBottomSheetBinding.liveProfileChatLyout.setOnClickListener(view -> {
//                        if (chatBanStatus == 1) {
//                            banChatRef.child(roomID).child(goLiveModelClass.getUserID()).setValue("0");
//                            profileBottomSheetBinding.banChatTv.setText("Chat");
//                            Toast.makeText(CallActivity.this, "UnBanned Successfully", Toast.LENGTH_SHORT).show();
//                        } else {
//                            banChatDialogbox(goLiveModelClass, profileBottomSheetBinding.banChatTv);
//                        }
//                    });
//
//                } else {
//                    profileBottomSheetBinding.liveProfileChatLyout.setOnClickListener(view -> banChatDialogbox(goLiveModelClass, profileBottomSheetBinding.banChatTv));
//                }
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        //sumit
        //   profileBottomSheetBinding.blockUserImg.setOnClickListener(view -> blockUserAlertBox(goLiveModelClass));
        profileBottomSheetBinding.txtName.setText(goLiveModelClass.getName());
//        profileBottomSheetBinding.txtUserName.setText(goLiveModelClass.getUserName());

        //Sumit
        getUserDetailApi(goLiveModelClass.getUserID(), profileBottomSheetBinding.liveIdProfileFollowTv, profileBottomSheetBinding.userIdAndCountry, profileBottomSheetBinding.followUnfollowImg, profileBottomSheetBinding.bottomProfileAgeTv, profileBottomSheetBinding.bottomProfileGenderImg, profileBottomSheetBinding.anchorimg, profileBottomSheetBinding.vipImage);

        //   getAppliedFrameApi(goLiveModelClass.getUserID(), profileBottomSheetBinding.liveBottomProfieFrame);

        //    profileBottomSheetBinding.liveProfileKickOuttLyout.setOnClickListener(view -> openKickDialog(goLiveModelClass));

        if (muteUsers.contains(goLiveModelClass.getUserID())) {
            profileBottomSheetBinding.txtMute.setText("UnMute");
        } else {
            profileBottomSheetBinding.txtMute.setText("Mute");
        }
        profileBottomSheetBinding.followUnfollowImg.setOnClickListener(view -> {
            if (goLiveModelClass.getUserID().equalsIgnoreCase(profileId)) {
                Toast.makeText(CallActivity.this, "You can't follow yourself", Toast.LENGTH_SHORT).show();
            } else {
                //sumit
                // followUnfollowUser(goLiveModelClass.getUserID(), profileBottomSheetBinding.followUnfollowImg, profileBottomSheetBinding.liveIdProfileFollowTv);
            }

        });
        profileBottomSheetBinding.liveProfileBottomAdminLlyout.setOnClickListener(view -> {

            AdminFirebaseRoot adminFirebaseRoot = new AdminFirebaseRoot("1", goLiveModelClass.getName(), goLiveModelClass.getImage(), goLiveModelClass.getUserID());

            if (adminList == null || adminList.size() == 0) {

                adminLiveRef.child(roomID).child(goLiveModelClass.getUserID()).setValue(adminFirebaseRoot);
                ref.child(otherUserId).child(liveType).child(otherUserId).child("viewer List").child(goLiveModelClass.getUserID()).child("adminStatus").setValue(true);

                Toast.makeText(CallActivity.this, "Added", Toast.LENGTH_SHORT).show();
            } else {

                for (int i = 0; i < adminList.size(); i++) {
                    if (adminList.get(i).getAdminId().equalsIgnoreCase(goLiveModelClass.getUserID())) {
                        Toast.makeText(CallActivity.this, " already admin ", Toast.LENGTH_SHORT).show();
                    } else {
                        adminLiveRef.child(roomID).child(goLiveModelClass.getUserID()).setValue(adminFirebaseRoot);
                        ref.child(otherUserId).child(liveType).child(otherUserId).child("viewer List").child(goLiveModelClass.getUserID()).child("adminStatus").setValue(true);
                        Toast.makeText(CallActivity.this, "Added", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        Glide.with(this).load(goLiveModelClass.getImage()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                profileBottomSheetBinding.progress.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                profileBottomSheetBinding.progress.setVisibility(View.GONE);
                return false;
            }
        }).into(profileBottomSheetBinding.imgProfile);

        profileBottomSheetBinding.llMute1.setOnClickListener(view -> {
            if (muteUsers.contains(goLiveModelClass.getUserID())) {
                GoLiveModelClass goLiveModelClass1 = new GoLiveModelClass();
                goLiveModelClass1.setUserID("-1");
                setMuteUnMuteUser(goLiveModelClass1, goLiveModelClass.getUserID());
            } else {
                setMuteUnMuteUser(goLiveModelClass, goLiveModelClass.getUserID());
            }
            bottomSheetDialog.dismiss();
        });


        profileBottomSheetBinding.llBan.setOnClickListener(view -> {
            //sumit
            // setBannedUser(goLiveModelClass);
            bottomSheetDialog.dismiss();
        });

        profileBottomSheetBinding.llProfile.setOnClickListener(view -> {
            Toast.makeText(this, "coming soon", Toast.LENGTH_SHORT).show();

        });

        profileBottomSheetBinding.llBlock.setOnClickListener(view -> {
            Toast.makeText(this, "coming soon", Toast.LENGTH_SHORT).show();

        });


    }

    private void getUserDetailApi(String otherUserId, TextView othrUsrFollowingTV, TextView countryTv, ImageView followingImg, TextView age, ImageView gender, ImageView anchorimg, ImageView vipImage) {

        HashMap<String, String> data = new HashMap<>();
        data.put("userId", profileId);
        data.put("otherUserId", otherUserId);

        new ApiViewModel().someFunctionality(CallActivity.this, data).observe(CallActivity.this, new Observer<OtherUserDataModel>() {
            @Override
            public void onChanged(OtherUserDataModel otherUserDataModel) {
                if (otherUserDataModel != null) {
                    if (otherUserDataModel.getStatus().equalsIgnoreCase("1")) {
                        countryTv.setText("ID:" + otherUserDataModel.getDetails().getUsername().toString() + " | " + otherUserDataModel.getDetails().getCountry());
                        //  age.setText(CommonUtils.ageCalcualte(otherUserDataModel.getDetails().getDob()));
                        if (otherUserDataModel.getDetails().getGender().equalsIgnoreCase("male")) {
                            //  gender.setImageResource(R.drawable.ic_male_gender__2_);
                        } else {
                            //  gender.setImageResource(R.drawable.femenine);
                        }

                        // anchor status
                        if (otherUserDataModel.getDetails().getHostStatus().equalsIgnoreCase("2")) {
                            anchorimg.setVisibility(View.VISIBLE);
                        } else {
                            anchorimg.setVisibility(View.GONE);
                        }

                        // vip livel
//                                if(otherUserDataModel.getDetails().getVipLevel().equalsIgnoreCase("0")){
//                                    // binding.editProfileVipLvlTv.setText("Viplvl.0");
//                                    vipImage.setVisibility(View.INVISIBLE);
//                                }else{
//                                    if(otherUserDataModel.getDetails().getVipLevel().equalsIgnoreCase("1")){
//                                        vipImage.setVisibility(View.VISIBLE);
//                                        vipImage.setImageResource(R.drawable.vip1img);
//
//                                    }else if(otherUserDataModel.getDetails().getVipLevel().equalsIgnoreCase("2")){
//                                        vipImage.setVisibility(View.VISIBLE);
//                                        vipImage.setImageResource(R.drawable.vip2img);
//
//                                    }else if(otherUserDataModel.getDetails().getVipLevel().equalsIgnoreCase("3")){
//                                        vipImage.setVisibility(View.VISIBLE);
//                                        vipImage.setImageResource(R.drawable.vip3img);
//
//
//                                    }else if(otherUserDataModel.getDetails().getVipLevel().equalsIgnoreCase("4")){
//                                        vipImage.setVisibility(View.VISIBLE);
//                                        vipImage.setImageResource(R.drawable.vip4img);
//                                    }else if(otherUserDataModel.getDetails().getVipLevel().equalsIgnoreCase("5")){
//                                        vipImage.setVisibility(View.VISIBLE);
//                                        vipImage.setImageResource(R.drawable.vip5img);
//                                    }else{
//                                        // binding.editProfileVipLvlTv.setText("Viplvl."+App.getSharedpref().getString("vipLevel"));
//                                        vipImage.setVisibility(View.INVISIBLE);
//                                    }
//                                    //  binding.editProfileVipLvlTv.setText("Viplvl."+App.getSharedpref().getString("vipLevel"));
//
//                                }

//                                if (otherUserDataModel.getDetails().isFollowStatus()) {
//                                    j = 1;
//                                    followingImg.setImageResource(R.drawable.ic_minus__2_);
//                                    followingImg.setImageTintList(ColorStateList.valueOf(Color.BLACK));
//                                    othrUsrFollowingTV.setText("Following");
//                                } else {
//                                    j = 2;
//                                    followingImg.setImageTintList(ColorStateList.valueOf(Color.BLACK));
//                                    followingImg.setImageResource(R.drawable.ic_plus__6_);
//                                    othrUsrFollowingTV.setText("Follow");
//                                }
                    } else {
                    }

//                            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_left);
//                            binding.animationRL.startAnimation(animation);
//
//                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.rlGustHost.getLayoutParams();
//                            params.topMargin = 40;
//
//                            final Handler handler = new Handler(Looper.getMainLooper());
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    binding.animationRL.setVisibility(View.GONE);
//                                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.rlGustHost.getLayoutParams();
//                                    params.topMargin = 80;
//                                }
//                            }, 5000);
//                            binding.animationRL.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(CallActivity.this, "Technical issue", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void setMuteUnMuteUser(GoLiveModelClass goLiveModelClass1, String userID) {
    }

    private void checkAdmin() {
        adminLiveRef.child(roomID).child(profileId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    adminId = snapshot.child("adminId").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private int getListEmptyPosition() {
        //this empty position live users
        for (int i = 0; i < liveJoinedHostUserList.size(); i++) {
            if (liveJoinedHostUserList.get(i).getUserID().equalsIgnoreCase("")) {
                emptyPosition = i;
                break;
            }
        }
        return emptyPosition;
    }


    private void sendRequestForMultiLive(String seatPosition, String lockSeat, String name) {
        Toast.makeText(this, "sit", Toast.LENGTH_SHORT).show();
        GoLiveModelClass goLiveModelClass = new GoLiveModelClass();
        goLiveModelClass.setUserID(profileId);
        String image = profileImage;
        goLiveModelClass.setImage(sharedpreferences.getString("profileImage", ""));
        goLiveModelClass.setName(sharedpreferences.getString("name", ""));
        goLiveModelClass.setUserName(profileUniqueId);
        goLiveModelClass.setRequestStatus("0");
        goLiveModelClass.setRequestStatus("1");
        isHostStatus = true;

        App.showLog("sendRequestForMultiLive loginRoom");
        loginRoom(profileId, profileName, audioRoomId, isHostStatus);


//      goLiveModelClass.setSvga(profileFrame);
//      goLiveModelClass.setEntryEffect(entryFrameEffect);

        goLiveModelClass.setSeatPosition(seatPosition);
        goLiveModelClass.setSeatLock(lockSeat);
        // 1 for not mute
        goLiveModelClass.setMute(mutevalueOfUSer);

        ref.child(otherUserId).child(liveType).child(otherUserId).child("multiLiveRequest").child(profileId).setValue(goLiveModelClass);
    }

    private void emojiBottomSheet() {

        Dialog viewDetails_box = new Dialog(this);
        viewDetails_box.setContentView(R.layout.call_emoji_bottom_sheet_dialog_box);
        viewDetails_box.getWindow().setBackgroundDrawable(new ColorDrawable());
        Window window = viewDetails_box.getWindow();
        viewDetails_box.setCanceledOnTouchOutside(true);
        window.setGravity(Gravity.BOTTOM);
        viewDetails_box.show();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RecyclerView emojiRV = viewDetails_box.findViewById(R.id.emojiRV);
        new ApiViewModel().sendLiveGift(this, profileId, "14").observe(this, new Observer<GiftModel>() {
            @Override
            public void onChanged(GiftModel giftModel) {
                if (giftModel != null) {
                    if (giftModel.getStatus() == 1) {
                        EmojiRVAdpter emojiRVAdpter = new EmojiRVAdpter(giftModel.getDetails().getGifts(), CallActivity.this, new EmojiRVAdpter.Callback() {
                            @Override
                            public void callback(GiftModel.Gift detail) {
                                HashMap<String, String> sendEmojiHs = new HashMap<>();
                                sendEmojiHs.put("emojiImg", detail.getImage());
                                sendEmojiHs.put("emojiSenderId", profileId);
                                sendEmojiHs.put("emojiSenderName", App.getSharedpref().getString("name"));
                                sendEmojiHs.put("emojiSenderImg", App.getSharedpref().getString("image"));
                                sendEmojiHs.put("status", "0");
                                sendEmojiHs.put("giftStatus", "2");
                                emojiRef.child(roomID).setValue(sendEmojiHs).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        viewDetails_box.dismiss();
                                    }
                                });
                            }
                        });
                        emojiRV.setAdapter(emojiRVAdpter);
                    }
                }
            }
        });

    }


    private void menuDialogBox() {
        Dialog dialog_share = new Dialog(CallActivity.this);
        dialog_share.setContentView(R.layout.menu_dialog_box);
        dialog_share.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog_share.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog_share.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_share.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog_share.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog_share.getWindow().setAttributes(layoutParams);
        Window window = dialog_share.getWindow();
        window.setGravity(Gravity.CENTER_HORIZONTAL);
        dialog_share.show();

        /************************** FIND IDS DIALOG **************************/
        LinearLayout themeIcon = dialog_share.findViewById(R.id.themeIcon);
        LinearLayout musicIcon = dialog_share.findViewById(R.id.musicIcon);
        LinearLayout lockIcon = dialog_share.findViewById(R.id.lockIcon);

        /************************** CLICK LISTENERS DIALOG **************************/

        themeIcon.setOnClickListener(v -> {
            themeBottomSheet(dialog_share);
            dialog_share.dismiss();
        });

        /************************** MUSIC LISTENER **************************/
        musicIcon.setOnClickListener(v -> {
            requestPermissions();
        });

        lockIcon.setOnClickListener(v -> {
            Alerter.create(this).setTitle("Lock Alert").setText("This feature is coming soon").setBackgroundColorRes(R.color.app_dark_color).show();
        });
    }

    /************************** THEME DIALOG **************************/
    private void themeBottomSheet(Dialog dialog) {
        LiveFreeThemeFragment.liveHostBackImg = liveHostBackImg;
        LivePurchasedThemeFragment.liveHostBackImg = liveHostBackImg;
        LiveThemeFragment.liveHostBackImg = liveHostBackImg;
        themesFragment = new LiveThemeFragment();
        themesFragment.show(getSupportFragmentManager(), themesFragment.getTag());
    }

    /************************** MUSIC PLAY DIALOG **************************/
    TextView musicSongName;
    TextView startMusicTimeTv;
    TextView endMusicTimeTv;
    SeekBar seekbarDialog12;

    private void playMusicDialogBox() {
        outSideBoxMusicCheck = 1;
        musicOnOffStatus = 1;
        Dialog dialog_share = new Dialog(CallActivity.this);
        dialog_share.setContentView(R.layout.music_play_dialog);
        dialog_share.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_share.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        ImageView switcch = dialog_share.findViewById(R.id.switcch);
        ImageView musicplaylist = dialog_share.findViewById(R.id.musicplaylist);
        ImageView nextMusic = dialog_share.findViewById(R.id.nextMusic);
        ImageView speaker = dialog_share.findViewById(R.id.speaker);
        ImageView minimize = dialog_share.findViewById(R.id.minimize);
        TextView volumetext = dialog_share.findViewById(R.id.volumetext);
        AppCompatSeekBar volumeSeekbar = dialog_share.findViewById(R.id.seekbarDialogvolume);
        LinearLayout volumelayout = dialog_share.findViewById(R.id.volumelayout);
        dialog_share.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog_share.getWindow().getAttributes());
        Window window = dialog_share.getWindow();
        window.setGravity(Gravity.BOTTOM);
        dialog_share.show();
        musicSongName = dialog_share.findViewById(R.id.musicSongName);
        startMusicTimeTv = dialog_share.findViewById(R.id.startMusicTimeTv);
        endMusicTimeTv = dialog_share.findViewById(R.id.endMusicTimeTv);
        playMusicDialogImg = dialog_share.findViewById(R.id.playMusicDialogImg);
        seekbarDialog12 = dialog_share.findViewById(R.id.seekbarDialog);
        musicSongName.setText(musicDetails.getTitle());
        endMusicTimeTv.setText(formatDuration(musicDetails.getDuration()));
        musicList = appDatabase.userDao().getAllSongs();

        /************************** NEXT PLAY MUSIC CLICK IN ROOM **************************/
        nextMusic.setOnClickListener(v -> {
            musicOnOffStatus = 1;
            try {
                if (musicList.size() > possss) {
                    possss++;
                    binding.musicPlayCirImg.setVisibility(View.VISIBLE);
                    binding.musicPlayCirImg.playAnimation();
                    ZegoAudioEffectPlayConfig config = new ZegoAudioEffectPlayConfig();
                    config.playCount = 10;
                    config.isPublishOut = true;
                    audioEffectPlayer.start(musicList.get(possss).getId(), musicList.get(possss).getPath(), config);
                    musicSongName.setText(musicList.get(possss).getTitle());
                } else {
                    possss = 0;
                }
            } catch (Exception e) {
                Toast.makeText(CallActivity.this, "" + e, Toast.LENGTH_SHORT).show();
            }
        });
        musictime = startMusicTimeTv.getText().toString();

        /************************** VOLUME SEEK BAR **************************/
        volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                volumetext.setText(progress + "" + "/" + "" + "100");
                int vol = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        /************************** ADD MUSIC DIALOG **************************/
        musicplaylist.setOnClickListener(v -> addMusicDialogBox());
        playMusicDialogImg.setOnClickListener(view -> {
            if (musicStatus == 0) {
                musicOnOffStatus = 0;
                playMusicFragmentImg.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                playMusicDialogImg.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                pause_music();
                binding.musicPlayCirImg.setVisibility(View.VISIBLE);
                binding.musicPlayCirImg.cancelAnimation();
                musicCountDownTimer.cancel();
                resumeCheck = 1;
                musicStatus = 1;
            } else {
                musicOnOffStatus = 1;
                playMusicFragmentImg.setImageResource(R.drawable.ic_baseline_pause_circle_24);
                playMusicDialogImg.setImageResource(R.drawable.ic_baseline_pause_circle_24);
                resume_music();
                binding.musicPlayCirImg.setVisibility(View.VISIBLE);
                binding.musicPlayCirImg.playAnimation();
                musicStatus = 0;
                resumeCheck = 2;
            }
        });

        /************************** SWITCH PLAY MUSIC DIALOG IN ROOM **************************/
        switcch.setOnClickListener(v -> {
            musicOnOffStatus = 0;
            pause_music();
            binding.musicPlayCirImg.setVisibility(View.GONE);
            binding.musicPlayCirImg.cancelAnimation();
            dialog_share.dismiss();
        });

        /************************** MINIMIZE PLAY MUSIC DIALOG IN ROOM **************************/
        minimize.setOnClickListener(v -> {
            binding.musicPlayCirImg.setVisibility(View.VISIBLE);
            binding.musicPlayCirImg.playAnimation();
            dialog_share.dismiss();
        });
        seekbarDialog12.setFocusableInTouchMode(false);
    }

    ModelSendGift modelSendGiftLucky;
    String dataGiftLucky;
    List<AlgorithmItem> gift;

    @Override
    public void getGift(ModelSendGift modelSendGift, List<AlgorithmItem> gift, String dataGift) {
        modelSendGiftLucky = modelSendGift;
        dataGiftLucky = dataGift;
        this.gift = gift;
        //sumit
        // binding.rlHit.setVisibility(View.VISIBLE);
        //  luckyCountNumber = Integer.parseInt(modelSendGift.getGiftCount());
        startTimerLucky();
    }

    public ChildEventListener giftsEventListener = new ChildEventListener() {

        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Log.d("TAG", "onChildAdded: +hewj");
            boolean o = false;

            ModelSendGift modelSendGift = snapshot.getValue(ModelSendGift.class);
            assert modelSendGift != null;
            setcoins(modelSendGift);
            Toast.makeText(CallActivity.this, "tytyty", Toast.LENGTH_SHORT).show();
            showGiftImage(modelSendGift, snapshot.getKey());
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };


    @SuppressLint("SetTextI18n")
    private void setcoins(ModelSendGift modelSendGift) {
        String tvTotalCoinsUSer;
        if (modelSendGift.getMybox() != null) {
            if (modelSendGift.getMybox().contains(".")) {
                tvTotalCoinsUSer = modelSendGift.getMybox().substring(0, modelSendGift.getMybox().indexOf("."));
            } else {
                tvTotalCoinsUSer = modelSendGift.getMybox();
            }
            binding.tvTotalCoins.setText(tvTotalCoinsUSer);
        } else {
            binding.tvTotalCoins.setText("0");
        }


        String mainChapterNum;
        if (modelSendGift.getLivebox() != null) {
            if (modelSendGift.getLivebox().contains(".")) {
                mainChapterNum = modelSendGift.getLivebox().substring(0, modelSendGift.getLivebox().indexOf("."));
            } else {
                mainChapterNum = modelSendGift.getLivebox();
            }
            // binding.tvStars.setText(mainChapterNum);
        } else {
            // binding.tvStars.setText("0");
        }

    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    private void showGiftImage(ModelSendGift modelSendGift, String key) {
        Log.d("TAG", "showGiftImage: inn");
        multiLiveAudioAdapter.notifyDataSetChanged();
        if (modelSendGift.getGiftType() != null) {
            if (modelSendGift.getGiftType().equalsIgnoreCase("lucky")) {
                binding.luckyGift.setVisibility(View.VISIBLE);
                FirebaseHelper.deleteGiftAfterRecevingFromFireBase(key, sharedpreferences.getString("id", ""));
                binding.imgHostProfile.setVisibility(View.VISIBLE);
                binding.luckyGift.animate().x(binding.imgHostProfile.getX()).y(binding.imgHostProfile.getY()).setDuration(4000).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        binding.luckyGift.setX(0);
                        binding.luckyGift.setY(0);
                    }
                }).start();
                Glide.with(binding.luckyGift.getContext()).load(modelSendGift.getGiftImage()).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        binding.luckyGift.clearAnimation();
                        binding.luckyGift.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                        new Handler().postDelayed(() -> {
                            binding.luckyGift.clearAnimation();
                            binding.luckyGift.setVisibility(View.GONE);
                        }, 4000);
                        return false;
                    }
                }).into(binding.luckyGift);
            } else {
                if (CallActivity.this.isDestroyed()) return;
                final long[] time = {8000l};

                try { // new URL needs try catch.
                    Log.d("animation", "animationn" + modelSendGift.getGiftImage());
                    SVGAParser parser = new SVGAParser(CallActivity.this);
                    parser.decodeFromURL(new URL(modelSendGift.getGiftImage()), new SVGAParser.ParseCompletion() {
                        @Override
                        public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                            SVGADynamicEntity dynamicEntity = new SVGADynamicEntity();
                            Log.d("animation", "animationn" + modelSendGift.getGiftImage());
                            dynamicEntity.setDynamicImage(modelSendGift.getGiftImage(), "99"); // Here is the KEY implementation.
                            SVGADrawable drawable = new SVGADrawable(videoItem, dynamicEntity);
                            binding.lottieGift.setVisibility(View.VISIBLE);
                            binding.lottieGift.setImageDrawable(drawable);
                            binding.lottieGift.startAnimation();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    binding.lottieGift.stopAnimation();
                                    binding.lottieGift.setVisibility(View.GONE);
                                }
                            }, 4000);
                        }

                        @Override
                        public void onError() {

                        }
                    }, null);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

//                Glide.with(binding.lottieGift.getContext()).load(modelSendGift.getGiftImage()).listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        if (countDownTimer != null) countDownTimer.cancel();
//                        Toast.makeText(CallActivity.this, "Gift Image Is Not Valid", Toast.LENGTH_SHORT).show();
//                        FirebaseHelper.deleteGiftAfterRecevingFromFireBase(key, sharedpreferences.getString("id",""));
//                        if (modelSendGift.getGiftType().equalsIgnoreCase("luxury")) {
//                            time[0] = 10000;
//                        } else if (modelSendGift.getGiftType().equalsIgnoreCase("vip")) {
//                            time[0] = 12000;
//                        } else {
//                            time[0] = 8000l;
//                        }
//                        countDownTimer = new CountDownTimer(time[0], 1000) {
//                            @Override
//                            public void onTick(long l) {
//                                Log.i("onTick: ", (l / 1000) + "");
//                            }
//
//                            @Override
//                            public void onFinish() {
////                                stopAudio();
//                                binding.lottieGift.setVisibility(View.GONE);
//                            }
//                        };
//                        countDownTimer.start();
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
////                        playAudio(modelSendGift.getSound());
//                        if (countDownTimer != null) countDownTimer.cancel();
//                        FirebaseHelper.deleteGiftAfterRecevingFromFireBase(key, sharedpreferences.getString("id",""));
//                        time[0] = 8000l;
//                        if (modelSendGift.getGiftType().equalsIgnoreCase("luxury")) {
//                            time[0] = 10000;
//                        } else if (modelSendGift.getGiftType().equalsIgnoreCase("vip")) {
//                            time[0] = 12000;
//                        }
//                        countDownTimer = new CountDownTimer(time[0], 1000) {
//                            @Override
//                            public void onTick(long l) {
//                                Log.i("onTick: ", (l / 1000) + "");
//                            }
//
//                            @Override
//                            public void onFinish() {
////                                stopAudio();
//
//                                binding.lottieGift.setVisibility(View.GONE);
//
//                            }
//                        };
//                        countDownTimer.start();
//                        return false;
//                    }
//                }).into(binding.lottieGift);

            }
        }
        FirebaseHelper.deleteGiftAfterRecevingFromFireBase(key, getIntent().getStringExtra("roomID"));

    }

    /************************** COUNT DOWN TIMER **************************/
    CountDownTimer countDownTimerTwo;
    int secLucky;

    private void startTimerLucky() {
        if (countDownTimerTwo != null) {
            countDownTimerTwo = null;
        }
        countDownTimerTwo = new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long l) {
                secLucky = (int) (l / 1000);
                //    binding.count.setText(String.valueOf(secLucky));
                if (secLucky == 0) {
                    //  binding.rlHit.clearAnimation();
                    //  binding.rlHit.setVisibility(View.GONE);
                    countDownTimerTwo = null;
                }
            }

            @Override
            public void onFinish() {
                //   binding.rlHit.clearAnimation();
                //   binding.rlHit.setVisibility(View.GONE);
                countDownTimerTwo = null;
            }
        }.start();
    }

    /************************** PLAY SEEK BAR MUSIC DIALOG **************************/
    TextView musicStartTimeTv;
    ImageView playMusicFragmentImg;
    TextView musicFragSongName;
    TextView musicEndTimeTv;
    long duration1;
    String title1;

    private void addMusicDialogBox() {
        Dialog dialog_share = new Dialog(CallActivity.this);
        dialog_share.setContentView(R.layout.play_music_dialog_box);
        dialog_share.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog_share.getWindow().getAttributes().windowAnimations = R.style.musicDialogAnimation;
        dialog_share.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog_share.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog_share.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog_share.getWindow().setAttributes(layoutParams);
        Window window = dialog_share.getWindow();
        window.setGravity(Gravity.BOTTOM);
        musicRv = dialog_share.findViewById(R.id.musicRv);
        dialog_share.show();

        LinearLayoutManager layoutManagermusic = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        musicRv.setLayoutManager(layoutManagermusic);

//      List<MusicTable> musicList = new ArrayList<>();
        musicList = appDatabase.userDao().getAllSongs();
        TextView musicRescanTv = dialog_share.findViewById(R.id.musicRescanTv);
        LinearLayout noMusicLayout = dialog_share.findViewById(R.id.noMusicLayout);
        RelativeLayout musicPlayRL = dialog_share.findViewById(R.id.musicPlayRL);
        playMusicFragmentImg = (ImageView) dialog_share.findViewById(R.id.playMusicFragmentImg);
        musicFragSongName = (TextView) dialog_share.findViewById(R.id.musicFragSongName);
        musicStartTimeTv = (TextView) dialog_share.findViewById(R.id.musicStartTimeTv);
        musicEndTimeTv = (TextView) dialog_share.findViewById(R.id.musicEndTimeTv);
        seekbar1 = (AppCompatSeekBar) dialog_share.findViewById(R.id.seekbar);
        startMusicTimeTv = (TextView) dialog_share.findViewById(R.id.musicStartTimeTv);
        ImageView volumeMusicFragmentImg = (ImageView) dialog_share.findViewById(R.id.volumeMusicFragmentImg);
        ImageView musicBackImg = (ImageView) dialog_share.findViewById(R.id.musicBackImg);
        AppCompatButton scanAndAddMusicBtn = (AppCompatButton) dialog_share.findViewById(R.id.scanAndAddMusicBtn);
        musicBackImg.setOnClickListener(v -> dialog_share.dismiss());
        if (musicList.isEmpty()) {
            noMusicLayout.setVisibility(View.VISIBLE);
            musicRv.setVisibility(View.GONE);
            musicRescanTv.setVisibility(View.GONE);
            musicPlayRL.setVisibility(View.GONE);
        } else {
            noMusicLayout.setVisibility(View.GONE);
            musicRv.setVisibility(View.VISIBLE);
            musicRescanTv.setVisibility(View.VISIBLE);
            musicRVAdapter = new MusicRVAdapter(musicList, CallActivity.this, new MusicRVAdapter.Callback() {
                @Override
                public void playMusic(MusicTable musicDetail, int musicPlayStatus, ImageView imageView, boolean status, int postin) {
                    musicDetails = musicDetail;
                    valueTime = 0;
                    resumeCheck = 0;
                    musicpos = postin;
                    duration1 = musicDetail.getDuration();
                    title1 = musicDetail.getTitle();
                    setTimer(musicDetails.getDuration(), musicDetails.getTitle(), false, 0, musicpos);
                    //  audioEffectManager = rtcEngine().getAudioEffectManager();
                    // audioEffectPlayer.setEffectPosition(musicDetail.getId(), 1);

                    musicFragSongName.setText(musicDetail.getTitle());
                    musicEndTimeTv.setText(formatDuration(musicDetail.getDuration()));

                    MusicId = musicDetail.getId();
                    //  audioEffectPlayer.preloadEffect(MusicId, musicDetail.getPath());

                    play_music(musicDetail, 0);
//                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.animationRL.getLayoutParams();
//                    params.addRule(RelativeLayout.BELOW, R.id.imgHostProfile);

                }

                /************************** SET MUSIC TIMER **************************/
                private void setTimer(long duration, String title, boolean stopTime, float resumeTimes, int posin) {
                    if (musicCountDownTimer != null) {
                        musicCountDownTimer.cancel();
                        musicCountDownTimer = null;
                    }
                    float dureationSeekbar = ((float) duration / 100000);
                    musicCountDownTimer = new CountDownTimer(duration, 1000) {
                        public void onTick(long millisUntilFinished) {
                            int pos = posin;
                            musicStartTimeTv.setText(formatDuration((long) (duration - (valueTime * 1000))));
                            musictime = musicStartTimeTv.getText().toString();
                            if (musictime.equals("00:01") || musictime.equals("00:00")) {
                                pos++;
                                Log.d("musicStartTimeTv", "musicStartTimeTv " + musicStartTimeTv);
                                binding.musicPlayCirImg.setVisibility(View.VISIBLE);
                                binding.musicPlayCirImg.playAnimation();
                                ZegoAudioEffectPlayConfig config = new ZegoAudioEffectPlayConfig();
                                config.playCount = 10;
                                config.isPublishOut = true;
                                audioEffectPlayer.start(musicList.get(pos).getId(), musicList.get(pos).getPath(), config);
                                musicFragSongName.setText(title);
                            } else {
                                pos = 0;
                            }
                            Log.d("music", "music =" + " " + formatDuration((long) (duration - (valueTime * 1000))));
                            seekbar1.setProgress((int) ((++valueTime) / dureationSeekbar));
                            //this is for outside music dialog box
                            if (outSideBoxMusicCheck == 1) {
                                startMusicTimeTv.setText(formatDuration((long) (duration - (valueTime * 1000))));
                                seekbarDialog12.setProgress((int) ((++valueTime) / dureationSeekbar));
                                String text = startMusicTimeTv.getText().toString();
                                Log.d("text", "musicTime = " + text);
                            }
                        }

                        public void onFinish() {
                            musicCountDownTimer.cancel();
                            binding.musicPlayCirImg.setVisibility(View.GONE);
                        }
                    };
                    musicCountDownTimer.start();
                }

                /************************** PLAY MUSIC DIALOG **************************/
                private void play_music(MusicTable musicDetail, int i) {

                    // musicOnOffStatus = 1;
                    binding.musicPlayCirImg.setVisibility(View.VISIBLE);

                    binding.musicPlayCirImg.playAnimation();
                    int audioEffectID = 1;
                    ZegoAudioEffectPlayConfig config = new ZegoAudioEffectPlayConfig();
                    config.playCount = 10;
                    config.isPublishOut = true;
                    audioEffectPlayer.start(audioEffectID, musicDetail.getPath(), config);

                    //this is for outside dialog box
                    if (outSideBoxMusicCheck == 1) {
                        playMusicDialogImg.setImageResource(R.drawable.ic_baseline_pause_circle_24);
                    }

                }

                /************************** DELETE MUSIC DIALOG **************************/
                @Override
                public void deleteMusic(MusicTable musicDetail, int posn) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CallActivity.this);
                    alertDialogBuilder.setMessage("Are you sure, You wanted to make decision");
                    alertDialogBuilder.setPositiveButton("yes", (arg0, arg1) -> {
                        appDatabase.userDao().deleteById(musicDetail.getId());
                        musicRv.setAdapter(musicRVAdapter);
                        musicRVAdapter.notifyDataSetChanged();
                    });
                    alertDialogBuilder.setNegativeButton("No", (dialog, which) -> finish());

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }
            });
            musicRv.setAdapter(musicRVAdapter);

        }

        //this is for outSide music box
//        if (outSideBoxMusicCheck == 1) {
//            seekbarDialog12.setFocusableInTouchMode(false);
//        }
        seekbar1.setFocusableInTouchMode(false);
        seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                play_music(musicDetails, seekBar.getProgress());
//                musicStartTimeTv.setText(formatDuration(seekBar.getProgress()));
            }
        });


        /************************** MUSIC PLAY BUTTON **************************/
        playMusicFragmentImg.setOnClickListener(view -> {
            if (musicStatus == 0) {
                playMusicFragmentImg.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                pause_music();
                musicCountDownTimer.cancel();
                resumeCheck = 1;
                musicStatus = 1;
                binding.musicPlayCirImg.setVisibility(View.GONE);
                binding.musicPlayCirImg.cancelAnimation();
                //this is for outside musicDialog
                if (outSideBoxMusicCheck == 1) {
                    playMusicDialogImg.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                }
            } else {
                playMusicFragmentImg.setImageResource(R.drawable.ic_baseline_pause_circle_24);
                resume_music();
                musicStatus = 0;
                resumeCheck = 2;
                binding.musicPlayCirImg.setVisibility(View.VISIBLE);
                binding.musicPlayCirImg.playAnimation();
                //this is for outside musicDialog
                if (outSideBoxMusicCheck == 1) {
                    playMusicDialogImg.setImageResource(R.drawable.ic_baseline_pause_circle_24);
                }
            }
        });
        musicRescanTv.setOnClickListener(view -> {
            rescanDialog();
            dialog_share.dismiss();
        });
        scanAndAddMusicBtn.setOnClickListener(view -> rescanDialog());
    }

    /************************** PAUSE MUSIC **************************/
    private void pause_music() {
        audioEffectPlayer.pauseAll();
    }

    /************************** RESUME MUSIC **************************/
    private void resume_music() {
        audioEffectPlayer.resumeAll();
    }

    /************************** ALL LOCAL MUSIC DISPLAY DIALOG IN ROOM **************************/
    private void rescanDialog() {
        Dialog dialog_share = new Dialog(CallActivity.this);
        dialog_share.setContentView(R.layout.rescan_dialog_box);
        dialog_share.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog_share.getWindow().getAttributes().windowAnimations = R.style.musicDialogAnimation;
        dialog_share.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        ImageView localAddedBackImg = dialog_share.findViewById(R.id.localAddedBackImg);
        dialog_share.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog_share.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog_share.getWindow().setAttributes(layoutParams);
        Window window = dialog_share.getWindow();
        window.setGravity(Gravity.BOTTOM);
        dialog_share.show();
        getMusic();
        TextView save = dialog_share.findViewById(R.id.save);
        localAddedBackImg.setOnClickListener(v -> {
            dialog_share.dismiss();
            addMusicDialogBox();
        });
        save.setOnClickListener(v -> {
            dialog_share.dismiss();
            addMusicDialogBox();
        });
        RecyclerView localAddedRV = dialog_share.findViewById(R.id.localAddedRV);
        LocalAddedAdapter localAddedAdapter = new LocalAddedAdapter(audioList, CallActivity.this);
        localAddedRV.setAdapter(localAddedAdapter);
        localAddedAdapter.notifyDataSetChanged();
    }

    /************************** DURATION CONVERTS INTO MINUTES **************************/
    //this method duration of song covert into mintues
    private String formatDuration(long duration) {
        long minutes = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS);
        long seconds = TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS) - minutes * TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES);
        return String.format("%02d:%02d", minutes, seconds);
    }

    /************************** REQUEST PERMISSIONS IN ROOM **************************/
    private void requestPermissions() {
        // below line is use to request permission in the current activity.
        // this method is use to handle error in runtime permissions
        Dexter.withActivity(this)
                // below line is use to request the number of permissions which are required in our app.
                .withPermissions(android.Manifest.permission.CAMERA,
                        // below is the list of permissions
                        android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE)
                // after adding permissions we are calling an with listener method.
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        // this method is called when all permissions are granted
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            // do you work now
                            addMusicDialogBox();
                        }
                        // check for permanent denial of any permission
                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permanently, we will show user a dialog message.
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).withErrorListener(error -> {
                    // we are displaying a toast message for error message.
                    Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                })
                // below line is use to run the permissions on same thread and to check the permissions
                .onSameThread().check();
    }

    /************************** SETTING PERMISSIONS **************************/
    private void showSettingsDialog() {
        // we are displaying an alert dialog for permissions
        AlertDialog.Builder builder = new AlertDialog.Builder(CallActivity.this);

        // below line is the title for our alert dialog.
        builder.setTitle("Need Permissions");

        // below line is our message for our dialog
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            // this method is called on click on positive button and on clicking shit button
            // we are redirecting our user from our app to the settings page of our app.
            dialog.cancel();
            // below is the intent from which we are redirecting our user.
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivityForResult(intent, 101);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            // this method is called when user click on negative button.
            dialog.cancel();
        });
        // below line is used to display our dialog
        builder.show();
    }

    /************************** GET MUSIC **************************/
    private void getMusic() {
        String[] proj = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.DATE_ADDED, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID};// Can include more data for more details and check it.

        Cursor audioCursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj, null, null, null);
        if (audioCursor != null) {

            if (audioCursor.moveToFirst()) {
                do {
                    String titleC = audioCursor.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                    String idC = audioCursor.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                    String albumC = audioCursor.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                    String artistC = audioCursor.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                    String pathC = audioCursor.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                    @SuppressLint("Range") Long durationC = audioCursor.getLong(audioCursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

                    Uri uri = Uri.parse("content://media/external/audio/albumart");
                    String artUriC = Uri.withAppendedPath(uri, albumC).toString();

                    File file = new File(pathC);
                    long length = file.length();
                    length = length / 1024;  //size in kb

                    SizeMB = (int) length / 1024;
                    if (SizeMB < 25) {
                        Music music = new Music(idC, titleC, albumC, artistC, pathC, durationC, artUriC);
                        audioList.add(music);
                    }
                } while (audioCursor.moveToNext());
            }
        }
        audioCursor.close();
    }


    @Override
    public void onBackPressed() {
        final Dialog dialog = new Dialog(CallActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.back_layout);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView minimize = dialog.findViewById(R.id.minimize);
        RelativeLayout milayout = dialog.findViewById(R.id.milayout);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.top_animation_code);
        milayout.setAnimation(animation);

        ImageView exit = dialog.findViewById(R.id.exit);
        RelativeLayout exitLayout = dialog.findViewById(R.id.exitLayout);
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_animation_code);
        exitLayout.setAnimation(animation2);
        dialog.show();

        minimize.setOnClickListener(v -> {
            dialog.dismiss();
            // setPIPScreen();
        });

        exit.setOnClickListener(v -> {

            muteMicRef.child(liveHostBackImg).removeValue();
            emojiRef.child(liveHostBackImg).removeValue();
            lockSeat.child(liveHostBackImg).removeValue();
            liveUsersRef.child(profileId).removeValue();
            ChatMessageModel chatMessageModel = new ChatMessageModel();
            liveUsersRef.child(otherUserId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
            ref.child(otherUserId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
            muteMicRef.child(otherUserId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });

            logoutRoom(audioRoomId);
//              zegoExpressEngine.stopPlayingStream(roomID);
//              zegoExpressEngine.logoutRoom(roomID);
            dialog.dismiss();

            //remove announcement and
//            muteMicRef.child(roomID).removeValue();
//            emojiRef.child(roomID).removeValue();
//            lockSeat.child(roomID).removeValue();
//            liveUsersRef.child(profileId).removeValue();
            startActivity(new Intent(CallActivity.this, HomeActivity.class));
        });
    }

    void loginRoom(String userID, String userName, String roomID, boolean isHost) {
        ZegoUser user = new ZegoUser(userID, userName);
        ZegoRoomConfig roomConfig = new ZegoRoomConfig();
        // The `onRoomUserUpdate` callback can be received only when
        // `ZegoRoomConfig` in which the `isUserStatusNotify` parameter is set to
        // `true` is passed.
        roomConfig.isUserStatusNotify = true;
        App.showLog("LOGINROOM roomID: " + roomID);
        App.showLog("LOGINROOM user: " + user);
        App.showLog("LOGINROOM roomConfig: " + roomConfig);

         /*zegoExpressEngine.loginRoom(roomID, user, roomConfig, (int error, JSONObject extendedData) -> {
            ZegoPlayerConfig playerConfig = new ZegoPlayerConfig();
            playerConfig.resourceMode = ZegoStreamResourceMode.ONLY_RTC;
            // Room login result. This callback is sufficient if you only need to
            // check the login result.

           if (isHost) {
                //startPreview();
                //   zegoExpressEngine.startPlayingStream(roomID,playerConfig);

//                    playerConfig.cdnConfig(true);  // Enable audio
//                    playerConfig.setVolume(80);  // Set the volume to 80%
//                    playerConfig.setAudioOutput(ZegoAudioOutputMode.AUDIO_OUTPUT_SPEAKER);
//                    startPublish(userID,roomID);
//                    playingStream(userID,roomID);
            } else {
                //startPlayStream(userID, roomID);
            }
        });*/
        zegoExpressEngine.loginRoom(roomID, user, roomConfig, (int error, JSONObject extendedData) -> {
            // Room login result. This callback is sufficient if you only need to
            // check the login result.
            if (error == 0) {
                Toast.makeText(this, "eerror" + error, Toast.LENGTH_SHORT).show();
                App.showLog("error errorrrrr :" + error);
                // Login successful.
                // Start the preview and stream publishing.
                Toast.makeText(this, "roomiiid " + roomID, Toast.LENGTH_SHORT).show();

                Toast.makeText(this, "Login successful.", Toast.LENGTH_LONG).show();
                Toast.makeText(this, "host Status " + isHost, Toast.LENGTH_SHORT).show();
                startPublish(userID, roomID);
            } else {
                // Login failed. For details, see [Error codes\|_blank](/404).
                Toast.makeText(this, "Login failed. error = " + error, Toast.LENGTH_LONG).show();
            }
        });
    }

    void startPlayStream(String streamID) {
        zegoExpressEngine.startPlayingStream(streamID);
    }

    void stopPlayStream(String streamID) {
        zegoExpressEngine.stopPlayingStream(streamID);
    }

    void startPublish(String userID, String roomID) {

        ZegoAudioConfig audioConfig = new ZegoAudioConfig(ZegoAudioConfigPreset.HIGH_QUALITY_STEREO);
        zegoExpressEngine.setAudioConfig(audioConfig);

        zegoExpressEngine.startPublishingStream(roomID);
        App.showLog("Start Publish:- " + roomID);
    }

    void logoutRoom(String roomID) {
        zegoExpressEngine.logoutRoom(roomID);
        zegoExpressEngine.stopPlayingStream(roomID);
        ChatSDKManager.getChatSDKManager().leaveRoom(roomID, new ZIMRoomLeftCallback() {
            @Override
            public void onRoomLeft(String roomID, ZIMError errorInfo) {
                App.showLog("Roomid:- " + roomID + " , errorinfo:- " + errorInfo.getMessage());
            }
        });
        if (am_i_host) {
            //Stop publishing
            zegoExpressEngine.stopPublishingStream();
        }
        stopListenEvent();
    }


    void startListenEvent() {
        zegoExpressEngine.setEventHandler(new IZegoEventHandler() {
            @Override
            // Callback for updates on the status of the streams in the room.
            public void onRoomStreamUpdate(String roomID, ZegoUpdateType updateType, ArrayList<ZegoStream> streamList, JSONObject extendedData) {
                super.onRoomStreamUpdate(roomID, updateType, streamList, extendedData);
                // When `updateType` is set to `ZegoUpdateType.ADD`, an audio and video
                // stream is added, and you can call the `startPlayingStream` method to
                // play the stream.
                App.showLog("Room id:- " + roomID + " , stremList:- " + streamList.get(0).streamID + " , update type:- 0(add) 1 (delete)" + updateType);
                if (updateType == ZegoUpdateType.ADD) {
                    startPlayStream(streamList.get(0).streamID);
                } else {
                    stopPlayStream(streamList.get(0).streamID);
                }
            }

            @Override
            // Callback for updates on the status of other users in the room.
            // Users can only receive callbacks when the isUserStatusNotify property of ZegoRoomConfig is set to `true` when logging in to the room (loginRoom).
            public void onRoomUserUpdate(String roomID, ZegoUpdateType updateType, ArrayList<ZegoUser> userList) {
                super.onRoomUserUpdate(roomID, updateType, userList);
                // You can implement service logic in the callback based on the login
                // and logout status of users.
                if (updateType == ZegoUpdateType.ADD) {
                    for (ZegoUser user : userList) {
                        String text = user.userID + "logged in to the room.";
                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
                    }
                } else if (updateType == ZegoUpdateType.DELETE) {
                    for (ZegoUser user : userList) {
                        String text = user.userID + "logged out of the room.";
                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            // Callback for updates on the current user's room connection status.
            public void onRoomStateChanged(String roomID, ZegoRoomStateChangedReason reason, int i, JSONObject jsonObject) {
                super.onRoomStateChanged(roomID, reason, i, jsonObject);
                if (reason == ZegoRoomStateChangedReason.LOGINING) {
                    // Logging in to a room. When `loginRoom` is called to log in to a
                    // room or `switchRoom` is called to switch to another room, the room
                    // enters this status, indicating that it is requesting a connection
                    // to the server. On the app UI, the status of logging in to the room
                    // is displayed.
                } else if (reason == ZegoRoomStateChangedReason.LOGINED) {
                    // Logging in to a room succeeds. When a user successfully logs in to
                    // a room or switches the room, the room enters this status. In this
                    // case, the user can receive notifications of addition or deletion of
                    // other users and their streams in the room. Only after a user
                    // successfully logs in to a room or switches the room,
                    // `startPublishingStream` and `startPlayingStream` can be called to
                    // publish and play streams properly.
                } else if (reason == ZegoRoomStateChangedReason.LOGIN_FAILED) {
                    // Logging in to a room fails. When a user fails to log in to a room
                    // or switch the room due to a reason such as incorrect AppID or
                    // Token, the room enters this status.
                    Toast.makeText(getApplicationContext(), "ZegoRoomStateChangedReason.LOGIN_FAILED", Toast.LENGTH_LONG).show();
                } else if (reason == ZegoRoomStateChangedReason.RECONNECTING) {
                    // The room connection is temporarily interrupted. The SDK will retry
                    // internally if the interruption is caused by poor network quality.
                } else if (reason == ZegoRoomStateChangedReason.RECONNECTED) {
                    // Reconnecting a room succeeds. The SDK will retry internally if the
                    // interruption is caused by poor network quality. If the reconnection
                    // is successful, the room enters this status.
                } else if (reason == ZegoRoomStateChangedReason.RECONNECT_FAILED) {
                    // Reconnecting a room fails. The SDK will retry internally if the
                    // interruption is caused by poor network quality. If the reconnection
                    // fails, the room enters this status.
                    Toast.makeText(getApplicationContext(), "ZegoRoomStateChangedReason.RECONNECT_FAILED", Toast.LENGTH_LONG).show();
                } else if (reason == ZegoRoomStateChangedReason.KICK_OUT) {
                    // The server forces a user to log out of a room. If a user who has
                    // logged in to room A tries to log in to room B, the server forces
                    // the user to log out of room A and room A enters this status.
                    Toast.makeText(getApplicationContext(), "ZegoRoomStateChangedReason.KICK_OUT", Toast.LENGTH_LONG).show();
                } else if (reason == ZegoRoomStateChangedReason.LOGOUT) {
                    // Logging out of a room succeeds. This is the default status of a
                    // room before login. If a user successfully logs out of a room by
                    // calling `logoutRoom` or `switchRoom`, the room enters this status.
                } else if (reason == ZegoRoomStateChangedReason.LOGOUT_FAILED) {
                    // Logging out of a room fails. If a user fails to log out of a room
                    // by calling `logoutRoom` or `switchRoom`, the room enters this
                    // status.
                }
            }

            // Status notification of audio and video stream publishing.
            @Override
            public void onPublisherStateUpdate(String streamID, ZegoPublisherState state, int errorCode, JSONObject extendedData) {
                super.onPublisherStateUpdate(streamID, state, errorCode, extendedData);
                if (errorCode != 0) {
                    // Stream publishing exception.
                }
                if (state == ZegoPublisherState.PUBLISHING) {
                    // Publishing streams.
                } else if (state == ZegoPublisherState.NO_PUBLISH) {
                    // Streams not published.
                    Toast.makeText(getApplicationContext(), "ZegoPublisherState.NO_PUBLISH", Toast.LENGTH_LONG).show();
                } else if (state == ZegoPublisherState.PUBLISH_REQUESTING) {
                    // Requesting stream publishing.
                }
            }

            // Status notifications of audio and video stream playing.
            // This callback is received when the status of audio and video stream
            // playing of a user changes. If an exception occurs during stream playing
            // due to a network interruption, the SDK automatically retries to play
            // the streams.
            @Override
            public void onPlayerStateUpdate(String streamID, ZegoPlayerState state, int errorCode, JSONObject extendedData) {
                super.onPlayerStateUpdate(streamID, state, errorCode, extendedData);
                if (errorCode != 0) {
                    // Stream playing exception.
                    Toast.makeText(getApplicationContext(), "onPlayerStateUpdate, state:" + state + "errorCode:" + errorCode, Toast.LENGTH_LONG).show();
                }
                if (state == ZegoPlayerState.PLAYING) {
                    // Playing streams.
                } else if (state == ZegoPlayerState.NO_PLAY) {
                    // Streams not played.
                    Toast.makeText(getApplicationContext(), "ZegoPlayerState.NO_PLAY", Toast.LENGTH_LONG).show();
                } else if (state == ZegoPlayerState.PLAY_REQUESTING) {
                    // Requesting stream playing.
                }
            }
        });
    }

    void stopListenEvent() {
        zegoExpressEngine.setEventHandler(null);
    }

//    ZegoLiveRoom.sharedInstance().startPlayingStream(streamID, new IZegoLivePlayerCallback() {
//        @Override
//        public void onPlayStateUpdate(int stateCode, ZegoStreamInfo zegoStreamInfo) {
//            if (stateCode == 0) {
//                // Successfully joined the live room
//            } else {
//                // Failed to join the live room
//            }
//        }
//    });

    @Override
    protected void onResume() {
        super.onResume();
        //Set Full Screen Background Window
        CallActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Set Full Screen Background Window
        CallActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    public void onPause() {
        super.onPause();
        /************************** CLEAR FULL SCREEN BACKGROUND WINDOW **************************/
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    protected void onDestroy() {
        startTimer();
        super.onDestroy();
    }

    private void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (liveStatus.equalsIgnoreCase("host")) {
            startActivity(new Intent(CallActivity.this, HomeActivity.class));
            finish();
        } else {
            countDownTimer = new CountDownTimer(120000, 1000) {
                public void onTick(long millisUntilFinished) {
                    Log.i("Agora : timer : ", String.valueOf(millisUntilFinished));
                }

                public void onFinish() {
                    hitEndLiveApi("1");
                }
            };

        }
        if (countDownTimer != null) {
            countDownTimer.start();
        }

    }

    private final long enterTime = System.currentTimeMillis();

    private void hitEndLiveApi(String s) {
//        rtcEngine().leaveChannel();
//        rtcEngine().enableLocalAudio(false);
        zegoExpressEngine.logoutRoom();
        zegoExpressEngine.muteLocalAudioMixing(false);

        ref.child("reservedSheet").child(liveId).removeValue();

        new ApiViewModel().stopAgoraBroadcasting(this, liveId, s).observe(CallActivity.this, modelAgoraLiveUsers -> {

            if (modelAgoraLiveUsers != null) {
                if (modelAgoraLiveUsers.getStatus().equalsIgnoreCase("1")) {

                    zegoExpressEngine.logoutRoom();
                    zegoExpressEngine.muteLocalAudioMixing(false);
//                    rtcEngine().leaveChannel();
//                    rtcEngine().enableLocalAudio(false);

                    long totalTime = System.currentTimeMillis() - enterTime;


                    startActivity(new Intent(CallActivity.this, HomeActivity.class).putExtra("coins", binding.tvTotalCoins.getText().toString()));
                    finish();


                } else {
                    Toast.makeText(this, modelAgoraLiveUsers.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "root is null", Toast.LENGTH_SHORT).show();
            }
        });


    }
}