package com.bango.bangoLive.fragments.Gifts.Model;

public class AlgorithmItem {

    private String userId;
    private String userImage;
    private String userName;

    public AlgorithmItem(String userId, String userImage, String userName) {
        this.userId = userId;
        this.userImage = userImage;
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
