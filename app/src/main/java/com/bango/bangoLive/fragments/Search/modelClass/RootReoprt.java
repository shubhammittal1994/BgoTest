package com.bango.bangoLive.fragments.Search.modelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RootReoprt {

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
        @SerializedName("reportAction")
        @Expose
        private String reportAction;

        @SerializedName("userReportActionId")
        @Expose
        private String userReportActionId;
        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("otherUserId")
        @Expose
        private String otherUserId;

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getReportAction() {
            return reportAction;
        }

        public void setReportAction(String reportAction) {
            this.reportAction = reportAction;
        }
    }
}