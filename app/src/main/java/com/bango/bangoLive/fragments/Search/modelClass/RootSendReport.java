package com.bango.bangoLive.fragments.Search.modelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RootSendReport {

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
        @SerializedName("userReportActionId")
        @Expose
        private String userReportActionId;
        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("otherUserId")
        @Expose
        private String otherUserId;
        @SerializedName("created")
        @Expose
        private String created;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserReportActionId() {
            return userReportActionId;
        }

        public void setUserReportActionId(String userReportActionId) {
            this.userReportActionId = userReportActionId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getOtherUserId() {
            return otherUserId;
        }

        public void setOtherUserId(String otherUserId) {
            this.otherUserId = otherUserId;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }
    }
}