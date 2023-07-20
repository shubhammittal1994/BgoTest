package com.bango.bangoLive.AudioRoom.MODEL;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OtherUserDataModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("details")
    @Expose
    private Details details;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        @SerializedName("host_status")
        @Expose
        private String hostStatus;
        @SerializedName("auth_token")
        @Expose
        private String authToken;
        @SerializedName("entryId")
        @Expose
        private String entryId;
        @SerializedName("register")
        @Expose
        private String register;
        @SerializedName("userBanStatus")
        @Expose
        private String userBanStatus;
        @SerializedName("liveStatus")
        @Expose
        private String liveStatus;
        @SerializedName("badge")
        @Expose
        private String badge;
        @SerializedName("crown")
        @Expose
        private String crown;
        @SerializedName("leval")
        @Expose
        private String leval;
        @SerializedName("my_level")
        @Expose
        private String myLevel;
        @SerializedName("talent_level")
        @Expose
        private String talentLevel;
        @SerializedName("monthlyCoins")
        @Expose
        private String monthlyCoins;
        @SerializedName("monthlySendCoins")
        @Expose
        private String monthlySendCoins;
        @SerializedName("purchasedCoin")
        @Expose
        private String purchasedCoin;
        @SerializedName("monthlyPurchasedCoins")
        @Expose
        private String monthlyPurchasedCoins;
        @SerializedName("expCoin")
        @Expose
        private String expCoin;
        @SerializedName("wallet")
        @Expose
        private String wallet;
        @SerializedName("incomeDollar")
        @Expose
        private String incomeDollar;
        @SerializedName("beans")
        @Expose
        private String beans;
        @SerializedName("privateAccount")
        @Expose
        private String privateAccount;
        @SerializedName("password_otp")
        @Expose
        private String passwordOtp;
        @SerializedName("loginOtp")
        @Expose
        private String loginOtp;
        @SerializedName("likeVideo")
        @Expose
        private String likeVideo;
        @SerializedName("followingUser")
        @Expose
        private String followingUser;
        @SerializedName("profilePhotoStatus")
        @Expose
        private String profilePhotoStatus;
        @SerializedName("onlineStatus")
        @Expose
        private String onlineStatus;
        @SerializedName("basicSalary")
        @Expose
        private String basicSalary;
        @SerializedName("coinSharing")
        @Expose
        private String coinSharing;
        @SerializedName("hoursLive")
        @Expose
        private String hoursLive;
        @SerializedName("coin")
        @Expose
        private String coin;
        @SerializedName("total_send_coin")
        @Expose
        private String totalSendCoin;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("bio")
        @Expose
        private String bio;
        @SerializedName("social_id")
        @Expose
        private String socialId;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("locationName")
        @Expose
        private String locationName;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("video")
        @Expose
        private String video;
        @SerializedName("followerCount")
        @Expose
        private String followerCount;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("likeNotifaction")
        @Expose
        private String likeNotifaction;
        @SerializedName("commentNotification")
        @Expose
        private String commentNotification;
        @SerializedName("followersNotification")
        @Expose
        private String followersNotification;
        @SerializedName("messageNotification")
        @Expose
        private String messageNotification;
        @SerializedName("videoNotification")
        @Expose
        private String videoNotification;
        @SerializedName("reg_id")
        @Expose
        private String regId;
        @SerializedName("deviceId")
        @Expose
        private String deviceId;
        @SerializedName("device_type")
        @Expose
        private String deviceType;
        @SerializedName("login_type")
        @Expose
        private String loginType;
        @SerializedName("usernameChangeStatus")
        @Expose
        private String usernameChangeStatus;
        @SerializedName("created")
        @Expose
        private Object created;
        @SerializedName("updated")
        @Expose
        private String updated;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("hotlist")
        @Expose
        private String hotlist;
        @SerializedName("liveHotlist")
        @Expose
        private String liveHotlist;
        @SerializedName("liveHostListDateTime")
        @Expose
        private String liveHostListDateTime;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("nationalId")
        @Expose
        private String nationalId;
        @SerializedName("agencyId")
        @Expose
        private String agencyId;
        @SerializedName("paymentType")
        @Expose
        private String paymentType;
        @SerializedName("join_agencyId")
        @Expose
        private String joinAgencyId;
        @SerializedName("registerType")
        @Expose
        private String registerType;
        @SerializedName("profileStatus")
        @Expose
        private String profileStatus;
        @SerializedName("posterImage")
        @Expose
        private String posterImage;
        @SerializedName("banUnban")
        @Expose
        private String banUnban;
        @SerializedName("kick")
        @Expose
        private String kick;
        @SerializedName("agencyBan")
        @Expose
        private String agencyBan;
        @SerializedName("antikick_block_mute")
        @Expose
        private String antikickBlockMute;
        @SerializedName("screen_shot_and_recording")
        @Expose
        private String screenShotAndRecording;
        @SerializedName("dp_approve")
        @Expose
        private String dpApprove;
        @SerializedName("admin")
        @Expose
        private String admin;
        @SerializedName("myFrame")
        @Expose
        private String myFrame;
        @SerializedName("myVipFrame")
        @Expose
        private String myVipFrame;
        @SerializedName("myAdminFrame")
        @Expose
        private String myAdminFrame;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("audioWallpers")
        @Expose
        private String audioWallpers;
        @SerializedName("vipLevel")
        @Expose
        private String vipLevel;
        @SerializedName("vipFrom")
        @Expose
        private String vipFrom;
        @SerializedName("vipTo")
        @Expose
        private String vipTo;
        @SerializedName("userLevelImage")
        @Expose
        private String userLevelImage;
        @SerializedName("userTalentLevelImage")
        @Expose
        private String userTalentLevelImage;
        @SerializedName("myAppliedFrame")
        @Expose
        private Object myAppliedFrame;
        @SerializedName("vip_details")
        @Expose
        private Object vipDetails;
        @SerializedName("userFramesList")
        @Expose
        private List<UserFrames> userFramesList;
        @SerializedName("totalCoinSend")
        @Expose
        private String totalCoinSend;
        @SerializedName("totalCoinGet")
        @Expose
        private String totalCoinGet;
        @SerializedName("agency_name")
        @Expose
        private String agencyName;
        @SerializedName("agency_code")
        @Expose
        private String agencyCode;
        @SerializedName("UserLiveStatus")
        @Expose
        private String userLiveStatus;
        @SerializedName("UserLiveId")
        @Expose
        private Object userLiveId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHostStatus() {
            return hostStatus;
        }

        public void setHostStatus(String hostStatus) {
            this.hostStatus = hostStatus;
        }

        public String getAuthToken() {
            return authToken;
        }

        public void setAuthToken(String authToken) {
            this.authToken = authToken;
        }

        public String getEntryId() {
            return entryId;
        }

        public void setEntryId(String entryId) {
            this.entryId = entryId;
        }

        public String getRegister() {
            return register;
        }

        public void setRegister(String register) {
            this.register = register;
        }

        public String getUserBanStatus() {
            return userBanStatus;
        }

        public void setUserBanStatus(String userBanStatus) {
            this.userBanStatus = userBanStatus;
        }

        public String getLiveStatus() {
            return liveStatus;
        }

        public void setLiveStatus(String liveStatus) {
            this.liveStatus = liveStatus;
        }

        public String getBadge() {
            return badge;
        }

        public void setBadge(String badge) {
            this.badge = badge;
        }

        public String getCrown() {
            return crown;
        }

        public void setCrown(String crown) {
            this.crown = crown;
        }

        public String getLeval() {
            return leval;
        }

        public void setLeval(String leval) {
            this.leval = leval;
        }

        public String getMyLevel() {
            return myLevel;
        }

        public void setMyLevel(String myLevel) {
            this.myLevel = myLevel;
        }

        public String getTalentLevel() {
            return talentLevel;
        }

        public void setTalentLevel(String talentLevel) {
            this.talentLevel = talentLevel;
        }

        public String getMonthlyCoins() {
            return monthlyCoins;
        }

        public void setMonthlyCoins(String monthlyCoins) {
            this.monthlyCoins = monthlyCoins;
        }

        public String getMonthlySendCoins() {
            return monthlySendCoins;
        }

        public void setMonthlySendCoins(String monthlySendCoins) {
            this.monthlySendCoins = monthlySendCoins;
        }

        public String getPurchasedCoin() {
            return purchasedCoin;
        }

        public void setPurchasedCoin(String purchasedCoin) {
            this.purchasedCoin = purchasedCoin;
        }

        public String getMonthlyPurchasedCoins() {
            return monthlyPurchasedCoins;
        }

        public void setMonthlyPurchasedCoins(String monthlyPurchasedCoins) {
            this.monthlyPurchasedCoins = monthlyPurchasedCoins;
        }

        public String getExpCoin() {
            return expCoin;
        }

        public void setExpCoin(String expCoin) {
            this.expCoin = expCoin;
        }

        public String getWallet() {
            return wallet;
        }

        public void setWallet(String wallet) {
            this.wallet = wallet;
        }

        public String getIncomeDollar() {
            return incomeDollar;
        }

        public void setIncomeDollar(String incomeDollar) {
            this.incomeDollar = incomeDollar;
        }

        public String getBeans() {
            return beans;
        }

        public void setBeans(String beans) {
            this.beans = beans;
        }

        public String getPrivateAccount() {
            return privateAccount;
        }

        public void setPrivateAccount(String privateAccount) {
            this.privateAccount = privateAccount;
        }

        public String getPasswordOtp() {
            return passwordOtp;
        }

        public void setPasswordOtp(String passwordOtp) {
            this.passwordOtp = passwordOtp;
        }

        public String getLoginOtp() {
            return loginOtp;
        }

        public void setLoginOtp(String loginOtp) {
            this.loginOtp = loginOtp;
        }

        public String getLikeVideo() {
            return likeVideo;
        }

        public void setLikeVideo(String likeVideo) {
            this.likeVideo = likeVideo;
        }

        public String getFollowingUser() {
            return followingUser;
        }

        public void setFollowingUser(String followingUser) {
            this.followingUser = followingUser;
        }

        public String getProfilePhotoStatus() {
            return profilePhotoStatus;
        }

        public void setProfilePhotoStatus(String profilePhotoStatus) {
            this.profilePhotoStatus = profilePhotoStatus;
        }

        public String getOnlineStatus() {
            return onlineStatus;
        }

        public void setOnlineStatus(String onlineStatus) {
            this.onlineStatus = onlineStatus;
        }

        public String getBasicSalary() {
            return basicSalary;
        }

        public void setBasicSalary(String basicSalary) {
            this.basicSalary = basicSalary;
        }

        public String getCoinSharing() {
            return coinSharing;
        }

        public void setCoinSharing(String coinSharing) {
            this.coinSharing = coinSharing;
        }

        public String getHoursLive() {
            return hoursLive;
        }

        public void setHoursLive(String hoursLive) {
            this.hoursLive = hoursLive;
        }

        public String getCoin() {
            return coin;
        }

        public void setCoin(String coin) {
            this.coin = coin;
        }

        public String getTotalSendCoin() {
            return totalSendCoin;
        }

        public void setTotalSendCoin(String totalSendCoin) {
            this.totalSendCoin = totalSendCoin;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public String getSocialId() {
            return socialId;
        }

        public void setSocialId(String socialId) {
            this.socialId = socialId;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getLocationName() {
            return locationName;
        }

        public void setLocationName(String locationName) {
            this.locationName = locationName;
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

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getFollowerCount() {
            return followerCount;
        }

        public void setFollowerCount(String followerCount) {
            this.followerCount = followerCount;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getLikeNotifaction() {
            return likeNotifaction;
        }

        public void setLikeNotifaction(String likeNotifaction) {
            this.likeNotifaction = likeNotifaction;
        }

        public String getCommentNotification() {
            return commentNotification;
        }

        public void setCommentNotification(String commentNotification) {
            this.commentNotification = commentNotification;
        }

        public String getFollowersNotification() {
            return followersNotification;
        }

        public void setFollowersNotification(String followersNotification) {
            this.followersNotification = followersNotification;
        }

        public String getMessageNotification() {
            return messageNotification;
        }

        public void setMessageNotification(String messageNotification) {
            this.messageNotification = messageNotification;
        }

        public String getVideoNotification() {
            return videoNotification;
        }

        public void setVideoNotification(String videoNotification) {
            this.videoNotification = videoNotification;
        }

        public String getRegId() {
            return regId;
        }

        public void setRegId(String regId) {
            this.regId = regId;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getLoginType() {
            return loginType;
        }

        public void setLoginType(String loginType) {
            this.loginType = loginType;
        }

        public String getUsernameChangeStatus() {
            return usernameChangeStatus;
        }

        public void setUsernameChangeStatus(String usernameChangeStatus) {
            this.usernameChangeStatus = usernameChangeStatus;
        }

        public Object getCreated() {
            return created;
        }

        public void setCreated(Object created) {
            this.created = created;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getHotlist() {
            return hotlist;
        }

        public void setHotlist(String hotlist) {
            this.hotlist = hotlist;
        }

        public String getLiveHotlist() {
            return liveHotlist;
        }

        public void setLiveHotlist(String liveHotlist) {
            this.liveHotlist = liveHotlist;
        }

        public String getLiveHostListDateTime() {
            return liveHostListDateTime;
        }

        public void setLiveHostListDateTime(String liveHostListDateTime) {
            this.liveHostListDateTime = liveHostListDateTime;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getNationalId() {
            return nationalId;
        }

        public void setNationalId(String nationalId) {
            this.nationalId = nationalId;
        }

        public String getAgencyId() {
            return agencyId;
        }

        public void setAgencyId(String agencyId) {
            this.agencyId = agencyId;
        }

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }

        public String getJoinAgencyId() {
            return joinAgencyId;
        }

        public void setJoinAgencyId(String joinAgencyId) {
            this.joinAgencyId = joinAgencyId;
        }

        public String getRegisterType() {
            return registerType;
        }

        public void setRegisterType(String registerType) {
            this.registerType = registerType;
        }

        public String getProfileStatus() {
            return profileStatus;
        }

        public void setProfileStatus(String profileStatus) {
            this.profileStatus = profileStatus;
        }

        public String getPosterImage() {
            return posterImage;
        }

        public void setPosterImage(String posterImage) {
            this.posterImage = posterImage;
        }

        public String getBanUnban() {
            return banUnban;
        }

        public void setBanUnban(String banUnban) {
            this.banUnban = banUnban;
        }

        public String getKick() {
            return kick;
        }

        public void setKick(String kick) {
            this.kick = kick;
        }

        public String getAgencyBan() {
            return agencyBan;
        }

        public void setAgencyBan(String agencyBan) {
            this.agencyBan = agencyBan;
        }

        public String getAntikickBlockMute() {
            return antikickBlockMute;
        }

        public void setAntikickBlockMute(String antikickBlockMute) {
            this.antikickBlockMute = antikickBlockMute;
        }

        public String getScreenShotAndRecording() {
            return screenShotAndRecording;
        }

        public void setScreenShotAndRecording(String screenShotAndRecording) {
            this.screenShotAndRecording = screenShotAndRecording;
        }

        public String getDpApprove() {
            return dpApprove;
        }

        public void setDpApprove(String dpApprove) {
            this.dpApprove = dpApprove;
        }

        public String getAdmin() {
            return admin;
        }

        public void setAdmin(String admin) {
            this.admin = admin;
        }

        public String getMyFrame() {
            return myFrame;
        }

        public void setMyFrame(String myFrame) {
            this.myFrame = myFrame;
        }

        public String getMyVipFrame() {
            return myVipFrame;
        }

        public void setMyVipFrame(String myVipFrame) {
            this.myVipFrame = myVipFrame;
        }

        public String getMyAdminFrame() {
            return myAdminFrame;
        }

        public void setMyAdminFrame(String myAdminFrame) {
            this.myAdminFrame = myAdminFrame;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getAudioWallpers() {
            return audioWallpers;
        }

        public void setAudioWallpers(String audioWallpers) {
            this.audioWallpers = audioWallpers;
        }

        public String getVipLevel() {
            return vipLevel;
        }

        public void setVipLevel(String vipLevel) {
            this.vipLevel = vipLevel;
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

        public String getUserLevelImage() {
            return userLevelImage;
        }

        public void setUserLevelImage(String userLevelImage) {
            this.userLevelImage = userLevelImage;
        }

        public String getUserTalentLevelImage() {
            return userTalentLevelImage;
        }

        public void setUserTalentLevelImage(String userTalentLevelImage) {
            this.userTalentLevelImage = userTalentLevelImage;
        }

        public Object getMyAppliedFrame() {
            return myAppliedFrame;
        }

        public void setMyAppliedFrame(Object myAppliedFrame) {
            this.myAppliedFrame = myAppliedFrame;
        }

        public Object getVipDetails() {
            return vipDetails;
        }

        public void setVipDetails(Object vipDetails) {
            this.vipDetails = vipDetails;
        }

        public List<UserFrames> getUserFramesList() {
            return userFramesList;
        }

        public void setUserFramesList(List<UserFrames> userFramesList) {
            this.userFramesList = userFramesList;
        }

        public String getTotalCoinSend() {
            return totalCoinSend;
        }

        public void setTotalCoinSend(String totalCoinSend) {
            this.totalCoinSend = totalCoinSend;
        }

        public String getTotalCoinGet() {
            return totalCoinGet;
        }

        public void setTotalCoinGet(String totalCoinGet) {
            this.totalCoinGet = totalCoinGet;
        }

        public String getAgencyName() {
            return agencyName;
        }

        public void setAgencyName(String agencyName) {
            this.agencyName = agencyName;
        }

        public String getAgencyCode() {
            return agencyCode;
        }

        public void setAgencyCode(String agencyCode) {
            this.agencyCode = agencyCode;
        }

        public String getUserLiveStatus() {
            return userLiveStatus;
        }

        public void setUserLiveStatus(String userLiveStatus) {
            this.userLiveStatus = userLiveStatus;
        }

        public Object getUserLiveId() {
            return userLiveId;
        }

        public void setUserLiveId(Object userLiveId) {
            this.userLiveId = userLiveId;
        }


        public class UserFrames {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("thumbnail")
            @Expose
            private String thumbnail;
            @SerializedName("level")
            @Expose
            private String level;
            @SerializedName("price")
            @Expose
            private String price;
            @SerializedName("validity")
            @Expose
            private String validity;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
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

        }

    }
}
