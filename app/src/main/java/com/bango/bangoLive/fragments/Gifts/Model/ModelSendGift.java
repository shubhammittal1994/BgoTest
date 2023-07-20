package com.bango.bangoLive.fragments.Gifts.Model;

public class ModelSendGift implements Comparable<ModelSendGift> {

    String userId;
    String userName;
    String giftId;
    String giftImage;
    String name;

    public String getGiftedUserId() {
        return giftedUserId;
    }

    public void setGiftedUserId(String giftedUserId) {
        this.giftedUserId = giftedUserId;
    }

    String giftedUserId;

    public String getGiftCount() {
        return giftCount;
    }

    public void setGiftCount(String giftCount) {
        this.giftCount = giftCount;
    }

    String liveLevel;
    String liveStars;
    String giftCount;
    String myLevel;
    String myStars;
    String userImage;
    String giftType;
    String giftName;
    String mybox;
    String livebox;

    public String getGiftType() {
        return giftType;
    }

    public void setGiftType(String giftType) {
        this.giftType = giftType;
    }

    String thumbnail;
    String gifttime;
    String sound;

    int giftPrice;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    long time;

    public String getThumbnail() {
        return thumbnail;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getMybox() {
        return mybox;
    }

    public void setMybox(String mybox) {
        this.mybox = mybox;
    }

    public String getLivebox() {
        return livebox;
    }

    public void setLivebox(String livebox) {
        this.livebox = livebox;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }


    public String getGifttime() {
        return gifttime;
    }

    public void setGifttime(String gifttime) {
        this.gifttime = gifttime;
    }

    public String getLiveLevel() {
        return liveLevel;
    }

    public void setLiveLevel(String liveLevel) {
        this.liveLevel = liveLevel;
    }

    public String getLiveStars() {
        return liveStars;
    }

    public void setLiveStars(String liveStars) {
        this.liveStars = liveStars;
    }

    public String getMyLevel() {
        return myLevel;
    }

    public void setMyLevel(String myLevel) {
        this.myLevel = myLevel;
    }

    public String getMyStars() {
        return myStars;
    }

    public void setMyStars(String myStars) {
        this.myStars = myStars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public int getGiftPrice() {
        return giftPrice;
    }

    public void setGiftPrice(int giftPrice) {
        this.giftPrice = giftPrice;
    }

    public String getGiftImage() {
        return giftImage;
    }

    public void setGiftImage(String giftImage) {
        this.giftImage = giftImage;
    }

    @Override
    public int compareTo(ModelSendGift modelSendGift) {
        int compareage
                = ((ModelSendGift) modelSendGift).getGiftPrice();

        //  For Ascending order
        return   compareage - this.giftPrice;
    }
}
