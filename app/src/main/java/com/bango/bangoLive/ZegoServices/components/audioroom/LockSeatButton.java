package com.bango.bangoLive.ZegoServices.components.audioroom;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bango.bangoLive.R;
import com.bango.bangoLive.ZegoServices.internal.ZEGOLiveAudioRoomManager;
import com.bango.bangoLive.ZegoServices.internal.sdk.components.express.ZImageButton;

public class LockSeatButton extends ZImageButton {

    public LockSeatButton(@NonNull Context context) {
        super(context);
    }

    public LockSeatButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LockSeatButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        super.initView();
        setImageResource(R.drawable.liveaudioroom_btn_lock_seat, R.drawable.liveaudioroom_btn_unlock_seat);

        ZEGOLiveAudioRoomManager.getInstance().addLiveAudioRoomListener(new ZEGOLiveAudioRoomManager.LiveAudioRoomListener() {
            @Override
            public void onLockSeatStatusChanged(boolean lock) {
                updateState(lock);
            }
        });
    }

    @Override
    public void open() {
        super.open();
        ZEGOLiveAudioRoomManager.getInstance().lockSeat(true);
    }

    @Override
    public void close() {
        super.close();
        ZEGOLiveAudioRoomManager.getInstance().lockSeat(false);
    }
}
