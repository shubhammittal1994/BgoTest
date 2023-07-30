package com.bango.bangoLive.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.bango.bangoLive.ZegoServices.ExpressService;
import com.bango.bangoLive.ZegoServices.ZegoSDKApiKey;
import com.bango.bangoLive.ZegoServices.zegoCloudChat.ChatSDKManager;
import com.bango.bangoLive.ZegoServices.zegoCloudChat.ZIMCustomTextMessage;
import com.bango.bangoLive.ZegoServices.zegoCloudChat.model.MessageModel;
import com.bango.bangoLive.utils.SharedPref;
import com.bango.bangoLive.utils.Singleton;
import com.google.firebase.FirebaseApp;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import im.zego.zegoexpress.ZegoExpressEngine;
import im.zego.zim.ZIM;
import im.zego.zim.callback.ZIMEventHandler;
import im.zego.zim.entity.ZIMConversationQueryConfig;
import im.zego.zim.callback.ZIMGroupCreatedCallback;
import im.zego.zim.entity.ZIMConversationChangeInfo;
import im.zego.zim.entity.ZIMError;
import im.zego.zim.entity.ZIMErrorUserInfo;
import im.zego.zim.entity.ZIMGroupFullInfo;
import im.zego.zim.entity.ZIMGroupInfo;
import im.zego.zim.entity.ZIMGroupMemberInfo;
import im.zego.zim.entity.ZIMGroupOperatedInfo;
import im.zego.zim.entity.ZIMMessage;
import im.zego.zim.entity.ZIMMessageReceiptInfo;
import im.zego.zim.entity.ZIMTextMessage;
import im.zego.zim.entity.ZIMUserInfo;
import im.zego.zim.enums.ZIMErrorCode;
import im.zego.zim.enums.ZIMGroupEvent;
import im.zego.zim.enums.ZIMGroupMemberEvent;
import im.zego.zim.enums.ZIMGroupMemberState;
import im.zego.zim.enums.ZIMGroupState;

public class App extends Application {


  /*  long appID = 641603141;
    String isTestEnvironment = "true";
    String appSign = "35cab12c284816e0113c0f0a0cf3a5ca3bd92534c0a91126469e7f595b014ee5";*/

    public static Singleton singleton;

    public ExpressService expressService;

    private Context context;
    private static App instance;
    // private StatsManager mStatsManager = new StatsManager();

    private SharedPreferences mPref;

    private static SharedPref sharedpref;

    //public ClientProxy proxy() {
    //   return ClientProxy.instance();
    // }


    public static App getAppContext() {
        return instance;
    }


    public SharedPreferences preferences() {
        return mPref;
    }

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();

        singleton = new Singleton();
        sharedpref = new SharedPref(context);


        ChatSDKManager.getChatSDKManager().setEventHandler(new ZIMEventHandler() {
            @Override
            public void onRoomMemberJoined(ZIM zim, ArrayList<ZIMUserInfo> memberList, String roomID) {
                super.onRoomMemberJoined(zim, memberList, roomID);
            }

            @Override
            public void onRoomMemberLeft(ZIM zim, ArrayList<ZIMUserInfo> memberList, String roomID) {
                super.onRoomMemberLeft(zim, memberList, roomID);
            }

            @Override
            public void onReceivePeerMessage(ZIM zim, ArrayList<ZIMMessage> messageList, String fromUserID) {
               showToast("message called");


            }

            @Override
            public void onConversationChanged(ZIM zim, ArrayList<ZIMConversationChangeInfo> conversationChangeInfoList) {
                super.onConversationChanged(zim, conversationChangeInfoList);
            }

            @Override
            public void onConversationTotalUnreadMessageCountUpdated(ZIM zim, int totalUnreadMessageCount) {
                super.onConversationTotalUnreadMessageCountUpdated(zim, totalUnreadMessageCount);
            }

            @Override
            public void onConversationMessageReceiptChanged(ZIM zim, ArrayList<ZIMMessageReceiptInfo> infos) {
                super.onConversationMessageReceiptChanged(zim, infos);
            }

            @Override
            public void onReceiveRoomMessage(ZIM zim, ArrayList<ZIMMessage> messageList, String fromRoomID) {
                super.onReceiveRoomMessage(zim, messageList, fromRoomID);
                showLog(messageList.size() + " -> " + messageList.get(messageList.size()-1) + " -> " + fromRoomID);
                for (ZIMMessage zimMessage : messageList) {
                    if (zimMessage instanceof ZIMTextMessage)
                    {
                        ZIMTextMessage zimTextMessage = (ZIMTextMessage) zimMessage;
                        EventBus.getDefault().post(new MessageModel(zimTextMessage.message));
                     //   showLog("Received message:- " + zimTextMessage.toString());
                    }
                }
            }

            @Override
            public void onReceiveGroupMessage(ZIM zim, ArrayList<ZIMMessage> messageList, String fromGroupID) {
                super.onReceiveGroupMessage(zim, messageList, fromGroupID);
                showLog(messageList.size() + " -> " + messageList.get(messageList.size()-1) + " -> " + fromGroupID);
                for (ZIMMessage zimMessage : messageList) {
                    if (zimMessage instanceof ZIMTextMessage)
                    {
                        ZIMTextMessage zimTextMessage = (ZIMTextMessage) zimMessage;
                        EventBus.getDefault().post(new MessageModel(zimTextMessage.message));

                       // showLog("Received message:- "+ zimTextMessage.message);
                    }
                }
            }

            @Override
            public void onGroupStateChanged(ZIM zim, ZIMGroupState state, ZIMGroupEvent event, ZIMGroupOperatedInfo operatedInfo, ZIMGroupFullInfo groupInfo) {
                super.onGroupStateChanged(zim, state, event, operatedInfo, groupInfo);
            }

            @Override
            public void onGroupMemberStateChanged(ZIM zim, ZIMGroupMemberState state, ZIMGroupMemberEvent event, ArrayList<ZIMGroupMemberInfo> userList, ZIMGroupOperatedInfo operatedInfo, String groupID) {
                super.onGroupMemberStateChanged(zim, state, event, userList, operatedInfo, groupID);
            }

            @Override
            public void onGroupMemberInfoUpdated(ZIM zim, ArrayList<ZIMGroupMemberInfo> userList, ZIMGroupOperatedInfo operatedInfo, String groupID) {
                super.onGroupMemberInfoUpdated(zim, userList, operatedInfo, groupID);
            }

        });

//        CaocConfig.Builder.create()
//                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
//                .enabled(false) //default: true
//                .showErrorDetails(false) //default: true
//                .showRestartButton(false) //default: true
//                .logErrorOnRestart(false) //default: true
//                .trackActivities(true) //default: false
//                .minTimeBetweenCrashesMs(2000) //default: 3000//default: bug image
//                .restartActivity(HomeMainActivity.class) //default: null (your app's launch activity)//default: null (default error activity)
//                //default: null
//                .apply();

//        try {
//            mRtcEngine = RtcEngine.create(getApplicationContext(), Constants.AGORA_APP_ID, mHandler);
//            // Sets the channel profile of the Agora RtcEngine.
//            // The Agora RtcEngine differentiates channel profiles and applies different optimization algorithms accordingly. For example, it prioritizes smoothness and low latency for a video call, and prioritizes video quality for a video broadcast.
//            mRtcEngine.setChannelProfile(io.agora.rtc.Constants.CHANNEL_PROFILE_LIVE_BROADCASTING);
//            mRtcEngine.enableVideo();
//            mRtcEngine.setLogFile(FileUtil.initializeLogFile(this));
////
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //  mPref = getSharedPreferences(Settings.Global.Constants.SF_NAME, Context.MODE_PRIVATE);
    }


    public static SharedPref getSharedpref() {
        return sharedpref;
    }

    public static boolean hasNetwork() {
        return instance.isNetworkConnected();
    }

    public static Singleton getSingleton() {
        return singleton;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public static Singleton getSingletone() {
        return singleton;
    }

    public static App the() {
        return instance;
    }

    public ExpressService getExpressService() {
        return getExpressServiceHelper(this, ZegoSDKApiKey.APP_ID,ZegoSDKApiKey.APP_SIGN);
    }

    private ExpressService getExpressServiceHelper(Application application, long appId, String appSign) {
        if (expressService == null) {
            expressService = new ExpressService(this, appId, appSign);
        }
        return expressService;
    }

    public static void showToast(String message) {
        Toast.makeText(getAppContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static void showLog(String message) {
        Log.e("--------->>>>>>>>>", message);
    }
}
