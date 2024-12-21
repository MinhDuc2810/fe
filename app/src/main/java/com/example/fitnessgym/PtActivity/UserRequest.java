package com.example.fitnessgym.PtActivity;

public class UserRequest {
    String userName;
    int user_id;
    String date;

    public UserRequest(String userName, int user_id, String date) {
        this.userName = userName;
        this.user_id = user_id;
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
