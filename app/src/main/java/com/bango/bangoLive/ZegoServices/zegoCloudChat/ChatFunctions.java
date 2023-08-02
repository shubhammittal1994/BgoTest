package com.bango.bangoLive.ZegoServices.zegoCloudChat;

import com.bango.bangoLive.application.App;

import java.util.ArrayList;

import im.zego.zim.callback.ZIMMessageSentCallback;
import im.zego.zim.callback.ZIMRoomMemberAttributesListQueriedCallback;
import im.zego.zim.entity.ZIMError;
import im.zego.zim.entity.ZIMMessage;
import im.zego.zim.entity.ZIMMessageSendConfig;
import im.zego.zim.entity.ZIMPushConfig;
import im.zego.zim.entity.ZIMRoomMemberAttributesInfo;
import im.zego.zim.entity.ZIMRoomMemberAttributesQueryConfig;
import im.zego.zim.entity.ZIMTextMessage;
import im.zego.zim.enums.ZIMConversationType;

public class ChatFunctions {
    private static ChatFunctions instance;

    private ChatFunctions() {

    }

    public static ChatFunctions getChatFunctionsInstance() {
        if (instance == null) {
            instance = new ChatFunctions();
        }
        return instance;
    }

    public static void sendMessage(String conversationID, ZIMTextMessage zimMessage, ZIMPushConfig pushConfig, ZIMMessageSendConfig config, ZIMConversationType zimConversationType) {
        App.showLog("--->>> Room conversation id:- " + conversationID + " -> " + zimMessage.message);
        ChatSDKManager.getChatSDKManager().sendMessage(zimMessage, conversationID, zimConversationType, config, new ZIMMessageSentCallback() {
            @Override
            public void onMessageAttached(ZIMMessage zimMessage) {
                // The callback on the message not sent yet. You can get a temporary object here and this object must be the same as that created zimMessage object. You can make your own business logic using this as needed, for example, display a UI ahead of time.
            }

            @Override
            public void onMessageSent(ZIMMessage zimMessage, ZIMError error) {
                // Implement the event callback on message sent.
                App.showToast("Message sent");
            }
        });
    }


    public static void joinRoom(String roomID) {
        ChatSDKManager.getChatSDKManager().queryRoomMemberAttributesList(roomID, new ZIMRoomMemberAttributesQueryConfig(), new ZIMRoomMemberAttributesListQueriedCallback() {
            @Override
            public void onRoomMemberAttributesListQueried(String roomID, ArrayList<ZIMRoomMemberAttributesInfo> infos, String nextFlag, ZIMError errorInfo) {
                App.showLog("--->>> Room queryRoomMemberAttributesList :- " + roomID + " -> " + infos.size());
            }
        });
        ChatSDKManager.getChatSDKManager().joinRoom(roomID, (roomInfo, errorInfo) -> {
            App.showLog("--->>> Room Joined:- " + roomID + " -> " + roomInfo.baseInfo.roomID + " -> " + roomInfo.baseInfo.roomName);
        });
    }
}
