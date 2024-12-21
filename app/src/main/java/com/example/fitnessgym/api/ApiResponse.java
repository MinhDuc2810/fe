package com.example.fitnessgym.api;

import com.example.fitnessgym.User.Userinfo;

public class ApiResponse {
    private String status;
    private String token;// Thay vì boolean, ta dùng String cho trạng thái
    private String message;
    private Userinfo user;


    public Userinfo getUser() {
        return user;
    }

    public void setUser(Userinfo user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // Constructor, Getter, Setter
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Phương thức giúp kiểm tra trạng thái thành công
    public boolean isSuccess() {
        return "success".equalsIgnoreCase(status); // Kiểm tra nếu status = "success"
    }
}
