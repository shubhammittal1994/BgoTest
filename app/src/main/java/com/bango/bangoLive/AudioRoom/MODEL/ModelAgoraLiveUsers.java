package com.bango.bangoLive.AudioRoom.MODEL;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelAgoraLiveUsers {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("details")
    @Expose
    private List<Detail> details = null;

    public String getSuccess() {
        return success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    public class Detail {

        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("host_status")
        @Expose
        private String host_status;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("coin")
        @Expose
        private String coin;
        @SerializedName("leval")
        @Expose
        private String leval;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("followerCount")
        @Expose
        private String followerCount;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("hostType")
        @Expose
        private String hostType;
        @SerializedName("liveGiftings")
        @Expose
        private String liveGiftings;
        @SerializedName("userId")
        @Expose
        private String userId;

        @SerializedName("liveName")
        @Expose
        private String liveName;

        public String getMyAppliedFrame() {
            return myAppliedFrame;
        }

        public void setMyAppliedFrame(String myAppliedFrame) {
            this.myAppliedFrame = myAppliedFrame;
        }

        @SerializedName("myAppliedFrame")
        @Expose
        private String myAppliedFrame;
        @SerializedName("vip_details")
        @Expose
        private VipDetails vipDetails;

        @SerializedName("frame_details")
        @Expose
        private FrameDetails frameDetails;

        public FrameDetails getFrameDetails() {
            return frameDetails;
        }

        public void setFrameDetails(FrameDetails frameDetails) {
            this.frameDetails = frameDetails;
        }

        public VipDetails getVipDetails() {
            return vipDetails;
        }

        public void setVipDetails(VipDetails vipDetails) {
            this.vipDetails = vipDetails;
        }

        public String getLiveName() {
            return liveName;
        }

        public void setLiveName(String liveName) {
            this.liveName = liveName;
        }

        public String getLiveGiftings() {
            return liveGiftings;
        }

        public void setLiveGiftings(String liveGiftings) {
            this.liveGiftings = liveGiftings;
        }

        public String getHost_status() {
            return host_status;
        }

        public void setHost_status(String host_status) {
            this.host_status = host_status;
        }

        @SerializedName("channelName")
        @Expose
        private String channelName;
        @SerializedName("count")
        @Expose
        private String count;
        @SerializedName("token")
        @Expose
        private String token;
        @SerializedName("starCount")
        @Expose
        private String starCount;
        @SerializedName("latitude")
        @Expose
        private String latitude;

        public String getStarCount() {
            return starCount;
        }

        public void setStarCount(String starCount) {
            this.starCount = starCount;
        }

        public String getPosterImageString() {
            return posterImageString;
        }

        public void setPosterImageString(String posterImageString) {
            this.posterImageString = posterImageString;
        }

        @SerializedName("posterImage")
        @Expose
        private String posterImageString;

        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("rtmToken")
        @Expose
        private String rtmToken;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("archivedDate")
        @Expose
        private String archivedDate;
        @SerializedName("created")
        @Expose
        private String created;
        @SerializedName("checkBoxStatus")
        @Expose
        private String checkBoxStatus;
        @SerializedName("box")
        @Expose
        private String box;
        @SerializedName("purchasedCoin")
        @Expose
        private String purchasedCoin;
        @SerializedName("userLeval")
        @Expose
        private String userLeval;
        @SerializedName("startCount")
        @Expose
        private String startCount;
        @SerializedName("followStatus")
        @Expose
        private String followStatus;
        @SerializedName("bool")
        @Expose
        private String bool;


        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getBool() {
            return bool;
        }

        public void setBool(String bool) {
            this.bool = bool;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCoin() {
            return coin;
        }

        public void setCoin(String coin) {
            this.coin = coin;
        }

        public String getLeval() {
            return leval;
        }

        public void setLeval(String leval) {
            this.leval = leval;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getFollowerCount() {
            return followerCount;
        }

        public void setFollowerCount(String followerCount) {
            this.followerCount = followerCount;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHostType() {
            return hostType;
        }

        public void setHostType(String hostType) {
            this.hostType = hostType;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getRtmToken() {
            return rtmToken;
        }

        public void setRtmToken(String rtmToken) {
            this.rtmToken = rtmToken;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getArchivedDate() {
            return archivedDate;
        }

        public void setArchivedDate(String archivedDate) {
            this.archivedDate = archivedDate;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getCheckBoxStatus() {
            return checkBoxStatus;
        }

        public void setCheckBoxStatus(String checkBoxStatus) {
            this.checkBoxStatus = checkBoxStatus;
        }

        public String getBox() {
            return box;
        }

        public void setBox(String box) {
            this.box = box;
        }

        public String getPurchasedCoin() {
            return purchasedCoin;
        }

        public void setPurchasedCoin(String purchasedCoin) {
            this.purchasedCoin = purchasedCoin;
        }

        public String getUserLeval() {
            return userLeval;
        }

        public void setUserLeval(String userLeval) {
            this.userLeval = userLeval;
        }

        public String getStartCount() {
            return startCount;
        }

        public void setStartCount(String startCount) {
            this.startCount = startCount;
        }

        public String getFollowStatus() {
            return followStatus;
        }

        public void setFollowStatus(String followStatus) {
            this.followStatus = followStatus;
        }

    }

    public class VipDetails {

        @SerializedName("id")
        @Expose
        private String id;
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

        public List<VipFrame> getVipFrames() {
            return vipFrames;
        }

        public void setVipFrames(List<VipFrame> vipFrames) {
            this.vipFrames = vipFrames;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

    }

    public class FrameDetails {
        @SerializedName("id")
        @Expose
        public String id;

        @SerializedName("level")
        @Expose
        public String level;

        @SerializedName("price")
        @Expose
        public String price;

        @SerializedName("validity")
        @Expose
        public String validity;

        @SerializedName("framesPerLevel")
        @Expose
        public String framesPerLevel;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getValidity() {
            return validity;
        }

        public void setValidity(String validity) {
            this.validity = validity;
        }

        public String getFramesPerLevel() {
            return framesPerLevel;
        }

        public void setFramesPerLevel(String framesPerLevel) {
            this.framesPerLevel = framesPerLevel;
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