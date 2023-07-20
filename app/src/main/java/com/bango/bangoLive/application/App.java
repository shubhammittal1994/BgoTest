package com.bango.bangoLive.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.bango.bangoLive.ZegoServices.ExpressService;
import com.bango.bangoLive.ZegoServices.zegoCloudChat.ChatSDKManager;
import com.bango.bangoLive.utils.SharedPref;
import com.bango.bangoLive.utils.Singleton;

import java.util.ArrayList;

import im.zego.zegoexpress.ZegoExpressEngine;
import im.zego.zim.ZIM;
import im.zego.zim.callback.ZIMEventHandler;
import im.zego.zim.entity.ZIMMessage;

public class App extends Application {


    long appID = 641603141;
    String isTestEnvironment = "true";
    String appSign = "35cab12c284816e0113c0f0a0cf3a5ca3bd92534c0a91126469e7f595b014ee5";

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
        ChatSDKManager.getChatSDKManager();
        ChatSDKManager.getChatSDKManager().setEventHandler(new ZIMEventHandler() {
            @Override
            public void onReceivePeerMessage(ZIM zim, ArrayList<ZIMMessage> messageList, String fromUserID) {
                Log.e("---->>>",messageList.get(0).);
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
        return getExpressServiceHelper(this, appID, appSign);
    }

    private ExpressService getExpressServiceHelper(Application application, long appId, String appSign) {
        if (expressService == null) {
            expressService = new ExpressService(this, appId, appSign);
        }
        return expressService;
    }

   public static void showToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
