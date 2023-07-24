package com.bango.bangoLive.ZegoServices.zegoCloudChat;

import android.util.Log;

import com.bango.bangoLive.ZegoServices.ZegoSDKApiKey;
import com.bango.bangoLive.application.App;

import im.zego.zim.ZIM;
import im.zego.zim.callback.ZIMMessageSentCallback;
import im.zego.zim.entity.ZIMAppConfig;
import im.zego.zim.entity.ZIMError;
import im.zego.zim.entity.ZIMMessage;
import im.zego.zim.entity.ZIMMessageSendConfig;
import im.zego.zim.entity.ZIMPushConfig;
import im.zego.zim.entity.ZIMTextMessage;
import im.zego.zim.enums.ZIMConversationType;
import im.zego.zim.enums.ZIMMessagePriority;

public class ChatFunctions {
    private static ChatFunctions instance;

    private ChatFunctions(){

    }

    public static ChatFunctions getChatFunctionsInstance(){
        if(instance==null){
            instance= new ChatFunctions();
        }
        return instance;
    }

    public static void sendMessage(String conversationID, ZIMCustomTextMessage zimMessage, ZIMPushConfig pushConfig,ZIMMessageSendConfig config ){
        ChatSDKManager.getChatSDKManager().sendMessage(zimMessage, conversationID, ZIMConversationType.GROUP, config, new ZIMMessageSentCallback() {
            @Override
            public void onMessageAttached(ZIMMessage zimMessage){
                // The callback on the message not sent yet. You can get a temporary object here and this object must be the same as that created zimMessage object. You can make your own business logic using this as needed, for example, display a UI ahead of time.
            }
            @Override
            public void onMessageSent(ZIMMessage zimMessage, ZIMError error) {
                // Implement the event callback on message sent.
               App.showToast(App.getAppContext(),"Message sent");
            }
        });
    }

    public static void joinRoom(String roomID) {
        ChatSDKManager.getChatSDKManager().joinRoom(roomID, (roomInfo, errorInfo) -> {
            Log.e("--->>>","Room Joined"+roomID+" "+roomInfo);
        });
    }
}
