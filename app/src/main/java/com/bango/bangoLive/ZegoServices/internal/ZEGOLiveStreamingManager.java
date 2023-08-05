package com.bango.bangoLive.ZegoServices.internal;

import com.bango.bangoLive.ZegoServices.internal.business.cohost.CoHostService;
import com.bango.bangoLive.ZegoServices.internal.business.cohost.CoHostService.CoHostListener;
import com.bango.bangoLive.ZegoServices.internal.business.pk.PKListener;
import com.bango.bangoLive.ZegoServices.internal.business.pk.PKService;
import com.bango.bangoLive.ZegoServices.internal.business.pk.PKService.PKInfo;
import com.bango.bangoLive.ZegoServices.internal.business.pk.PKService.PKRequest;
import com.bango.bangoLive.ZegoServices.internal.sdk.basic.ZEGOSDKUser;
import com.bango.bangoLive.ZegoServices.internal.sdk.express.IExpressEngineEventHandler;
import com.bango.bangoLive.ZegoServices.internal.sdk.zim.IZIMEventHandler;
import com.bango.bangoLive.ZegoServices.internal.business.UserRequestCallback;
import com.bango.bangoLive.ZegoServices.internal.sdk.zim.ZEGOSDKManager;

import im.zego.zegoexpress.callback.IZegoMixerStartCallback;
import im.zego.zegoexpress.constants.ZegoPublisherState;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class ZEGOLiveStreamingManager {

    private static final class Holder {

        private static final ZEGOLiveStreamingManager INSTANCE = new ZEGOLiveStreamingManager();

    }

    public static ZEGOLiveStreamingManager getInstance() {
        return Holder.INSTANCE;
    }


    private PKService pkService;
    private CoHostService coHostService;

    public void init() {
        pkService = new PKService();
        coHostService = new CoHostService();

        pkService.initWhenUserLogin();
    }

    public void addRoomListeners() {
        ZEGOSDKManager.getInstance().expressService.addEventHandler(new IExpressEngineEventHandler() {
            @Override
            public void onReceiveStreamAdd(List<ZEGOSDKUser> userList) {
                coHostService.onReceiveStreamAdd(userList);
                pkService.onReceiveStreamAdd(userList);
            }

            @Override
            public void onReceiveStreamRemove(List<ZEGOSDKUser> userList) {
                coHostService.onReceiveStreamRemove(userList);
            }

            @Override
            public void onPublisherStateUpdate(String streamID, ZegoPublisherState state, int errorCode,
                JSONObject extendedData) {
                coHostService.onPublisherStateUpdate(streamID, state, errorCode);
            }

            @Override
            public void onPlayerRecvVideoFirstFrame(String streamID) {
                pkService.onPlayerRecvVideoFirstFrame(streamID);
            }

            @Override
            public void onPlayerSyncRecvSEI(String streamID, byte[] data) {
                pkService.onPlayerSyncRecvSEI(streamID, data);
            }
        });
        ZEGOSDKManager.getInstance().zimService.addEventHandler(new IZIMEventHandler() {
            @Override
            public void onRoomAttributesUpdated2(List<Map<String, String>> setProperties,
                List<Map<String, String>> deleteProperties) {
                pkService.onRoomAttributesUpdated(setProperties, deleteProperties);
            }
        });

        pkService.addListener(new LiveStreamingListener() {

            @Override
            public void onPKStarted() {
                ZEGOSDKManager.getInstance().zimService.removeAllRequest();
                ZEGOSDKUser currentUser = ZEGOSDKManager.getInstance().expressService.getCurrentUser();
                if (ZEGOLiveStreamingManager.getInstance().isCoHost(currentUser.userID)) {
                    ZEGOSDKManager.getInstance().expressService.openCamera(false);
                    ZEGOSDKManager.getInstance().expressService.openMicrophone(false);
                    ZEGOSDKManager.getInstance().expressService.stopPublishingStream();
                }
            }
        });
    }


    public void setHostUser(ZEGOSDKUser userInfo) {
        coHostService.setHostUser(userInfo);
    }

    public boolean isCurrentUserHost() {
        return coHostService.isLocalUserHost();
    }

    public ZEGOSDKUser getHostUser() {
        return coHostService.getHostUser();
    }

    public boolean isHost(String userID) {
        return coHostService.isHost(userID);
    }

    public boolean isCoHost(String userID) {
        return coHostService.isCoHost(userID);
    }

    public boolean isAudience(String userID) {
        return coHostService.isAudience(userID);
    }

    public void endCoHost(){
        coHostService.endCoHost();
    }

    public void startCoHost(){
        coHostService.startCoHost();
    }

    public void removeRoomListeners() {
        coHostService.removeRoomListeners();
        pkService.removeRoomListeners();
    }

    public void removeRoomData() {
        coHostService.removeRoomData();
        pkService.removeRoomData();
    }

    public void removeUserListeners() {
        coHostService.removeUserListeners();
        pkService.removeUserListeners();
        removeRoomListeners();
    }

    public void removeUserData() {
        coHostService.removeUserData();
        pkService.removeUserData();
    }

    public void startPublishingStream() {
        ZEGOSDKUser currentUser = ZEGOSDKManager.getInstance().expressService.getCurrentUser();
        String currentRoomID = ZEGOSDKManager.getInstance().expressService.getCurrentRoomID();
        String generateUserStreamID = generateUserStreamID(currentUser.userID, currentRoomID);
        ZEGOSDKManager.getInstance().expressService.startPublishingStream(generateUserStreamID);
    }

    public String generateUserStreamID(String userID, String roomID) {
        return coHostService.generateUserStreamID(userID, roomID);
    }

    public void sendPKBattlesStopRequest() {
        pkService.sendPKBattlesStopRequest();
    }

    public PKInfo getPKInfo() {
        return pkService.getPKInfo();
    }

    public void stopPKBattle() {
        pkService.stopPKBattle();
    }

    public void setCurrentPKInfo(PKInfo pkInfo) {
        pkService.setCurrentPKInfo(pkInfo);
    }

    public void acceptPKBattleStartRequest(String requestID) {
        pkService.acceptPKBattleStartRequest(requestID);
    }

    public void rejectPKBattleStartRequest(String requestID) {
        pkService.rejectPKBattleStartRequest(requestID);
    }

    public boolean isPKUser(String userID) {
        return pkService.isPKUser(userID);
    }

    public boolean isPKUserMuted() {
        return pkService.isPKUserMuted();
    }

    public void mutePKUser(boolean mute, IZegoMixerStartCallback callback) {
        pkService.mutePKUser(mute, callback);
    }

    public PKRequest getSendPKStartRequest() {
        return pkService.getSendPKStartRequest();
    }

    public void sendPKBattlesStartRequest(String targetUserID, UserRequestCallback callback) {
        pkService.sendPKBattlesStartRequest(targetUserID, callback);
    }

    public void cancelPKBattleStartRequest(String requestID, String targetUserID) {
        pkService.cancelPKBattleStartRequest(requestID, targetUserID);
    }

    public void addLiveStreamingListener(LiveStreamingListener listener) {
        pkService.addListener(listener);
        coHostService.addListener(listener);
    }

    public void removeLiveStreamingListener(LiveStreamingListener listener) {
        pkService.removeListener(listener);
        coHostService.removeListener(listener);
    }

    public interface LiveStreamingListener extends CoHostListener, PKListener {

    }

}
