package com.example.fitnessgym.Membership;

import java.io.Serializable;

public class Membership implements Serializable {
    int id;
    String membershipStart;
    String membershipEnd ;
    String userName;

    public Membership(int id, String membershipStart, String membershipEnd, String userName) {
        this.id = id;
        this.membershipStart = membershipStart;
        this.membershipEnd = membershipEnd;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMembershipStart() {
        return membershipStart;
    }

    public void setMembershipStart(String membershipStart) {
        this.membershipStart = membershipStart;
    }

    public String getMembershipEnd() {
        return membershipEnd;
    }

    public void setMembershipEnd(String membershipEnd) {
        this.membershipEnd = membershipEnd;
    }
}
