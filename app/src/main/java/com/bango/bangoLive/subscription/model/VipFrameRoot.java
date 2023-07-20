package com.bango.bangoLive.subscription.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VipFrameRoot {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("details")
    @Expose
    private Details details;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public class Details {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("vipLevel")
        @Expose
        private String vipLevel;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("vipFrom")
        @Expose
        private String vipFrom;
        @SerializedName("vipTo")
        @Expose
        private String vipTo;
        @SerializedName("created")
        @Expose
        private String created;
        @SerializedName("vipId")
        @Expose
        private String vipId;
        @SerializedName("coins")
        @Expose
        private String coins;
        @SerializedName("vipicon")
        @Expose
        private String vipicon;
        @SerializedName("uniqueframes")
        @Expose
        private String uniqueframes;
        @SerializedName("entranceeffect")
        @Expose
        private String entranceeffect;
        @SerializedName("getthiscar")
        @Expose
        private String getthiscar;
        @SerializedName("friends")
        @Expose
        private String friends;
        @SerializedName("following")
        @Expose
        private String following;
        @SerializedName("coinsPerDay")
        @Expose
        private String coinsPerDay;
        @SerializedName("colorfullMessage")
        @Expose
        private String colorfullMessage;
        @SerializedName("flyingComment")
        @Expose
        private String flyingComment;
        @SerializedName("hdeCountryAndOnlineTime")
        @Expose
        private String hdeCountryAndOnlineTime;
        @SerializedName("exclusiveGifts")
        @Expose
        private String exclusiveGifts;
        @SerializedName("preventFromBeingKicked")
        @Expose
        private String preventFromBeingKicked;
        @SerializedName("antiBan")
        @Expose
        private String antiBan;
        @SerializedName("valid")
        @Expose
        private String valid;
        @SerializedName("vipIconImage")
        @Expose
        private String vipIconImage;
        @SerializedName("uniqueFrameImage")
        @Expose
        private String uniqueFrameImage;
        @SerializedName("entranceEffectImage")
        @Expose
        private String entranceEffectImage;
        @SerializedName("getThisCarImage")
        @Expose
        private String getThisCarImage;
        @SerializedName("friendsImage")
        @Expose
        private String friendsImage;
        @SerializedName("followingFriends")
        @Expose
        private String followingFriends;
        @SerializedName("coinsImage")
        @Expose
        private String coinsImage;
        @SerializedName("mainImage")
        @Expose
        private String mainImage;
        @SerializedName("colorMessageImage")
        @Expose
        private String colorMessageImage;
        @SerializedName("flyingCommentImage")
        @Expose
        private String flyingCommentImage;
        @SerializedName("exclusiveGiftImage")
        @Expose
        private String exclusiveGiftImage;
        @SerializedName("vipFrames")
        @Expose
        private List<VipFrame> vipFrames = null;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getVipLevel() {
            return vipLevel;
        }

        public void setVipLevel(String vipLevel) {
            this.vipLevel = vipLevel;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getVipFrom() {
            return vipFrom;
        }

        public void setVipFrom(String vipFrom) {
            this.vipFrom = vipFrom;
        }

        public String getVipTo() {
            return vipTo;
        }

        public void setVipTo(String vipTo) {
            this.vipTo = vipTo;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getVipId() {
            return vipId;
        }

        public void setVipId(String vipId) {
            this.vipId = vipId;
        }

        public String getCoins() {
            return coins;
        }

        public void setCoins(String coins) {
            this.coins = coins;
        }

        public String getVipicon() {
            return vipicon;
        }

        public void setVipicon(String vipicon) {
            this.vipicon = vipicon;
        }

        public String getUniqueframes() {
            return uniqueframes;
        }

        public void setUniqueframes(String uniqueframes) {
            this.uniqueframes = uniqueframes;
        }

        public String getEntranceeffect() {
            return entranceeffect;
        }

        public void setEntranceeffect(String entranceeffect) {
            this.entranceeffect = entranceeffect;
        }

        public String getGetthiscar() {
            return getthiscar;
        }

        public void setGetthiscar(String getthiscar) {
            this.getthiscar = getthiscar;
        }

        public String getFriends() {
            return friends;
        }

        public void setFriends(String friends) {
            this.friends = friends;
        }

        public String getFollowing() {
            return following;
        }

        public void setFollowing(String following) {
            this.following = following;
        }

        public String getCoinsPerDay() {
            return coinsPerDay;
        }

        public void setCoinsPerDay(String coinsPerDay) {
            this.coinsPerDay = coinsPerDay;
        }

        public String getColorfullMessage() {
            return colorfullMessage;
        }

        public void setColorfullMessage(String colorfullMessage) {
            this.colorfullMessage = colorfullMessage;
        }

        public String getFlyingComment() {
            return flyingComment;
        }

        public void setFlyingComment(String flyingComment) {
            this.flyingComment = flyingComment;
        }

        public String getHdeCountryAndOnlineTime() {
            return hdeCountryAndOnlineTime;
        }

        public void setHdeCountryAndOnlineTime(String hdeCountryAndOnlineTime) {
            this.hdeCountryAndOnlineTime = hdeCountryAndOnlineTime;
        }

        public String getExclusiveGifts() {
            return exclusiveGifts;
        }

        public void setExclusiveGifts(String exclusiveGifts) {
            this.exclusiveGifts = exclusiveGifts;
        }

        public String getPreventFromBeingKicked() {
            return preventFromBeingKicked;
        }

        public void setPreventFromBeingKicked(String preventFromBeingKicked) {
            this.preventFromBeingKicked = preventFromBeingKicked;
        }

        public String getAntiBan() {
            return antiBan;
        }

        public void setAntiBan(String antiBan) {
            this.antiBan = antiBan;
        }

        public String getValid() {
            return valid;
        }

        public void setValid(String valid) {
            this.valid = valid;
        }

        public String getVipIconImage() {
            return vipIconImage;
        }

        public void setVipIconImage(String vipIconImage) {
            this.vipIconImage = vipIconImage;
        }

        public String getUniqueFrameImage() {
            return uniqueFrameImage;
        }

        public void setUniqueFrameImage(String uniqueFrameImage) {
            this.uniqueFrameImage = uniqueFrameImage;
        }

        public String getEntranceEffectImage() {
            return entranceEffectImage;
        }

        public void setEntranceEffectImage(String entranceEffectImage) {
            this.entranceEffectImage = entranceEffectImage;
        }

        public String getGetThisCarImage() {
            return getThisCarImage;
        }

        public void setGetThisCarImage(String getThisCarImage) {
            this.getThisCarImage = getThisCarImage;
        }

        public String getFriendsImage() {
            return friendsImage;
        }

        public void setFriendsImage(String friendsImage) {
            this.friendsImage = friendsImage;
        }

        public String getFollowingFriends() {
            return followingFriends;
        }

        public void setFollowingFriends(String followingFriends) {
            this.followingFriends = followingFriends;
        }

        public String getCoinsImage() {
            return coinsImage;
        }

        public void setCoinsImage(String coinsImage) {
            this.coinsImage = coinsImage;
        }

        public String getMainImage() {
            return mainImage;
        }

        public void setMainImage(String mainImage) {
            this.mainImage = mainImage;
        }

        public String getColorMessageImage() {
            return colorMessageImage;
        }

        public void setColorMessageImage(String colorMessageImage) {
            this.colorMessageImage = colorMessageImage;
        }

        public String getFlyingCommentImage() {
            return flyingCommentImage;
        }

        public void setFlyingCommentImage(String flyingCommentImage) {
            this.flyingCommentImage = flyingCommentImage;
        }

        public String getExclusiveGiftImage() {
            return exclusiveGiftImage;
        }

        public void setExclusiveGiftImage(String exclusiveGiftImage) {
            this.exclusiveGiftImage = exclusiveGiftImage;
        }

        public List<VipFrame> getVipFrames() {
            return vipFrames;
        }

        public void setVipFrames(List<VipFrame> vipFrames) {
            this.vipFrames = vipFrames;
        }
    }

    public class VipFrame {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("vipType")
        @Expose
        private String vipType;
        @SerializedName("frameName")
        @Expose
        private String frameName;
        @SerializedName("framePrice")
        @Expose
        private String framePrice;
        @SerializedName("validity")
        @Expose
        private String validity;
        @SerializedName("image")
        @Expose
        private String image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVipType() {
            return vipType;
        }

        public void setVipType(String vipType) {
            this.vipType = vipType;
        }

        public String getFrameName() {
            return frameName;
        }

        public void setFrameName(String frameName) {
            this.frameName = frameName;
        }

        public String getFramePrice() {
            return framePrice;
        }

        public void setFramePrice(String framePrice) {
            this.framePrice = framePrice;
        }

        public String getValidity() {
            return validity;
        }

        public void setValidity(String validity) {
            this.validity = validity;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
