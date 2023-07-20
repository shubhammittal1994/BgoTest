package com.bango.bangoLive.ZegoServices.zegoCloudChat;

import com.bango.bangoLive.ZegoServices.ZegoSDKApiKey;
import com.bango.bangoLive.application.App;

import im.zego.zim.ZIM;
import im.zego.zim.entity.ZIMAppConfig;

public class ChatSDKManager {

    private static  ZIM instance;

    private ChatSDKManager(){

    }

     public static ZIM getChatSDKManager(){
        if(instance==null){
            ZIMAppConfig appConfig=new ZIMAppConfig();
            appConfig.appID = ZegoSDKApiKey.APP_ID;
            appConfig.appSign = ZegoSDKApiKey.APP_SIGN;
           instance=  ZIM.create(appConfig, App.getInstance());
        }
        return instance;
    }

}
