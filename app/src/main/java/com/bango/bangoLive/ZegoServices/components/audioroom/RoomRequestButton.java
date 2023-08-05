package com.bango.bangoLive.ZegoServices.components.audioroom;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import com.bango.bangoLive.R;
import com.bango.bangoLive.ZegoServices.internal.ZEGOLiveAudioRoomManager;
import com.bango.bangoLive.ZegoServices.internal.sdk.basic.ZEGOSDKUser;
import com.bango.bangoLive.ZegoServices.internal.sdk.zim.IZIMEventHandler;
import com.bango.bangoLive.ZegoServices.internal.sdk.zim.RoomRequest;
import com.bango.bangoLive.ZegoServices.internal.sdk.zim.ZEGOSDKManager;
import com.bango.bangoLive.ZegoServices.internal.utils.Utils;
import im.zego.zim.ZIM;
import im.zego.zim.entity.ZIMUserInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoomRequestButton extends FrameLayout {

    private ImageView imageView;
    private ImageFilterView redPoint;

    public RoomRequestButton(@NonNull Context context) {
        super(context);
        initView();
    }

    public RoomRequestButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RoomRequestButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public RoomRequestButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr,
        int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    protected void initView() {
        imageView = new ImageView(getContext());
        addView(imageView);
        imageView.setImageResource(R.drawable.liveaudioroom_bottombar_cohost);
        imageView.setScaleType(ScaleType.CENTER);
        redPoint = new ImageFilterView(getContext());
        redPoint.setBackgroundColor(Color.parseColor("#FF0D23"));
        redPoint.setRoundPercent(1.0f);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        LayoutParams redPointParams = new LayoutParams(Utils.dp2px(8, displayMetrics), Utils.dp2px(8, displayMetrics));
        redPointParams.gravity = Gravity.TOP | Gravity.END;
        addView(redPoint, redPointParams);

        hideRedPoint();

        ZEGOSDKManager.getInstance().zimService.addEventHandler(new IZIMEventHandler() {
            @Override
            public void onInComingRoomRequestReceived(RoomRequest request) {
                showRedPoint();
            }

            @Override
            public void onInComingRoomRequestCancelled(RoomRequest request) {
                checkRedPoint();
            }

            @Override
            public void onAcceptIncomingRoomRequest(int errorCode, RoomRequest request) {
                checkRedPoint();
            }

            @Override
            public void onRejectIncomingRoomRequest(int errorCode, RoomRequest request) {
                checkRedPoint();
            }

            @Override
            public void onRoomMemberLeft(ZIM zim, ArrayList<ZIMUserInfo> memberList, String roomID) {
                super.onRoomMemberLeft(zim, memberList, roomID);
                checkRedPoint();
            }
        });
        setBackgroundResource(R.drawable.bg_circle_dark);
    }

    private void showRedPoint() {
        redPoint.setVisibility(View.VISIBLE);
    }

    private void hideRedPoint() {
        redPoint.setVisibility(View.GONE);
    }

    public void checkRedPoint() {
        ZEGOSDKUser localUser = ZEGOSDKManager.getInstance().expressService.getCurrentUser();
        ZEGOSDKUser hostUser = ZEGOLiveAudioRoomManager.getInstance().getHostUser();
        if (Objects.equals(hostUser, localUser)) {
            List<String> requestUserList = ZEGOSDKManager.getInstance().zimService.getRoomRequestUserList();
            if (!requestUserList.isEmpty()) {
                showRedPoint();
            } else {
                hideRedPoint();
            }
        }
    }
}
