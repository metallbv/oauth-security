package com.client.authorizationcode.user;

import java.util.List;

public class ListUsersProfile {

    public List<UserProfile> getUserProfileList() {
        return userProfileList;
    }

    public void setUserProfileList(List<UserProfile> userProfileList) {
        this.userProfileList = userProfileList;
    }

    private List<UserProfile> userProfileList;
}
