package com.bango.bangoLive.ZegoServices.zegoCloudChat;

import com.bango.bangoLive.ZegoServices.ZegoSdkEnums.MessageTypeEnum;

import im.zego.zim.entity.ZIMTextMessage;

public class ZIMCustomTextMessage extends ZIMTextMessage {
    MessageTypeEnum messageTypeEnum;
    String name = "";
    public MessageTypeEnum getMessageTypeEnum() {
        return messageTypeEnum;
    }

    public void setMessageTypeEnum(MessageTypeEnum messageTypeEnum) {
        this.messageTypeEnum = messageTypeEnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ZIMCustomTextMessage{" +
                "messageTypeEnum=" + messageTypeEnum +
                ", name='" + name + '\'' +
                '}';
    }
}
