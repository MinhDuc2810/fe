package com.example.fitnessgym.PTrequest;

public class SentRequest {
    int user_id;
    int pt_id;
    String date;
    int slot_id;

    public SentRequest(int user_id, int pt_id, String date, int slot_id) {
        this.user_id = user_id;
        this.pt_id = pt_id;
        this.date = date;
        this.slot_id = slot_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPt_id() {
        return pt_id;
    }

    public void setPt_id(int pt_id) {
        this.pt_id = pt_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSlot_id() {
        return slot_id;
    }

    public void setSlot_id(int slot_id) {
        this.slot_id = slot_id;
    }
}
