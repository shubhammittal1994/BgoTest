package com.bango.bangoLive.ZegoServices.components;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bango.bangoLive.databinding.DialogRoomReqestListBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.bango.bangoLive.R;
import com.bango.bangoLive.ZegoServices.internal.sdk.zim.ZEGOSDKManager;
import com.bango.bangoLive.ZegoServices.internal.sdk.basic.ZEGOSDKUser;
import com.bango.bangoLive.ZegoServices.internal.sdk.express.IExpressEngineEventHandler;
import com.bango.bangoLive.ZegoServices.internal.sdk.zim.IZIMEventHandler;
import com.bango.bangoLive.ZegoServices.internal.sdk.zim.RoomRequest;
import java.util.List;

public class RoomRequestListDialog extends BottomSheetDialog {

    private DialogRoomReqestListBinding binding;
    private RoomRequestListAdapter seatRequestAdapter;
    private IZIMEventHandler incomingRoomRequestListener;
    private IExpressEngineEventHandler roomUserChangeListener;

    public RoomRequestListDialog(@NonNull Context context) {
        super(context, R.style.TransparentDialog);
    }

    public RoomRequestListDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected RoomRequestListDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogRoomReqestListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.dimAmount = 0.1f;
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
        setCanceledOnTouchOutside(true);
        window.setBackgroundDrawable(new ColorDrawable());

        // both need setPeekHeight & setLayoutParams
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int height = (int) (displayMetrics.heightPixels * 0.6f);
        getBehavior().setPeekHeight(height);
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(-1, height);
        binding.liveRequestListLayout.setLayoutParams(params);

        binding.requestRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        seatRequestAdapter = new RoomRequestListAdapter();

        binding.requestRecyclerview.setAdapter(seatRequestAdapter);
        incomingRoomRequestListener = new IZIMEventHandler() {
            @Override
            public void onInComingRoomRequestReceived(RoomRequest request) {
                seatRequestAdapter.addItem(request.sender);
            }

            @Override
            public void onInComingRoomRequestCancelled(RoomRequest request) {
                seatRequestAdapter.removeItem(request.sender);
            }

            @Override
            public void onAcceptIncomingRoomRequest(int errorCode, RoomRequest request) {
                RoomRequest roomRequest = ZEGOSDKManager.getInstance().zimService.getRoomRequestByRequestID(
                    request.requestID);
                if (roomRequest != null) {
                    seatRequestAdapter.removeItem(roomRequest.receiver);
                }
            }

            @Override
            public void onRejectIncomingRoomRequest(int errorCode, RoomRequest request) {
                RoomRequest roomRequest = ZEGOSDKManager.getInstance().zimService.getRoomRequestByRequestID(
                    request.requestID);
                if (roomRequest != null) {
                    seatRequestAdapter.removeItem(roomRequest.receiver);
                }
            }
        };
        roomUserChangeListener = new IExpressEngineEventHandler() {

            @Override
            public void onUserLeft(List<ZEGOSDKUser> userList) {
                for (ZEGOSDKUser zegosdkUser : userList) {
                    seatRequestAdapter.removeItem(zegosdkUser.userID);
                }
            }
        };

        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                List<String> requestUserList = ZEGOSDKManager.getInstance().zimService.getRoomRequestUserList();
                seatRequestAdapter.setItems(requestUserList);
                ZEGOSDKManager.getInstance().expressService.addEventHandler(roomUserChangeListener);
                ZEGOSDKManager.getInstance().zimService.addEventHandler(incomingRoomRequestListener);
            }
        });
        setOnDismissListener(dialog -> {
            ZEGOSDKManager.getInstance().zimService.removeEventHandler(incomingRoomRequestListener);
            ZEGOSDKManager.getInstance().expressService.removeEventHandler(roomUserChangeListener);
        });
    }
}
