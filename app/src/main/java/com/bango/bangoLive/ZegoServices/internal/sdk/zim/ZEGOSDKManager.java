package com.bango.bangoLive.ZegoServices.internal.sdk.zim;

import android.app.Application;
import android.content.Context;

import com.bango.bangoLive.ZegoServices.internal.sdk.basic.MergeCallBack;
import com.bango.bangoLive.ZegoServices.internal.sdk.basic.ZEGOSDKCallBack;
import com.bango.bangoLive.ZegoServices.internal.sdk.express.ExpressService;
import com.bango.bangoLive.ZegoServices.internal.utils.LogUtil;
import com.bango.bangoLive.application.App;

import org.json.JSONObject;

import im.zego.zegoexpress.callback.IZegoRoomLoginCallback;
import im.zego.zegoexpress.callback.IZegoRoomLogoutCallback;
import im.zego.zegoexpress.callback.IZegoUploadLogResultCallback;
import im.zego.zegoexpress.constants.ZegoScenario;
import im.zego.zim.callback.ZIMLogUploadedCallback;
import im.zego.zim.callback.ZIMLoggedInCallback;
import im.zego.zim.callback.ZIMRoomEnteredCallback;
import im.zego.zim.callback.ZIMRoomLeftCallback;
import im.zego.zim.entity.ZIMError;
import im.zego.zim.entity.ZIMRoomFullInfo;
import im.zego.zim.enums.ZIMErrorCode;

public class ZEGOSDKManager {

    public ExpressService expressService = new ExpressService();
    public ZIMService zimService = new ZIMService();
    private Context context;
    private long appID;
    private String appSign;
    private String roomId;

    private static final class Holder {

        private static final ZEGOSDKManager INSTANCE = new ZEGOSDKManager();
    }

    public static ZEGOSDKManager getInstance() {
        return Holder.INSTANCE;
    }

    public void initSDK(Application application, long appID, String appSign, long appIDChat, String appSignChat) {
        initSDK(application, appID, appSign, appIDChat, appSignChat, ZegoScenario.DEFAULT);
    }

    public void initSDK(Application application, long appID, String appSign, long appIDChat, String appSignChat, ZegoScenario scenario) {
        expressService.initSDK(application, appID, appSign, scenario);
        zimService.initSDK(application, appIDChat, appSignChat);
        context = application.getApplicationContext();
        this.appID = appID;
        this.appSign = appSign;
    }

    public void connectUser(String userID, String userName, ZEGOSDKCallBack callback) {
        expressService.connectUser(userID, userName);
        zimService.connectUser(userID, userName, new ZIMLoggedInCallback() {
            @Override
            public void onLoggedIn(ZIMError errorInfo) {
                if (callback != null) {
                    callback.onResult(errorInfo.code.value(), errorInfo.message);
                }
                App.showLog("sdkmanager connect user chat");
            }
        });
    }

    public void connectUser(String userID, String userName, String token, ZEGOSDKCallBack callback) {
        expressService.connectUser(userID, userName);
        zimService.connectUser(userID, userName, token, new ZIMLoggedInCallback() {
            @Override
            public void onLoggedIn(ZIMError errorInfo) {
                if (callback != null) {
                    callback.onResult(errorInfo.code.value(), errorInfo.message);
                }
            }
        });
    }

    public void disconnectUser() {
        zimService.logoutRoom(roomId, null);
        expressService.logoutRoom(roomId, null);
        zimService.disconnectUser();
        expressService.disconnectUser();

    }

    public void loginRoom(String roomID, ZegoScenario scenario, ZEGOSDKCallBack callback) {
        zimService.loginRoom(roomID, new ZIMRoomEnteredCallback() {
            @Override
            public void onRoomEntered(ZIMRoomFullInfo roomInfo, ZIMError errorInfo) {
                if (errorInfo.code == ZIMErrorCode.SUCCESS) {
                    roomId = roomID;
                    expressService.setRoomScenario(scenario);
                    expressService.loginRoom(roomID, new IZegoRoomLoginCallback() {
                        @Override
                        public void onRoomLoginResult(int errorCode, JSONObject extendedData) {
                            App.showLog("express room logged in error coe:- " + errorInfo);
                            if (callback != null) {
                                callback.onResult(errorCode, "express error:" + extendedData.toString());
                            }
                        }
                    });
                } else {
                    if (callback != null) {
                        App.showLog(errorInfo.code.value() + "  --- zim error:" + errorInfo.message);
                        callback.onResult(errorInfo.code.value(), "zim error:" + errorInfo.message);
                    }
                }
            }
        });
    }

    public void loginRoom(String roomID, String token, ZegoScenario scenario, ZEGOSDKCallBack callback) {
        zimService.loginRoom(roomID, new ZIMRoomEnteredCallback() {
            @Override
            public void onRoomEntered(ZIMRoomFullInfo roomInfo, ZIMError errorInfo) {
                if (errorInfo.code == ZIMErrorCode.SUCCESS) {
                    roomId = roomID;
                    expressService.setRoomScenario(scenario);
                    expressService.loginRoom(roomID, token, new IZegoRoomLoginCallback() {
                        @Override
                        public void onRoomLoginResult(int errorCode, JSONObject extendedData) {
                            if (callback != null) {
                                callback.onResult(errorCode, "express error:" + extendedData.toString());
                            }
                        }
                    });
                } else {
                    if (callback != null) {
                        callback.onResult(errorInfo.code.value(), "zim error:" + errorInfo.message);
                    }
                }
            }
        });
    }

    public void logoutRoom(String roomID, ZEGOSDKCallBack callBack) {
        App.showLog("sdkmanager logout room call");
        MergeCallBack<Integer, ZIMError> mergeCallBack = new MergeCallBack<Integer, ZIMError>() {
            @Override
            public void onResult(Integer integer, ZIMError zimError) {
                int errorCode;
                String errorMessage = "";
                if (integer == 0) {
                    errorMessage = zimError.message;
                    errorCode = zimError.code.value();
                } else {
                    errorCode = integer;
                }
                if (callBack != null) {
                    callBack.onResult(errorCode, errorMessage);
                }
            }
        };
        if (roomId == null) roomId = roomID;

        expressService.logoutRoom(roomId, new IZegoRoomLogoutCallback() {
            @Override
            public void onRoomLogoutResult(int errorCode, JSONObject extendedData) {
                mergeCallBack.setResult1(errorCode);
            }
        });
        zimService.logoutRoom(roomId, new ZIMRoomLeftCallback() {
            @Override
            public void onRoomLeft(String roomID, ZIMError errorInfo) {
                mergeCallBack.setResult2(errorInfo);
            }
        });
    }

    public void uploadLog(ZEGOSDKCallBack callBack) {

        MergeCallBack<Integer, ZIMError> mergeCallBack = new MergeCallBack<Integer, ZIMError>() {
            @Override
            public void onResult(Integer integer, ZIMError zimError) {
                int errorCode;
                String errorMessage = "";
                if (integer == 0) {
                    errorMessage = zimError.message;
                    errorCode = zimError.code.value();
                } else {
                    errorCode = integer;
                }
                if (callBack != null) {
                    callBack.onResult(errorCode, errorMessage);
                }
            }
        };
        expressService.uploadLog(new IZegoUploadLogResultCallback() {

            @Override
            public void onUploadLogResult(int errorCode) {
                mergeCallBack.setResult1(errorCode);
            }
        });

        zimService.uploadLog(new ZIMLogUploadedCallback() {

            @Override
            public void onLogUploaded(ZIMError errorInfo) {
                mergeCallBack.setResult2(errorInfo);
            }
        });
    }

    public void setDebugMode(boolean debugMode) {
        LogUtil.setDebug(debugMode);
    }
}
