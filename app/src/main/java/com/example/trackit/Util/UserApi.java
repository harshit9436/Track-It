package com.example.trackit.Util;

import android.app.Application;

public class UserApi extends Application {
    String username;
    String userID;
    private static UserApi userApi;

    public UserApi(String username, String userID) {
        this.username = username;
        this.userID = userID;
    }

    public UserApi() {
    }

    public static UserApi getInstance(){
        if (userApi == null){
            userApi = new UserApi();
        }
        return userApi;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
