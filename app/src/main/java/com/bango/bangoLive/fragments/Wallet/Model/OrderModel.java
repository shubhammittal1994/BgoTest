package com.bango.bangoLive.fragments.Wallet.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderModel {

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

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("coinsId")
        @Expose
        private String coinsId;
        @SerializedName("coinsAmount")
        @Expose
        private String coinsAmount;
        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("coinsPrice")
        @Expose
        private String coinsPrice;
        @SerializedName("orderId")
        @Expose
        private String orderId;
        @SerializedName("date")
        @Expose
        private String date;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        @SerializedName("time")
        @Expose
        private String time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCoinsId() {
            return coinsId;
        }

        public void setCoinsId(String coinsId) {
            this.coinsId = coinsId;
        }

        public String getCoinsAmount() {
            return coinsAmount;
        }

        public void setCoinsAmount(String coinsAmount) {
            this.coinsAmount = coinsAmount;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCoinsPrice() {
            return coinsPrice;
        }

        public void setCoinsPrice(String coinsPrice) {
            this.coinsPrice = coinsPrice;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

    }
}
