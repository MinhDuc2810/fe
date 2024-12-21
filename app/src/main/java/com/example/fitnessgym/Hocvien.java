package com.example.fitnessgym;

public class Hocvien {
    String userName;
    int userId;
    String phoneNumber;
    int sessionsLeft;

    public Hocvien(String userName, int userId, String phoneNumber, int sessionsLeft) {
        this.userName = userName;
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.sessionsLeft = sessionsLeft;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getSessionsLeft() {
        return sessionsLeft;
    }

    public void setSessionsLeft(int sessionsLeft) {
        this.sessionsLeft = sessionsLeft;
    }
}
