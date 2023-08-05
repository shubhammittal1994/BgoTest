package com.bango.bangoLive.ZegoServices.internal.business.call;

public interface ReceiveCallListener {

    void onReceiveNewCall(String requestID, String inviter, String userName, int type);
}
