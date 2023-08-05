package com.bango.bangoLive.ZegoServices.zegoCloudChat;

import com.bango.bangoLive.ZegoServices.ZEGOSDKKeyCenter;
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
            appConfig.appID = ZEGOSDKKeyCenter.APP_ID_CHAT;
            appConfig.appSign = ZEGOSDKKeyCenter.APP_SIGN_CHAT;
           instance=  ZIM.create(appConfig, App.getInstance());
        }
        return instance;
    }

}
