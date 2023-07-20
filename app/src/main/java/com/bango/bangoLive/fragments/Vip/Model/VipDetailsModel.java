package com.bango.bangoLive.fragments.Vip.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VipDetailsModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("details")
    @Expose
    private Details details;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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
        @SerializedName("coins")
        @Expose
        private String coins;
        @SerializedName("valid")
        @Expose
        private String valid;
        @SerializedName("level")
        @Expose
        private String level;
        @SerializedName("vipMemberShip")
        @Expose
        private String vipMemberShip;
        @SerializedName("uniqueFrames")
        @Expose
        private String uniqueFrames;
        @SerializedName("entranceEffects")
        @Expose
        private String entranceEffects;
        @SerializedName("micWave")
        @Expose
        private String micWave;
        @SerializedName("exclusiveBubble")
        @Expose
        private String exclusiveBubble;
        @SerializedName("roomCard")
        @Expose
        private String roomCard;
        @SerializedName("badge")
        @Expose
        private String badge;
        @SerializedName("entranceEffect")
        @Expose
        private String entranceEffect;
        @SerializedName("vipGift")
        @Expose
        private String vipGift;
        @SerializedName("Highlight Name")
        @Expose
        private String highlightName;
        @SerializedName("vipSeat")
        @Expose
        private String vipSeat;
        @SerializedName("vipDataCard")
        @Expose
        private String vipDataCard;
        @SerializedName("LikeMe")
        @Expose
        private String likeMe;
        @SerializedName("visitorFunction")
        @Expose
        private String visitorFunction;
        @SerializedName("antiBanMic")
        @Expose
        private String antiBanMic;
        @SerializedName("antiKick")
        @Expose
        private String antiKick;
        @SerializedName("vipImage")
        @Expose
        private String vipImage;
        @SerializedName("entrymp4")
        @Expose
        private String entrymp4;
        @SerializedName("entrysvg")
        @Expose
        private String entrysvg;
        @SerializedName("mess1")
        @Expose
        private String mess1;
        @SerializedName("mess2")
        @Expose
        private String mess2;
        @SerializedName("mess1svg")
        @Expose
        private String mess1svg;
        @SerializedName("mess2svg")
        @Expose
        private String mess2svg;
        @SerializedName("framemp4")
        @Expose
        private String framemp4;
        @SerializedName("framesvg")
        @Expose
        private String framesvg;

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

        public String getValid() {
            return valid;
        }

        public void setValid(String valid) {
            this.valid = valid;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getVipMemberShip() {
            return vipMemberShip;
        }

        public void setVipMemberShip(String vipMemberShip) {
            this.vipMemberShip = vipMemberShip;
        }

        public String getUniqueFrames() {
            return uniqueFrames;
        }

        public void setUniqueFrames(String uniqueFrames) {
            this.uniqueFrames = uniqueFrames;
        }

        public String getEntranceEffects() {
            return entranceEffects;
        }

        public void setEntranceEffects(String entranceEffects) {
            this.entranceEffects = entranceEffects;
        }

        public String getMicWave() {
            return micWave;
        }

        public void setMicWave(String micWave) {
            this.micWave = micWave;
        }

        public String getExclusiveBubble() {
            return exclusiveBubble;
        }

        public void setExclusiveBubble(String exclusiveBubble) {
            this.exclusiveBubble = exclusiveBubble;
        }

        public String getRoomCard() {
            return roomCard;
        }

        public void setRoomCard(String roomCard) {
            this.roomCard = roomCard;
        }

        public String getBadge() {
            return badge;
        }

        public void setBadge(String badge) {
            this.badge = badge;
        }

        public String getEntranceEffect() {
            return entranceEffect;
        }

        public void setEntranceEffect(String entranceEffect) {
            this.entranceEffect = entranceEffect;
        }

        public String getVipGift() {
            return vipGift;
        }

        public void setVipGift(String vipGift) {
            this.vipGift = vipGift;
        }

        public String getHighlightName() {
            return highlightName;
        }

        public void setHighlightName(String highlightName) {
            this.highlightName = highlightName;
        }

        public String getVipSeat() {
            return vipSeat;
        }

        public void setVipSeat(String vipSeat) {
            this.vipSeat = vipSeat;
        }

        public String getVipDataCard() {
            return vipDataCard;
        }

        public void setVipDataCard(String vipDataCard) {
            this.vipDataCard = vipDataCard;
        }

        public String getLikeMe() {
            return likeMe;
        }

        public void setLikeMe(String likeMe) {
            this.likeMe = likeMe;
        }

        public String getVisitorFunction() {
            return visitorFunction;
        }

        public void setVisitorFunction(String visitorFunction) {
            this.visitorFunction = visitorFunction;
        }

        public String getAntiBanMic() {
            return antiBanMic;
        }

        public void setAntiBanMic(String antiBanMic) {
            this.antiBanMic = antiBanMic;
        }

        public String getAntiKick() {
            return antiKick;
        }

        public void setAntiKick(String antiKick) {
            this.antiKick = antiKick;
        }

        public String getVipImage() {
            return vipImage;
        }

        public void setVipImage(String vipImage) {
            this.vipImage = vipImage;
        }

        public String getEntrymp4() {
            return entrymp4;
        }

        public void setEntrymp4(String entrymp4) {
            this.entrymp4 = entrymp4;
        }

        public String getEntrysvg() {
            return entrysvg;
        }

        public void setEntrysvg(String entrysvg) {
            this.entrysvg = entrysvg;
        }

        public String getMess1() {
            return mess1;
        }

        public void setMess1(String mess1) {
            this.mess1 = mess1;
        }

        public String getMess2() {
            return mess2;
        }

        public void setMess2(String mess2) {
            this.mess2 = mess2;
        }

        public String getMess1svg() {
            return mess1svg;
        }

        public void setMess1svg(String mess1svg) {
            this.mess1svg = mess1svg;
        }

        public String getMess2svg() {
            return mess2svg;
        }

        public void setMess2svg(String mess2svg) {
            this.mess2svg = mess2svg;
        }

        public String getFramemp4() {
            return framemp4;
        }

        public void setFramemp4(String framemp4) {
            this.framemp4 = framemp4;
        }

        public String getFramesvg() {
            return framesvg;
        }

        public void setFramesvg(String framesvg) {
            this.framesvg = framesvg;
        }

    }
}
