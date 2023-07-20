package com.bango.bangoLive.fragments.Gifts.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SendGiftModel {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("status")
    @Expose
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

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

        @SerializedName("myStar")
        @Expose
        private String myStar;
        @SerializedName("coinsRecieved")
        @Expose
        private String coinsRecieved;
        @SerializedName("liveStar")
        @Expose
        private String liveStar;
        @SerializedName("liveBox")
        @Expose
        private String liveBox;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("startStatus")
        @Expose
        private String startStatus;
        @SerializedName("starcount")
        @Expose
        private String starcount;
        @SerializedName("strastatus")
        @Expose
        private String strastatus;
        @SerializedName("gift_sum")
        @Expose
        private List<GiftSum> giftSum;

        public List<GiftSum> getGiftSum() {
            return giftSum;
        }

        public void setGiftSum(List<GiftSum> giftSum) {
            this.giftSum = giftSum;
        }

        public String getStarcount() {
            return starcount;
        }

        public void setStarcount(String starcount) {
            this.starcount = starcount;
        }

        public String getStrastatus() {
            return strastatus;
        }

        public void setStrastatus(String strastatus) {
            this.strastatus = strastatus;
        }

        public String getCoinsrecieved() {
            return coinsrecieved;
        }

        public void setCoinsrecieved(String coinsrecieved) {
            this.coinsrecieved = coinsrecieved;
        }

        @SerializedName("coinsrecieved")
        @Expose
        private String coinsrecieved;

        public String getMyStar() {
            return myStar;
        }

        public void setMyStar(String myStar) {
            this.myStar = myStar;
        }

        public String getCoinsRecieved() {
            return coinsRecieved;
        }

        public void setCoinsRecieved(String coinsRecieved) {
            this.coinsRecieved = coinsRecieved;
        }

        public String getLiveStar() {
            return liveStar;
        }

        public void setLiveStar(String liveStar) {
            this.liveStar = liveStar;
        }

        public String getLiveBox() {
            return liveBox;
        }

        public void setLiveBox(String liveBox) {
            this.liveBox = liveBox;
        }

        public String getStartStatus() {
            return startStatus;
        }

        public void setStartStatus(String startStatus) {
            this.startStatus = startStatus;
        }

        public class GiftSum {

            @SerializedName("coin")
            @Expose
            private String coin;
            @SerializedName("userId")
            @Expose
            private String userId;
            @SerializedName("username")
            @Expose
            private String username;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("image")
            @Expose
            private String image;

            public String getCoin() {
                return coin;
            }

            public void setCoin(String coin) {
                this.coin = coin;
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

            public void setUsername(String username) {
                this.username = username;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

        }

    }
}
