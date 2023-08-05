package com.bango.bangoLive.ZegoServices.components.audioroom;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bango.bangoLive.R;
import com.bango.bangoLive.ZegoServices.internal.ZEGOLiveAudioRoomManager;
import com.bango.bangoLive.ZegoServices.internal.sdk.zim.ZEGOSDKManager;
import com.bango.bangoLive.ZegoServices.internal.sdk.basic.ZEGOSDKUser;
import com.bango.bangoLive.ZegoServices.internal.sdk.components.express.ZTextButton;
import com.bango.bangoLive.ZegoServices.internal.sdk.zim.IZIMEventHandler;
import com.bango.bangoLive.ZegoServices.internal.sdk.zim.RoomRequest;
import com.bango.bangoLive.ZegoServices.internal.sdk.zim.RoomRequestCallback;
import com.bango.bangoLive.ZegoServices.internal.sdk.zim.RoomRequestType;
import com.bango.bangoLive.ZegoServices.internal.utils.ToastUtil;
import com.bango.bangoLive.ZegoServices.internal.utils.Utils;
import java.util.Objects;
import org.json.JSONException;
import org.json.JSONObject;

public class TakeSeatButton extends ZTextButton {

    private String mRequestID;

    public TakeSeatButton(@NonNull Context context) {
        super(context);
    }

    public TakeSeatButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TakeSeatButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
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
                if (Objects.equals(request.requestID, mRequestID)) {
                    updateUI();
                }
            }

            @Override
            public void onCancelOutgoingRoomRequest(int errorCode, RoomRequest request) {
                if (errorCode == 0) {
                    if (Objects.equals(request.requestID, mRequestID)) {
                        mRequestID = null;
                        updateUI();
                    }
                }
            }

            @Override
            public void onOutgoingRoomRequestAccepted(RoomRequest request) {
                if (Objects.equals(request.requestID, mRequestID)) {
                    mRequestID = null;
                    updateUI();
                }
            }

            @Override
            public void onOutgoingRoomRequestRejected(RoomRequest request) {
                if (Objects.equals(request.requestID, mRequestID)) {
                    mRequestID = null;
                    updateUI();
                }
            }
        });
        updateUI();
    }

    @Override
    protected void afterClick() {
        super.afterClick();
        ZEGOSDKUser localUser = ZEGOSDKManager.getInstance().expressService.getCurrentUser();
        ZEGOSDKUser hostUser = ZEGOLiveAudioRoomManager.getInstance().getHostUser();
        if (localUser == null) {
            return;
        }
        if (hostUser == null) {
            RoomRequest roomRequest = ZEGOSDKManager.getInstance().zimService.getRoomRequestByRequestID(mRequestID);
            if (roomRequest != null) {
                ZEGOSDKManager.getInstance().zimService.cancelRoomRequest(roomRequest, new RoomRequestCallback() {
                    @Override
                    public void onRoomRequestSend(int errorCode, String requestID) {

                    }
                });
                mRequestID = null;
            } else {
                mRequestID = null;
                updateUI();
            }

        } else {
            RoomRequest roomRequest = ZEGOSDKManager.getInstance().zimService.getRoomRequestByRequestID(mRequestID);
            if (roomRequest == null) {
                int availableSeatIndex = ZEGOLiveAudioRoomManager.getInstance().findFirstAvailableSeatIndex();
                if (availableSeatIndex == -1) {
                    ToastUtil.show(getContext(), "cannot find available seat");
                    return;
                }
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("room_request_type", RoomRequestType.REQUEST_TAKE_SEAT);
                } catch (JSONException e) {

                }
                ZEGOSDKManager.getInstance().zimService.sendRoomRequest(hostUser.userID, jsonObject.toString(),
                    new RoomRequestCallback() {
                        @Override
                        public void onRoomRequestSend(int errorCode, String requestID) {
                            if (errorCode == 0) {
                                mRequestID = requestID;
                            }
                        }
                    });
            } else {
                ZEGOSDKManager.getInstance().zimService.cancelRoomRequest(roomRequest, new RoomRequestCallback() {
                    @Override
                    public void onRoomRequestSend(int errorCode, String requestID) {
                        mRequestID = null;
                        updateUI();
                    }
                });
            }
        }
    }

    public void updateUI() {
        RoomRequest roomRequest = ZEGOSDKManager.getInstance().zimService.getRoomRequestByRequestID(mRequestID);
        if (roomRequest == null) {
            setText("Apply to Take Seat");
        } else {
            setText("Cancel Take Seat");
        }
        setBackgroundResource(R.drawable.bg_cohost_btn);
        setCompoundDrawablesWithIntrinsicBounds(R.drawable.liveaudioroom_bottombar_cohost, 0, 0, 0);
    }
}
