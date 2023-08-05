package com.bango.bangoLive.ZegoServices.internal.sdk.zim;

import im.zego.zim.callback.ZIMEventHandler;
import java.util.List;
import java.util.Map;

public abstract class IZIMEventHandler extends ZIMEventHandler {

    // ---- room
    public void onInComingRoomRequestReceived(RoomRequest request) {
    }

    public void onInComingRoomRequestCancelled(RoomRequest request) {
    }

    public void onAcceptIncomingRoomRequest(int errorCode, RoomRequest request) {
    }

    public void onRejectIncomingRoomRequest(int errorCode, RoomRequest request) {
    }

    public void onSendRoomRequest(int errorCode, RoomRequest request) {
    }

    public void onCancelOutgoingRoomRequest(int errorCode, RoomRequest request) {
    }

    public void onOutgoingRoomRequestAccepted(RoomRequest request) {
    }

    public void onOutgoingRoomRequestRejected(RoomRequest request) {
    }

    public void onRoomAttributesUpdated2(List<Map<String, String>> setProperties,
        List<Map<String, String>> deleteProperties) {
    }
    // ---------room end---

    public void onInComingUserRequestReceived(String requestID, String inviter, String extendedData) {
    }

    public void onInComingUserRequestTimeout(String requestID) {
    }

    public void onInComingUserRequestCancelled(String requestID, String inviter, String extendedData) {
    }

    public void onOutgoingUserRequestTimeout(String requestID) {
    }

    public void onOutgoingUserRequestAccepted(String requestID, String invitee, String extendedData) {
    }

    public void onOutgoingUserRequestRejected(String requestID, String invitee, String extendedData) {
    }

    public void onUserAvatarUpdated(String userID, String url) {
    }


    public void onSendRoomCommand(int errorCode, String command) {

    }

    public void onRoomCommandReceived(String senderID, String command) {

    }
}
