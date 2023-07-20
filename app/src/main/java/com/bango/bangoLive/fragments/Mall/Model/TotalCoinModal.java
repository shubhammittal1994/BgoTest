package com.bango.bangoLive.fragments.Mall.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TotalCoinModal {

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
        @SerializedName("purchasedCoin")
        @Expose
        private String purchasedCoin;
        @SerializedName("myCoins")
        @Expose
        private String myCoins;
        @SerializedName("coin")
        @Expose
        private String coin;
        @SerializedName("monthlyCoins")
        @Expose
        private String monthlyCoins;

        public String getMonthlyCoins() {
            return monthlyCoins;
        }

        public void setMonthlyCoins(String monthlyCoins) {
            this.monthlyCoins = monthlyCoins;
        }

        public String getCoin() {
            return coin;
        }

        public void setCoin(String coin) {
            this.coin = coin;
        }

        public String getMyCoins() {
            return myCoins;
        }

        public void setMyCoins(String myCoins) {
            this.myCoins = myCoins;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPurchasedCoin() {
            return purchasedCoin;
        }

        public void setPurchasedCoin(String purchasedCoin) {
            this.purchasedCoin = purchasedCoin;
        }

    }
}
