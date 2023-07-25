package com.bango.bangoLive.ZegoServices.zegoCloudChat.model;

public class MessageModel {
    String message;
    String userName;
    String userId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public MessageModel(String message){
        String[] fullStringArray = message.split("-->\\$\\$<--");
        this.message=fullStringArray[0];
        this.userId=fullStringArray[1];
        this.userName=fullStringArray[2];
    };


}
