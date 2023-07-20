package com.bango.bangoLive.fragments.Wallet.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Root_My_Wallet implements Serializable {
  @SerializedName("success")
  @Expose
  private String success;
  @SerializedName("message")
  @Expose
  private String message;
  @SerializedName("details")
  @Expose
  private List<Detail> details = null;

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

  public List<Detail> getDetails() {
    return details;
  }

  public void setDetails(List<Detail> details) {
    this.details = details;
  }

  public class Detail {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("dollars")
    @Expose
    private String dollars;
    @SerializedName("coins")
    @Expose
    private String coins;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getDollars() {
      return dollars;
    }

    public void setDollars(String dollars) {
      this.dollars = dollars;
    }

    public String getCoins() {
      return coins;
    }

    public void setCoins(String coins) {
      this.coins = coins;
    }


  }}
