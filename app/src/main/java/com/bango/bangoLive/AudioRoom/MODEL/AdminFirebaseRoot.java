package com.bango.bangoLive.AudioRoom.MODEL;

public class AdminFirebaseRoot {
    String adminStatus,adminName,adminImg,adminId;

    public AdminFirebaseRoot() {
    }

    public AdminFirebaseRoot(String adminStatus, String adminName, String adminImg, String adminId) {
        this.adminStatus = adminStatus;
        this.adminName = adminName;
        this.adminImg = adminImg;
        this.adminId = adminId;
    }

    public String getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(String adminStatus) {
        this.adminStatus = adminStatus;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminImg() {
        return adminImg;
    }

    public void setAdminImg(String adminImg) {
        this.adminImg = adminImg;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
}
