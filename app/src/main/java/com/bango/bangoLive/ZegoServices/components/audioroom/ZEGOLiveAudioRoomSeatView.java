package com.bango.bangoLive.ZegoServices.components.audioroom;

import android.content.Context;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bango.bangoLive.databinding.LayoutMultiUserLiveListBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bango.bangoLive.R;
import com.bango.bangoLive.ZegoServices.components.LetterIconView;
import com.bango.bangoLive.ZegoServices.internal.ZEGOLiveAudioRoomManager;
import com.bango.bangoLive.ZegoServices.internal.sdk.basic.ZEGOSDKUser;
import com.bango.bangoLive.ZegoServices.internal.utils.Utils;

public class ZEGOLiveAudioRoomSeatView extends LinearLayout {

    /*private ImageView seatIconBackground;
    private ImageView userCustomAvatarView;
    private LetterIconView letterIconView;
    private ImageView hostTagView;
    private TextView userNameView;*/
    LayoutMultiUserLiveListBinding  binding;

    public ZEGOLiveAudioRoomSeatView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public ZEGOLiveAudioRoomSeatView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ZEGOLiveAudioRoomSeatView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setOrientation(VERTICAL);
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();

        // background: lock/unlock
        // letterIcon,customIcon
        // hostTag

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        binding = LayoutMultiUserLiveListBinding.inflate(layoutInflater);
        View view = binding.getRoot();

        FrameLayout contentLayout = new FrameLayout(getContext());
        LinearLayout.LayoutParams contentLayoutParams = new LinearLayout.LayoutParams(Utils.dp2px(96, displayMetrics),
            Utils.dp2px(15, displayMetrics));
        addView(contentLayout, contentLayoutParams);

        /*FrameLayout.LayoutParams avatarParams = new FrameLayout.LayoutParams(Utils.dp2px(54, displayMetrics),
            Utils.dp2px(54, displayMetrics));
        seatIconBackground = new ImageView(getContext());
        seatIconBackground.setBackgroundResource(R.drawable.audioroom_icon_seat);
        contentLayout.addView(seatIconBackground, avatarParams);*/

        /*avatarParams.gravity = Gravity.CENTER;
        letterIconView = new LetterIconView(getContext());
        letterIconView.setCircleBackgroundRadius(avatarParams.width / 2);
        letterIconView.setLetter("");
        contentLayout.addView(letterIconView, avatarParams);

        userCustomAvatarView = new ImageView(getContext());
        userCustomAvatarView.setLayoutParams(avatarParams);
        contentLayout.addView(userCustomAvatarView);

        hostTagView = new ImageView(getContext());
        hostTagView.setImageResource(R.drawable.audioroom_icon_seat_host);
        hostTagView.setVisibility(GONE);
        FrameLayout.LayoutParams hostTagViewParams = new FrameLayout.LayoutParams(-2, -2);
        hostTagViewParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        hostTagViewParams.bottomMargin = Utils.dp2px(4, getResources().getDisplayMetrics());
        contentLayout.addView(hostTagView, hostTagViewParams);*/

        // username below
        /*userNameView = new TextView(getContext());
        userNameView.setEllipsize(TruncateAt.END);
        userNameView.setGravity(Gravity.CENTER);
        userNameView.setSingleLine();
        userNameView.setPadding(Utils.dp2px(4, getResources().getDisplayMetrics()), 0,
            Utils.dp2px(4, getResources().getDisplayMetrics()), 0);
        userNameView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        LinearLayout.LayoutParams userNameParams = new LinearLayout.LayoutParams(Utils.dp2px(66, displayMetrics),
            Utils.dp2px(14, displayMetrics));
        addView(userNameView, userNameParams);*/

        addView(view);
    }

    public void onUserUpdate(ZEGOSDKUser audioRoomUser) {
        if (audioRoomUser != null) {
            addUserToSeat(audioRoomUser);
        } else {
            removeUserFromSeat();
        }
    }

    private void removeUserFromSeat() {
        //letterIconView.setLetter("");
        binding.txtUserName.setText("");
        binding.imgUserProfile.setImageDrawable(null);
    }

    private void addUserToSeat(ZEGOSDKUser audioRoomUser) {
        //letterIconView.setLetter(audioRoomUser.userName.toUpperCase());
        binding.txtUserName.setText(audioRoomUser.userName);
        String userAvatarUrl = ZEGOLiveAudioRoomManager.getInstance().getUserAvatar(audioRoomUser.userID);
        onUserAvatarUpdated(userAvatarUrl);
    }

    public void onLockChanged(boolean lock) {
        if (lock) {
            binding.imgChair.setBackgroundResource(R.drawable.chair);
        } else {
            binding.imgChair.setBackgroundResource(R.drawable.chair);
        }
    }

    public void onUserAvatarUpdated(String url) {
        if (TextUtils.isEmpty(url)) {
            binding.imgUserProfile.setImageDrawable(null);
        } else {
            RequestOptions requestOptions = new RequestOptions().circleCrop();
            Glide.with(getContext()).load(url).apply(requestOptions).into(binding.imgUserProfile);
        }
    }

    public void showHostTag() {
        //hostTagView.setVisibility(VISIBLE);
    }

    public void hideHostTag() {
        //hostTagView.setVisibility(GONE);
    }
}
