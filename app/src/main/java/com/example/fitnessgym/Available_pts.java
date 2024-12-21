package com.example.fitnessgym;

import java.io.Serializable;

public class Available_pts implements Serializable {
    String ptName;
    String email;
    String phoneNumber;
    int sessionsLeft;
    int ptId;

    public Available_pts(String ptName, String email, String phoneNumber, int sessionsLeft, int ptId) {
        this.ptName = ptName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.sessionsLeft = sessionsLeft;
        this.ptId = ptId;
    }

    public String getPtName() {
        return ptName;
    }

    public void setPtName(String ptName) {
        this.ptName = ptName;
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

    public int getSessionsLeft() {
        return sessionsLeft;
    }

    public void setSessionsLeft(int sessionsLeft) {
        this.sessionsLeft = sessionsLeft;
    }

    public int getPtId() {
        return ptId;
    }

    public void setPtId(int ptId) {
        this.ptId = ptId;
    }
}
