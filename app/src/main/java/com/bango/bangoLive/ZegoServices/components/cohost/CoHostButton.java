package com.bango.bangoLive.ZegoServices.components.cohost;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bango.bangoLive.R;
import com.bango.bangoLive.ZegoServices.internal.ZEGOLiveStreamingManager;
import com.bango.bangoLive.ZegoServices.internal.ZEGOLiveStreamingManager.LiveStreamingListener;
import com.bango.bangoLive.ZegoServices.internal.business.cohost.CoHostService.Role;
import com.bango.bangoLive.ZegoServices.internal.sdk.zim.ZEGOSDKManager;
import com.bango.bangoLive.ZegoServices.internal.sdk.basic.ZEGOSDKUser;
import com.bango.bangoLive.ZegoServices.internal.sdk.components.express.ZTextButton;
import com.bango.bangoLive.ZegoServices.internal.sdk.zim.IZIMEventHandler;
import com.bango.bangoLive.ZegoServices.internal.sdk.zim.RoomRequest;
import com.bango.bangoLive.ZegoServices.internal.sdk.zim.RoomRequestCallback;
import com.bango.bangoLive.ZegoServices.internal.sdk.zim.RoomRequestType;
import com.bango.bangoLive.ZegoServices.internal.sdk.zim.ZIMService;
import com.bango.bangoLive.ZegoServices.internal.utils.Utils;
import java.util.Objects;
import org.json.JSONException;
import org.json.JSONObject;

public class CoHostButton extends ZTextButton {

    private String mRoomRequestID;

    public CoHostButton(@NonNull Context context) {
        super(context);
    }

    public CoHostButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CoHostButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void initView() {
        super.initView();

        setTextColor(Color.WHITE);
        setTextSize(13);
        setGravity(Gravity.CENTER);
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        setPadding(Utils.dp2px(14, displayMetrics), 0, Utils.dp2px(16, displayMetrics), 0);
        setCompoundDrawablePadding(Utils.dp2px(6, displayMetrics));

        ZEGOSDKManager.getInstance().zimService.addEventHandler(new IZIMEventHandler() {
            @Override
            public void onSendRoomRequest(int errorCode, RoomRequest request) {
                if (errorCode == 0) {
                    if (Objects.equals(request.requestID, mRoomRequestID)) {
                        requestCoHostUI();
                    }
                }
            }

            @Override
            public void onCancelOutgoingRoomRequest(int errorCode, RoomRequest request) {
                if (errorCode == 0) {
                    if (Objects.equals(request.requestID, mRoomRequestID)) {
                        removeRoomRequestID();
                        audienceUI();
                    }
                }
            }

            @Override
            public void onOutgoingRoomRequestAccepted(RoomRequest request) {
                if (Objects.equals(request.requestID, mRoomRequestID)) {
                    removeRoomRequestID();
                    coHostUI();
                }
            }

            @Override
            public void onOutgoingRoomRequestRejected(RoomRequest request) {
                if (Objects.equals(request.requestID, mRoomRequestID)) {
                    removeRoomRequestID();
                    audienceUI();
                }
            }
        });

        ZEGOLiveStreamingManager.getInstance().addLiveStreamingListener(new LiveStreamingListener() {
            @Override
            public void onRoleChanged(String userID, int after) {
                ZEGOSDKUser currentUser = ZEGOSDKManager.getInstance().expressService.getCurrentUser();
                if (currentUser.userID.equals(userID)) {
                    if (after == Role.AUDIENCE) {
                        mRoomRequestID = null;
                    }
                    updateUI();
                }
            }
        });
        audienceUI();
    }

    @Override
    protected void afterClick() {
        super.afterClick();
        ZEGOSDKUser localUser = ZEGOSDKManager.getInstance().expressService.getCurrentUser();
        ZEGOSDKUser hostUser = ZEGOLiveStreamingManager.getInstance().getHostUser();
        if (localUser == null || hostUser == null) {
            return;
        }

        if (ZEGOLiveStreamingManager.getInstance().isCoHost(localUser.userID)) {
            ZEGOLiveStreamingManager.getInstance().endCoHost();
            audienceUI();
        } else {
            RoomRequest roomRequest = ZEGOSDKManager.getInstance().zimService.getRoomRequestByRequestID(mRoomRequestID);
            if (roomRequest == null) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("room_request_type", RoomRequestType.REQUEST_COHOST);
                } catch (JSONException e) {

                }
                ZEGOSDKManager.getInstance().zimService.sendRoomRequest(hostUser.userID, jsonObject.toString(),
                    new RoomRequestCallback() {
                        @Override
                        public void onRoomRequestSend(int errorCode, String requestID) {
                            if (errorCode == 0) {
                                mRoomRequestID = requestID;
                            }
                        }
                    });
            } else {
                ZEGOSDKManager.getInstance().zimService.cancelRoomRequest(roomRequest, null);
            }
        }

    }

    public void updateUI() {
        ZEGOSDKUser localUser = ZEGOSDKManager.getInstance().expressService.getCurrentUser();
        ZIMService zimService = ZEGOSDKManager.getInstance().zimService;
        if (ZEGOLiveStreamingManager.getInstance().isCoHost(localUser.userID)) {
            coHostUI();
        } else if (ZEGOLiveStreamingManager.getInstance().isAudience(localUser.userID)) {
            RoomRequest roomRequest = zimService.getRoomRequestByRequestID(mRoomRequestID);
            if (roomRequest == null) {
                audienceUI();
            } else {
                requestCoHostUI();
            }
        }
    }

    private void coHostUI() {
        setText("End");
        setBackgroundResource(R.drawable.livestreaming_bg_end_cohost_btn);
        setCompoundDrawablesWithIntrinsicBounds(R.drawable.liveaudioroom_bottombar_cohost, 0, 0, 0);
    }

    private void requestCoHostUI() {
        setText("Cancel CoHost");
        setBackgroundResource(R.drawable.bg_cohost_btn);
        setCompoundDrawablesWithIntrinsicBounds(R.drawable.liveaudioroom_bottombar_cohost, 0, 0, 0);
    }

    private void audienceUI() {
        setText("Request Co-host");
        setBackgroundResource(R.drawable.bg_cohost_btn);
        setCompoundDrawablesWithIntrinsicBounds(R.drawable.liveaudioroom_bottombar_cohost, 0, 0, 0);
    }

    public void removeRoomRequestID() {
        mRoomRequestID = null;
    }
}
