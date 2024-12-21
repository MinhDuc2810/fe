package com.example.fitnessgym.User;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    @SerializedName("userName")
    private String name;

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;
    // Giá trị mặc định là "user"

    // Constructor, getter và setter
    public RegisterRequest(String name, String phoneNumber ,String email, String password) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    // Getter và Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}



