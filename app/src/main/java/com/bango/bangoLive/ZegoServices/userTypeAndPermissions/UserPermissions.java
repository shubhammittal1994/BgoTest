package com.bango.bangoLive.ZegoServices.userTypeAndPermissions;

public class UserPermissions {

    public UserPermissions() {
    }

    public UserPermissions(boolean canAnyOneMuteMe, boolean canAnyOneKickOutMe) {
        this.canAnyOneMuteMe = canAnyOneMuteMe;
        this.canAnyOneKickOutMe = canAnyOneKickOutMe;
    }

    public boolean canAnyOneMuteMe = false;
    public boolean canAnyOneKickOutMe = false;

    public boolean isCanAnyOneMuteMe() {
        return canAnyOneMuteMe;
    }

    public void setCanAnyOneMuteMe(boolean canAnyOneMuteMe) {
        this.canAnyOneMuteMe = canAnyOneMuteMe;
    }

    public boolean isCanAnyOneKickOutMe() {
        return canAnyOneKickOutMe;
    }

    public void setCanAnyOneKickOutMe(boolean canAnyOneKickOutMe) {
        this.canAnyOneKickOutMe = canAnyOneKickOutMe;
    }
}
