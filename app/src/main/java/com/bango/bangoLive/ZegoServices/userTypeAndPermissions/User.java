package com.bango.bangoLive.ZegoServices.userTypeAndPermissions;

import com.bango.bangoLive.ZegoServices.ZegoSdkEnums.CurrentUserTypeEnums;

public class User {
    public UserPermissions userPermissions;
    CurrentUserTypeEnums currentUserTypeEnums;

    public UserPermissions getUserPermissions() {
        return userPermissions;
    }

    public void setUserPermissions(UserPermissions userPermissions) {
        this.userPermissions = userPermissions;
    }

    public CurrentUserTypeEnums getCurrentUserType() {
        return currentUserTypeEnums;
    }

    public void setCurrentUserType(CurrentUserTypeEnums currentUserTypeEnums) {
        this.currentUserTypeEnums = currentUserTypeEnums;
    }

    public User(CurrentUserTypeEnums currentUserTypeEnums) {
        this.currentUserTypeEnums = currentUserTypeEnums;
        switch (currentUserTypeEnums) {
            case AUDIENCE: {
              this.userPermissions= new UserPermissions(true,false);
                break;
            }

            case HOST: {
                this.userPermissions= new UserPermissions(false,false);
                break;
            }

            case VIP1: {
                this.userPermissions= new UserPermissions(true,true);
                break;
            }

            default: {
                this.userPermissions= new UserPermissions(false,false);
            }


        }
    }


}
