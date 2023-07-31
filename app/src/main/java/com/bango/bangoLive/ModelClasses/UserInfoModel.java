package com.bango.bangoLive.ModelClasses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserInfoModel implements Serializable {
    @SerializedName("userId")
    String userId = "";

    @SerializedName("userName")
    String userName = "";

    @SerializedName("userProfile")
    String userProfile = "";

    @SerializedName("userMuteStatus")
    boolean userMuteStatus = false;

    @SerializedName("userSeatPosition")
    int userSeatPosition = -1;

    @SerializedName("isUserBlocked")
    boolean isUserBlocked = false;

    @SerializedName("isUserKickedOut")
    boolean isUserKickedOut = false;
}
