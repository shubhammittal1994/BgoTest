package com.bango.bangoLive.ZegoServices.userTypeAndPermissions;

import com.bango.bangoLive.ZegoServices.CurrentUserType;

public class User {
    public UserPermissions userPermissions;
    CurrentUserType currentUserType;

    public UserPermissions getUserPermissions() {
        return userPermissions;
    }

    public void setUserPermissions(UserPermissions userPermissions) {
        this.userPermissions = userPermissions;
    }

    public CurrentUserType getCurrentUserType() {
        return currentUserType;
    }

    public void setCurrentUserType(CurrentUserType currentUserType) {
        this.currentUserType = currentUserType;
    }

    public User(CurrentUserType currentUserType) {
        this.currentUserType = currentUserType;
        switch (currentUserType) {
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
