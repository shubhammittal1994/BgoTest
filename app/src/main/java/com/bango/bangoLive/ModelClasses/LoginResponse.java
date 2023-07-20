package com.bango.bangoLive.ModelClasses;

import java.io.Serializable;

public class LoginResponse implements Serializable {

    public String success;
    public String message;
    public LoginDetails details;

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

    public LoginDetails getDetails() {
        return details;
    }

    public void setDetails(LoginDetails details) {
        this.details = details;
    }

}
