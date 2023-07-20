package com.bango.bangoLive.fragments.Gifts.Model;

public class ModelAgoraMessages {

    String message,image,username,userId,level,AdminStatus, giftImage, levelImage,vipImage, smsColorful;
    int type;
    long timeStamp;

    public ModelAgoraMessages() {
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public ModelAgoraMessages(String message, String image, String username, String userId, String level, int type, String AdminStatus, String giftImage, String levelImage, String vipImage , String smsColorful, long timeStamp ) {
        this.message = message;
        this.image = image;
        this.username = username;
        this.AdminStatus = AdminStatus;
        this.userId=userId;
        this.level=level;
        this.levelImage=levelImage;
        this.type=type;
        this.giftImage=giftImage;
        this.vipImage=vipImage;
        this.smsColorful=smsColorful;
        this.timeStamp=timeStamp;
    }

    public String getSmsColorful() {
        return smsColorful;
    }

    public void setSmsColorful(String smsColorful) {
        this.smsColorful = smsColorful;
    }

    public String getLevelImage() {
        return levelImage;
    }

    public void setLevelImage(String levelImage) {
        this.levelImage = levelImage;
    }

    public int getType() {
        return type;
    }

    public String getGiftImage() {
        return giftImage;
    }

    public String getVipImage() {
        return vipImage;
    }

    public void setVipImage(String vipImage) {
        this.vipImage = vipImage;
    }

    public void setGiftImage(String giftImage) {
        this.giftImage = giftImage;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public String getAdminStatus() {
        return AdminStatus;
    }

    public void setAdminStatus(String adminStatus) {
        AdminStatus = adminStatus;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }}
