package com.bango.bangoLive.ZegoServices.zegoCloudChat;

import com.bango.bangoLive.ZegoServices.ZegoSdkEnums.MessageTypeEnum;

import im.zego.zim.entity.ZIMTextMessage;

public class ZIMCustomTextMessage extends ZIMTextMessage {
    MessageTypeEnum messageTypeEnum;

    public MessageTypeEnum getMessageTypeEnum() {
        return messageTypeEnum;
    }

    public void setMessageTypeEnum(MessageTypeEnum messageTypeEnum) {
        this.messageTypeEnum = messageTypeEnum;
    }
}
