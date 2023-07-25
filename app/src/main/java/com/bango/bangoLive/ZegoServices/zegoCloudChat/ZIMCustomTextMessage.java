package com.bango.bangoLive.ZegoServices.zegoCloudChat;

import com.bango.bangoLive.ZegoServices.ZegoSdkEnums.MessageTypeEnum;

import im.zego.zim.entity.ZIMMessage;
import im.zego.zim.enums.ZIMMessageType;

public class ZIMCustomTextMessage extends ZIMMessage {
    MessageTypeEnum messageTypeEnum;
    public String name = "";
    public String message = "";

    public ZIMCustomTextMessage() {
        super(ZIMMessageType.TEXT);
    }

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ZIMCustomTextMessage{" +
                "messageTypeEnum=" + messageTypeEnum +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
