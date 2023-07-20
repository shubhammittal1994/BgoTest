package com.bango.bangoLive.fragments.Search.modelClass;

import java.io.Serializable;

public class FollowingRoot implements Serializable {
    public String success;
    public String message;
    public boolean follow_status;;

    public String following_count;
    public Object followingUser_count;
    public String getSuccess() {
        return success;
    }

    public boolean isFollow_status() {
        return follow_status;
    }

    public void setFollow_status(boolean follow_status) {
        this.follow_status = follow_status;
    }

    public Object getFollowingUser_count() {
        return followingUser_count;
    }

    public void setFollowingUser_count(Object followingUser_count) {
        this.followingUser_count = followingUser_count;
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


    public String getFollowing_count() {
        return following_count;
    }

    public void setFollowing_count(String following_count) {
        this.following_count = following_count;
    }
}
