package com.bango.bangoLive.ZegoServices.internal.business;

public interface UserRequestCallback {

    void onUserRequestSend(int errorCode, String requestID);
}
