package com.example.fitnessgym;

public class Password {

    String currentPassword;
    String newPassword;

    public Password( String currentPassword ,String newPassword ) {

        this.newPassword = newPassword;
        this.currentPassword = currentPassword;
    }



    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
}
