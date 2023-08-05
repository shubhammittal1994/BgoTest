package com.bango.bangoLive.ZegoServices.components.call;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import androidx.appcompat.app.AppCompatActivity;
import com.bango.bangoLive.R;
import com.bango.bangoLive.ZegoServices.internal.ZegoCallInvitationManager;
import com.bango.bangoLive.ZegoServices.internal.business.call.FullCallInfo;
import com.bango.bangoLive.ZegoServices.internal.sdk.zim.ZEGOSDKManager;
import com.bango.bangoLive.ZegoServices.internal.sdk.zim.IZIMEventHandler;
import com.bango.bangoLive.ZegoServices.internal.business.UserRequestCallback;
import com.bango.bangoLive.ZegoServices.internal.utils.ToastUtil;
import com.bango.bangoLive.databinding.DialogIncomingCallBinding;

import im.zego.zegoexpress.callback.IZegoRoomLoginCallback;
import im.zego.zegoexpress.constants.ZegoScenario;
import org.json.JSONObject;

public class IncomingCallDialog extends AppCompatActivity {

    private DialogIncomingCallBinding binding;
    private FullCallInfo callInfo;
    private IZIMEventHandler zimEventHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setGravity(Gravity.TOP);

        binding = DialogIncomingCallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setFinishOnTouchOutside(false);

        zimEventHandler = new IZIMEventHandler() {

            @Override
            public void onInComingUserRequestTimeout(String requestID) {
                if (requestID.equals(callInfo.callID)) {
                    finish();
                }
            }

            @Override
            public void onInComingUserRequestCancelled(String requestID, String inviter, String extendedData) {
                if (requestID.equals(callInfo.callID)) {
                    finish();
                }
            }
        };
        ZEGOSDKManager.getInstance().zimService.addEventHandler(zimEventHandler);

        callInfo = FullCallInfo.parse(getIntent().getStringExtra("callInfo"));

        binding.dialogCallIcon.setLetter(callInfo.callerUserName);

        binding.dialogCallName.setText(callInfo.callerUserName);
        if (callInfo.isVideoCall()) {
            binding.dialogCallType.setText("ZEGO VIDEO CALL");
            binding.dialogCallAccept.setImageResource(R.drawable.call_icon_dialog_video_accept);
        } else {
            binding.dialogCallType.setText("ZEGO VOICE CALL");
            binding.dialogCallAccept.setImageResource(R.drawable.call_icon_dialog_voice_accept);
        }

        binding.dialogCallAccept.setOnClickListener(v -> {
            ZegoCallInvitationManager.getInstance().acceptCallRequest(callInfo.callID, new UserRequestCallback() {
                @Override
                public void onUserRequestSend(int errorCode, String requestID) {
                    if (errorCode == 0) {
                        if (callInfo.isVideoCall()) {
                            ZEGOSDKManager.getInstance().expressService.setRoomScenario(
                                ZegoScenario.STANDARD_VIDEO_CALL);
                        } else {
                            ZEGOSDKManager.getInstance().expressService.setRoomScenario(
                                ZegoScenario.STANDARD_VOICE_CALL);
                        }
                        ZEGOSDKManager.getInstance().expressService.loginRoom(callInfo.callID,
                            new IZegoRoomLoginCallback() {
                                @Override
                                public void onRoomLoginResult(int errorCode, JSONObject extendedData) {
                                    if (errorCode == 0) {
                                        //CallInvitationActivity.startActivity(IncomingCallDialog.this, callInfo);
                                    } else {
                                        ToastUtil.show(IncomingCallDialog.this, "joinExpressRoom failed :" + errorCode);
                                    }
                                    finish();
                                }
                            });
                    } else {
                        ToastUtil.show(IncomingCallDialog.this, "callAccept failed :" + errorCode);
                        finish();
                    }
                }
            });
        });
        binding.dialogCallDecline.setOnClickListener(v -> {
            ZegoCallInvitationManager.getInstance().rejectCallRequest(callInfo.callID, new UserRequestCallback() {
                @Override
                public void onUserRequestSend(int errorCode, String requestID) {
                    if (errorCode != 0) {
                        ToastUtil.show(IncomingCallDialog.this, "callReject failed :" + errorCode);
                    }
                    finish();
                }
            });
        });

        binding.dialogReceiveCall.setOnClickListener(v -> {
            //CallWaitActivity.startActivity(IncomingCallDialog.this, callInfo);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ZEGOSDKManager.getInstance().zimService.removeEventHandler(zimEventHandler);
    }
}