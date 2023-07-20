package com.bango.bangoLive.fragments.Gifts.Model;

import java.util.List;

public class GoLiveModelClass {
        private String userID = "";
        private String image = "";
        private String liveType = "";
        private String liveStatus = "";
        private boolean live = false;
        private String name = "";
        private String userName = "";
        private String requestStatus = "";
        private String pkTime = "";
        private String pkStatus = "";
        private String otherUserChannelName = "";
        private String otherUserToken = "";
        private String svga = "";
        private String entryEffect="";
        private String banOrKick = "";
        private String mute="";
        private String seatPosition="";
        private String gender="";
        private String hostDob="";
        private String hostGender="";
        private Long peerId = 1l;

        public String getEntryEffect() {
            return entryEffect;
        }

        public void setEntryEffect(String entryEffect) {
            this.entryEffect = entryEffect;
        }

        public String getSvga() {
            return svga;
        }

        public void setSvga(String svga) {
            this.svga = svga;
        }

        public Long getPeerId() {
            return peerId;
        }

        public void setPeerId(Long peerId) {
            this.peerId = peerId;
        }

        public String getHostDob() {
            return hostDob;
        }

        public void setHostDob(String hostDob) {
            this.hostDob = hostDob;
        }

        public String getHostGender() {
            return hostGender;
        }

        public void setHostGender(String hostGender) {
            this.hostGender = hostGender;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        private String age="";

        private boolean adminStatus=false;

        public boolean isAdminStatus() {
            return adminStatus;
        }

        public void setAdminStatus(boolean adminStatus) {
            this.adminStatus = adminStatus;
        }

        public String getSeatPosition() {
            return seatPosition;
        }
        private String seatLock="";

        public String getSeatLock() {
            return seatLock;
        }

        public void setSeatLock(String seatLock) {
            this.seatLock = seatLock;
        }

        public void setSeatPosition(String seatPosition) {
            this.seatPosition = seatPosition;
        }

        public String getMute() {
            return mute;
        }
        public void setMute(String mute) {
            this.mute = mute;
        }

        public String getBanOrKick() {
            return banOrKick;
        }

        public void setBanOrKick(String banOrKick) {
            this.banOrKick = banOrKick;
        }

        public String getPkTime() {
            return pkTime;
        }

        public void setPkTime(String pkTime) {
            this.pkTime = pkTime;
        }

        public String getPkStatus() {
            return pkStatus;
        }

        public void setPkStatus(String pkStatus) {
            this.pkStatus = pkStatus;
        }

        public String getOtherUserChannelName() {
            return otherUserChannelName;
        }

        public void setOtherUserChannelName(String otherUserChannelName) {
            this.otherUserChannelName = otherUserChannelName;
        }

        public String getOtherUserToken() {
            return otherUserToken;
        }

        public void setOtherUserToken(String otherUserToken) {
            this.otherUserToken = otherUserToken;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLiveType() {
            return liveType;
        }

        public void setLiveType(String liveType) {
            this.liveType = liveType;
        }

        public String getLiveStatus() {
            return liveStatus;
        }

        public void setLiveStatus(String liveStatus) {
            this.liveStatus = liveStatus;
        }

        public boolean isLive() {
            return live;
        }

        public void setLive(boolean live) {
            this.live = live;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getRequestStatus() {
            return requestStatus;
        }

        public void setRequestStatus(String requestStatus) {
            this.requestStatus = requestStatus;
        }


}
