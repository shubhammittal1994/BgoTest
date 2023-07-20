package com.bango.bangoLive.fragments.Wallet.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public class Data {

        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("customerId")
        @Expose
        private String customerId;
        @SerializedName("cardNumber")
        @Expose
        private String cardNumber;

        public String getGift() {
            return gift;
        }

        public void setGift(String gift) {
            this.gift = gift;
        }

        public Integer getCoins() {
            return coins;
        }

        public void setCoins(Integer coins) {
            this.coins = coins;
        }

        @SerializedName("gift")
        @Expose
        private String gift;
        @SerializedName("coins")
        @Expose
        private Integer coins;
        @SerializedName("expMonth")
        @Expose
        private String expMonth;
        @SerializedName("expYear")
        @Expose
        private String expYear;
        @SerializedName("cvv")
        @Expose
        private String cvv;
        @SerializedName("cardType")
        @Expose
        private String cardType;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("date")
        @Expose
        private String date;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getExpMonth() {
            return expMonth;
        }

        public void setExpMonth(String expMonth) {
            this.expMonth = expMonth;
        }

        public String getExpYear() {
            return expYear;
        }

        public void setExpYear(String expYear) {
            this.expYear = expYear;
        }

        public String getCvv() {
            return cvv;
        }

        public void setCvv(String cvv) {
            this.cvv = cvv;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

    }

}

