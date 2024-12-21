package com.example.fitnessgym;

public class Sessions {
    int request_id;
    String date;
    String time;
    int user_id;
    String userName;
    String phoneNumber;

    public Sessions(int request_id, String date, String time, int user_id, String userName, String phoneNumber) {
        this.request_id = request_id;
        this.date = date;
        this.time = time;
        this.user_id = user_id;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
