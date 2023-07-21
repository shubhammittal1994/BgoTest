package com.bango.bangoLive.ZegoServices.zegoCloudChat;

import im.zego.zim.callback.ZIMMessageSentCallback;
import im.zego.zim.entity.ZIMError;
import im.zego.zim.entity.ZIMMessage;
import im.zego.zim.entity.ZIMMessageSendConfig;
import im.zego.zim.entity.ZIMPushConfig;
import im.zego.zim.entity.ZIMTextMessage;
import im.zego.zim.enums.ZIMConversationType;
import im.zego.zim.enums.ZIMMessagePriority;

public class ChatFunctions {

    void sendMessage(String conversationID,ZIMTextMessage zimMessage, ZIMPushConfig pushConfig,ZIMMessageSendConfig config ){


        // The following shows how to send one-to-one message, the [conversationType] needs to be set to [ZIMConversationTypePeer].


       /* ZIMTextMessage zimMessage = new ZIMTextMessage();
        zimMessage.message = "Message content";

        ZIMMessageSendConfig config = new ZIMMessageSendConfig();
// Set priority for the message. 1: Low (by default). 2: Medium. 3: High.
        config.priority = ZIMMessagePriority.LOW;
// Set the offline push notificaition config.
        ZIMPushConfig pushConfig = new ZIMPushConfig();
        pushConfig.title = "Title of the offline push";
        pushConfig.content= "Content of the offline push";
        // pushConfig.extendedData = "Extended info of the offline push";
        config.pushConfig = pushConfig;*/

// In 1-on-1 chats, the conversationID is the peer user ID. In group chats, the conversationID is the groupID. In the chat room, the conversationID is the roomID.
        ChatSDKManager.getChatSDKManager().sendMessage(zimMessage, conversationID, ZIMConversationType.PEER,config, new ZIMMessageSentCallback() {
            @Override
            public void onMessageAttached(ZIMMessage zimMessage){
                // The callback on the message not sent yet. You can get a temporary object here and this object must be the same as that created zimMessage object. You can make your own business logic using this as needed, for example, display a UI ahead of time.
            }
            @Override
            public void onMessageSent(ZIMMessage zimMessage, ZIMError error) {
                // Implement the event callback on message sent.
            }
        });
    }
}
