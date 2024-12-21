package com.example.fitnessgym;

public class OTP {
    String otp;
    String email;

    public OTP(String otp, String email) {
        this.otp = otp;
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
