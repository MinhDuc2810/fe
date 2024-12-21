package com.example.fitnessgym.User;

import java.io.Serializable;

public class Ptinfo implements Serializable {
    private int id;
    private String userName;
    private String email;
    private String phoneNumber;
    private String avatar;
    private String role ;


    public Ptinfo(int userid, String userName, String email, String phoneNumber, String avatar, String role) {
        this.id = userid;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
